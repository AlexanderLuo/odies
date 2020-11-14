package org.share.odies.annotation;

import java.lang.annotation.*;


/**
 * @author Alexander Lo
 * @version V1.0, 2020-06-30
 * @code 打在方法上 计算分值
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface MethodSortedSet {

    String prefix() default "";

    String key();

    String score() default "";
}