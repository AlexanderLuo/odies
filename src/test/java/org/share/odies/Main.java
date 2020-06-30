package org.share.odies;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.share.odies.dao.UserRepository;
import org.share.odies.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.ShardedJedisPool;

/**
 * Auth: Alexander Lo
 * Date: 2020-06-29
 * Description:
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class Main {

    @Autowired
    ShardedJedisPool shardedJedisPool;

    @Autowired
    UserRepository userRepository;


    @Test
    public void set(){
        shardedJedisPool.getResource().set("a",""+System.currentTimeMillis());
        System.err.println(shardedJedisPool.getResource().get("a"));
    }



    @Test
    public void roSet(){
        User user = new User();
        user.setId(1L);
        user.setName("测试");
        user.setAddr("地址");
        userRepository.save(user);
    }


    @Test
    public void roGet(){
        System.err.println(JSON.toJSONString(userRepository.findById(1L)));
    }



    @Test
    public void findAll(){
        System.err.println(JSON.toJSONString(userRepository.findAll()));
    }



}
