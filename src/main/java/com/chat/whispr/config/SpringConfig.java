package com.chat.whispr.config;

import ch.rasc.sse.eventbus.config.EnableSseEventBus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
@EnableSseEventBus
public class SpringConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    /*@Bean
    public ObjectMapper objectMapper() { return new ObjectMapper();}*/
}
