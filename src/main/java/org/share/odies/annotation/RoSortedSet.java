package org.share.odies.annotation;

import java.lang.annotation.*;

/**
 * 
 * 类注解  获取类存取的key
 * 如果有此注解 则当前进行更新保存的对象，需序列当前对象id到当前key的value
 * @author jun.liu(by xiaoyu)
 * @date 2016年8月11日
 * @reviewer 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RoSortedSet {
	
	String key();

	String score() default "";
}
