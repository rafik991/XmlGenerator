package pl.echoweb.util;

import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * Rafal on 6/8/2014.
 */
@Component
public class CachedProvider {
    @Value("${mem.cached.env}")
    private String memCachedEnv;
    @Value("${mem.port.number}")
    private Integer portNum;
    @Value("${mem.cached.enable}")
    private Boolean memCacheEnabled;
    private MemcachedClient memcachedClient;
    final int EXPIRE_TIME = 10000;

    public Object getObjectFromMemCache(String key) {
        if (memCacheEnabled)
            return getObject(key);
        return null;
    }

    private Object getObject(String key) {
        Object result = null;
        try {
            memcachedClient = getMemCachedClient();
            result = memcachedClient.get(key);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            memcachedClient.shutdown(1, TimeUnit.SECONDS);
            return result;
        }
    }

    private MemcachedClient getMemCachedClient() throws IOException {
        return new MemcachedClient(new InetSocketAddress(memCachedEnv, portNum));
    }

    public void storeObjectInMemCache(String key, Object obj) {
        if (memCacheEnabled)
            storeObject(key, obj);
    }

    private void storeObject(String key, Object obj) {
        try {
            memcachedClient = getMemCachedClient();
            memcachedClient.add(key, EXPIRE_TIME, obj);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            memcachedClient.shutdown(1, TimeUnit.SECONDS);
        }
    }
}
