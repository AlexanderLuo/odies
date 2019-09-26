package org.share.odies.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.share.odies.core.JedisRepositoryContext;
import org.springframework.beans.factory.FactoryBean;

public class MapperFactoryBean<T> implements FactoryBean<T> {
    protected final Log logger = LogFactory.getLog(this.getClass());

    private Class<T> mapperInterface;

    private JedisRepositoryContext<T> jedisRepositoryContext;


    public MapperFactoryBean(){ }

    public MapperFactoryBean(Class<T> mapperInterface){
        this.mapperInterface = mapperInterface;}


    @Override
    public T getObject() throws Exception {
        return jedisRepositoryContext.getService(this.mapperInterface);
    }

    @Override
    public Class<T> getObjectType() {
        return this.mapperInterface;
    }


    public Class<T> getMapperInterface() {
        return mapperInterface;
    }

    public void setMapperInterface(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    public JedisRepositoryContext<T> getJedisRepositoryContext() {
        return jedisRepositoryContext;
    }

    public void setJedisRepositoryContext(JedisRepositoryContext<T> jedisRepositoryContext) {
        this.jedisRepositoryContext = jedisRepositoryContext;
    }
}
