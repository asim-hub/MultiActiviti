package com.example.multiactiviti

import java.util.*

data class StudentModel (
    var idstud: Int = getAutoId(),
    var name_stud: String = "",
    var group_stud: String = ""
) {
    companion object {
        fun getAutoId(): Int {
            val random = Random()
            return random.nextInt(100)
        }
    }
}