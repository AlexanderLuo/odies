package org.share.odies.core.methods;

import org.share.odies.api.JedisTemplate;
import org.share.odies.core.proxy.Invocation;


public class MetricsMethod extends AdapterInterface{
    public MetricsMethod(JedisTemplate jedisTemplate) {
        super(jedisTemplate);
    }

    private boolean exist(){
        Object[] args = getArguments();
        if (!hasArguments())
            return false;

        String id = args[0].toString();
        String fullKey = getPrefix() + ":" + id;

        return this.jedisTemplate.exists(fullKey);
    }

    private long count(){
        return jedisTemplate.zCard(getPrefix() +":" + "list");
    }






    @Override
    public Object invoke(Invocation invocation) throws Throwable{
        super.invoke(invocation);
        switch (getMethodName()){
            case "exists":{
                return this.exist();
            }
            case "count":{
                return this.count();
            }

        }

        throw new RuntimeException("method not found----" + getMethodName());
    }




}
