package org.share.odies.core.proxy.methods;

import org.share.odies.api.JedisTemplate;
import org.share.odies.core.proxy.Invocation;
import org.share.odies.vo.SortedSetVO;
import redis.clients.util.SafeEncoder;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DeleteMethod extends  AdapterInterface {


    public DeleteMethod(JedisTemplate jedisTemplate) {
        super(jedisTemplate);
    }


    private Object delete(){
        Object[] args = getArguments();
        String fullKey = getPrefix() + ":" + args[0].toString();
        jedisTemplate.del(fullKey);

        return null;
    }


    private Object deleteAll(){
        logger.info("---------");
        jedisTemplate.zRange(getIdSortedSetKey(),0,-1).size();
        logger.info("---------");

        Set<byte[]>  res =  jedisTemplate.zRange(getIdSortedSetKey(),0,-1);

        res.forEach(x -> logger.info(getPrefix() + ":" + SafeEncoder.encode(x)));


        res.forEach(x -> jedisTemplate.del(getPrefix() + ":" + SafeEncoder.encode(x)));


        List<String> keys = getRoEntityMeta().getSortedSetKeys().values().stream().map(SortedSetVO::getKey).collect(Collectors.toList());
        keys.forEach(x -> jedisTemplate.del(getPrefix() + ":" + x));

        return null;

    }


    @Override
    public Object invoke(Invocation invocation) throws Throwable{
        super.invoke(invocation);

        switch (getMethodName()){
            case "delete":{
                return this.delete();
            }
            case "deleteAll":{
                return this.deleteAll();
            }
        }



        throw new RuntimeException("method not found----" + getMethodName());
    }
}
