package com.cmx.chatserver.security.component.session.redis;

import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.HashSet;
import java.util.Set;

public abstract class BaseRedisManager implements IRedisManager {

    @Autowired
    protected volatile JedisPool jedisPool;


    /** expire time in seconds*/
    protected static final int DEFAULT_EXPIRE = 3600;
    protected int expire = DEFAULT_EXPIRE;

    /** the number of elements returned at every iteration*/
    protected static final int DEFAULT_COUNT = 100;
    protected int count = DEFAULT_COUNT;

    /**
     * get value from redis
     * @param key
     * @return
     */
    @Override
    public byte[] get(byte[] key){
        if (key == null) {
            return null;
        }
        byte[] value = null;
        Jedis jedis = jedisPool.getResource();
        try{
            value = jedis.get(key);
        }finally{
            jedis.close();
        }
        return value;
    }

    /**
     * set
     * @param key
     * @param value
     * @return
     */
    @Override
    public byte[] set(byte[] key,byte[] value){
        if (key == null) {
            return null;
        }
        Jedis jedis = jedisPool.getResource();
        try{
            jedis.set(key,value);
            if(this.getExpire() != 0){
                jedis.expire(key, this.getExpire());
            }
        }finally{
            jedis.close();
        }
        return value;
    }

    /**
     * set
     * @param key
     * @param value
     * @param expire
     * @return
     */
    @Override
    public byte[] set(byte[] key,byte[] value,int expire){
        if (key == null) {
            return null;
        }
        Jedis jedis = jedisPool.getResource();
        try{
            jedis.set(key,value);
            if(expire != 0){
                jedis.expire(key, expire);
            }
        }finally{
            jedis.close();
        }
        return value;
    }

    /**
     * del
     * @param key
     */
    @Override
    public void del(byte[] key){
        if (key == null) {
            return;
        }
        Jedis jedis = jedisPool.getResource();
        try{
            jedis.del(key);
        }finally{
            jedis.close();
        }
    }

    /**
     * size
     */
    @Override
    public Long dbSize(){
        Long dbSize;
        Jedis jedis = jedisPool.getResource();
        try{
            dbSize = jedis.dbSize();
        }finally{
            jedis.close();
        }
        return dbSize;
    }

    /**
     * keys
     *
     * @param pattern
     * @return
     */
    public Set<byte[]> keys(byte[] pattern) {
        Set<byte[]> keys = null;
        Jedis jedis = jedisPool.getResource();

        try{
            keys = new HashSet<byte[]>();
            ScanParams params = new ScanParams();
            params.count(count);
            params.match(pattern);
            byte[] cursor = ScanParams.SCAN_POINTER_START_BINARY;
            ScanResult<byte[]> scanResult;
            do{
                scanResult = jedis.scan(cursor,params);
                keys.addAll(scanResult.getResult());
                cursor = scanResult.getCursorAsBytes();
            }while(scanResult.getStringCursor().compareTo(ScanParams.SCAN_POINTER_START) > 0);
        }finally{
            jedis.close();
        }
        return keys;

    }

    @Override
    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
