package org.share.odies.annotation.support;

import org.share.odies.api.OdiesRepository;
import org.share.odies.core.JedisRepositoryContext;
import org.share.odies.core.MapperFactoryBean;
import org.share.odies.vo.RoEntityMeta;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
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



    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);



        if (beanDefinitions.isEmpty())
            this.logger.warn("No Odies Ops was found in '" + Arrays.toString(basePackages) + "' package. Please check your configuration.");


        this.processBeanDefinitions(beanDefinitions);

        return beanDefinitions;
    }


    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {

        beanDefinitions.forEach(x->{
            GenericBeanDefinition definition = (GenericBeanDefinition)x.getBeanDefinition();

//            addGenericArgumentValue  first  or got error
            definition.getConstructorArgumentValues().addGenericArgumentValue(definition.getBeanClassName());
            definition.setBeanClass(MapperFactoryBean.class);
//            todo 更新操作api
            definition.getPropertyValues().add("jedisRepositoryContext",new RuntimeBeanReference("jedisRepositoryContext"));
            definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);


        });




    }

    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {

        if (!beanDefinition.getMetadata().isInterface()
                || beanDefinition.getMetadata().getInterfaceNames().length == 0
                || !Arrays.asList(beanDefinition.getMetadata().getInterfaceNames()).contains(OdiesRepository.class.getName())
        )
            return false;

        try {
            Class clazz = Class.forName(beanDefinition.getMetadata().getClassName());
            ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericInterfaces()[0];
            Class entityClass = (Class) parameterizedType.getActualTypeArguments()[0];

            logger.info("[suppot-odies]:RoEntity " + entityClass.getName());

            RoEntityMeta roEntityMeta = RoEntityMeta.fromClass(entityClass);
            JedisRepositoryContext.roEntityMetaMapCache.put(clazz.getName(),roEntityMeta);

            return true;


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Class<? extends Annotation> getAnnotationClass() {
        return annotationClass;
    }

    public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }
}
