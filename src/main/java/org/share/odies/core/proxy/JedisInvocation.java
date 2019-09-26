package org.share.odies.core.proxy;


import java.util.HashMap;
import java.util.Map;


public class JedisInvocation implements Invocation {

    private String methodName;
    private Object[] args;
    private Map<String,Object> attachments;

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }



    @Override
    public String getMethodName() {
        return methodName;
    }

    @Override
    public Class<?>[] getParameterTypes() {
        return new Class[0];
    }

    @Override
    public Object[] getArguments() {
        return args;
    }

    public void setArguments(Object[] args) {
        this.args = args;
    }

    @Override
    public Map<String, Object> getAttachments() {
        return attachments;
    }

    @Override
    public Object getAttachment(String key) {
        if (this.attachments == null)
            return null;

        return this.attachments.get(key);
    }

    public void setAttachments(String key,Object value) {
        if (this.attachments == null)
            this.attachments = new HashMap<>();
        this.attachments.put(key,value);
    }


}
