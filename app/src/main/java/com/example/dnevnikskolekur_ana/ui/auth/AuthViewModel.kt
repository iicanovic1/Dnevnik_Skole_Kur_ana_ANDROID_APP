package com.example.dnevnikskolekur_ana.ui.auth

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.ktornoteapp.repositories.StudentRepository
import com.example.dnevnikskolekur_ana.other.Resource
import kotlinx.coroutines.launch


class AuthViewModel @ViewModelInject constructor(
        private val repository: StudentRepository
) : ViewModel() {

    private val _registerStatus = MutableLiveData<Resource<String>>()
    val registerStatus : LiveData<Resource<String>> = _registerStatus

    fun register(email : String, password: String, repeatedPassword : String){
        _registerStatus.postValue(Resource.loading(null))
        if(email.isEmpty() || password.isEmpty() || repeatedPassword.isEmpty()) {
            _registerStatus.postValue(Resource.error("Polja ne smiju biti prazna!", null))
            return
        }
        if(password != repeatedPassword){
            _registerStatus.postValue(Resource.error("Unesene lozinke se ne podudaraju!", null))
            return
        }

        viewModelScope.launch {
            val result = repository.register(email,password)
            _registerStatus.postValue(result)
        }
    }
}