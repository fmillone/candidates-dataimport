package io.virtualmind.candidates.dataimport.job

import groovy.transform.CompileStatic
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
@CompileStatic
class JobService {

    @Autowired
    JobLauncher launcher

    @Autowired
    ImportCandidatesJobBuilder candidatesJobBuilder

    JobExecution startImportCandidates(MultipartFile file){
        Workbook workbook = WorkbookFactory.create(file.getInputStream())
        launcher.run(candidatesJobBuilder.createJob(workbook.first()), new JobParameters())
    }
}
