package io.baris.performance.lettuce;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisConnection;
import com.lambdaworks.redis.codec.ByteArrayCodec;
import io.baris.performance.model.Genie;
import org.springframework.cache.support.AbstractValueAdaptingCache;

import java.io.IOException;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class LettuceCache extends AbstractValueAdaptingCache {

    private final String name;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final RedisConnection<byte[], byte[]> connection;

    LettuceCache(String name, RedisClient redisClient) {
        super(true);
        checkNotNull(redisClient);
        checkArgument(name != null && name.length() > 0);
        this.name = name;
        connection = redisClient.connect(ByteArrayCodec.INSTANCE);
    }

    @Override
    protected Object lookup(Object key) {
        try {
            final byte[] bytes = connection.get(objectMapper.writeValueAsBytes(key));
            return objectMapper.readValue(bytes, 0, bytes.length, Genie.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public RedisConnection<byte[], byte[]> getNativeCache() {
        return connection;
    }

    @Override
    public void put(Object key, Object value) {
        try {
            connection.set(objectMapper.writeValueAsBytes(key), objectMapper.writeValueAsBytes(value));
            return;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void evict(Object key) {
        try {
            connection.expire(objectMapper.writeValueAsBytes(key), 0);
            return;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }
}
