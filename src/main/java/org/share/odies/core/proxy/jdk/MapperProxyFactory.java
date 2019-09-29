package org.share.odies.core.proxy.jdk;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.share.odies.core.JedisRepositoryContext;
import org.share.odies.core.proxy.JedisInvocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MapperProxyFactory<T>  implements InvocationHandler {

    private final Class<T> mapperInterface;
    private JedisRepositoryContext jedisRepositoryContext;
    private Log logger = LogFactory.getLog(this.getClass());



    public MapperProxyFactory(Class<T> mapperInterface, JedisRepositoryContext jedisRepositoryContext) {
        logger.info("[suppot-jedis]:repository creating "+mapperInterface.getName());
        this.mapperInterface = mapperInterface;
        this.jedisRepositoryContext = jedisRepositoryContext;
    }




    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        JedisInvocation invocation = new JedisInvocation();
        invocation.setMethodName(method.getName());
        invocation.setArguments(args);
        invocation.setAttachments("mapperInterface",this.mapperInterface.getName());
        invocation.setAttachments("Ro", JedisRepositoryContext.roEntityMetaMapCache.get(this.mapperInterface.getName()));
        return jedisRepositoryContext.invoke(invocation);
    }



    @SuppressWarnings("unchecked")
    public T newInstance() {
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[] { mapperInterface }, this);
    }

}
