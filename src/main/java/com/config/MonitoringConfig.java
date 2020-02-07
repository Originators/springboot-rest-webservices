package com.config;

import io.micrometer.appoptics.AppOpticsConfig;
import io.micrometer.appoptics.AppOpticsMeterRegistry;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.lang.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MonitoringConfig {

    @Value("management.metrics.export.appoptics.api-token")
    private String MY_TOKEN;

    AppOpticsConfig appopticsConfig = new AppOpticsConfig() {
        @Override
        public String apiToken() {
            return MY_TOKEN;
        }

        @Override
        @Nullable
        public String get(String k) {
            return null;
        }
    };
    MeterRegistry registry = new AppOpticsMeterRegistry(appopticsConfig, Clock.SYSTEM);
}
