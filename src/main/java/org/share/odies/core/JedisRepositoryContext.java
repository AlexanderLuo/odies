package org.share.odies.core;

import com.alibaba.fastjson.JSON;
import org.share.odies.Constants;
import org.share.odies.api.JedisTemplate;
import org.share.odies.core.proxy.Invocation;
import org.share.odies.core.proxy.Invoker;
import org.share.odies.core.proxy.jdk.MapperProxyFactory;
import org.share.odies.core.proxy.methods.*;
import org.share.odies.vo.RoEntityMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class JedisRepositoryContext<T>  {


    private Logger logger = LoggerFactory.getLogger(JedisRepositoryContext.class);
    private JedisTemplate jedisTemplate;

    public static  Map<String, RoEntityMeta> roEntityMetaMapCache =  new ConcurrentHashMap<>();
    public static Map<String, Invoker>  methodInvokerMap = new ConcurrentHashMap<>();;

    public JedisRepositoryContext(JedisTemplate jedisTemplate){
        this.jedisTemplate = jedisTemplate;
        this.buildMethods();
    }



    private void buildMethods(){
        methodInvokerMap = new HashMap<>();

        FetchMethod fetchMethod = new FetchMethod(jedisTemplate);
        SaveMethod saveMethod = new SaveMethod(jedisTemplate);
        LockMethod lockMethod = new LockMethod(jedisTemplate);
        MetricsMethod metricsMethod = new MetricsMethod(jedisTemplate);
        DeleteMethod deleteMethod = new DeleteMethod(jedisTemplate);


        for (String savemethod : Constants.SAVEMETHODS) {
            methodInvokerMap.put(savemethod,saveMethod);
        }

        for (String fentchmenthod : Constants.FETCHMETHODS) {
            methodInvokerMap.put(fentchmenthod,fetchMethod);
        }

        for (String lockmethod : Constants.LOCKMETHODS) {
            methodInvokerMap.put(lockmethod,lockMethod);
        }


        for (String name : Constants.METRICSMETHODS) {
            methodInvokerMap.put(name,metricsMethod);
        }

        for (String name : Constants.DELETEMETHODS) {
            methodInvokerMap.put(name,deleteMethod);
        }




        logger.info("[suppot-jedis]:methodInvokerMap "+JSON.toJSONString(methodInvokerMap));

    }






    public T getService(Class<T> mapperInterface){
        return new MapperProxyFactory<>(mapperInterface,this).newInstance();
    }




    public Object invoke(Invocation invocation) throws Throwable {
        return methodInvokerMap.get(invocation.getMethodName()).invoke(invocation);

    }








}
