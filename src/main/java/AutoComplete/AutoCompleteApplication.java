package AutoComplete;

import AutoComplete.config.AutoCompleteModule;
import AutoComplete.resources.AutoCompleteResource;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.dropwizard.Application;
import io.dropwizard.redis.RedisClientBundle;
import io.dropwizard.redis.RedisClientFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class AutoCompleteApplication extends Application<AutoCompleteConfiguration> {

    private static Injector injector;

    public static void main(final String[] args) throws Exception {
        new AutoCompleteApplication().run(args);
    }

    private final RedisClientBundle<String, String, AutoCompleteConfiguration> redis = new RedisClientBundle<String, String, AutoCompleteConfiguration>() {
        @Override
        public RedisClientFactory<String, String> getRedisClientFactory(AutoCompleteConfiguration configuration) {
            return configuration.getRedisClientFactory();
        }
    };

    @Override
    public String getName() {
        return "AutoComplete";
    }

    @Override
    public void initialize(final Bootstrap<AutoCompleteConfiguration> bootstrap) {
        super.initialize(bootstrap);
        bootstrap.addBundle(redis);
    }

    @Override
    public void run(final AutoCompleteConfiguration configuration,
                    final Environment environment) {
        injector = Guice.createInjector(new AutoCompleteModule());
        environment.jersey().register(injector.getInstance(AutoCompleteResource.class));
    }

}
