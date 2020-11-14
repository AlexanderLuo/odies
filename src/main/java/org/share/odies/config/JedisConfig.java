
package org.share.odies.config;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;


/**
 * @author Alexander Lo
 * @version V1.0, 2020-06-30
 * @code 接入spring
 */
@ConditionalOnProperty({"spring.redis.host"})
@Configuration
public class JedisConfig {

    private static final Logger log = LoggerFactory.getLogger(JedisConfig.class);
    private RedisProperties redisProperties;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;


    public JedisConfig(RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
    }

    @Bean
    public RedisTemplate<Serializable, Serializable> functionDomainRedisTemplate() {
        RedisTemplate<Serializable, Serializable> redisTemplate = new RedisTemplate();
        this.initDomainRedisTemplate(redisTemplate, this.redisConnectionFactory);
        return redisTemplate;
    }

    private void initDomainRedisTemplate(RedisTemplate<Serializable, Serializable> redisTemplate, RedisConnectionFactory factory) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setConnectionFactory(factory);
    }

    @Bean
    public HashOperations<Serializable, Serializable, Object> hashOperations(RedisTemplate<Serializable, Serializable> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    @Bean
    public ValueOperations<Serializable, Serializable> valueOperations(RedisTemplate<Serializable, Serializable> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    @Bean
    public ListOperations<Serializable, Serializable> listOperations(RedisTemplate<Serializable, Serializable> redisTemplate) {
        return redisTemplate.opsForList();
    }

    @Bean
    public SetOperations<Serializable, Serializable> setOperations(RedisTemplate<Serializable, Serializable> redisTemplate) {
        return redisTemplate.opsForSet();
    }

    @Bean
    public ZSetOperations<Serializable, Serializable> zSetOperations(RedisTemplate<Serializable, Serializable> redisTemplate) {
        return redisTemplate.opsForZSet();
    }

    @Bean(destroyMethod = "destroy")
    ShardedJedisPool jedisPool() {
        RedisProperties p = this.redisProperties;
        log.info("redis configuration = {}", p);
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();

        //lettuce 驱动
        config.setMaxWaitMillis(p.getLettuce().getPool().getMaxWait().getSeconds());
        config.setMaxTotal(p.getLettuce().getPool().getMaxActive());
        config.setMinIdle(p.getLettuce().getPool().getMinIdle());
        config.setMaxIdle(p.getLettuce().getPool().getMaxIdle());

        //jedis 驱动 这个驱动用不了 redis template
//        config.setMaxIdle(p.getJedis().getPool().getMaxIdle());
//        config.setMaxWaitMillis(p.getJedis().getPool().getMaxWait().getSeconds());
//        config.setMaxTotal(p.getJedis().getPool().getMaxActive());
//        config.setMinIdle(p.getJedis().getPool().getMinIdle());


        config.setTestWhileIdle(true);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        config.setMinEvictableIdleTimeMillis(TimeUnit.MINUTES.toMillis(10L));


        JedisShardInfo info = new JedisShardInfo(p.getHost(), p.getPort(), (int) p.getTimeout().getSeconds() * 1000);

        info.setPassword(Strings.emptyToNull(p.getPassword()));
        ShardedJedisPool pool = new ShardedJedisPool(config, Lists.newArrayList(new JedisShardInfo[]{info}));
        ShardedJedis jedis = pool.getResource();

        Throwable var6 = null;

        try {
            jedis.get("test-connection");
        } catch (Throwable var15) {
            var6 = var15;
            throw var15;
        } finally {
            if (jedis != null) {
                if (var6 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var14) {
                        var6.addSuppressed(var14);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return pool;
    }
}
