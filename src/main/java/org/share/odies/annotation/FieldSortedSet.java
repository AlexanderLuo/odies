package org.share.odies.annotation;

import java.lang.annotation.*;


/**
 * @author Alexander Lo
 * @version V1.0, 2020-06-30
 * @code
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface FieldSortedSet {

    String prefix() default "";

    String key();

    String score() default "";
}