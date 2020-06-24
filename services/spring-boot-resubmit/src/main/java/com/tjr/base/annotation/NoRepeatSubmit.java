package com.tjr.base.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoRepeatSubmit {

    /**
     * 默认1s钟以内算重复提交
     * @return
     */
    long timeout() default 1;
}
