package com.sunset.rider.lab.msvclabroom.config;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeneralConfig {

    @Bean
    public WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }
}
