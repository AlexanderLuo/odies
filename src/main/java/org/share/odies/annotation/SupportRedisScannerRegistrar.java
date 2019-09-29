package org.share.odies.annotation;

import org.share.odies.annotation.support.SupportRedisScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

public class SupportRedisScannerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, BeanFactoryAware {
    private BeanFactory beanFactory;
    private ResourceLoader resourceLoader;
    private Logger logger = LoggerFactory.getLogger(SupportRedisScannerRegistrar.class);


    public SupportRedisScannerRegistrar() {
        logger.info("[suppot-ordies]:doScan");
    }



    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        SupportRedisScanner scanner = new SupportRedisScanner(registry);

        try {
            if (this.resourceLoader != null)
                scanner.setResourceLoader(this.resourceLoader);


            List<String> packages = AutoConfigurationPackages.get(this.beanFactory);
            scanner.setAnnotationClass(Repository.class);
            scanner.registerFilters();
            scanner.doScan(StringUtils.toStringArray(packages));

        } catch (IllegalStateException ex) {
            this.logger.error("Could not determine auto-configuration package, automatic  scanning disabled.");
        }

    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
