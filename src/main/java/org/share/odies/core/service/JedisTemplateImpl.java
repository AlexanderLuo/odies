package org.share.odies.core.service;


import org.share.odies.api.JedisTemplate;
import org.share.odies.core.utils.RedisUtil;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.ShardedJedis;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.Tuple;
import redis.clients.util.Pool;

public class JedisTemplateImpl implements JedisTemplate<ShardedJedis> {

    protected ShardedJedisPool shardedJedisPool;

    public JedisTemplateImpl() { }

    public JedisTemplateImpl(ShardedJedisPool shardedJedisPool ) {this.shardedJedisPool = shardedJedisPool; }

    protected Pool<ShardedJedis> getPool() {
        return this.shardedJedisPool;
    }

    @Override
    public byte[] hget(String key, String field) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var4 = null;

        byte[] var5;
        try {
            var5 = jedis.hget(key.getBytes(), field.getBytes());
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }
    @Override
    public Long incrby(String key, long increment) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var5 = null;

        Long var6;
        try {
            var6 = jedis.incrBy(key, increment);
        } catch (Throwable var15) {
            var5 = var15;
            throw var15;
        } finally {
            if (jedis != null) {
                if (var5 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var14) {
                        var5.addSuppressed(var14);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var6;
    }
    @Override
    public Long hdel(String key, String field) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var4 = null;

        Long var5;
        try {
            var5 = jedis.hdel(key.getBytes(), new byte[][]{field.getBytes()});
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }
    @Override
    public long hset(String key, String field, byte[] value) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var5 = null;

        long var6;
        try {
            var6 = jedis.hset(key.getBytes(), field.getBytes(), value);
        } catch (Throwable var16) {
            var5 = var16;
            throw var16;
        } finally {
            if (jedis != null) {
                if (var5 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var15) {
                        var5.addSuppressed(var15);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var6;
    }
    @Override
    public boolean hsetnx(String key, String field, byte[] value) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var5 = null;

        boolean var6;
        try {
            var6 = jedis.hsetnx(key.getBytes(), field.getBytes(), value).intValue() == 1;
        } catch (Throwable var15) {
            var5 = var15;
            throw var15;
        } finally {
            if (jedis != null) {
                if (var5 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var14) {
                        var5.addSuppressed(var14);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var6;
    }
    @Override
    public String hmset(String key, Map<byte[], byte[]> hash) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var4 = null;

        String var5;
        try {
            var5 = jedis.hmset(key.getBytes(), hash);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }
    @Override
    public List<byte[]> hmget(String key, byte[]... fields) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var4 = null;

        List var5;
        try {
            var5 = jedis.hmget(key.getBytes(), fields);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }
    @Override
    public Map<byte[], byte[]> hgetAll(String key) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var3 = null;

        Map var4;
        try {
            var4 = jedis.hgetAll(key.getBytes());
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }
    @Override
    public boolean hexists(String key, String field) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var4 = null;

        boolean var5;
        try {
            var5 = jedis.hexists(key.getBytes(), field.getBytes());
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }
    @Override
    public Long sadd(String key, byte[] value) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var4 = null;

        Long var5;
        try {
            var5 = jedis.sadd(key.getBytes(), new byte[][]{value});
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }
    @Override
    public Long scard(String key) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var3 = null;

        Long var4;
        try {
            var4 = jedis.scard(key);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }
    @Override
    public boolean sismember(String key, byte[] member) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var4 = null;

        boolean var5;
        try {
            var5 = jedis.sismember(key.getBytes(), member);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }
    @Override
    public Set<byte[]> smembers(String key) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var3 = null;

        Set var4;
        try {
            var4 = jedis.smembers(key.getBytes());
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }
    @Override
    public Long srem(String key, byte[] members) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var4 = null;

        Long var5;
        try {
            var5 = jedis.srem(key.getBytes(), new byte[][]{members});
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }
    @Override
    public Long del(String key) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var3 = null;

        Long var4;
        try {
            var4 = jedis.del(key.getBytes());
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }
    @Override
    public boolean exists(String key) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var3 = null;

        boolean var4;
        try {
            var4 = jedis.exists(key.getBytes());
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }
    @Override
    public boolean zadd(String key, double score, byte[] member) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var6 = null;

        boolean var8;
        try {
            Long result = jedis.zadd(key.getBytes(), score, member);
            var8 = result == 1L || result == 0L;
        } catch (Throwable var17) {
            var6 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var6 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var6.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var8;
    }
    @Override
    public Long zrem(String key, byte[]... members) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var4 = null;

        Long var5;
        try {
            var5 = jedis.zrem(key.getBytes(), members);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }
    @Override
    public Long zrem(String key, String member) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var4 = null;

        Long var5;
        try {
            var5 = jedis.zrem(key.getBytes(), new byte[][]{member.getBytes()});
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }
    @Override
    public Long zrem(String key, String... members) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var4 = null;

        Long var5;
        try {
            var5 = jedis.zrem(key, members);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }
    @Override
    public Set<byte[]> zRange(String key, long start, long end) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var7 = null;

        Set var8;
        try {
            var8 = jedis.zrange(key.getBytes(), start, end);
        } catch (Throwable var17) {
            var7 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var7 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var7.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var8;
    }
    @Override
    public Set<byte[]> zrevrange(String key, long start, long end) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var7 = null;

        Set var8;
        try {
            var8 = jedis.zrevrange(key.getBytes(), start, end);
        } catch (Throwable var17) {
            var7 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var7 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var7.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var8;
    }
    @Override
    public Set<Tuple> zrevrangeWithScore(String key, long start, long end) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var7 = null;

        Set var8;
        try {
            var8 = jedis.zrevrangeWithScores(key.getBytes(), start, end);
        } catch (Throwable var17) {
            var7 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var7 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var7.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var8;
    }
    @Override
    public Set<byte[]> zangeByScore(String key, String min, String max) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var5 = null;

        Set var6;
        try {
            var6 = jedis.zrangeByScore(key.getBytes(), min.getBytes(), max.getBytes());
        } catch (Throwable var15) {
            var5 = var15;
            throw var15;
        } finally {
            if (jedis != null) {
                if (var5 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var14) {
                        var5.addSuppressed(var14);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var6;
    }
    @Override
    public Set<Tuple> zrangeWithScores(String key, long start, long end) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var7 = null;

        Set var8;
        try {
            var8 = jedis.zrangeWithScores(key.getBytes(), start, end);
        } catch (Throwable var17) {
            var7 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var7 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var7.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var8;
    }
    @Override
    public Long zCard(String key) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var3 = null;

        Long var4;
        try {
            var4 = jedis.zcard(key.getBytes());
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }
    @Override
    public Double zscore(String key, byte[] member) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var4 = null;

        Double var5;
        try {
            var5 = jedis.zscore(key.getBytes(), member);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }
    @Override
    public Integer zscoreToInt(String key, byte[] member) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var4 = null;

        Integer var6;
        try {
            Double d = jedis.zscore(key.getBytes(), member);
            var6 = d == null ? null : d.intValue();
        } catch (Throwable var15) {
            var4 = var15;
            throw var15;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var14) {
                        var4.addSuppressed(var14);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var6;
    }
    @Override
    public Long zscoreToLong(String key, byte[] member) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var4 = null;

        Long var6;
        try {
            Double d = jedis.zscore(key.getBytes(), member);
            var6 = d == null ? null : d.longValue();
        } catch (Throwable var15) {
            var4 = var15;
            throw var15;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var14) {
                        var4.addSuppressed(var14);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var6;
    }
    @Override
    public Long zrank(String key, byte[] member) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var4 = null;

        Long var5;
        try {
            var5 = jedis.zrank(key.getBytes(), member);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }
    @Override
    public Double zincrby(String key, double score, byte[] member) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var6 = null;

        Double var7;
        try {
            var7 = jedis.zincrby(key.getBytes(), score, member);
        } catch (Throwable var16) {
            var6 = var16;
            throw var16;
        } finally {
            if (jedis != null) {
                if (var6 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var15) {
                        var6.addSuppressed(var15);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var7;
    }
    @Override
    public Long rpush(String key, byte[] value) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var4 = null;

        Long var5;
        try {
            var5 = jedis.rpush(key.getBytes(), new byte[][]{value});
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }
    @Override
    public Long rpush(String key, byte[]... strings) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var4 = null;

        Long var5;
        try {
            var5 = jedis.rpush(key.getBytes(), strings);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }
    @Override
    public Long lpush(String key, byte[]... strings) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var4 = null;

        Long var5;
        try {
            var5 = jedis.lpush(key.getBytes(), strings);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }
    @Override
    public Long lpush(String key, String... strings) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var4 = null;

        Long var5;
        try {
            var5 = jedis.lpush(key, strings);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }
    @Override
    public Long rpush(String key, String... strings) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var4 = null;

        Long var5;
        try {
            var5 = jedis.rpush(key, strings);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }
    @Override
    public String ltrim(String key, long start, long end) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var7 = null;

        String var8;
        try {
            var8 = jedis.ltrim(key.getBytes(), start, end);
        } catch (Throwable var17) {
            var7 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var7 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var7.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var8;
    }
    @Override
    public List<byte[]> lrange(String key, long start, long end) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var7 = null;

        List var8;
        try {
            var8 = jedis.lrange(key.getBytes(), start, end);
        } catch (Throwable var17) {
            var7 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var7 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var7.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var8;
    }
    @Override
    public long llen(String key) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var3 = null;

        long var4;
        try {
            var4 = jedis.llen(key.getBytes());
        } catch (Throwable var14) {
            var3 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var3.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }
    @Override
    public byte[] lpop(String key) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var3 = null;

        byte[] var4;
        try {
            var4 = jedis.lpop(key.getBytes());
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }
    @Override
    public Set<byte[]> zrevrangeByScore(String key, byte[] max, byte[] min) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var5 = null;

        Set var6;
        try {
            var6 = jedis.zrevrangeByScore(key.getBytes(), max, min);
        } catch (Throwable var15) {
            var5 = var15;
            throw var15;
        } finally {
            if (jedis != null) {
                if (var5 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var14) {
                        var5.addSuppressed(var14);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var6;
    }
    @Override
    public Set<byte[]> zrevrangeByScore(String key, String max, String min) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var5 = null;

        Set var6;
        try {
            var6 = jedis.zrevrangeByScore(key.getBytes(), max.getBytes(), min.getBytes());
        } catch (Throwable var15) {
            var5 = var15;
            throw var15;
        } finally {
            if (jedis != null) {
                if (var5 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var14) {
                        var5.addSuppressed(var14);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var6;
    }
    @Override
    public Set<byte[]> zrevrangeByScore(String key, String max, String min, int offset, int count) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var7 = null;

        Set var8;
        try {
            var8 = jedis.zrevrangeByScore(key.getBytes(), max.getBytes(), min.getBytes(), offset, count);
        } catch (Throwable var17) {
            var7 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var7 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var7.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var8;
    }
    @Override
    public long zcount(String key, String min, String max) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var5 = null;

        long var7;
        try {
            Long count = jedis.zcount(key.getBytes(), min.getBytes(), max.getBytes());
            var7 = count == null ? 0L : count;
        } catch (Throwable var17) {
            var5 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var5 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var5.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var7;
    }
    @Override
    public Long zremrangeByScore(String key, String start, String end) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var5 = null;

        Long var6;
        try {
            var6 = jedis.zremrangeByScore(key.getBytes(), start.getBytes(), end.getBytes());
        } catch (Throwable var15) {
            var5 = var15;
            throw var15;
        } finally {
            if (jedis != null) {
                if (var5 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var14) {
                        var5.addSuppressed(var14);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var6;
    }
    @Override
    public Long zremrangeByRank(String key, long start, long end) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var7 = null;

        Long var8;
        try {
            var8 = jedis.zremrangeByRank(key.getBytes(), start, end);
        } catch (Throwable var17) {
            var7 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var7 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var7.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var8;
    }
    @Override
    public Long zcount(String key, double min, double max) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var7 = null;

        Long var8;
        try {
            var8 = jedis.zcount(key.getBytes(), min, max);
        } catch (Throwable var17) {
            var7 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var7 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var7.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var8;
    }
    @Override
    public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var9 = null;

        Set var10;
        try {
            var10 = jedis.zrangeByScore(key, min, max, offset, count);
        } catch (Throwable var19) {
            var9 = var19;
            throw var19;
        } finally {
            if (jedis != null) {
                if (var9 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var18) {
                        var9.addSuppressed(var18);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var10;
    }
    @Override
    public Set<String> zrangeByScore(String key, String min, String max) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var5 = null;

        Set var6;
        try {
            var6 = jedis.zrangeByScore(key, min, max);
        } catch (Throwable var15) {
            var5 = var15;
            throw var15;
        } finally {
            if (jedis != null) {
                if (var5 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var14) {
                        var5.addSuppressed(var14);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var6;
    }
    @Override
    public Set<String> zrangeByScore(String key, long min, long max) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var7 = null;

        Set var8;
        try {
            var8 = jedis.zrangeByScore(key, (double)min, (double)max);
        } catch (Throwable var17) {
            var7 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var7 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var7.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var8;
    }
    @Override
    public void expire(String key, int seconds) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var4 = null;

        try {
            jedis.expire(key.getBytes(), seconds);
        } catch (Throwable var13) {
            var4 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var4.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }

        }

    }
    @Override
    public void expireAt(String key, Long unixTime) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var4 = null;

        try {
            jedis.expireAt(key.getBytes(), unixTime);
        } catch (Throwable var13) {
            var4 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var4.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }

        }

    }
    @Override
    public List<Object> pipeHgetall(List<String> ids) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var3 = null;

        try {
            ShardedJedisPipeline jedisPipeline = jedis.pipelined();
            Iterator var5 = ids.iterator();

            while(var5.hasNext()) {
                String id = (String)var5.next();
                jedisPipeline.hgetAll(id.getBytes());
            }

            List var16 = jedisPipeline.syncAndReturnAll();
            return var16;
        } catch (Throwable var14) {
            var3 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var3.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }
    }
    @Override
    public void pipeSadd(String key, String... members) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var4 = null;

        try {
            ShardedJedisPipeline jedisPipeline = jedis.pipelined();
            jedisPipeline.sadd(key, members);
            jedisPipeline.syncAndReturnAll();
        } catch (Throwable var13) {
            var4 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var4.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }

        }

    }
    @Override
    public void pipeZadd(String key, double score, List<String> members) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var6 = null;

        try {
            ShardedJedisPipeline jedisPipeline = jedis.pipelined();
            Iterator var8 = members.iterator();

            while(var8.hasNext()) {
                String member = (String)var8.next();
                jedisPipeline.zadd(key.getBytes(), score, member.getBytes());
            }

            jedisPipeline.syncAndReturnAll();
        } catch (Throwable var17) {
            var6 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var6 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var6.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }

        }
    }
    @Override
    public List<Object> pipeZcard(List<String> ids) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var3 = null;

        try {
            ShardedJedisPipeline jedisPipeline = jedis.pipelined();
            Iterator var5 = ids.iterator();

            while(var5.hasNext()) {
                String id = (String)var5.next();
                jedisPipeline.zcard(id.getBytes());
            }

            List var16 = jedisPipeline.syncAndReturnAll();
            return var16;
        } catch (Throwable var14) {
            var3 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var3.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }
    }
    @Override
    public void pipeHmset(String key, List<Map<byte[], byte[]>> hashs) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var4 = null;

        try {
            ShardedJedisPipeline jedisPipeline = jedis.pipelined();
            Iterator var6 = hashs.iterator();

            while(var6.hasNext()) {
                Map<byte[], byte[]> hash = (Map)var6.next();
                jedisPipeline.hmset(key.getBytes(), hash);
            }

            jedisPipeline.syncAndReturnAll();
        } catch (Throwable var15) {
            var4 = var15;
            throw var15;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var14) {
                        var4.addSuppressed(var14);
                    }
                } else {
                    jedis.close();
                }
            }

        }
    }
    @Override
    public Long hincrBy(String key, byte[] field, long value) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var6 = null;

        Long var7;
        try {
            var7 = jedis.hincrBy(key.getBytes(), field, value);
        } catch (Throwable var16) {
            var6 = var16;
            throw var16;
        } finally {
            if (jedis != null) {
                if (var6 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var15) {
                        var6.addSuppressed(var15);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var7;
    }
    @Override
    public byte[] get(String key) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var3 = null;

        byte[] var4;
        try {
            var4 = jedis.get(key.getBytes());
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }
    @Override
    public void set(String key, byte[] value) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var4 = null;

        try {
            jedis.set(key.getBytes(), value);
        } catch (Throwable var13) {
            var4 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var4.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }

        }

    }
    @Override
    public void setex(String key, int seconds, byte[] value) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var5 = null;

        try {
            jedis.setex(key.getBytes(), seconds, value);
        } catch (Throwable var14) {
            var5 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var5 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var5.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

    }
    @Override
    public boolean setnx(String key, byte[] value) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var4 = null;

        boolean var6;
        try {
            Long r = jedis.setnx(key.getBytes(), value);
            var6 = r != null && r.intValue() == 1;
        } catch (Throwable var15) {
            var4 = var15;
            throw var15;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var14) {
                        var4.addSuppressed(var14);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var6;
    }
    @Override
    public Long getTTL(String key) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var3 = null;

        Long var4;
        try {
            var4 = jedis.ttl(key.getBytes());
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }
    @Override
    public Long incr(String key) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var3 = null;

        Long var4;
        try {
            var4 = jedis.incr(key.getBytes());
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }
    @Override
    public Long decr(String key) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var3 = null;

        Long var4;
        try {
            var4 = jedis.decr(key.getBytes());
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }
    @Override
    public Long zrevrank(String key, byte[] member) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var4 = null;

        Long var5;
        try {
            var5 = jedis.zrevrank(key.getBytes(), member);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }
    @Override
    public List<Object> pipeRpop(byte[] key, long size) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var5 = null;

        try {
            ShardedJedisPipeline jedisPipeline = jedis.pipelined();

            for(int i = 0; (long)i < size; ++i) {
                jedisPipeline.rpop(key);
            }

            List var18 = jedisPipeline.syncAndReturnAll();
            return var18;
        } catch (Throwable var16) {
            var5 = var16;
            throw var16;
        } finally {
            if (jedis != null) {
                if (var5 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var15) {
                        var5.addSuppressed(var15);
                    }
                } else {
                    jedis.close();
                }
            }

        }
    }
    @Override
    public Long lrem(String key, long count, byte[] value) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var6 = null;

        Long var7;
        try {
            var7 = jedis.lrem(key.getBytes(), count, value);
        } catch (Throwable var16) {
            var6 = var16;
            throw var16;
        } finally {
            if (jedis != null) {
                if (var6 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var15) {
                        var6.addSuppressed(var15);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var7;
    }
    @Override
    public List<Object> pipeGet(List<String> keys) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var3 = null;

        try {
            ShardedJedisPipeline jedisPipeline = jedis.pipelined();
            Iterator var5 = keys.iterator();

            while(var5.hasNext()) {
                String key = (String)var5.next();
                jedisPipeline.get(key.getBytes());
            }

            List var16 = jedisPipeline.syncAndReturnAll();
            return var16;
        } catch (Throwable var14) {
            var3 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var3.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }
    }
    @Override
    public List<Object> pipeHget(Collection<String> keys, String field) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var4 = null;

        try {
            ShardedJedisPipeline jedisPipeline = jedis.pipelined();
            Iterator var6 = keys.iterator();

            while(var6.hasNext()) {
                String key = (String)var6.next();
                jedisPipeline.hget(key, field);
            }

            List var17 = jedisPipeline.syncAndReturnAll();
            return var17;
        } catch (Throwable var15) {
            var4 = var15;
            throw var15;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var14) {
                        var4.addSuppressed(var14);
                    }
                } else {
                    jedis.close();
                }
            }

        }
    }
    @Override
    public void pipeHincrBy(Map<String, Integer> keyToCounts, String field) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var4 = null;

        try {
            ShardedJedisPipeline jedisPipeline = jedis.pipelined();
            Iterator var6 = keyToCounts.entrySet().iterator();

            while(var6.hasNext()) {
                Entry<String, Integer> entry = (Entry)var6.next();
                Integer value = (Integer)entry.getValue();
                if (value != null && value != 0) {
                    jedisPipeline.hincrBy((String)entry.getKey(), field, (long)value);
                }
            }

            jedisPipeline.sync();
        } catch (Throwable var16) {
            var4 = var16;
            throw var16;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var15) {
                        var4.addSuppressed(var15);
                    }
                } else {
                    jedis.close();
                }
            }

        }

    }
    @Override
    public void pipeZrem(String key, Collection<String> members) {
        if (!CollectionUtils.isEmpty(members)) {
            ShardedJedis jedis = this.shardedJedisPool.getResource();
            Throwable var4 = null;

            try {
                ShardedJedisPipeline jedisPipeline = jedis.pipelined();
                jedisPipeline.zrem(key, (String[])members.toArray(new String[members.size()]));
                jedisPipeline.sync();
            } catch (Throwable var13) {
                var4 = var13;
                throw var13;
            } finally {
                if (jedis != null) {
                    if (var4 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var12) {
                            var4.addSuppressed(var12);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }

        }
    }
    @Override
    public void pipeSet(List<String> keys, List<byte[]> values) {

        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var4 = null;

        try {
            ShardedJedisPipeline jedisPipeline = jedis.pipelined();

            for(int i = 0; i < keys.size(); ++i) {
                String key = (String)keys.get(i);
                jedisPipeline.set(RedisUtil.toByteArray(key), (byte[])values.get(i));
            }

            jedisPipeline.sync();
        } catch (Throwable var15) {
            var4 = var15;
            throw var15;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var14) {
                        var4.addSuppressed(var14);
                    }
                } else {
                    jedis.close();
                }
            }

        }
    }
    @Override
    public void pipeHSet(Map<String, String> keyToValue, String field) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var4 = null;

        try {
            ShardedJedisPipeline jedisPipeline = jedis.pipelined();
            Iterator var6 = keyToValue.entrySet().iterator();

            while(var6.hasNext()) {
                Entry<String, String> entry = (Entry)var6.next();
                jedisPipeline.hset((String)entry.getKey(), field, (String)entry.getValue());
            }

            jedisPipeline.sync();
        } catch (Throwable var15) {
            var4 = var15;
            throw var15;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var14) {
                        var4.addSuppressed(var14);
                    }
                } else {
                    jedis.close();
                }
            }

        }
    }


    @Override
    public void pipeLpush(byte[] key, byte[]... values) {


            ShardedJedis jedis = this.shardedJedisPool.getResource();
            Throwable var4 = null;

            try {
                ShardedJedisPipeline pipelined = jedis.pipelined();
                pipelined.lpush(key, values);
                pipelined.sync();
            } catch (Throwable var13) {
                var4 = var13;
                throw var13;
            } finally {
                if (jedis != null) {
                    if (var4 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var12) {
                            var4.addSuppressed(var12);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }

    }

    @Override
    public void pipeLpush(Collection<byte[]> keys, Collection<byte[]> values) {
        if (CollectionUtils.isEmpty(keys) || CollectionUtils.isEmpty(values))
            return;


            ShardedJedis jedis = this.shardedJedisPool.getResource();
            Throwable var4 = null;

            try {
                ShardedJedisPipeline pipelined = jedis.pipelined();
                Iterator<byte[]> keyIterator = keys.iterator();
                Iterator<byte[]> valueIterator = values.iterator();
                IntStream.range(0, keys.size()).forEach((i) -> {
                    pipelined.lpush((byte[])keyIterator.next(), new byte[][]{(byte[])valueIterator.next()});
                });
                pipelined.syncAndReturnAll();
            } catch (Throwable var15) {
                var4 = var15;
                throw var15;
            } finally {
                if (jedis != null) {
                    if (var4 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var14) {
                            var4.addSuppressed(var14);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }

    }

    public Long persist(String key) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var3 = null;

        Long var4;
        try {
            var4 = jedis.persist(key);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }


    public <T> T returnableCallback(Function<ShardedJedis, T> callback) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var3 = null;

        Object var4;
        try {
            var4 = callback.apply(jedis);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return (T) var4;
    }

    public List<Object> pipeZrange(List<String> keys) {
        ShardedJedis jedis = (ShardedJedis)this.getPool().getResource();
        Throwable var3 = null;

        try {
            ShardedJedisPipeline jedisPipeline = jedis.pipelined();
            Iterator var5 = keys.iterator();

            while(var5.hasNext()) {
                String key = (String)var5.next();
                jedisPipeline.zrange(key, 0L, -1L);
            }

            List var16 = jedisPipeline.syncAndReturnAll();
            return var16;
        } catch (Throwable var14) {
            var3 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var3.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }
    }
    public void jedisCallback(Consumer<ShardedJedis> callback) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Throwable var3 = null;

        try {
            callback.accept(jedis);
        } catch (Throwable var12) {
            var3 = var12;
            throw var12;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var11) {
                        var3.addSuppressed(var11);
                    }
                } else {
                    jedis.close();
                }
            }

        }

    }

    public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
        this.shardedJedisPool = shardedJedisPool;
    }


}
