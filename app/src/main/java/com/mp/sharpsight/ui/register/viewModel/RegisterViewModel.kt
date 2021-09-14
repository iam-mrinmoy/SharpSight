package com.mp.sharpsight.ui.register.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.isalepro.requestHandler.ApiResponse
import com.mp.sharpsight.ui.register.repository.RegisterRepository

class RegisterViewModel : ViewModel() {

    private val repository = RegisterRepository

    var userEmail= MutableLiveData<String>()
    var userName= MutableLiveData<String>()
    var userPassword= MutableLiveData<String>()

    fun registerNewUser(
    ): LiveData<ApiResponse>? {
        return repository.registerNewUser(userEmail.value.toString(), userName.value.toString(), userPassword.value.toString())
    }
}