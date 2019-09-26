package org.share.odies;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.share.odies.annotation.SupportRedisScannerRegistrar;
import org.share.odies.api.JedisTemplate;
import org.share.odies.core.properties.RedisProperties;
import org.share.odies.core.JedisRepositoryContext;
import org.share.odies.core.service.JedisTemplateImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

@ConditionalOnProperty({"spring.redis.host"})
@EnableConfigurationProperties({RedisProperties.class})
@Configuration
@Import({SupportRedisScannerRegistrar.class})
public class OdiesAutoConfigurationStarter {
    private static final Logger log = LoggerFactory.getLogger(OdiesAutoConfigurationStarter.class);

    @Autowired
    private RedisProperties redisProperties;


    public OdiesAutoConfigurationStarter(){ }




    @Bean(destroyMethod = "destroy")
    @ConditionalOnMissingBean
    ShardedJedisPool shardedJedisPool() {
        log.info("[suppot-odies]:create shardedJedisPool ");
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(redisProperties.getMaxTotal());
        config.setMinIdle(redisProperties.getMinIdle());
        config.setTestWhileIdle(redisProperties.isTestWhileIdle());
        config.setTestOnBorrow(redisProperties.isTestOnBorrow());
        config.setMinEvictableIdleTimeMillis(TimeUnit.MINUTES.toMillis(10L));

        JedisShardInfo info = new JedisShardInfo(redisProperties.getHost(), redisProperties.getPort(), redisProperties.getTimeout());


        info.setPassword(StringUtils.isEmpty(redisProperties.getPassword())?null:redisProperties.getPassword());

        ShardedJedisPool pool = new ShardedJedisPool(config, Collections.singletonList(info));
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





    @ConditionalOnMissingBean
    @Bean
    JedisTemplate jedisTemplate(@Autowired ShardedJedisPool shardedJedisPool){
        log.info("[suppot-odies]:create JedisTemplate ");
        return new JedisTemplateImpl(shardedJedisPool);
    }


    @ConditionalOnMissingBean
    @Bean
    JedisRepositoryContext jedisRepositoryContext(@Autowired JedisTemplate jedisTemplate){
        log.info("[suppot-odies]:create JedisRepositoryContext ");
        return new JedisRepositoryContext(jedisTemplate);
    }



}
