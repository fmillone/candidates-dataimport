package io.virtualmind.candidates.dataimport

import groovy.transform.CompileStatic
import io.virtualmind.candidates.dataimport.job.JobService
import org.springframework.batch.core.JobExecution
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController()
@CompileStatic
class ImportController {

    @Autowired
    JobService service


    @PostMapping('/candidates')
    Map importCandidates(@RequestParam('file') MultipartFile file) {
        filterResponse(service.startImportCandidates(file))
    }

    private static Map filterResponse(JobExecution it) {
        [id        : it.id, status: it.status,
         startTime : it.startTime, endTime: it.endTime,
         exitStatus: it.exitStatus]
    }
}
