package com.mobile.mobile_components.model

import java.util.ArrayList

object CurrentData {
    var usersList = ArrayList<User>()
    private var currentUser : User? = null

    fun getCurrentUser() : User? {
        return currentUser
    }

    fun setCurrentUser(user: User?){
        currentUser = user
    }

    fun addUser(s : User){
        usersList.add(s)
    }

    fun login(email: String?, password: String?): User? {
        for(u: User in usersList){
            if(u.email == email && u.password == password){
                return u
            }
        }
        return null
    }

    fun getUserByEmail(email: String?): User? {
        for(u: User in usersList){
            if(u.email == email){
                return u
            }
        }
        return null
    }

    fun updateUserInfo(user: User){
        for(u: User in usersList!!){
            if(u.email == user.email){
                u.phone = user.phone
            }
        }
    }

}