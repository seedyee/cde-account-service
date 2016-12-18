package io.cde.account.config;

import java.net.UnknownHostException;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;

/**
 * @author lcl
 * 使用redis作为缓存配置类
 */
@Configuration
@EnableCaching(proxyTargetClass = true) //启用缓存,Consider injecting the bean as one of its interfaces or forcing the use of CGLib-based proxies by setting proxyTargetClass=true on @EnableCaching
public class RedisCacheConfiguration extends CachingConfigurerSupport {

    /**
     * JedisConnectionFactor对象.
     *
     * @return jedisConnectionFactory
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        return  new JedisConnectionFactory();
    }

    /**
     * 配置StringRedisTemplate实体.
     *
     * @return StringRedisTemplate实体
     */
    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        final StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(jedisConnectionFactory());
        return stringRedisTemplate;
    }

    /**
     * 配置RedisTemplate.
     *
     * @return 返回RedisTemplate对象
     * @throws UnknownHostException 没有配置host异常
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() throws UnknownHostException {
        final RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        final Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(
                Object.class);
        final ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
        om.enableDefaultTyping(DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        //使用StringRedisSerializer作为序列化key，默认是使用JdkSerializationRedisSerializer
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 配置redis缓存管理对象.
     *
     * @param redisTemplate redisTemplate对象
     * @return 返回RedisCacheManager
     */
    @Bean
    public CacheManager cacheManager(final RedisTemplate<?, ?> redisTemplate) {
        return new RedisCacheManager(redisTemplate);
    }
}
