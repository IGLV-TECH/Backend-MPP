package mpp.kotlin.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication

@SpringBootApplication
@EntityScan("domain")
class BackendApplication

fun main(args: Array<String>) {
	runApplication<BackendApplication>(*args)
}
