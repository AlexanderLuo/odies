

package org.share.odies.bean;

import java.io.Serializable;
import java.util.Map;

import org.share.odies.annotation.RoSortedSet;
import org.share.odies.trans.RedisObject;
import org.share.odies.trans.Translator;
import org.share.odies.trans.impl.TranslatorBuilder;



@RoSortedSet(prefix = "list")
public abstract class IdRedisEntity<ID extends Serializable>  implements RedisObject {

    private ID id;
    private Translator translator = TranslatorBuilder.getDefaultTranslatorSingleton();

    public IdRedisEntity() {
    }


    public void fromMap(Map<byte[], byte[]> map) {
        this.translator.fillObject(this, map);
    }
    public Map<byte[], byte[]> toMap() {
        return this.translator.toRedisData(this);
    }



    public ID getId() {
        return this.id;
    }
    public void setId(ID id) {
        this.id = id;
    }

}
