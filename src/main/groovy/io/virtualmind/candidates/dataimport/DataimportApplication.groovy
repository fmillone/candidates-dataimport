package io.virtualmind.candidates.dataimport

import groovy.transform.CompileStatic
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
@CompileStatic
class DataimportApplication {

	static void main(String[] args) {
		SpringApplication.run DataimportApplication, args
	}
}
