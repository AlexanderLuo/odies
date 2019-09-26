package org.share.odies.core.proxy.methods;

import org.share.odies.api.JedisTemplate;
import org.share.odies.core.proxy.Invocation;
import org.share.odies.core.utils.TransferTool;
import org.share.odies.vo.RoEntityMeta;
import redis.clients.util.SafeEncoder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SaveMethod extends  AdapterInterface{

    public SaveMethod(JedisTemplate jedisTemplate) {
        super(jedisTemplate);
    }


    private Object save() throws IllegalAccessException {

        Object saver  = getArguments()[0];
        String id = getIdFrom(saver);
        String fullId = getPrefix() + ":" + getIdFrom(saver);

        RoEntityMeta roEntityMeta = getRoEntityMeta();

        Map<byte[], byte[]> bhash = TransferTool.obj2Map(saver)
                .entrySet()
                .stream()
                .collect(Collectors.toMap(k-> SafeEncoder.encode(k.getKey()), v->SafeEncoder.encode(v.getValue())));


        this.jedisTemplate.hmset(fullId, bhash);



        roEntityMeta.getSortedSetKeys().forEach((key, vo) -> {
            long score = System.currentTimeMillis();
            String fullkey = roEntityMeta.getPrefix() + ":" + vo.getKey();


            this.jedisTemplate.zadd(fullkey, score, id.getBytes());

        });

        return saver;

    }


    private List<Object> findAll(){
        return null;
    }


    @Override
    public Object invoke(Invocation invocation) throws Throwable {
        super.invoke(invocation);
        String methodName = getMethodName();
        if (!hasArguments())
            return null;

        if (methodName.equals("save"))
            return this.save();
        if (methodName.equals("findAll"))
            return this.findAll();



        throw new RuntimeException("method not found----" + getMethodName());
    }

}
