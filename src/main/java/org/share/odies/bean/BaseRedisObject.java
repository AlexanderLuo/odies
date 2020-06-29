

package org.share.odies.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;

import org.share.odies.trans.RedisObject;
import org.share.odies.trans.Translator;
import org.share.odies.trans.impl.TranslatorBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseRedisObject<ID extends Serializable> extends BaseRo implements RedisObject {
    private static final long serialVersionUID = 1494528706005149030L;
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    private ID id;
    private Timestamp createTimestamp = new Timestamp(System.currentTimeMillis());
    private Timestamp updateTimestamp;
    private Translator translator = TranslatorBuilder.getDefaultTranslatorSingleton();

    public BaseRedisObject() {
    }

    public void fromMap(Map<byte[], byte[]> map) {
        this.translator.fillObject(this, map);
    }

    public Map<byte[], byte[]> toMap() {
        return this.translator.toRedisData(this);
    }

    public Logger getLog() {
        return this.log;
    }

    public ID getId() {
        return this.id;
    }

    public Timestamp getCreateTimestamp() {
        return this.createTimestamp;
    }

    public Timestamp getUpdateTimestamp() {
        return this.updateTimestamp;
    }

    public Translator getTranslator() {
        return this.translator;
    }

    public void setLog(Logger log) {
        this.log = log;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public void setCreateTimestamp(Timestamp createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public void setUpdateTimestamp(Timestamp updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public void setTranslator(Translator translator) {
        this.translator = translator;
    }
}
