package com.cmx.chatserver.security.config;

import lombok.Data;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
public class RedisConfig {

    private String host;

    private Integer port;

    private Integer timeout;

    private String password;

    private Integer database;

    @Bean
    public JedisPool jedisPool() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        /** 最大空闲数*/
        config.setMaxIdle(1);
        config.setMaxTotal(100);
        config.setMinIdle(0);
        String pwd = (password.equals("") || password == null) ? null : password;
        return new JedisPool(config, host, port, 2000, pwd, database);
    }


}
