package com.project.cloud.common.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.project.cloud.common.security.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class AutoFillHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "deleted", Integer.class, 0);
        this.strictInsertFill(metaObject, "version", Integer.class, 0);

        String username = getCurrentUsername();
        this.strictInsertFill(metaObject, "createBy", String.class, username);
        this.strictInsertFill(metaObject, "updateBy", String.class, username);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());

        String username = getCurrentUsername();
        this.strictUpdateFill(metaObject, "updateBy", String.class, username);
    }

    private String getCurrentUsername() {
        try {
            return SecurityUtils.getUsername();
        } catch (Exception e) {
            log.debug("获取当前用户名失败", e);
        }
        return "system";
    }
}