package com.example.tools

import java.io.File
import java.nio.file.FileVisitResult
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes

//Класс для подсчета размера лежащих файлов в директории

object DirSizeCounter{

    private var size = 0L

    fun countDirSize(path: File): Long {
        Files.walkFileTree(path.toPath(), DirWalker())
        return size
    }

    private class DirWalker : SimpleFileVisitor<Path>() {

        init {
            size = 0L
        }

        override fun visitFile(file: Path?, attrs: BasicFileAttributes?): FileVisitResult {
            kotlin.runCatching {
                val newFile = file?.toAbsolutePath()?.toFile()
                size += newFile!!.length()
            }.onFailure { }
            return super.visitFile(file, attrs)
        }
    }
}

