package io.virtualmind.candidates.dataimport.job

import groovy.json.JsonSlurper
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.virtualmind.candidates.dataimport.model.Candidate
import org.springframework.batch.item.ItemWriter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

import java.time.LocalDate
import java.time.format.DateTimeFormatter

@CompileStatic
@Slf4j
@Component
class CandidatePublisher implements ItemWriter<Candidate> {

    @Autowired
    RestTemplate restTemplate

    @Override
    void write(List<? extends Candidate> items) throws Exception {
        items?.each {
            publish it
        }
    }

    private void publish(Candidate candidate) {
        log.debug("publishing candidate $candidate")
        ResponseEntity<String> response = restTemplate.postForEntity('/graphql', entityFor(candidate), String)
        if (!response.statusCode.is2xxSuccessful()) {
            log.info("cannot import $candidate due ${extractErrorMessage(response.body)}")
        } else {
            log.info("$candidate Successfully imported with id ${extractId(response.body)}")
        }
    }

    @CompileDynamic
    private static String extractId(String body) {
        new JsonSlurper().parseText(body).data?.createUser?.id
    }

    private static HttpEntity entityFor(Candidate candidate) {
        new HttpEntity([query: createQuery(candidate), variables: ''])
    }

    static String createQuery(Candidate candidate) {
        """
        mutation {
            createUser(
            ${field('name', candidate.name)}
            ${field('email', candidate.email)}
            ${field('skill', candidate.profile)}
            ${field('residence', candidate.residence)},
            ${dateField('lastContact', candidate.lastContact)},
            role: "candidate"
            ){id}}
        """
    }

    private static String field(String name, def value) {
        value ? "$name: \"$value\"" : ''
    }

    private static String dateField(String name, LocalDate localDate) {
        localDate ? field(name, DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(localDate.atStartOfDay())) : ''
    }

    @CompileDynamic
    private static String extractErrorMessage(String response) {
        (response =~ /"message":"([^(]+)/)[0][1]
    }

}