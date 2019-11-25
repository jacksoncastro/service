package br.com.jackson.configuration;

import java.util.Random;
import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.uncommons.maths.random.MersenneTwisterRNG;

@Configuration
public class AppConfig {


    @Bean
    public Random getRandom() {
        return new MersenneTwisterRNG();
    }


    @Bean
    public Executor asyncExecutor() {
    	ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    	executor.setCorePoolSize(2);
    	executor.setMaxPoolSize(5);
    	executor.setQueueCapacity(500);
    	executor.initialize();
    	return executor;
    }
}