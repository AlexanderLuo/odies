package org.share.odies.service;

import com.google.common.collect.Sets;
import org.share.odies.api.OdiesRepository;
import org.share.odies.bean.BaseRedisObject;
import org.share.odies.exceptions.OdiesUsageException;
import org.share.odies.utils.RedisUtil;
import org.share.odies.vo.PageOf;
import org.share.odies.vo.PageResult;
import org.share.odies.vo.SortBy;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;

import java.io.Serializable;
import java.util.*;



/**
 * @version V1.0, 2020-06-29
 * @author Alexander Lo
 * @code  默认实现
 */
public class DefaultOdiesRepository<T extends BaseRedisObject<ID>, ID extends Serializable>
		extends ShardedJedisCurdCommonRedisDao<T, ID>  implements OdiesRepository<T,ID> {




	/**
	 * 将Set<bytes[]>转换为List<Long>
	 */
	protected List<Long> bytesSet2LongList(Collection<byte[]> set){
		if(set == null)return Collections.emptyList();
		List<Long> result = new ArrayList<>(set.size());
		for (byte[] bs : set) {
			result.add(RedisUtil.byteArrayToLong(bs));
		}
		return result;
	}

	/**
	 * 将Set<bytes[]>的数据转换为Long后装入目标集合
	 */
	protected <TARGET extends Collection<Long>> TARGET bytesSet2TargetCollection(Collection<byte[]> set, TARGET target){
		if(target == null)
			throw new RuntimeException("target null");

		if(set == null)return target;
		for (byte[] bs : set) {
			target.add(RedisUtil.byteArrayToLong(bs));
		}
		return target;
	}
	
	/**
	 * 以timstamp为score,将id放入sorted-set
	 */
	protected boolean zadd(String key, Date timestamp, Long id){
		return zadd(key, timestamp.getTime(), RedisUtil.toByteArray(id));
	}
	
	/**
	 * @Description: 以time为score 将string放入sorted-set
	 */
	protected boolean zadd(String key, Date timestamp, String str){
		return zadd(key, timestamp.getTime(), RedisUtil.toByteArray(str));
	}
	
	
	/**
	 * 从一个保存Long值的sorted-set中,分页查询值,并转化为一个List<Long>返回,降序
	 */
	protected List<Long> findIdListFromSortedSetRevrange(String key, Integer page, Integer size) {
		long start = page * size;//0
		long end = (page+1) * size -1;//10
		Set<byte[]> set = zrevrange(key, start , end);
		return bytesSet2LongList(set);
	}
	
	/**
	 * 配合pop使用
	 */
	protected void lpushToList(String listKey, ID id){
		//将足迹信息存入list,便于task持久化
		lpush(listKey, RedisUtil.toByteArray(id));
	}
	
	/**
	 * 配合push使用
	 */
	protected List<T> rpopFromList(String listKey, int size){
		long nowsize = llen(listKey);
		nowsize = Math.min(size, nowsize);
		List<Object> list = pipeRpop(listKey.getBytes(), nowsize);
		Set<byte[]> idset = Sets.newHashSet();
		for (Object o : list) {
			byte[] data = (byte[])o;
			idset.add(data);
		}
		return findByIds(idset);
	}
	
	protected List<Long> pipeLlen(List<String> keys){
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			ShardedJedisPipeline jedisPipeline = jedis.pipelined();
			for (String key : keys) {
				jedisPipeline.llen(key);
			}
			List lenth = jedisPipeline.syncAndReturnAll();
			return lenth;
		} finally {
			shardedJedisPool.returnResource(jedis);
		}
	}



	@Override
	public void	save(T ro) {
		super.save(ro);
	}

	@Override
	public T findById(ID id) {
		String prefixKey = super.getHashKey(id);
		return super.findByKey(prefixKey);
	}

	@Override
	public boolean exists(ID id) {
		String prefixKey = super.getHashKey(id);
		return super.exists(prefixKey);
	}


	@Override
	public void delete(ID id) {
		delete(findById(id));
	}


	@Override
	public long count() {

		if (super.hasRoSortedSet()) {
			return zCard(this.getRoSortedSetKey());
		}

		throw new OdiesUsageException("对象批操作 对应的Ro 需要@RoSortedSet注解");
	}






	@Override
	public List<T> findAll() {
		if(super.hasRoSortedSet()){
			List<String> keys = getKeyListFromSortedSet(this.getRoSortedSetKey());
			return findByKeys(keys);
		}

		throw new OdiesUsageException("对象批操作 对应的Ro 需要@RoSortedSet注解");
	}

	@Override
	public void saveAll(Iterable<T> ros) {
		super.save(ros);
	}


	@Override
	public PageResult<T> findAll(PageOf pageOf) {
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
			keys.add(getHashKey(id));
		}
		return findByKeys(keys);
	}

	@Override
	public void deleteAllById(Iterable<ID> ids) {
		if (ids == null || !ids.iterator().hasNext())
			return;

		List<String> keys = new ArrayList<String>();
		for (ID id : ids) {
			keys.add(getHashKey(id));
		}

		//tagger 删除对应的zset

		super.pipleDelete(keys);

	}

	@Override
	public void deleteAll() {

		if(super.hasRoSortedSet()){
			//tagger 删除对应的zset
			List<String> keys = getKeyListFromSortedSet(this.getRoSortedSetKey());
			super.pipleDelete(keys);

		}

		throw new OdiesUsageException("对象批操作 对应的Ro 需要@RoSortedSet注解");
	}




}

