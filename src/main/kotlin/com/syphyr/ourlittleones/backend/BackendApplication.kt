package com.syphyr.ourlittleones.backend

import com.syphyr.ourlittleones.backend.database.configuration.DatabaseConfiguration
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class BackendApplication{
	@Bean
	fun commandLineRunner(dbConfig: DatabaseConfiguration): CommandLineRunner {
		return CommandLineRunner {
			dbConfig.seed()
		}
	}
}

fun main(args: Array<String>) {
	runApplication<BackendApplication>(*args)
}
