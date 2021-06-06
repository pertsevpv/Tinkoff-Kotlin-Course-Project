package com.example

import io.restassured.RestAssured

fun main() {
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
    ConsoleCommandHandler().start()
}

