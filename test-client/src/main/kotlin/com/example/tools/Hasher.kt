package com.example.tools

import org.apache.commons.codec.digest.DigestUtils

object Hasher {
    fun sha512Hex(mes: String): String = DigestUtils.sha512Hex(mes)
}