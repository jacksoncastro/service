package br.com.jackson.configuration;

import java.util.Random;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.uncommons.maths.random.MersenneTwisterRNG;

@Configuration
public class AppConfig {


    @Bean
    public Random getRandom() {
        return new MersenneTwisterRNG();
    }
}