package org.share.odies.core.proxy.methods;

import org.share.odies.api.JedisTemplate;
import org.share.odies.core.proxy.Invocation;


public class LockMethod extends AdapterInterface {

    public LockMethod(JedisTemplate jedisTemplate) {
        super(jedisTemplate);
    }


    private boolean lock(){
        Object[] args = getArguments();
        String lockKey = getPrefix() + ":" + args[0].toString();
        int seconds = (int)args[1];
        boolean ok = jedisTemplate.setnx(lockKey,"lock".getBytes());
        if (!ok)
            return false;
        jedisTemplate.expire(lockKey,seconds);
        return true;


    }


    private Object unlock(){
        Object[] args = getArguments();
        String lockKey = getPrefix() + ":" + args[0].toString();
        jedisTemplate.del(lockKey);
        return null;
    }


    @Override
    public Object invoke(Invocation invocation) throws Throwable{
        super.invoke(invocation);
        switch (getMethodName()){
            case "lock":{
                return this.lock();
            }
            case "unlock":{
                return this.unlock();
            }
        }

        throw new RuntimeException("method not found----" + getMethodName());


    }


}
