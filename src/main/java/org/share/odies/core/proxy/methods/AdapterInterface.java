package org.share.odies.core.proxy.methods;

import com.alibaba.fastjson.JSON;
import org.share.odies.api.JedisTemplate;
import org.share.odies.core.proxy.Invocation;
import org.share.odies.core.proxy.Invoker;
import org.share.odies.vo.RoEntityMeta;
import redis.clients.util.SafeEncoder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

abstract class AdapterInterface implements Invoker {

    JedisTemplate jedisTemplate;
    Invocation invocation;



    public AdapterInterface(JedisTemplate jedisTemplate){
        this.jedisTemplate = jedisTemplate;
    }


    @Override
    public Object invoke(Invocation invocation) throws Throwable{
        logger.info( "[suppot-jedis]: invoke:"+invocation.getMethodName()+"---" + JSON.toJSONString(invocation));
        this.invocation = invocation;
        return null;
    }





    Object map2Obj(Map<byte[],byte[]> byteMap){
        Map shash = byteMap.entrySet().stream()
                .collect(Collectors.toMap(k-> SafeEncoder.encode(k.getKey()), v->SafeEncoder.encode(v.getValue())));
        String json = JSON.toJSONString(shash);

        return JSON.parseObject(json,getEntityClass());


    }

    /********************************************************************************************************************
     *  RoEntityMeta
    ********************************************************************************************************************/
    RoEntityMeta getRoEntityMeta(){ return (RoEntityMeta) invocation.getAttachment("Ro"); }
    String getPrefix() { return getRoEntityMeta().getPrefix();}
    Class getEntityClass(){return getRoEntityMeta().getEntityClass();}
    String getIdSortedSetKey() {return getPrefix() + ":" + "list";}
    String getIdFrom(Object object) throws IllegalAccessException {
        return getRoEntityMeta().getId().get(object).toString();
    }
    List<String>  getAllSortedKeys(){return null;}


    /********************************************************************************************************************
     *  Method Info
    ********************************************************************************************************************/
    String getMethodName(){ return  invocation.getMethodName();}
    int getArgumentsSize(){ return invocation.getArguments().length;}
    boolean hasArguments(){ return getArgumentsSize() > 0;}
    Object[] getArguments(){ return invocation.getArguments();}
    Class[] getArgumentsType(){return invocation.getParameterTypes();}









}
