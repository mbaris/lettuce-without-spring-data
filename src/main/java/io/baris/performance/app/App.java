package io.baris.performance.app;

import com.lambdaworks.redis.RedisClient;
import io.baris.performance.lettuce.LettuceCacheManager;
import io.baris.performance.model.Genie;
import io.baris.performance.model.GenieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@EnableCaching
@Component
public class App {

    @Autowired
    private GenieRepository genieRepository;

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context
                = new AnnotationConfigApplicationContext("io.baris");

        App app = context.getBean(App.class);
        app.start();
    }

    private void start() throws InterruptedException {
        final long begin = System.nanoTime();
        for (int n = 0; n <= 100000; n++) {
            String key = UUID.randomUUID().toString();
            final Genie genie = new Genie().setName(UUID.randomUUID().toString()).setId(key);
            genieRepository.createGenieLettuce(genie);
            final Genie byIdLettuce = genieRepository.getByIdLettuce(key);
            if(!byIdLettuce.equals(genie)){
                throw new IllegalStateException();
            }
        }
        System.out.println(Thread.currentThread().getName() + " took :" + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - begin));
    }

    @Bean
    CacheManager lettuceCacheManager() {
        RedisClient redisClient = RedisClient.create("redis://localhost");
        return new LettuceCacheManager(redisClient);
    }
}
