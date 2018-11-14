package io.virtualmind.candidates.dataimport

import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import io.virtualmind.candidates.dataimport.model.Candidate
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.springframework.batch.core.ChunkListener
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.listener.ChunkListenerSupport
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@CompileStatic
class ImportCandidatesJobBuilder {

    @Autowired
    JobBuilderFactory jobBuilder

    @Autowired
    StepBuilderFactory stepBuilder

    Job createJob(Sheet sheet) {
        jobBuilder.get('importCandidates')
                .start(importCandidatesStep(sheet))
                .build()
    }

    @CompileDynamic
    private Step importCandidatesStep(Sheet sheet) {
        stepBuilder.get('import candidates step')
                .<Row, Candidate> chunk(1)
                .reader(new RowReader(sheet))
                .processor(new CandidatesProcessor())
                .writer(new CandidatePublisher())
//                .faultTolerant()
//                .skip(Exception)
//                .skipLimit(20000)
                .build()
    }

}
