package org.share.odies.service;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.share.odies.annotation.*;
import org.share.odies.annotation.SortedSet;
import org.share.odies.bean.IdRedisEntity;
import org.share.odies.exceptions.OdiesUsageException;
import org.share.odies.utils.ExpressionUtil;
import org.share.odies.utils.RedisUtil;
import org.share.odies.utils.SortedSetAssist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import redis.clients.jedis.PipelineBase;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.util.Pool;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.*;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @version V1.0, 2020-06-30
 * @author Alexander Lo
 * @code wrapper
 */
public class ShardedJedisCurdCommonRedisDao<T extends IdRedisEntity<ID>, ID extends Serializable>  extends ShardedJedisRedis {



    protected Logger logger = LoggerFactory.getLogger(getClass());
    private static final String SEPARATOR = ":";
    private static final String DEFAULT_RO_ZSET_KEY = "list";


    //   T  实际类
    private Class<T> entityClass;
    private String keyPrefix = null;
    // save Ro 计算分值
    private Expression expression = null;
    // ro.prefix:sort.prefix
    private String roSortedSetKey = null;



    private Map<String, SortedSet> fieldName_Annotation_Map = null;
    private Map<SortedSet, SortedSetAssist<T, ID>> fieldInSortedSetMap = null;


    private Map<String, SortedSet> methodName_Annotation_Map = null;
    private Map<SortedSet, SortedSetAssist<T, ID>> methodInSortedSetMap = null;




    @SuppressWarnings("unchecked")
    public ShardedJedisCurdCommonRedisDao() {
        super();

        entityClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        //类ro基础注解
        Ro ro = entityClass.getAnnotation(Ro.class);

        if (ro != null) {
            keyPrefix = ro.prefix().intern();
        } else {
            throw new OdiesUsageException("Not find Ro annotation");
        }



        ExpressionParser parser = new SpelExpressionParser();

        SortedSet roSortedSet = entityClass.getAnnotation(SortedSet.class);

        if(roSortedSet != null){
            roSortedSetKey = getKeyPrefix() + SEPARATOR + roSortedSet.prefix();
            if (StringUtils.isNotBlank(roSortedSet.score())) {
                expression = parser.parseExpression(roSortedSet.score());
            }
        }else {
            roSortedSetKey =  getKeyPrefix() + SEPARATOR + DEFAULT_RO_ZSET_KEY;
        }



        if (entityClass != null) {
            /**返回类中所有字段，包括公共、保护、默认（包）访问和私有字段，但不包括继承的字段 
             * entity.getFields();只返回对象所表示的类或接口的所有可访问公共字段 
             * 在class中getDeclared**()方法返回的都是所有访问权限的字段、方法等； 
             * 可看API 
             * */
            Field[] fields = entityClass.getDeclaredFields();
            if (fields.length > 0) {
                for (Field field : fields) {
                    if (!Modifier.isFinal(field.getModifiers())) {
                        field.setAccessible(true);//修改访问权限

                        //获取字段中包含的注解
                        SortedSet fieldSortedSet = field.getAnnotation(SortedSet.class);

                        if (fieldSortedSet != null) {

                            if (fieldInSortedSetMap == null) {
                                fieldInSortedSetMap = new HashMap<>();
                            }
                            if (fieldName_Annotation_Map == null) {
                                fieldName_Annotation_Map = new HashMap<>();
                            }


                            fieldInSortedSetMap.put(
                                    fieldSortedSet,
                                    new SortedSetAssist<>(
                                            field.getName(),
                                            StringUtils.isBlank(fieldSortedSet.prefix()) ? getKeyPrefix() + SEPARATOR + field.getName() : fieldSortedSet.prefix() + SEPARATOR + field.getName(),
                                            parser.parseExpression(fieldSortedSet.prefix()),
                                            StringUtils.isNotBlank(fieldSortedSet.score()) ? parser.parseExpression(fieldSortedSet.score()) : null
                                    )
                            );
                            fieldName_Annotation_Map.put(field.getName(), fieldSortedSet);
                        }
                    }
                }
            }


            Method[] methods = entityClass.getMethods();
            if (methods.length > 0) {
                for (Method method : methods) {
                    if (!Modifier.isFinal(method.getModifiers())) {
                        method.setAccessible(true);//修改访问权限
                        //获取方法中包含的注解

                        SortedSet methodSortedSet = method.getAnnotation(SortedSet.class);
                        if (methodSortedSet != null) {
                            if (methodInSortedSetMap == null) {
                                methodInSortedSetMap = new HashMap<>();
                            }
                            if (methodName_Annotation_Map == null) {
                                methodName_Annotation_Map = new HashMap<>();
                            }
                            methodInSortedSetMap.put(
                                    methodSortedSet,
                                    new SortedSetAssist<>(
                                            method.getName(),
                                            StringUtils.isBlank(methodSortedSet.prefix()) ? getKeyPrefix() + SEPARATOR + method.getName() : methodSortedSet.prefix() + SEPARATOR + method.getName(),
                                            parser.parseExpression(methodSortedSet.prefix()),
                                            StringUtils.isNotBlank(methodSortedSet.score()) ? parser.parseExpression(methodSortedSet.score()) : null
                                    )
                            );

                            methodName_Annotation_Map.put(method.getName(), methodSortedSet);
                        }
                    }
                }
            }
        }
    }



    /**
     * @version V1.0, 2020-06-29
     * @author Alexander Lo
     * @code  key  ----  Obj
     */
    public T findByKey(String key) {
        Map<byte[], byte[]> map = hgetAll(key);

        if (map == null || map.isEmpty()) {
            return null;
        }
        else {
            T ro = instance();
            if(ro != null) {
                ro.fromMap(map);
                return ro;
            }

            logger.error(entityClass.getName() + "--key:" + key +"查询失败");
            return null;
        }
    }


    /**
     * @version V1.0, 2020-06-29
     * @author Alexander Lo
     * @code 批查
     */
    @SuppressWarnings("unchecked")
    public List<T> findByKeys(List<String> keys) {
        List<T> result = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(keys)) {

            List<Object> list = pipeHgetall(keys);

            for (int i = 0; i < list.size(); i++) {
                Map<byte[], byte[]> map = (Map<byte[], byte[]>) list.get(i);
                if (map != null && !map.isEmpty()) {
                    T ro = instance();

                    if(ro != null) {
                        ro.fromMap(map);
                        result.add(ro);
                    }else {
                        logger.error(entityClass.getName() + "查询失败");
                    }

                }
            }
        }

        return result;
    }





    /**
     * @version V1.0, 2020-06-29
     * @author Alexander Lo
     * @code 批量删除
     */
    @SuppressWarnings("deprecation")
    public void pipleDelete(List<String> keys){
        Pool<ShardedJedis> pool = getPool();
        ShardedJedis jedis = null;
        try {
            jedis = pool.getResource();
            ShardedJedisPipeline jedisPipeline = jedis.pipelined();
            for (String key : keys) {
                jedisPipeline.del(key);
            }
            jedisPipeline.syncAndReturnAll();
        } catch (Exception e){
            logger.error("redis save iterator error.", e);
        }
        finally {
            pool.returnResource(jedis);
        }

    }



    /********************************************************************************************************************
     *   ROM  CRUD  操作
    ********************************************************************************************************************/


    /**
     * @version V1.0, 2020-06-29
     * @author Alexander Lo
     * @code 单个保存 同时建立zset
     */
    public void save(T ro) {
        long score = ExpressionUtil.getScore(new StandardEvaluationContext(ro), expression);
        zadd(this.getRoSortedSetKey(), score, RedisUtil.toByteArray(ro.getId()));

        if (MapUtils.isNotEmpty(fieldInSortedSetMap)) {
            for (SortedSet fieldSortedSet : fieldInSortedSetMap.keySet()) {
                SortedSetAssist<T, ID> field = fieldInSortedSetMap.get(fieldSortedSet);
                zadd(field.getKey(ro), field.getScore(ro), RedisUtil.toByteArray(ro.getId()));
            }
        }

        if (MapUtils.isNotEmpty(methodInSortedSetMap)) {
            for (SortedSet methodSortedSet : methodInSortedSetMap.keySet()) {
                SortedSetAssist<T, ID> method = methodInSortedSetMap.get(methodSortedSet);
                zadd(method.getKey(ro), method.getScore(ro), RedisUtil.toByteArray(ro.getId()));
            }
        }

        hmset(getRoFullKey(ro.getId()), ro.toMap());
    }





    /**
     * @version V1.0, 2020-06-29
     * @author Alexander Lo
     * @code 批量保存
     */
    public void save(Iterable<T> ros) {
        Pool<ShardedJedis> pool = getPool();
        ShardedJedis jedis = null;
        try {
            jedis = pool.getResource();
            ShardedJedisPipeline jedisPipeline = jedis.pipelined();
            for (T ro : ros) {
                this.save(ro, jedisPipeline);
            }
            jedisPipeline.syncAndReturnAll();
        } catch (Exception e){
            logger.error("redis save iterator error.", e);
        }
        finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }



    /**
     * @version V1.0, 2020-06-29
     * @author Alexander Lo
     * @code 批量保存实现
     */
    private void save(T ro, PipelineBase pipeline) {

        long score = ExpressionUtil.getScore(new StandardEvaluationContext(ro), expression);
        pipeline.zadd(RedisUtil.toByteArray(this.getRoSortedSetKey()), score, RedisUtil.toByteArray(ro.getId()));

        if (MapUtils.isNotEmpty(fieldInSortedSetMap)) {
            for (SortedSet fieldSortedSet : fieldInSortedSetMap.keySet()) {
                SortedSetAssist<T, ID> field = fieldInSortedSetMap.get(fieldSortedSet);
                pipeline.zadd(RedisUtil.toByteArray(field.getKey(ro)), field.getScore(ro), RedisUtil.toByteArray(ro.getId()));
            }
        }

        if (MapUtils.isNotEmpty(methodInSortedSetMap)) {
            for (SortedSet methodSortedSet : methodInSortedSetMap.keySet()) {
                SortedSetAssist<T, ID> method = methodInSortedSetMap.get(methodSortedSet);
                pipeline.zadd(RedisUtil.toByteArray(method.getKey(ro)), method.getScore(ro), RedisUtil.toByteArray(ro.getId()));
            }
        }
        pipeline.hmset(RedisUtil.toByteArray(getRoFullKey(ro.getId())), ro.toMap());
    }





    /**
     * @version V1.0, 2020-06-29
     * @author Alexander Lo
     * @code 单个删除 同时删除对应的 zset
     */
    public void delete(T ro) {
        if (ro != null) {

            zrem(this.getRoSortedSetKey(), RedisUtil.toByteArray(ro.getId()));

            if (MapUtils.isNotEmpty(fieldInSortedSetMap)) {
                for (SortedSet fieldSortedSet : fieldInSortedSetMap.keySet()) {
                    SortedSetAssist<T, ID> field = fieldInSortedSetMap.get(fieldSortedSet);
                    zrem(field.getKey(ro), RedisUtil.toByteArray(ro.getId()));
                }
            }

            if (MapUtils.isNotEmpty(methodInSortedSetMap)) {
                for (SortedSet methodSortedSet : methodInSortedSetMap.keySet()) {
                    SortedSetAssist<T, ID> method = methodInSortedSetMap.get(methodSortedSet);
                    zrem(method.getKey(ro), RedisUtil.toByteArray(ro.getId()));
                }
            }
            del(getRoFullKey(ro.getId()));
        }
    }








    /********************************************************************************************************************
     *  Normal get/set
    ********************************************************************************************************************/
    public String getKeyPrefix() {
        return keyPrefix;
    }
    public String getRoFullKey(Serializable id) {
        return getKeyPrefix() + SEPARATOR + id;
    }

    public String getRoFullKey(byte[] byteId) {
        return getKeyPrefix() + SEPARATOR + new String(byteId);
    }

    public List<String> getRoFullKey(Collection<byte[]> ids) {
        if (CollectionUtils.isNotEmpty(ids)) {
            List<String> keys = new ArrayList<>(ids.size());
            for (byte[] bid : ids) {
                keys.add(getRoFullKey(bid));
            }
            return keys;
        } else {
            return newArrayList();
        }
    }



    public String getRoSortedSetKey() {
        return roSortedSetKey;
    }



    /**
     * @version V1.0, 2020-06-30
     * @author Alexander Lo
     * @code 获取 sorted set 里面的所有key
     */
    public List<String> getKeyListFromSortedSet(String roSortedSetKey) {
        Set<byte[]> ids = zRange(roSortedSetKey, 0, -1);
        return getKeyListByIdSet(ids);
    }

    /**
     * @version V1.0, 2020-06-30
     * @author Alexander Lo
     * @code id -> key
     */
    public List<String> getKeyListByIdSet(Set<byte[]> ids) {
        if (CollectionUtils.isNotEmpty(ids)) {
            List<String> keys = new ArrayList<>(ids.size());
            for (byte[] bid : ids) {
                keys.add(getRoFullKey(bid));
            }
            return keys;
        }
        return newArrayList();
    }



    private T instance() {
        try {
            return entityClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
