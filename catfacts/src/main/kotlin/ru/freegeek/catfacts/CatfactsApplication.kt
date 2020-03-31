package ru.freegeek.catfacts

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@EnableProcessApplication
@SpringBootApplication
class CatfactsApplication

fun main(args: Array<String>) {
	runApplication<CatfactsApplication>(*args)
}
