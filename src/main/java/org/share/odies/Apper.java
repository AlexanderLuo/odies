package org.share.odies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.ShardedJedisPool;

/**
 * Auth: Alexander Lo
 * Date: 2020-06-29
 * Description:
 */

@SpringBootApplication
public class Apper implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Apper.class,args);
    }

    @Autowired
    RedisTemplate redisTemplate;


    @Autowired
    ShardedJedisPool jedisPool;


    @Override
    public void run(String... args) throws Exception {


    }
}
