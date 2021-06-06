package com.example.database.entities

//Заготовки

import javax.persistence.*

@Entity
@Table(name = "USER_TABLE")
class User(_name: String, _hash: String) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(name = "NAME")
    var name: String? = null

    @Column(name = "HASH")
    var hash: String? = null

    init {
        name = _name
        hash = _hash
    }

    constructor() : this("", "")

}

