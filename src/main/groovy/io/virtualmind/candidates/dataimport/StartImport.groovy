package io.virtualmind.candidates.dataimport

import groovy.util.logging.Slf4j
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component

@Component
class StartImport implements ApplicationRunner {

    @Autowired
    JobLauncher launcher

    @Autowired
    ImportCandidatesJobBuilder candidatesJobBuilder

    @Autowired
    ResourceLoader resourceLoader

    @Override
    void run(ApplicationArguments args) throws Exception {

        Resource resource = resourceLoader.getResource('Candidates.xlsx')
        Workbook workbook = WorkbookFactory.create(resource.getInputStream())
        launcher.run(candidatesJobBuilder.createJob(workbook.first()), new JobParameters())
    }
}
