package AutoComplete.config;

import AutoComplete.cache.AutoCompleteCache;
import com.google.inject.AbstractModule;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;

public class AutoCompleteModule extends AbstractModule {

    @Override
    protected void configure () {
        RedisClient redisClient = RedisClient.create("redis://localhost:6379/");
        final StatefulRedisConnection<String, String> connection = redisClient.connect();
        bind(AutoCompleteCache.class).toInstance(new AutoCompleteCache(connection));
    }
}
