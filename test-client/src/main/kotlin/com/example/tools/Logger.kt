package com.example.tools

import java.io.BufferedWriter
import java.io.FileWriter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Logger {

    enum class TAGS {
        UPLOAD, DOWNLOAD, QUERY, FAILURE, GET, DELETE
    }

    fun log(str: String, tag: TAGS) {
        println(str)
        runCatching {
            BufferedWriter(FileWriter("logs/${curDate()}.log", true)).use {
                it.apply {
                    write("${curTime()} [$tag]")
                    write("\n")
                    write(str)
                    write("\n\n")

                }
            }
        }.onFailure {

        }
    }

    private fun curDate(): String =
        LocalDateTime.now().format(DateTimeFormatter.ISO_DATE)

    private fun curTime(): String =
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
}


