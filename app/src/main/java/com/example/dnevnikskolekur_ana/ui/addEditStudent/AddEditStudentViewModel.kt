package com.example.dnevnikskolekur_ana.ui.addEditStudent

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.ktornoteapp.repositories.StudentRepository
import com.example.dnevnikskolekur_ana.data.local.entities.Student
import com.example.dnevnikskolekur_ana.other.Event
import com.example.dnevnikskolekur_ana.other.Resource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AddEditStudentViewModel @ViewModelInject constructor(
        private val repository: StudentRepository
) : ViewModel() {

    private val _student = MutableLiveData<Event<Resource<Student>>>()
    val student: LiveData<Event<Resource<Student>>> = _student

    fun insertStudent( student: Student) = GlobalScope.launch {
        repository.insertStudent(student)
    }

    fun getStudentById(studentID: String)= viewModelScope.launch {
        _student.postValue(Event(Resource.loading(null)))
        val studnet = repository.getStudentById(studentID)
        studnet?.let {
            _student.postValue(Event(Resource.success(it)))
        } ?: _student.postValue(Event(Resource.error("Student nije pronađen",null)))
    }
}