package com.project.cloud.common.log.annotation;

import com.project.cloud.common.core.enums.BusinessType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperLog {

    String title() default "";

    BusinessType businessType() default BusinessType.OTHER;

    boolean isSaveRequestData() default true;

    boolean isSaveResponseData() default true;
}
