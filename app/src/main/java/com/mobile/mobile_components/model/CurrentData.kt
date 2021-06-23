package com.mobile.mobile_components.model

object CurrentData {

    private var currentUser : Student? = null

    fun getCurrentUser() : Student? {
        return currentUser
    }

    fun setCurrentUser(student: Student?){
        currentUser = student
    }

}