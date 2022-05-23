package com.example.dnevnikskolekur_ana.ui.studentDetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.ktornoteapp.repositories.StudentRepository
import com.example.dnevnikskolekur_ana.data.local.entities.Access
import com.example.dnevnikskolekur_ana.other.Event
import com.example.dnevnikskolekur_ana.other.Resource
import kotlinx.coroutines.launch


class StudentDetailViewModel @ViewModelInject constructor(
       private val repository: StudentRepository
): ViewModel() {

    private val _addAccessStatus = MutableLiveData<Event<Resource<String>>>()
    val addAccessStatus : LiveData<Event<Resource<String>>> = _addAccessStatus

    fun observeStudentByID(studentID: String) = repository.observeStudentID(studentID)

    fun addAccessToStudent(studentID: String, access: Access) {
        _addAccessStatus.postValue(Event(Resource.loading(null)))
        if(access.email.isEmpty() || studentID.isEmpty()){
            _addAccessStatus.postValue(Event(Resource.error("Morate unijeti podatke korisnika, polje ne smije biti prazno!", null)))
            return
        }
        viewModelScope.launch {
            val result = repository.addAccessToStudent(studentID,access)
            _addAccessStatus.postValue(Event(result))
        }
    }
}