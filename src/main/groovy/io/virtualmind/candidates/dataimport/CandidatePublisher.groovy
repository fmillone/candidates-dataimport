package io.virtualmind.candidates.dataimport


import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.virtualmind.candidates.dataimport.model.Candidate
import org.springframework.batch.item.ItemWriter

@CompileStatic
@Slf4j
class CandidatePublisher implements ItemWriter<Candidate> {


    @Override
    void write(List<? extends Candidate> items) throws Exception {
        items?.each {
            publish it
        }
    }

    private void publish(Candidate candidate) {
        log.info("publishing candidate $candidate")
    }
}
