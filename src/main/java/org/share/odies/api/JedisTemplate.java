package org.share.odies.api;

import redis.clients.jedis.Tuple;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @version V1.0, 2020-06-29
 * @author Alexander Lo
 * @code redis api
 */
public interface JedisTemplate<T> {


    /********************************************************************************************************************
     *  Hash  Api
    ********************************************************************************************************************/

    /**
     * Returns the value associated with field in the hash stored at key.
     */
    byte[] hget(String var1, String var2);

    /**
     * Removes the specified fields from the hash stored at key.
     * Specified fields that do not exist within this hash are ignored.
     * If key does not exist, it is treated as an empty hash and this command returns 0.
     */
    Long hdel(String var1, String var2);


    /**
     * Sets field in the hash stored at key to value.
     * If key does not exist, a new key holding a hash is created.
     * If field already exists in the hash, it is overwritten.
     */
    long hset(String var1, String var2, byte[] var3);



    /**
     * Sets field in the hash stored at key to value, only if field does not yet exist.
     * If key does not exist, a new key holding a hash is created.
     * If field already exists, this operation has no effect.
     */
    boolean hsetnx(String var1, String var2, byte[] var3);


    /**
     * Sets the specified fields to their respective values in the hash stored at key.
     * This command overwrites any existing fields in the hash.
     * If key does not exist, a new key holding a hash is created.
     */
    String hmset(String var1, Map<byte[], byte[]> var2);


    /**
     * Returns the values associated with the specified fields in the hash stored at key.
     */
    List<byte[]> hmget(String var1, byte[]... var2);


    /**
     * Returns all fields and values of the hash stored at key.
     * In the returned value, every field name is followed by its value, so the length of the reply is twice the size of the hash.
     */
    Map<byte[], byte[]> hgetAll(String var1);


    /**
     * Returns if field is an existing field in the hash stored at key.
     */
    boolean hexists(String var1, String var2);



    /********************************************************************************************************************
     *  Set Api
    ********************************************************************************************************************/

    /**
     * Add the specified members to the set stored at key.
     * Specified members that are already a member of this set are ignored.
     * If key does not exist, a new set is created before adding the specified members.
     * An error is returned when the value stored at key is not a set.
     */
    Long sadd(String var1, byte[] var2);


    Long scard(String var1);



    /**
     * Returns if member is a member of the set stored at key.
     */
    boolean sismember(String var1, byte[] var2);

    /**
     * Returns all the members of the set value stored at key.
     * This has the same effect as running SINTER with one argument key.
     */
    Set<byte[]> smembers(String var1);

    /**
     * Remove the specified members from the set stored at key.
     * Specified members that are not a member of this set are ignored.
     * If key does not exist, it is treated as an empty set and this command returns 0.
     * An error is returned when the value stored at key is not a set.
     */

    Long srem(String var1, byte[] var2);


    /**
     * Removes the specified keys. A key is ignored if it does not exist.
     */
    Long del(String var1);

    /**
     * Returns if key exists.
     */
    boolean exists(String var1);


    /********************************************************************************************************************
     *  ZSet api
    ********************************************************************************************************************/

    /**
     * Adds all the specified members with the specified scores to the sorted set stored at key.
     */
    boolean zadd(String var1, double var2, byte[] var4);

    /**
     * Removes the specified members from the sorted set stored at key. Non existing members are ignored.
     */
    Long zrem(String var1, byte[]... var2);

    /**
     * {@linkplain #zrem(String, byte[]...)}
     */
    Long zrem(String var1, String var2);

    /**
     * {@linkplain #zrem(String, byte[]...)}
     */
    Long zrem(String var1, String... var2);

    /**
     * Returns the specified range of elements in the sorted set stored at key.
     * The elements are considered to be ordered from the lowest to the highest score.
     * Lexicographical order is used for elements with equal score.
     */
    Set<byte[]> zRange(String var1, long var2, long var4);

    /**
     * Returns the specified range of elements in the sorted set stored at key.
     * The elements are considered to be ordered from the highest to the lowest score.
     * Descending lexicographical order is used for elements with equal score.
     * Apart from the reversed ordering, ZREVRANGE is similar to {@linkplain #zRange(String, long, long)}.
     */
    Set<byte[]> zrevrange(String var1, long var2, long var4);



    Set<Tuple> zrevrangeWithScore(String var1, long var2, long var4);
    Set<byte[]> zangeByScore(String var1, String var2, String var3);
    Set<Tuple> zrangeWithScores(String var1, long var2, long var4);


    //获取列表元素总数
    Long zCard(String var1);

    //获取sort set score
    Double zscore(String var1, byte[] var2);


    Integer zscoreToInt(String var1, byte[] var2);
    Long zscoreToLong(String var1, byte[] var2);
    Long zrank(String var1, byte[] var2);
    Double zincrby(String var1, double var2, byte[] var4);



    /********************************************************************************************************************
     *   List Api
    ********************************************************************************************************************/
    Long rpush(String var1, byte[] var2);
    Long rpush(String var1, byte[]... var2);
    Long lpush(String var1, byte[]... var2);
    Long lpush(String var1, String... var2);
    Long rpush(String var1, String... var2);


    String ltrim(String var1, long var2, long var4);

    List<byte[]> lrange(String var1, long var2, long var4);

    long llen(String var1);

    byte[] lpop(String var1);

    Set<byte[]> zrevrangeByScore(String var1, byte[] var2, byte[] var3);

    Set<byte[]> zrevrangeByScore(String var1, String var2, String var3);

    Set<byte[]> zrevrangeByScore(String var1, String var2, String var3, int var4, int var5);

    long zcount(String var1, String var2, String var3);

    Long zremrangeByScore(String var1, String var2, String var3);

    Long zremrangeByRank(String var1, long var2, long var4);

    Long zcount(String var1, double var2, double var4);

    Set<String> zrangeByScore(String var1, double var2, double var4, int var6, int var7);

    Set<String> zrangeByScore(String var1, String var2, String var3);

    Set<String> zrangeByScore(String var1, long var2, long var4);

    void expire(String var1, int var2);

    void expireAt(String var1, Long var2);

    List<Object> pipeHgetall(List<String> var1);

    void pipeSadd(String var1, String... var2);

    void pipeZadd(String var1, double var2, List<String> var4);

    List<Object> pipeZcard(List<String> var1);

    void pipeHmset(String var1, List<Map<byte[], byte[]>> var2);

    Long hincrBy(String var1, byte[] var2, long var3);

    byte[] get(String var1);

    void set(String var1, byte[] var2);

    void setex(String var1, int var2, byte[] var3);

    boolean setnx(String var1, byte[] var2);

    Long getTTL(String var1);

    Long incr(String var1);

    Long decr(String var1);

    Long zrevrank(String var1, byte[] var2);

    List<Object> pipeRpop(byte[] var1, long var2);

    Long lrem(String var1, long var2, byte[] var4);

    Long incrby(String var1, long var2);






    List<Object> pipeGet(List<String> var1);

    List<Object> pipeHget(Collection<String> var1, String var2);

    void pipeHincrBy(Map<String, Integer> var1, String var2);

    void pipeZrem(String var1, Collection<String> var2);

    void pipeSet(List<String> var1, List<byte[]> var2);

    void pipeHSet(Map<String, String> var1, String var2);

    void pipeLpush(byte[] var1, byte[]... var2);

    void pipeLpush(Collection<byte[]> var1, Collection<byte[]> var2);

    Long persist(String var1);
}
