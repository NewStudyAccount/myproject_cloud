package com.project.cloud.generator.service.impl;

import com.project.cloud.generator.domain.entity.GenConfig;
import com.project.cloud.generator.service.ICodeGeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 代码生成服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CodeGeneratorServiceImpl implements ICodeGeneratorService {

    @Override
    public Map<String, String> generate(GenConfig config) {
        Map<String, String> result = new HashMap<>();

        // 准备模板上下文
        VelocityContext context = createContext(config);

        // 生成各层代码
        result.put("Entity.java", generateFromTemplate("java/Entity.java.vm", context));
        result.put("VO.java", generateFromTemplate("java/VO.java.vm", context));
        result.put("DTO.java", generateFromTemplate("java/DTO.java.vm", context));
        result.put("Query.java", generateFromTemplate("java/Query.java.vm", context));
        result.put("Mapper.java", generateFromTemplate("java/Mapper.java.vm", context));
        result.put("Service.java", generateFromTemplate("java/Service.java.vm", context));
        result.put("ServiceImpl.java", generateFromTemplate("java/ServiceImpl.java.vm", context));
        result.put("Controller.java", generateFromTemplate("java/Controller.java.vm", context));
        result.put("index.vue", generateFromTemplate("vue/index.vue.vm", context));
        result.put("api.ts", generateFromTemplate("vue/api.ts.vm", context));

        return result;
    }

    @Override
    public Map<String, String> preview(GenConfig config) {
        return generate(config);
    }

    private VelocityContext createContext(GenConfig config) {
        VelocityContext context = new VelocityContext();

        // 基础信息
        context.put("tableName", config.getTableName());
        context.put("moduleName", config.getModuleName());
        context.put("packageName", config.getPackageName());
        context.put("entityName", config.getEntityName());
        context.put("author", config.getAuthor());

        // 首字母小写的实体名
        String entityNameUncap = config.getEntityName().substring(0, 1).toLowerCase() + config.getEntityName().substring(1);
        context.put("entityNameUncap", entityNameUncap);

        // 包路径
        context.put("packagePath", config.getPackageName().replace(".", "/"));

        // TODO: 从数据库获取表字段信息
        // context.put("columns", columns);

        return context;
    }

    private String generateFromTemplate(String templateName, VelocityContext context) {
        try {
            StringWriter writer = new StringWriter();
            Velocity.evaluate(context, writer, "codegen", getTemplate(templateName));
            return writer.toString();
        } catch (Exception e) {
            log.error("生成代码失败: {}", templateName, e);
            return "";
        }
    }

    private String getTemplate(String templateName) {
        // TODO: 从资源文件读取模板
        // 这里返回简单的模板示例
        return switch (templateName) {
            case "java/Entity.java.vm" -> getEntityTemplate();
            case "java/VO.java.vm" -> getVOTemplate();
            case "java/DTO.java.vm" -> getDTOTemplate();
            case "java/Query.java.vm" -> getQueryTemplate();
            case "java/Mapper.java.vm" -> getMapperTemplate();
            case "java/Service.java.vm" -> getServiceTemplate();
            case "java/ServiceImpl.java.vm" -> getServiceImplTemplate();
            case "java/Controller.java.vm" -> getControllerTemplate();
            case "vue/index.vue.vm" -> getVueIndexTemplate();
            case "vue/api.ts.vm" -> getVueApiTemplate();
            default -> "";
        };
    }

    private String getEntityTemplate() {
        return """
                package ${packageName}.domain.entity;

                import com.baomidou.mybatisplus.annotation.TableField;
                import com.baomidou.mybatisplus.annotation.TableName;
                import com.project.cloud.common.mybatis.base.BaseEntity;
                import lombok.Data;
                import lombok.EqualsAndHashCode;

                /**
                 * ${entityName} 实体
                 */
                @Data
                @EqualsAndHashCode(callSuper = true)
                @TableName("${tableName}")
                public class ${entityName} extends BaseEntity {

                    // TODO: 添加字段
                }
                """;
    }

    private String getVOTemplate() {
        return """
                package ${packageName}.domain.vo;

                import io.swagger.v3.oas.annotations.media.Schema;
                import lombok.Data;

                import java.io.Serializable;
                import java.time.LocalDateTime;

                /**
                 * ${entityName} VO
                 */
                @Data
                @Schema(description = "${entityName}信息")
                public class ${entityName}VO implements Serializable {

                    private static final long serialVersionUID = 1L;

                    @Schema(description = "ID")
                    private Long id;

                    // TODO: 添加字段

                    @Schema(description = "创建时间")
                    private LocalDateTime createTime;

                    @Schema(description = "更新时间")
                    private LocalDateTime updateTime;
                }
                """;
    }

    private String getDTOTemplate() {
        return """
                package ${packageName}.domain.dto;

                import io.swagger.v3.oas.annotations.media.Schema;
                import lombok.Data;

                import java.io.Serializable;

                /**
                 * ${entityName} DTO
                 */
                @Data
                @Schema(description = "${entityName}DTO")
                public class ${entityName}DTO implements Serializable {

                    private static final long serialVersionUID = 1L;

                    @Schema(description = "ID")
                    private Long id;

                    // TODO: 添加字段
                }
                """;
    }

    private String getQueryTemplate() {
        return """
                package ${packageName}.domain.query;

                import com.project.cloud.common.core.domain.model.BaseQuery;
                import io.swagger.v3.oas.annotations.media.Schema;
                import lombok.Data;
                import lombok.EqualsAndHashCode;

                /**
                 * ${entityName} 查询参数
                 */
                @Data
                @EqualsAndHashCode(callSuper = true)
                @Schema(description = "${entityName}查询参数")
                public class ${entityName}Query extends BaseQuery {

                    private static final long serialVersionUID = 1L;

                    // TODO: 添加查询字段
                }
                """;
    }

    private String getMapperTemplate() {
        return """
                package ${packageName}.mapper;

                import com.baomidou.mybatisplus.core.mapper.BaseMapper;
                import ${packageName}.domain.entity.${entityName};
                import org.apache.ibatis.annotations.Mapper;

                /**
                 * ${entityName} Mapper
                 */
                @Mapper
                public interface ${entityName}Mapper extends BaseMapper<${entityName}> {
                }
                """;
    }

    private String getServiceTemplate() {
        return """
                package ${packageName}.service;

                import com.baomidou.mybatisplus.extension.service.IService;
                import com.project.cloud.common.core.result.PageResult;
                import ${packageName}.domain.dto.${entityName}DTO;
                import ${packageName}.domain.entity.${entityName};
                import ${packageName}.domain.query.${entityName}Query;
                import ${packageName}.domain.vo.${entityName}VO;

                import java.util.List;

                /**
                 * ${entityName} 服务接口
                 */
                public interface I${entityName}Service extends IService<${entityName}> {

                    /**
                     * 查询详情
                     */
                    ${entityName}VO detail(${entityName}Query query);

                    /**
                     * 查询列表（分页）
                     */
                    PageResult<${entityName}VO> list(${entityName}Query query);

                    /**
                     * 新增
                     */
                    void add(${entityName}DTO dto);

                    /**
                     * 更新
                     */
                    void update(${entityName}DTO dto);

                    /**
                     * 删除
                     */
                    void delete(List<Long> ids);
                }
                """;
    }

    private String getServiceImplTemplate() {
        return """
                package ${packageName}.service.impl;

                import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
                import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
                import com.project.cloud.common.core.exception.BusinessException;
                import com.project.cloud.common.core.result.PageResult;
                import com.project.cloud.common.mybatis.base.BaseService;
                import ${packageName}.domain.dto.${entityName}DTO;
                import ${packageName}.domain.entity.${entityName};
                import ${packageName}.domain.query.${entityName}Query;
                import ${packageName}.domain.vo.${entityName}VO;
                import ${packageName}.mapper.${entityName}Mapper;
                import ${packageName}.service.I${entityName}Service;
                import lombok.RequiredArgsConstructor;
                import lombok.extern.slf4j.Slf4j;
                import org.springframework.stereotype.Service;
                import org.springframework.transaction.annotation.Transactional;

                import java.util.List;
                import java.util.stream.Collectors;

                /**
                 * ${entityName} 服务实现
                 */
                @Slf4j
                @Service
                @RequiredArgsConstructor
                public class ${entityName}ServiceImpl extends BaseService<${entityName}Mapper, ${entityName}> implements I${entityName}Service {

                    @Override
                    public ${entityName}VO detail(${entityName}Query query) {
                        if (query.getId() == null) {
                            throw new BusinessException("ID不能为空");
                        }

                        ${entityName} entity = getById(query.getId());
                        if (entity == null) {
                            return null;
                        }

                        return convertToVO(entity);
                    }

                    @Override
                    public PageResult<${entityName}VO> list(${entityName}Query query) {
                        LambdaQueryWrapper<${entityName}> wrapper = new LambdaQueryWrapper<>();
                        // TODO: 添加查询条件
                        wrapper.orderByDesc(${entityName}::getCreateTime);

                        Page<${entityName}> page = new Page<>(query.getPageNum(), query.getPageSize());
                        Page<${entityName}> result = page(page, wrapper);

                        List<${entityName}VO> voList = result.getRecords().stream()
                                .map(this::convertToVO)
                                .collect(Collectors.toList());

                        return PageResult.of(result.getTotal(), voList);
                    }

                    @Override
                    @Transactional(rollbackFor = Exception.class)
                    public void add(${entityName}DTO dto) {
                        ${entityName} entity = new ${entityName}();
                        // TODO: 设置属性
                        save(entity);
                    }

                    @Override
                    @Transactional(rollbackFor = Exception.class)
                    public void update(${entityName}DTO dto) {
                        ${entityName} entity = getById(dto.getId());
                        if (entity == null) {
                            throw new BusinessException("数据不存在");
                        }
                        // TODO: 更新属性
                        updateById(entity);
                    }

                    @Override
                    @Transactional(rollbackFor = Exception.class)
                    public void delete(List<Long> ids) {
                        if (ids == null || ids.isEmpty()) {
                            throw new BusinessException("删除ID不能为空");
                        }
                        removeByIds(ids);
                    }

                    private ${entityName}VO convertToVO(${entityName} entity) {
                        ${entityName}VO vo = new ${entityName}VO();
                        vo.setId(entity.getId());
                        // TODO: 设置属性
                        vo.setCreateTime(entity.getCreateTime());
                        vo.setUpdateTime(entity.getUpdateTime());
                        return vo;
                    }
                }
                """;
    }

    private String getControllerTemplate() {
        return """
                package ${packageName}.controller;

                import com.project.cloud.common.core.enums.BusinessType;
                import com.project.cloud.common.core.result.PageResult;
                import com.project.cloud.common.core.result.Result;
                import com.project.cloud.common.log.annotation.OperLog;
                import ${packageName}.domain.dto.${entityName}DTO;
                import ${packageName}.domain.query.${entityName}Query;
                import ${packageName}.domain.vo.${entityName}VO;
                import ${packageName}.service.I${entityName}Service;
                import io.swagger.v3.oas.annotations.Operation;
                import io.swagger.v3.oas.annotations.tags.Tag;
                import jakarta.validation.Valid;
                import lombok.RequiredArgsConstructor;
                import org.springframework.web.bind.annotation.PostMapping;
                import org.springframework.web.bind.annotation.RequestBody;
                import org.springframework.web.bind.annotation.RequestMapping;
                import org.springframework.web.bind.annotation.RestController;

                import java.util.List;

                /**
                 * ${entityName} 控制器
                 */
                @Tag(name = "${entityName}管理")
                @RestController
                @RequestMapping("/${moduleName}/${entityNameUncap}")
                @RequiredArgsConstructor
                public class ${entityName}Controller {

                    private final I${entityName}Service ${entityNameUncap}Service;

                    @PostMapping("/detail")
                    @Operation(summary = "查询详情")
                    public Result<${entityName}VO> detail(@RequestBody ${entityName}Query query) {
                        return Result.success(${entityNameUncap}Service.detail(query));
                    }

                    @PostMapping("/list")
                    @Operation(summary = "查询列表")
                    public Result<PageResult<${entityName}VO>> list(@RequestBody ${entityName}Query query) {
                        return Result.success(${entityNameUncap}Service.list(query));
                    }

                    @PostMapping("/add")
                    @Operation(summary = "新增")
                    @OperLog(title = "${entityName}管理", businessType = BusinessType.INSERT)
                    public Result<Void> add(@RequestBody @Valid ${entityName}DTO dto) {
                        ${entityNameUncap}Service.add(dto);
                        return Result.success();
                    }

                    @PostMapping("/update")
                    @Operation(summary = "更新")
                    @OperLog(title = "${entityName}管理", businessType = BusinessType.UPDATE)
                    public Result<Void> update(@RequestBody @Valid ${entityName}DTO dto) {
                        ${entityNameUncap}Service.update(dto);
                        return Result.success();
                    }

                    @PostMapping("/delete")
                    @Operation(summary = "删除")
                    @OperLog(title = "${entityName}管理", businessType = BusinessType.DELETE)
                    public Result<Void> delete(@RequestBody List<Long> ids) {
                        ${entityNameUncap}Service.delete(ids);
                        return Result.success();
                    }
                }
                """;
    }

    private String getVueIndexTemplate() {
        return """
                <template>
                  <div class="app-container">
                    <!-- 搜索区域 -->
                    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch">
                      <el-form-item label="名称" prop="name">
                        <el-input v-model="queryParams.name" placeholder="请输入名称" clearable style="width: 200px" />
                      </el-form-item>
                      <el-form-item>
                        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
                        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
                      </el-form-item>
                    </el-form>

                    <!-- 操作按钮 -->
                    <el-row :gutter="10" class="mb8">
                      <el-col :span="1.5">
                        <el-button type="primary" plain icon="Plus" @click="handleAdd">新增</el-button>
                      </el-col>
                      <el-col :span="1.5">
                        <el-button type="danger" plain icon="Delete" :multiple="true" @click="handleDelete">删除</el-button>
                      </el-col>
                      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" />
                    </el-row>

                    <!-- 数据表格 -->
                    <el-table v-loading="loading" :data="dataList" @selection-change="handleSelectionChange">
                      <el-table-column type="selection" width="55" align="center" />
                      <el-table-column label="ID" align="center" prop="id" />
                      <!-- TODO: 添加其他列 -->
                      <el-table-column label="创建时间" align="center" prop="createTime" width="180" />
                      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="150">
                        <template #default="scope">
                          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)">修改</el-button>
                          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)">删除</el-button>
                        </template>
                      </el-table-column>
                    </el-table>

                    <!-- 分页 -->
                    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

                    <!-- 添加/修改对话框 -->
                    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
                      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
                        <!-- TODO: 添加表单项 -->
                      </el-form>
                      <template #footer>
                        <div class="dialog-footer">
                          <el-button type="primary" @click="submitForm">确 定</el-button>
                          <el-button @click="cancel">取 消</el-button>
                        </div>
                      </template>
                    </el-dialog>
                  </div>
                </template>

                <script setup lang="ts">
                import { ref, onMounted } from 'vue';
                import { ElMessageBox, ElMessage } from 'element-plus';
                import { list, detail, add, update, del } from './api';

                const loading = ref(false);
                const dataList = ref([]);
                const total = ref(0);
                const showSearch = ref(true);
                const open = ref(false);
                const title = ref('');
                const ids = ref([]);
                const single = ref(true);
                const multiple = ref(true);

                const queryParams = ref({
                  pageNum: 1,
                  pageSize: 10
                });

                const form = ref({});
                const rules = ref({});

                onMounted(() => {
                  getList();
                });

                const getList = async () => {
                  loading.value = true;
                  try {
                    const res = await list(queryParams.value);
                    dataList.value = res.rows;
                    total.value = res.total;
                  } finally {
                    loading.value = false;
                  }
                };

                const handleQuery = () => {
                  queryParams.value.pageNum = 1;
                  getList();
                };

                const resetQuery = () => {
                  queryParams.value = { pageNum: 1, pageSize: 10 };
                  handleQuery();
                };

                const handleSelectionChange = (selection: any[]) => {
                  ids.value = selection.map((item: any) => item.id);
                  single.value = selection.length !== 1;
                  multiple.value = !selection.length;
                };

                const handleAdd = () => {
                  reset();
                  open.value = true;
                  title.value = '添加';
                };

                const handleUpdate = async (row: any) => {
                  reset();
                  const res = await detail({ id: row.id });
                  form.value = res.data;
                  open.value = true;
                  title.value = '修改';
                };

                const submitForm = async () => {
                  try {
                    if (form.value.id) {
                      await update(form.value);
                    } else {
                      await add(form.value);
                    }
                    ElMessage.success('操作成功');
                    open.value = false;
                    getList();
                  } catch (error) {
                    console.error(error);
                  }
                };

                const handleDelete = async (row?: any) => {
                  const deleteIds = row?.id ? [row.id] : ids.value;
                  await ElMessageBox.confirm('是否确认删除选中的数据？', '警告', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                  });
                  await del(deleteIds);
                  ElMessage.success('删除成功');
                  getList();
                };

                const reset = () => {
                  form.value = {};
                };

                const cancel = () => {
                  open.value = false;
                  reset();
                };
                </script>
                """;
    }

    private String getVueApiTemplate() {
        return """
                import request from '@/utils/request';

                // 查询列表
                export function list(data: any) {
                  return request({
                    url: '/${moduleName}/${entityNameUncap}/list',
                    method: 'post',
                    data: data
                  });
                }

                // 查询详情
                export function detail(data: any) {
                  return request({
                    url: '/${moduleName}/${entityNameUncap}/detail',
                    method: 'post',
                    data: data
                  });
                }

                // 新增
                export function add(data: any) {
                  return request({
                    url: '/${moduleName}/${entityNameUncap}/add',
                    method: 'post',
                    data: data
                  });
                }

                // 更新
                export function update(data: any) {
                  return request({
                    url: '/${moduleName}/${entityNameUncap}/update',
                    method: 'post',
                    data: data
                  });
                }

                // 删除
                export function del(data: any) {
                  return request({
                    url: '/${moduleName}/${entityNameUncap}/delete',
                    method: 'post',
                    data: data
                  });
                }
                """;
    }
}
