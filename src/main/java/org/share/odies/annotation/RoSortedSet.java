package org.share.odies.annotation;

import java.lang.annotation.*;


/**
 * @version V1.0, 2020-06-30
 * @author Alexander Lo
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
