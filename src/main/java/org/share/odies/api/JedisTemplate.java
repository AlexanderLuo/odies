package org.share.odies.api;

import redis.clients.jedis.Tuple;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface JedisTemplate<T> {

    byte[] hget(String var1, String var2);

    Long hdel(String var1, String var2);

    long hset(String var1, String var2, byte[] var3);

    boolean hsetnx(String var1, String var2, byte[] var3);

    String hmset(String var1, Map<byte[], byte[]> var2);

    List<byte[]> hmget(String var1, byte[]... var2);

    Map<byte[], byte[]> hgetAll(String var1);

    boolean hexists(String var1, String var2);

    Long sadd(String var1, byte[] var2);

    Long scard(String var1);

    boolean sismember(String var1, byte[] var2);

    Set<byte[]> smembers(String var1);

    Long srem(String var1, byte[] var2);

    Long del(String var1);

    boolean exists(String var1);

    boolean zadd(String var1, double var2, byte[] var4);

    Long zrem(String var1, byte[]... var2);

    Long zrem(String var1, String var2);

    Long zrem(String var1, String... var2);

    Set<byte[]> zRange(String var1, long var2, long var4);

    Set<byte[]> zrevrange(String var1, long var2, long var4);

    Set<Tuple> zrevrangeWithScore(String var1, long var2, long var4);

    Set<byte[]> zangeByScore(String var1, String var2, String var3);

    Set<Tuple> zrangeWithScores(String var1, long var2, long var4);

    Long zCard(String var1);

    Double zscore(String var1, byte[] var2);

    Integer zscoreToInt(String var1, byte[] var2);

    Long zscoreToLong(String var1, byte[] var2);

    Long zrank(String var1, byte[] var2);

    Double zincrby(String var1, double var2, byte[] var4);

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
