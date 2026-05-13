package com.project.cloud.generator.service.impl;

import com.project.cloud.generator.domain.entity.GenConfig;
import com.project.cloud.generator.service.GenCodeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class GenCodeServiceImpl implements GenCodeService {

    private final VelocityEngine velocityEngine;

    public GenCodeServiceImpl() {
        velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(VelocityEngine.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class",
                org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader.class.getName());
        velocityEngine.init();
    }

    @Override
    public Map<String, String> generateCode(GenConfig config) {
        Map<String, String> result = new HashMap<>();

        VelocityContext context = buildContext(config);

        String entityName = toPascalCase(config.getTableName());
        String entityNameUncap = toCamelCase(config.getTableName());
        String moduleName = config.getModuleName();
        String packageName = config.getPackageName();

        context.put("entityName", entityName);
        context.put("entityNameUncap", entityNameUncap);
        context.put("moduleName", moduleName);
        context.put("packageName", packageName);
        context.put("author", config.getAuthor());
        context.put("tableComment", config.getTableComment());

        String basePath = packageName.replace('.', '/') + "/" + moduleName;

        result.put(basePath + "/domain/entity/" + entityName + ".java",
                generate("templates/Entity.java.vm", context));
        result.put(basePath + "/domain/vo/" + entityName + "VO.java",
                generate("templates/VO.java.vm", context));
        result.put(basePath + "/domain/dto/" + entityName + "DTO.java",
                generate("templates/DTO.java.vm", context));
        result.put(basePath + "/domain/query/" + entityName + "Query.java",
                generate("templates/Query.java.vm", context));
        result.put(basePath + "/mapper/" + entityName + "Mapper.java",
                generate("templates/Mapper.java.vm", context));
        result.put(basePath + "/service/I" + entityName + "Service.java",
                generate("templates/Service.java.vm", context));
        result.put(basePath + "/service/impl/" + entityName + "ServiceImpl.java",
                generate("templates/ServiceImpl.java.vm", context));
        result.put(basePath + "/controller/" + entityName + "Controller.java",
                generate("templates/Controller.java.vm", context));

        // Vue files
        result.put("views/" + moduleName + "/" + entityNameUncap + "/index.vue",
                generate("templates/VuePage.vue.vm", context));
        result.put("api/" + moduleName + "/" + entityNameUncap + ".ts",
                generate("templates/VueApi.ts.vm", context));

        return result;
    }

    private VelocityContext buildContext(GenConfig config) {
        VelocityContext context = new VelocityContext();
        context.put("config", config);
        return context;
    }

    private String generate(String templatePath, VelocityContext context) {
        try {
            Template template = velocityEngine.getTemplate(templatePath, "UTF-8");
            StringWriter writer = new StringWriter();
            template.merge(context, writer);
            return writer.toString();
        } catch (Exception e) {
            log.error("模板生成失败: {}", templatePath, e);
            return "";
        }
    }

    private String toPascalCase(String tableName) {
        StringBuilder sb = new StringBuilder();
        boolean nextUpper = true;
        for (char c : tableName.toCharArray()) {
            if (c == '_' || c == '-') {
                nextUpper = true;
            } else if (nextUpper) {
                sb.append(Character.toUpperCase(c));
                nextUpper = false;
            } else {
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }

    private String toCamelCase(String tableName) {
        String pascal = toPascalCase(tableName);
        return Character.toLowerCase(pascal.charAt(0)) + pascal.substring(1);
    }
}
