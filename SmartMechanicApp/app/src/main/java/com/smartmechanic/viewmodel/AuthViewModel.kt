package com.smartmechanic.viewmodel

import androidx.lifecycle.ViewModel
import com.smartmechanic.repository.AuthRepository

class AuthViewModel: ViewModel() {
    private val repo = AuthRepository()

    fun registerUser(name:String,email:String,password:String,onSuccess:()->Unit,onFailure:(String)->Unit) =
      repo.registerUser(name,email,password,onSuccess,onFailure)

    fun registerMechanic(name:String,email:String,password:String,license:String,onSuccess:()->Unit,onFailure:(String)->Unit) =
      repo.registerMechanic(name,email,password,license,onSuccess,onFailure)

    fun login(email:String,password:String,onSuccess:(role:String)->Unit,onFailure:(String)->Unit) =
      repo.login(email,password,onSuccess,onFailure)
}
