package com.example.dnevnikskolekur_ana.ui.studentDetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.androiddevs.ktornoteapp.repositories.StudentRepository


class StudentDetailViewModel @ViewModelInject constructor(
       private val repository: StudentRepository
): ViewModel() {

    fun observeStudentByID(studentID: String) = repository.observeStudentID(studentID)
}