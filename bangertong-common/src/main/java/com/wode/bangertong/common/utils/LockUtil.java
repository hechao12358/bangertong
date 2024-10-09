package com.wode.bangertong.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class LockUtil {

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 获得锁
     *
     * @param lock
     * @return
     */
    public boolean lock(String lock) {
        return (boolean) redisTemplate.execute((RedisCallback) connection -> {
            //获取时间毫秒值
            long expireAt = System.currentTimeMillis() + 5000 + 1;
            //获取锁
            Boolean acquire = connection.setNX(lock.getBytes(), String.valueOf(expireAt).getBytes());
            if (acquire) {
                return true;
            } else {
                byte[] bytes = connection.get(lock.getBytes());
                //非空判断
                if (Objects.nonNull(bytes) && bytes.length > 0) {
                    long expireTime = Long.parseLong(new String(bytes));
                    // 如果锁已经过期
                    if (expireTime < System.currentTimeMillis()) {
                        // 重新加锁，防止死锁
                        byte[] set = connection.getSet(lock.getBytes(), String.valueOf(System.currentTimeMillis() + 5000 + 1).getBytes());
                        return Long.parseLong(new String(set)) < System.currentTimeMillis();
                    }
                }
            }
            return false;
        });
    }

    /**
     * 删除锁
     *
     * @param key
     */
    public void deleteLock(String key) {
        redisTemplate.delete(key);
    }
}
