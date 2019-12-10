package com.ifi.trainer_ui.config;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;

import static org.junit.jupiter.api.Assertions.*;

class RestConfigurationTest {

    @Test
    void restConfiguration_shouldBeAConfiguration(){
        assertNotNull(RestConfiguration.class.getAnnotation(Configuration.class));
    }

    @Test
    void restTemplate_shouldExist() {
        var restTemplate = new RestConfiguration().restTemplate();
        assertNotNull(restTemplate);
    }

    @Test
    void trainerApiRestTemplate_shouldBeAnnotated() throws NoSuchMethodException {
        var trainerApiRestTemplate =
                RestConfiguration.class.getDeclaredMethod("trainerApiRestTemplate");
        var bean = trainerApiRestTemplate.getAnnotation(Bean.class);

        assertNotNull(bean);
    }

    @Test
    void restTemplate_shouldBeAnnotated() throws NoSuchMethodException {
        var restTemplate =
                RestConfiguration.class.getDeclaredMethod("restTemplate");
        var bean = restTemplate.getAnnotation(Bean.class);

        assertNotNull(bean);
    }

    @Test
    void trainerApiRestTemplate_shouldHaveBasicAuth() {
        var restConfig = new RestConfiguration();
        restConfig.username = "user";
        restConfig.password = "pass";

        var restTemplate = restConfig.trainerApiRestTemplate();

        assertNotNull(restTemplate);

        var interceptors = restTemplate.getInterceptors();
        assertNotNull(interceptors);
        assertEquals(1, interceptors.size());

        var interceptor = interceptors.get(0);
        assertNotNull(interceptor);

        assertEquals(BasicAuthenticationInterceptor.class, interceptor.getClass());
    }
}