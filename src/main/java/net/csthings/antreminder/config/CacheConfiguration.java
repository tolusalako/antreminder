package net.csthings.antreminder.config;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.google.common.cache.CacheBuilder;

import net.csthings.antreminder.entity.dto.AccountReminderDto;
import net.csthings.antreminder.entity.dto.ReminderDto;
import net.csthings.antreminder.entity.dto.ValidationAccountDto;

@Configuration
@ComponentScan("net.csthings.antreminder.entity.dto")
@EnableCaching
public class CacheConfiguration {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        GuavaCache remindersCache = new GuavaCache(ReminderDto.TABLE_NAME,
                CacheBuilder.newBuilder().maximumSize(150).build());
        GuavaCache accountReminderCache = new GuavaCache(AccountReminderDto.TABLE_NAME,
                CacheBuilder.newBuilder().maximumSize(100).expireAfterAccess(5, TimeUnit.MINUTES).build());
        GuavaCache validationCache = new GuavaCache(ValidationAccountDto.TABLE_NAME,
                CacheBuilder.newBuilder().maximumSize(100).expireAfterAccess(5, TimeUnit.MINUTES).build());
        cacheManager.setCaches(Arrays.asList(remindersCache, accountReminderCache, validationCache));
        return cacheManager;
    }

}
