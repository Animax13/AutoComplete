package AutoComplete.cache;

import com.google.inject.Singleton;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

@Singleton
public class AutoCompleteCache {

    private RedisCommands<String, String> commands;

    public AutoCompleteCache (StatefulRedisConnection<String, String> connection) {
        commands = connection.sync();
    }

    public void putInCache (String key, String value) {
        commands.set(key, value);
    }

    public String getFromCache (String key) {
        return commands.get(key);
    }
}
