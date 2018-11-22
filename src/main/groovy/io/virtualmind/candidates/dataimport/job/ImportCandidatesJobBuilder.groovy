package io.virtualmind.candidates.dataimport.job

import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import io.virtualmind.candidates.dataimport.model.Candidate
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.item.validator.ValidationException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.time.Instant

@Service
@CompileStatic
class ImportCandidatesJobBuilder {

    @Autowired
    JobBuilderFactory jobBuilder

    @Autowired
    StepBuilderFactory stepBuilder

    @Autowired
    CandidatePublisher candidatePublisher

    Job createJob(Sheet sheet) {
        jobBuilder.get("importCandidates ${Instant.now()}")
                .start(importCandidatesStep(sheet))
                .build()
    }

    @CompileDynamic
    private Step importCandidatesStep(Sheet sheet) {
        stepBuilder.get('import candidates step')
                .<Row, Candidate> chunk(1)
                .reader(new RowReader(sheet))
                .processor(new CandidatesProcessor())
                .writer(candidatePublisher)
                .build()
    }

}
