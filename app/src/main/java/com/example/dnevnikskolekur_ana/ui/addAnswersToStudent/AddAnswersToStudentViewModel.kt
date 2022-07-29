package com.example.dnevnikskolekur_ana.ui.addAnswersToStudent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.ktornoteapp.repositories.StudentRepository
import com.example.dnevnikskolekur_ana.data.local.entities.Answer
import com.example.dnevnikskolekur_ana.data.local.entities.Juz
import com.example.dnevnikskolekur_ana.data.local.entities.Student
import com.example.dnevnikskolekur_ana.other.Event
import com.example.dnevnikskolekur_ana.other.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAnswersToStudentViewModel @Inject constructor(
    private val repository: StudentRepository
): ViewModel() {

    private val _student = MutableLiveData<Event<Resource<Student>>>()
    val student: LiveData<Event<Resource<Student>>> = _student


    fun getStudentById(studentID: String)= viewModelScope.launch {
        _student.postValue(Event(Resource.loading(null)))
        val studnet = repository.getStudentById(studentID)
        studnet?.let {
            _student.postValue(Event(Resource.success(it)))
        } ?: _student.postValue(Event(Resource.error("Student nije pronađen",null)))
    }

    fun addAnswerToCurStudent(student: Student) = GlobalScope.launch {
        repository.insertStudent(student)
    }
}