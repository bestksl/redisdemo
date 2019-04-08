package com.ksl;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import static junit.framework.TestCase.assertEquals;


public class redisdemo {
    private Jedis jedis = new Jedis("127.0.0.1", 6379);

    //单例测试
    @Test
    public void testRedis1() {
        //
        jedis.set("name", "ksl");
        assertEquals("ksl", jedis.get("name"));
        //注意关闭释放资源
        jedis.close();
    }

    //连接池测试
    @Test
    public void testRedis2() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(30);
        jedisPoolConfig.setMaxIdle(10);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, "127.0.0.1", 6379);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set("name1", "kslup7");
            assertEquals("kslup7", jedis.get("name1"));
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
            jedisPool.close();
        }


    }
}
