package io.virtualmind.candidates.dataimport.config

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.client.ResponseErrorHandler

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR

@Slf4j
@CompileStatic
class RestTemplateErrorHandler implements ResponseErrorHandler {

    @Override
    boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        HttpStatus.Series series = httpResponse.statusCode.series()
        return series == CLIENT_ERROR || series == SERVER_ERROR
    }

    @Override
    void handleError(ClientHttpResponse httpResponse) throws IOException {
        log.info("request failed with status code ${httpResponse.statusCode}")
    }
}
