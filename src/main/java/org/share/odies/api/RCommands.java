package org.share.odies.api;

import redis.clients.jedis.Tuple;
import redis.clients.util.Pool;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @version V1.0, 2020-06-29
 * @author Alexander Lo
 * @code redis api
 */
public interface RCommands<T> {

    String echo(String var1);


    /********************************************************************************************************************
     *  key
    ********************************************************************************************************************/
    Long del(String key);
    boolean exists(String key);
    String type(String key);


    /**
     * @version V1.0, 2020-06-29
     * @author Alexander Lo
     * @code 过期时间
     */
    void expire(String key, int seconds);
    Long pexpire(String key, long millisecondsTimestamp);
    void expireAt(String key, Long unixTime);
    Long pexpireAt(String key, long millisecondsTimestamp);
    Long persist(String key);
    Long getTTL(String key);
    Long pttl(String key);




    void setex(String key, int seconds, byte[] value);
    boolean setnx(String key, byte[] value);



    /********************************************************************************************************************
     *  String
    ********************************************************************************************************************/
    byte[] get(String key);
    void set(String var1, byte[] value);
    void set(String var1, String value);
    String set(String key, String value, String nxxx);
    String set(String key, String value, String nxxx, String expx, long time);

    Long incr(String key);
    Long decr(String key);
    Long incrby(String key, long increment);




    /********************************************************************************************************************
     *  Hash
    ********************************************************************************************************************/
    byte[] hget(String var1, String var2);
    Long hdel(String var1, String var2);
    long hset(String var1, String var2, byte[] var3);
    boolean hsetnx(String var1, String var2, byte[] var3);
    String hmset(String var1, Map<byte[], byte[]> var2);
    List<byte[]> hmget(String var1, byte[]... var2);
    Map<byte[], byte[]> hgetAll(String var1);
    boolean hexists(String var1, String var2);



    /********************************************************************************************************************
     *  Set
    ********************************************************************************************************************/

    Long sadd(String key, byte[] member);
    Long scard(String key);
    boolean sismember(String key, byte[] member);
    Set<byte[]> smembers(String key);
    Long srem(String key, byte[] member);


    /********************************************************************************************************************
     *  Zset
    ********************************************************************************************************************/
    Long zCard(String key);
    boolean zadd(String key, double score, byte[] member);

    Long zrem(String key, byte[]... members);
    Long zrem(String key, String member);
    Long zrem(String key, String... member);

    Set<byte[]> zRange(String key, long start, long end);
    Set<byte[]> zrevrange(String key, long start, long end);
    Set<byte[]> zangeByScore(String key, String min, String max);



    Double zscore(String key, byte[] member);
    Integer zscoreToInt(String key, byte[] member);
    Long zscoreToLong(String key, byte[] member);
    Long zrank(String key, byte[] member);
    Double zincrby(String key, double score, byte[] member);




    Set<Tuple> zrevrangeWithScore(String key, long start, long end);
    Set<Tuple> zrangeWithScores(String key, long var2, long var4);
    Set<Tuple> zrangeByScoreWithScores(String key, double var2, double var4);
    Set<Tuple> zrevrangeByScoreWithScores(String key, double var2, double var4);
    Set<Tuple> zrangeByScoreWithScores(String key, double var2, double var4, int var6, int var7);
    Set<Tuple> zrangeByScoreWithScores(String key, String var2, String var3, int var4, int var5);
    Set<Tuple> zrevrangeByScoreWithScores(String key, String var2, String var3, int var4, int var5);







    /********************************************************************************************************************
     *  List
    ********************************************************************************************************************/
    Long rpush(String var1, byte[] var2);
    Long rpush(String var1, byte[]... var2);
    Long rpush(String var1, String... var2);


    Long lpush(String var1, byte[]... var2);
    Long lpush(String var1, String... var2);

    String ltrim(String var1, long var2, long var4);

    List<byte[]> lrange(String var1, long var2, long var4);

    long llen(String var1);

    byte[] lpop(String var1);

    Set<byte[]> zrevrangeByScore(String var1, byte[] var2, byte[] var3);

    Set<byte[]> zrevrangeByScore(String var1, String var2, String var3);

    Set<byte[]> zrevrangeByScore(String var1, String var2, String var3, int var4, int var5);

    long zcount(String var1, String var2, String var3);

    Long zlexcount(String var1, String var2, String var3);

    Set<String> zrangeByLex(String var1, String var2, String var3);

    Long zremrangeByScore(String var1, String var2, String var3);

    Long zremrangeByRank(String var1, long var2, long var4);

    Long zcount(String var1, double var2, double var4);

    Set<String> zrangeByScore(String var1, double var2, double var4, int var6, int var7);

    Set<String> zrangeByScore(String var1, String var2, String var3);

    Set<String> zrangeByScore(String var1, long var2, long var4);



    List<Object> pipeHgetall(List<String> var1);

    void pipeSadd(String var1, String... var2);
    void pipeZadd(String var1, double var2, List<String> var4);

    List<Object> pipeZcard(List<String> var1);



    Long setrange(String var1, long var2, String var4);

    String getrange(String var1, long var2, long var4);

    String getSet(String var1, String var2);

    List<String> blpop(int var1, String var2);
    List<String> brpop(int var1, String var2);


    Long hincrBy(String var1, byte[] var2, long var3);


    Long zrevrank(String var1, byte[] var2);
    List<Object> pipeRpop(byte[] var1, long var2);
    Long lrem(String var1, long var2, byte[] var4);




    void pipeHmset(String var1, List<Map<byte[], byte[]>> var2);
    List<Object> pipeGet(List<String> var1);
    List<Object> pipeHget(Collection<String> var1, String var2);
    void pipeHincrBy(Map<String, Integer> var1, String var2);
    void pipeZrem(String var1, Collection<String> var2);
    void pipeSet(List<String> var1, List<byte[]> var2);
    void pipeHSet(Map<String, String> var1, String var2);
    void pipeLpush(byte[] var1, byte[]... var2);
    void pipeLpush(Collection<byte[]> var1, Collection<byte[]> var2);




}
