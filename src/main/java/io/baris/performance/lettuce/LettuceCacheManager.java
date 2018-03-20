package io.baris.performance.lettuce;


import com.lambdaworks.redis.RedisClient;
import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;

import java.util.Collection;
import java.util.Collections;

public class LettuceCacheManager extends AbstractCacheManager {

    private RedisClient redisClient;

    public LettuceCacheManager(RedisClient redisClient) {
        this.redisClient = redisClient;
    }

    @Override
    protected Collection<? extends Cache> loadCaches() {
        return Collections.emptyList();
    }

    @Override
    protected Cache getMissingCache(String name) {
        return new LettuceCache(name, redisClient);
    }
}
