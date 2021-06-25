package com.mobile.mobile_components.model

import java.util.ArrayList

object CurrentData {
    var usersList = ArrayList<User>()
    var salesList = ArrayList<Sale>()
    private var currentUser : User? = null

    init{
        salesList.add(Sale(1,"Carro Toyota","Está como Nuevo", 800.00, 123.0, 12.0, 82811234 ))
        salesList.add(Sale(2,"Moto Yamaha","50 mil Kilómetros", 1000.00, 123.0, 12.0, 82811234 ))
        salesList.add(Sale(3,"Laptop Dell","Usada solo para la Universidad", 3.99, 123.0, 12.0, 82811234 ))
        salesList.add(Sale(4,"Vasos de Vidrio","Sin ningún rayón", 7.99, 123.0, 12.0, 82811234 ))
        salesList.add(Sale(5,"Cuchillos","Totalmente Afilados", 5.00, 123.0, 12.0, 82811234 ))
    }

    fun getSales() : ArrayList<Sale>{
        return salesList;
    }

    init{
        usersList.add(User("java@gmail.com","123","Javier",
            "Amador Delgado","60804290"))

        usersList.add(User("kevin@gmail.com","321","Kevin",
            "Flores García","86821333"))

        usersList.add(User("alberto@gmail.com","alb123","Alberto",
            "Amador Delgado","22896386"))

    }

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