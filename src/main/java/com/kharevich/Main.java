package com.kharevich;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.sync.RedisCommands;
import org.junit.Assert;
import redis.clients.jedis.Jedis;

/**
 * Created by zeremit on 6/23/16.
 */
public class Main {

    private static final String KEY = "KEY";

    private static final String VALUE = "VALUE";

    private static final String PING_RESPONSE = "PONG";

    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost");
        jedis.select(2);
        Assert.assertEquals(jedis.ping(), PING_RESPONSE);
        jedis.set(KEY, VALUE);
        Assert.assertEquals(jedis.get(KEY), VALUE);
        jedis.flushAll();
        jedis.close();

        RedisClient redisClient = RedisClient.create("redis://localhost/");
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> dbConnection = connection.sync();
        dbConnection.select(3);
        Assert.assertEquals(dbConnection.ping(), PING_RESPONSE);
        dbConnection.set(KEY, VALUE);
        Assert.assertEquals(dbConnection.get(KEY), VALUE);
        dbConnection.flushall();
        dbConnection.close();
        connection.close();
    }
}
