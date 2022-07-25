package com.example.dnevnikskolekur_ana.ui.addAnswersToStudent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.ktornoteapp.repositories.StudentRepository
import com.example.dnevnikskolekur_ana.data.local.entities.Answer
import com.example.dnevnikskolekur_ana.data.local.entities.Juz
import com.example.dnevnikskolekur_ana.other.Event
import com.example.dnevnikskolekur_ana.other.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAnswersToStudentViewModel @Inject constructor(
    private val repository: StudentRepository
): ViewModel() {

    private val _addAnswerStatus = MutableLiveData<Event<Resource<String>>>()
    val addAnswerStatus : LiveData<Event<Resource<String>>> = _addAnswerStatus

    fun observeStudentByID(studentID: String) = repository.observeStudentByID(studentID)

    fun addAnswerToStudent(studentID: String, answer: Answer) {
        _addAnswerStatus.postValue(Event(Resource.loading(null)))
        if(answer.juzNumber == Juz.JUZ_NULL){
            _addAnswerStatus.postValue(Event(Resource.error("Morate odabrati barem džuz!", null)))
            return
        }
        viewModelScope.launch {
            val result = repository.addAnswerToStudent(studentID,answer)
            _addAnswerStatus.postValue(Event(result))
        }
    }
}