package io.virtualmind.candidates.dataimport

import groovy.transform.CompileStatic
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.SimpleAsyncTaskExecutor
import org.springframework.core.task.TaskExecutor

@Configuration
@EnableBatchProcessing
@CompileStatic
class BatchConfig {

    @Bean
    TaskExecutor taskExecutor() {
        new SimpleAsyncTaskExecutor()
    }
}
