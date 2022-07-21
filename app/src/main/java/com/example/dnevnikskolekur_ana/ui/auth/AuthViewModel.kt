package com.example.dnevnikskolekur_ana.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.ktornoteapp.repositories.StudentRepository
import com.example.dnevnikskolekur_ana.other.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
        private val repository: StudentRepository
) : ViewModel() {

    private val _registerStatus = MutableLiveData<Resource<String>>()
    val registerStatus : LiveData<Resource<String>> = _registerStatus

    private val _loginStatus = MutableLiveData<Resource<String>>()
    val loginStatus : LiveData<Resource<String>> = _loginStatus

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

    fun login(email : String, password: String){
        _loginStatus.postValue(Resource.loading(null))
        if(email.isEmpty() || password.isEmpty()) {
            _loginStatus.postValue(Resource.error("Polja ne smiju biti prazna!", null))
            return
        }

        viewModelScope.launch {
            val result = repository.login(email,password)
            _loginStatus.postValue(result)
        }
    }
}