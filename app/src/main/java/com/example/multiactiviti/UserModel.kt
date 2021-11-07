package com.example.multiactiviti

import java.util.*


data class UserModel (
    var iduser: Int = getAutoId(),
    var email: String = "",
    var role: String = ""
) {
    companion object {
        fun getAutoId(): Int {
            val random = Random()
            return random.nextInt(100)
        }
    }
}