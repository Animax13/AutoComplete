package AutoComplete.config;

import AutoComplete.cache.AutoCompleteCache;
import com.google.common.io.Resources;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;

import java.net.URL;
import java.util.Properties;

public class AutoCompleteModule extends AbstractModule {

    private String propertyFileName;

    public AutoCompleteModule(String propertyFileName) {
        this.propertyFileName = propertyFileName;
    }

    @Override
    protected void configure () {
        Properties properties = new Properties();
        readPropertyFile(properties, propertyFileName+".properties");
        Names.bindProperties(binder(), properties);

        RedisClient redisClient = RedisClient.create("redis://localhost:6379/");
        final StatefulRedisConnection<String, String> connection = redisClient.connect();
        bind(AutoCompleteCache.class).toInstance(new AutoCompleteCache(connection));
    }

    protected void readPropertyFile(Properties properties, String fileName) {
        try {
            URL resource = Resources.getResource(fileName);
            properties.load(resource.openStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
