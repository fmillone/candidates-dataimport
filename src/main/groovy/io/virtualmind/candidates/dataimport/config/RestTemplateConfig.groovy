package io.virtualmind.candidates.dataimport.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class RestTemplateConfig {

    @Value('${backend.url}')
    String backendUrl

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder){
        builder.rootUri(backendUrl)
                .errorHandler(new RestTemplateErrorHandler())
                .build()
    }

}
