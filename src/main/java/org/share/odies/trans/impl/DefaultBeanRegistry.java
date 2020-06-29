package org.share.odies.trans.impl;

import org.share.odies.trans.BeanRegistry;
import org.share.odies.trans.ConvertorRegistry;
import org.share.odies.trans.PropertyDescriptorExtends;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Auth: Alexander Lo
 * Date: 2020-06-29
 * Description: bean属性仓库实现
 */
public class DefaultBeanRegistry implements BeanRegistry {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Map<Class,List<PropertyDescriptorExtends>> properties = new HashMap<>(16);



    @Override
    public List<PropertyDescriptorExtends> findProperties(Class beanClazz) {
        List<PropertyDescriptorExtends> result = properties.get(beanClazz);
        if(result==null){
            synchronized (beanClazz){
                if(properties.get(beanClazz) == null){
                    register(beanClazz);
                    result = properties.get(beanClazz);
                }
            }
        }
        return result;
    }

    @Override
    public <T> T newBeanInstance(Class<T> beanClazz) {
        return null;
    }

    @Override
    public void register(Class beanClazz) {
        List<PropertyDescriptorExtends> result = new ArrayList<>();
        try {
            BeanInfo bi = Introspector.getBeanInfo(beanClazz, Object.class);
            PropertyDescriptor[] pds = bi.getPropertyDescriptors();
            for (PropertyDescriptor pd : pds) {
                if(getConvertorRegistry().isSupportedPropertyType(pd.getPropertyType())){
                    result.add(new PropertyDescriptorExtends(pd,beanClazz));
                }
                else{
                    logger.warn("ro[clazz={}] prop[name={},type={}] is unsupported", beanClazz,pd.getName(),pd.getPropertyType());
                }
            }
        } catch (IntrospectionException e) {

        }
        properties.put(beanClazz, result);
    }

    @Override
    public ConvertorRegistry getConvertorRegistry() {
        return null;
    }
}
