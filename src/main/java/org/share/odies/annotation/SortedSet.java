package org.share.odies.annotation;


import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface SortedSet {


    String prefix();
    /**
     * @version V1.0, 2020-06-30
     * @author Alexander Lo
     * @code 指定分值计算项
     */
    String score() default "";

}
