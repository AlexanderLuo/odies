package org.share.odies.service;

import com.google.common.collect.Sets;
import org.share.odies.api.JedisCurdRepository;
import org.share.odies.bean.IdRedisEntity;
import org.share.odies.exceptions.OdiesUsageException;
import org.share.odies.utils.RedisUtil;
import org.share.odies.vo.PageOf;
import org.share.odies.vo.PageResult;
import org.share.odies.vo.SortBy;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;

import java.io.Serializable;
import java.util.*;

import static com.google.common.collect.Lists.newArrayList;


/**
 * @author Alexander Lo
 * @version V1.0, 2020-06-29
 * @code 默认实现
 */
public class DefaultJedisRepository<T extends IdRedisEntity<ID>, ID extends Serializable>
        extends ShardedJedisCurdCommonRedisDao<T, ID> implements JedisCurdRepository<T, ID> {


    @Override
    public void save(T ro) {
        super.save(ro);
    }

    @Override
    public T findById(ID id) {
        String prefixKey = super.getRoFullKey(id);
        return super.findByKey(prefixKey);
    }

    @Override
    public List<T> findByIds(Iterable<ID> ids) {
        if (ids == null || !ids.iterator().hasNext())
            return newArrayList();
        List<String> keys = new ArrayList<String>();
        for (ID id : ids) {
            keys.add(super.getRoFullKey(id));
        }
        return super.findByKeys(keys);
    }

    @Override
    public boolean exists(ID id) {
        String prefixKey = super.getRoFullKey(id);
        return super.exists(prefixKey);
    }


    @Override
    public void delete(ID id) {
        delete(findById(id));
    }


    @Override
    public long count() {
        return zCard(this.getRoSortedSetKey());
    }


    @Override
    public List<T> findAll() {
        List<String> keys = getKeyListFromSortedSet(super.getRoSortedSetKey());
        return findByKeys(keys);
    }

    @Override
    public void saveAll(Iterable<T> ros) {
        super.save(ros);
    }


    @Override
    public PageResult<T> findAll(PageOf pageOf) {

//		Set<byte[]> ids = zRange(this.getRoSortedSetKey(), Math.max(0, page - 1) * 10, Math.max(1, page) * 10 - 1);

//		Set<byte[]> ids = zrevrange(this.getRoSortedSetKey(), Math.max(0, page - 1) * 10, Math.max(1, page) * 10 - 1);
//		return findByKeys(getRoFullKey(ids));
        return null;

    }

    @Override
    public PageResult<T> findAll(PageOf pageOf, SortBy sortBy) {
        return null;
    }


    @Override
    public List<T> findAllById(Iterable<ID> ids) {
        if (ids == null || !ids.iterator().hasNext())
            return new ArrayList<>();

        List<String> keys = new ArrayList<String>();
        for (ID id : ids) {
            keys.add(getRoFullKey(id));
        }
        return findByKeys(keys);
    }

    @Override
    public void deleteAllById(Iterable<ID> ids) {
        if (ids == null || !ids.iterator().hasNext())
            return;

        List<String> keys = new ArrayList<String>();
        for (ID id : ids) {
            keys.add(getRoFullKey(id));
        }

        //tagger 删除对应的zset

        super.pipleDelete(keys);

    }


    @Override
    public void deleteAll() {

        //tagger 删除对应的zset
        List<String> keys = getKeyListFromSortedSet(this.getRoSortedSetKey());

        super.pipleDelete(keys);


        throw new OdiesUsageException("对象批操作 对应的Ro 需要@RoSortedSet注解");
    }


}

