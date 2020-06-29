package org.share.odies.trans;

import java.io.Serializable;
import java.util.Map;

public interface Translator extends Serializable {

    String SEPERATOR = ".";

    /**
     * 将一个 redis hash 数据转换为一个 ro 对象
     *
     * @param redisData
     * @return
     */
    <RO> RO toObject(Class<RO> clazz, Map<byte[], byte[]> redisData);


    /**
     * 将一个 redis hash 数据向对象中填充
     *
     * @param ro        被填充数据的对象
     * @param redisData
     * @return
     */
    void fillObject(Object ro, Map<byte[], byte[]> redisData);


    /**
     * 将一个 ro 对象转换为 redis hash 数据
     *
     * @param object
     * @return
     */
    Map<byte[], byte[]> toRedisData(RedisObject object);


    DataTransformer getDataTransformer();

    ConvertorRegistry getConvertorRegistry();

    BeanRegistry getBeanRegistry();

}
