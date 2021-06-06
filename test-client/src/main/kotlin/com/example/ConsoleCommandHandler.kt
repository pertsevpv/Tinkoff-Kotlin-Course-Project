package com.example

import com.example.services.queries.DeleteFileRequest
import com.example.services.queries.GetFileRequest
import com.example.services.queries.UploadFileRequest
import com.example.services.AWSService
import com.example.services.FileService
import com.example.services.UserService
import com.example.session.CurrentSession
import java.io.File
import java.util.*

class ConsoleCommandHandler {

    private val sc = Scanner(System.`in`)

    fun start() {
        while (true) {
            println("\nEnter your command: ")
            kotlin.runCatching {
                when (sc.next().toLowerCase()) {
                    "register" -> register()
                    "authorize",
                    "login" -> login()

                    "logout",
                    "exit" -> logout()

                    "myfiles" -> getMyFiles()
                    "share" -> share()
                    "ablefiles" -> ableFiles()
                    "userlist" -> userList()
                    "upload" -> upload()
                    "download" -> download()

                    "delete",
                    "remove" -> delete()

                    "quit",
                    "q" -> return

                    else -> {
                        println("Unknown command")
                    }
                }
            }.onFailure {
                println(it.message)
            }
        }
    }

    private fun getMyFiles() {
        val resp = FileService().getMyFiles(CurrentSession.currentUser!!.id!!)
        resp.forEach { println(it) }
    }

    private fun register() {
        if (CurrentSession.currentUser != null) {
            println("You are authorized already. Please, exit to change account")
        } else {
            print("UserName: ")
            val userName = sc.next()
            print("Password: ")
            val password = sc.next()
            println(CurrentSession.register(userName, password))
        }
    }

    private fun login() {
        if (CurrentSession.currentUser != null) {
            println("You are authorized already. Please, exit to change account")
        } else {
            print("UserName: ")
            val userName = sc.next()
            print("Password: ")
            val password = sc.next()
            println(CurrentSession.authorize(userName, password))
        }
    }

    private fun logout() {
        println(CurrentSession.exited())
    }

    private fun upload() {
        CurrentSession.checkAuthorization()
        print("File to upload: ")
        val filePath = sc.next()
        print("Upload as: ")
        val uplPath = sc.next()
        print("File status (public/private): ")
        val status = sc.next().toLowerCase()
        if ((status != "private") && (status != "public")) {
            println("Incorrect status")
            println("Status must be \"public\" or \"private\"")
        } else {
            val req = UploadFileRequest(
                byteArray = File(filePath).readBytes(),
                folder = CurrentSession.currentUser!!.name!!,
                name = uplPath,
                status = status
            )
            val res = AWSService().uploadFile(req)
            println(res.message)
        }
    }

    private fun download() {
        CurrentSession.checkAuthorization()
        print("File to download: ")
        val filePath = sc.next()
        print("From folder: ")
        val foldPath = sc.next()
        print("Download as: ")
        val dowPath = sc.next()
        if (FileService().checkAccess(CurrentSession.currentUser!!.id!!, filePath, foldPath).not()) {
            println("You haven't access to this file")
            return
        }
        val req = GetFileRequest(
            fileName = filePath,
            fileFolder = foldPath,
            toPath = dowPath
        )
        val res = AWSService().downloadFile(req)
        if (res) println("Downloaded")
        else {
            println("Failed to Download")
        }
    }

    private fun share() {
        CurrentSession.checkAuthorization()
        print("UserName: ")
        val userName = sc.next()
        print("FileName: ")
        val fileName = sc.next()
        val resp = FileService().giveAccess(
            CurrentSession.currentUser!!.id!!,
            userName,
            CurrentSession.currentUser!!.name!!,
            fileName
        )
        if (resp.status) println("You shared $fileName with $userName")
        else println(resp.message)
    }

    private fun ableFiles() {
        FileService().getAllAbleFiles(CurrentSession.currentUser!!.id!!).forEach {
            println(it)
        }
    }

    private fun userList() {
        println("Getting all users")
        runCatching {
            UserService().getAllUsers().forEach { println(it) }
        }.onFailure {
            println("Failed to get all users")
        }
    }

    private fun delete() {
        CurrentSession.checkAuthorization()
        print("File to delete: ")
        val fileName = sc.next()
        val req = DeleteFileRequest(fileName, CurrentSession.currentUser!!.name!!)
        val res = AWSService().deleteFile(req)
        println(res.message)
    }
}