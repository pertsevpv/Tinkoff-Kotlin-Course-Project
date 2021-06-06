package com.example.database.entities

import javax.persistence.*

@Entity
@Table(name = "FILE_TABLE")
class File(_fileName: String, _folderName: String) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(name = "FILE_NAME")
    var fileName: String? = null

    @Column(name = "FOLDER_NAME")
    var folderName: String? = null

    init {
        fileName = _fileName
        folderName = _folderName
    }

    constructor() : this("", "")

    override fun toString(): String {
        return "$folderName/$fileName"
    }
}
