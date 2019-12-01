package AutoComplete;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.redis.RedisClientFactory;
import org.hibernate.validator.constraints.*;

import javax.validation.Valid;
import javax.validation.constraints.*;

public class AutoCompleteConfiguration extends Configuration {

    @Valid
    @NotNull
    @JsonProperty("redis")
    private RedisClientFactory<String, String> redisClientFactory;

    @JsonProperty
    private String propertyFileName;

    public RedisClientFactory<String, String> getRedisClientFactory() {
        return redisClientFactory;
    }

    public String getPropertyFileName() {
        return propertyFileName;
    }
}
