package com.example.dnevnikskolekur_ana.ui.students

import androidx.lifecycle.*
import com.androiddevs.ktornoteapp.repositories.StudentRepository
import com.example.dnevnikskolekur_ana.data.local.entities.Student
import com.example.dnevnikskolekur_ana.other.Event
import com.example.dnevnikskolekur_ana.other.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentsViewModel @Inject constructor (
        private val repository: StudentRepository
): ViewModel() {

    private  val _forceUpdate = MutableLiveData<Boolean>(false)
    fun syncAllStudents() = _forceUpdate.postValue(true)

    private val _allStudents = _forceUpdate.switchMap {
        repository.getAllStudents().asLiveData(viewModelScope.coroutineContext)
    }.switchMap {
        MutableLiveData(Event(it))
    }
    val allStudents : LiveData<Event<Resource<List<Student>>>> = _allStudents

    fun insertStudent(studnet: Student)= viewModelScope.launch {
        repository.insertStudent(studnet)
    }

    fun deleteStudent(studentID : String) = viewModelScope.launch {
        repository.deleteStudent(studentID)
    }

    fun deleteLocallyDeletedStudentID(deletedStudentID: String) = viewModelScope.launch {
        repository.deleteLocallyDeletedStudentID(deletedStudentID)
    }
}