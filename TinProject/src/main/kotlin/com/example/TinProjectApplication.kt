package com.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TinProjectApplication

fun main(args: Array<String>) {
    runApplication<TinProjectApplication>(*args)
}