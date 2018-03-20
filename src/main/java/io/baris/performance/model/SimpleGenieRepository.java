package io.baris.performance.model;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class SimpleGenieRepository implements GenieRepository {

    private static final String GENIE_CACHE_NAME = "genies";

    @Cacheable(cacheNames = GENIE_CACHE_NAME)
    public Genie getByIdLettuce(String id) throws InterruptedException {
        System.out.println("this should not happen");
        Thread.sleep(5000);
        throw new RuntimeException("this should not happen");
    }

    @CachePut(cacheNames = GENIE_CACHE_NAME, key = "#genie.getId()")
    public Genie createGenieLettuce(Genie genie) {
        return genie;
    }
}