package org.share.odies.annotation;

import java.lang.annotation.*;


/**
 * @author Alexander Lo
 * @version V1.0, 2020-06-30
 * @code Marker 在实体上做zset
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RoSortedSet {

    String prefix();

    String score() default "";
}
