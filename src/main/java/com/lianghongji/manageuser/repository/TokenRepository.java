package com.lianghongji.manageuser.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

/**
 * 用户相关的Repository
 *
 * @author liang.hongji
 */
@Repository
public class TokenRepository {

    private static final Logger LOG = LoggerFactory.getLogger(TokenRepository.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String MANAGE_TOKEN_PREFIX = "gmdropship:manageuser:toekn:";

    /**
     *保存用户token
     * 时长为30分钟
     */
    public void saveManageUser(String token, Long id){
        redisTemplate.opsForValue().set(MANAGE_TOKEN_PREFIX + token, id + "", 60 * 30, TimeUnit.SECONDS);
    }

    public Long findManageUserAndUpdate(String token){
        String userId = redisTemplate.opsForValue().get(MANAGE_TOKEN_PREFIX + token);
        if (userId == null){
            return 0L;
        }
        saveManageUser(token, Long.parseLong(userId));
        return Long.parseLong(userId);
    }
}
