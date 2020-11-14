package org.share.odies.trans;


import java.util.List;

/**
 * Bean 属性仓库
 */
public interface BeanRegistry {


    /**
     * 根据对象获取其属性描述符扩展,首先从缓存中查找,如果没有找到,则注册一次并缓存,然后获取
     */
    List<PropertyDescriptorExtends> findProperties(Class beanClazz);

    /**
     * 反射创建一个对象
     */
    <T> T newBeanInstance(Class<T> beanClazz);

    /**
     * 注册对象 使其属性描述符得到缓存
     *
     * @param beanClazz
     */
    void register(Class beanClazz);


    /**
     * 属性填装器 确定支持的属性
     */
    ConvertorRegistry getConvertorRegistry();

}
