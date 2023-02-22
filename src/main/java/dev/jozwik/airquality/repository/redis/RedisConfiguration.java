package dev.jozwik.airquality.repository.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;


@Configuration
@EnableRedisRepositories(basePackages = "dev.jozwik.airquality")
public class RedisConfiguration {

    @Bean
    public JedisConnectionFactory getRedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<?, ?> getRedisTemplate() {
        final RedisTemplate<?, ?> template = new RedisTemplate<>();
        template.setConnectionFactory(getRedisConnectionFactory());
        return template;
    }
}
