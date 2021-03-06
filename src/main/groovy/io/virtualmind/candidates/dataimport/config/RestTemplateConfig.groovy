package io.virtualmind.candidates.dataimport.config

import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
@CompileStatic
class RestTemplateConfig {

    @Value('${core.url}')
    String backendUrl

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder){
        builder.rootUri(backendUrl)
                .errorHandler(new RestTemplateErrorHandler())
                .build()
    }

}
