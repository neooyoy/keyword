package com.cj.annotation;

import com.cj.crmenum.ModifyActionEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 业务日志处理自定义注解
 * @author zhaolm
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BusinessLog {
    ModifyActionEnum value();
}
