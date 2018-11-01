package com.cmx.chatserver.security.component.session.redis;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.Protocol;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: cmx
 * 集群的redis先写上吧 一般用不上
 */
public class RedisSentinelManager extends BaseRedisManager implements IRedisManager {

    private static final String DEFAULT_HOST = "127.0.0.1:26379,127.0.0.1:26380,127.0.0.1:26381";
    private String host = DEFAULT_HOST;

    private static final String DEFAULT_MASTER_NAME = "mymaster";
    private String masterName = DEFAULT_MASTER_NAME;

    private int timeout = Protocol.DEFAULT_TIMEOUT;

    private int soTimeout = Protocol.DEFAULT_TIMEOUT;

    private String password;

    private int database = Protocol.DEFAULT_DATABASE;

    private JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

    private void init() {
        synchronized (this) {
            if (jedisPool == null) {
                String[] sentinelHosts = host.split(",\\s*");
                Set<String> sentinels = new HashSet<>();
                Collections.addAll(sentinels, sentinelHosts);
                //jedisPool = new JedisSentinelPool(masterName, sentinels, jedisPoolConfig, timeout, soTimeout, password, database);
            }
        }
    }

}
