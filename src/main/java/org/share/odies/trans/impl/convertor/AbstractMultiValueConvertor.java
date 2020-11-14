package org.share.odies.trans.impl.convertor;

import org.apache.commons.lang3.StringUtils;
import org.share.odies.trans.ConvertorRegistry;
import org.share.odies.trans.DataItem;
import org.share.odies.trans.Translator;
import org.share.odies.trans.ValueConvertor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractMultiValueConvertor<V> implements ValueConvertor<V> {

    private ConvertorRegistry convertorRegistry;
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected ConvertorRegistry getConvertorRegistry() {
        return convertorRegistry;
    }

    public AbstractMultiValueConvertor(ConvertorRegistry convertorRegistry) {
        this.convertorRegistry = convertorRegistry;
    }

    protected DataItem[] resolveItems(String key, Object value) {
        return this.resolveItems(null, key, value);
    }

    protected DataItem[] resolveItems(String prefix, String propKey, Object value) {
        if (StringUtils.isNotBlank(prefix)) {
            propKey = prefix + Translator.SEPERATOR + propKey;
        }
        ValueConvertor<Object> convertor = getConvertorRegistry().findConvertor(value.getClass());
        if (convertor == null) {
            logger.warn("prop[name={},type={}] convertor not found", propKey, value.getClass().getSimpleName());
        }
        return convertor.toRedisData(propKey, value);
    }

}
