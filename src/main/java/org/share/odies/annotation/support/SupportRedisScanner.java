package org.share.odies.annotation.support;

import org.share.odies.Constants;
import org.share.odies.core.JedisRepositoryContext;
import org.share.odies.core.MapperFactoryBean;
import org.share.odies.vo.RoEntityMeta;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Set;

public class SupportRedisScanner extends ClassPathBeanDefinitionScanner {
    private Class<? extends Annotation> annotationClass;




    public SupportRedisScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }


    public void registerFilters(){
        if (this.annotationClass != null) {
            addIncludeFilter(new AnnotationTypeFilter(this.annotationClass));
        }
    }


    /**
     * 已经过了 isCandidateComponent 逻辑了
     * @param basePackages
     * @return
     */
    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        if (beanDefinitions.isEmpty()) {
            this.logger.warn("No MyBatis mapper was found in '" + Arrays.toString(basePackages) + "' package. Please check your configuration.");
        } else {
            this.processBeanDefinitions(beanDefinitions);
        }

        return beanDefinitions;
    }


    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {

        beanDefinitions.forEach(x->{

            GenericBeanDefinition definition = (GenericBeanDefinition)x.getBeanDefinition();
            definition.getConstructorArgumentValues().addGenericArgumentValue(definition.getBeanClassName());
            definition.setBeanClass(MapperFactoryBean.class);
            definition.getPropertyValues().add("jedisRepositoryContext",new RuntimeBeanReference("jedisRepositoryContext"));
            definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);


        });




    }

    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {


        if (!beanDefinition.getMetadata().isInterface()
                || beanDefinition.getMetadata().getInterfaceNames().length == 0
                || !Arrays.asList(beanDefinition.getMetadata().getInterfaceNames()).contains(Constants.API)
        )
            return false;

        try {
            Class clazz = Class.forName(beanDefinition.getMetadata().getClassName());
            ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericInterfaces()[0];
            Class entityClass = (Class) parameterizedType.getActualTypeArguments()[0];

            logger.info("[suppot-jedis]:RoEntity " + entityClass.getName());


            RoEntityMeta roEntityMeta = RoEntityMeta.fromClass(entityClass);
            JedisRepositoryContext.roEntityMetaMapCache.put(clazz.getName(),roEntityMeta);

            return true;


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    protected boolean checkCandidate(String beanName, BeanDefinition beanDefinition) {
        if (super.checkCandidate(beanName, beanDefinition)) {
            return true;
        } else {
            this.logger.warn("Skipping MapperFactoryBean with name '" + beanName + "' and '" + beanDefinition.getBeanClassName() + "' mapperInterface. Bean already defined with the same name!");
            return false;
        }
    }



    public Class<? extends Annotation> getAnnotationClass() {
        return annotationClass;
    }

    public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }
}
