package com.example.database.entities

import javax.persistence.*

@Entity
@Table(name = "FILE_STATUS_TABLE")
class FileStatus(_userId: Int, _fileId: Int, _ownerId: Int, _status: String) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(name = "USER_ID")
    var userId: Int? = null

    @Column(name = "FILE_ID")
    var fileId: Int? = null

    @Column(name = "OWNER_ID")
    var ownerId: Int? = null

    @Column(name = "STATUS")
    var status: String? = null

    init {
        userId = _userId
        fileId = _fileId
        ownerId = _ownerId
        status = _status
    }

    constructor() : this(-1, -1, -1, "")
}
