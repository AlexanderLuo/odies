package org.share.odies.core.methods;

import com.alibaba.fastjson.JSON;
import org.share.odies.api.JedisTemplate;
import org.share.odies.core.proxy.Invocation;
import org.share.odies.vo.*;
import org.springframework.util.CollectionUtils;
import redis.clients.util.SafeEncoder;

import java.util.*;
import java.util.stream.Collectors;

public class FetchMethod extends AdapterInterface {


    public FetchMethod(JedisTemplate jedisTemplate) {
        super(jedisTemplate);
    }

    private Object findById(){

        Object[] args = invocation.getArguments();
        if (args.length == 0)
            return null;

        String id = args[0].toString();
        RoEntityMeta roEntityMeta =getRoEntityMeta();
        String fullKey = roEntityMeta.getPrefix() + ":" + id;

        Map<byte[], byte[]>  response =   this.jedisTemplate.hgetAll(fullKey);

        if (CollectionUtils.isEmpty(response))
            return null;


        Map shash = response.entrySet()
                .stream()
                .collect(Collectors.toMap(k->SafeEncoder.encode(k.getKey()), v->SafeEncoder.encode(v.getValue())));


        return JSON.parseObject(JSON.toJSONString(shash),roEntityMeta.getEntityClass());
    }


    private List<Object> findAll(){
        RoEntityMeta roEntityMeta = getRoEntityMeta();
        SortedSetVO id = roEntityMeta.getSortedSetKeys().get(roEntityMeta.getId().getName());
        String fullkey = roEntityMeta.getPrefix() + ":" + id.getKey();
        Object[] args = invocation.getArguments();
        Set<byte[]> ids = this.jedisTemplate.zRange(fullkey,0,-1);
        if (CollectionUtils.isEmpty(ids))
            return null;

        List<Map<byte[],byte[]>> bytes = this.jedisTemplate.pipeHgetall(ids.stream().map(SafeEncoder::encode).collect(Collectors.toList()));

        return bytes.stream().map(x ->{
            Map shash = x.entrySet()
                    .stream()
                    .collect(Collectors.toMap(k->SafeEncoder.encode(k.getKey()), v->SafeEncoder.encode(v.getValue())));
            return JSON.parseObject(JSON.toJSONString(shash),roEntityMeta.getEntityClass());

        }).collect(Collectors.toList());

    }





    private PageResult findByPage(){
        PageOf pageOf = (PageOf) getArguments()[0];
        SortBy sortBy = (SortBy) getArguments()[1];


        int from = pageOf.getPage() * pageOf.getPageSize();
        int to = from + pageOf.getPageSize();

        long total = jedisTemplate.zCard(getIdSortedSetKey());


        if (total == 0)
            return new PageResult();





        List<String> keys = (List<String>) Optional.of(jedisTemplate.zRange(getIdSortedSetKey(),from,to))
                .orElse(new HashSet<byte[]>())
                .stream()
                .map(x -> SafeEncoder.encode((byte[])x))
                .map(x -> getPrefix()+":"+x)
                .collect(Collectors.toList());

        List<Object> objects =  jedisTemplate.pipeHgetall(keys);


        return new PageResult<>(pageOf.getPage(),pageOf.getPageSize(),(int)total,
                objects.stream().map(x->map2Obj((Map<byte[], byte[]>) x)).collect(Collectors.toList()));

    }




    @Override
    public Object invoke(Invocation invocation) throws Throwable {
        super.invoke(invocation);
        String methodName = getMethodName();



        if (methodName.equals("findById"))
            return this.findById();


        if (methodName.equals("findAll")){
            if (!hasArguments())
                return this.findAll();

            return this.findByPage();

        }




        throw new RuntimeException("method not found----" + getMethodName());
    }



}
