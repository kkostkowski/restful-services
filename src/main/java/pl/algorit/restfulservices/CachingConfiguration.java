package pl.algorit.restfulservices;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;

import java.util.Arrays;

@Configuration
@EnableCaching
public class CachingConfiguration {

    @Bean
    @Primary
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheCacheManager().getObject());
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheCacheManager() {
        EhCacheManagerFactoryBean factory = new EhCacheManagerFactoryBean();
        factory.setConfigLocation(new ClassPathResource("ehcache.xml"));
        factory.setShared(true);
        return factory;
    }

    @Bean("serviceStatusBasedCacheManager")
    public CacheManager serviceStatusBasedCacheManager(BackendServiceStatusManager backendServiceStatusManager) {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                new ConcurrentMapCache("comments") {
                    @Override
                    protected Object lookup(Object key) {
                        if (backendServiceStatusManager.isServiceAvailable("commentsService"))
                            return super.lookup(key);
                        else {
                            return null;
                        }
                    }
                }
        ));
        return cacheManager;
    }
}