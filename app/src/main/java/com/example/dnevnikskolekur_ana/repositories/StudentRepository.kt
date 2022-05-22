package com.androiddevs.ktornoteapp.repositories

import android.app.Application
import com.example.dnevnikskolekur_ana.data.local.StudentDao
import com.example.dnevnikskolekur_ana.data.local.entities.Student
import com.example.dnevnikskolekur_ana.data.remote.StudentApi
import com.example.dnevnikskolekur_ana.data.remote.requests.AccountRequest
import com.example.dnevnikskolekur_ana.other.Resource
import com.example.dnevnikskolekur_ana.other.checkForInternetConnection
import com.example.dnevnikskolekur_ana.other.networkBoundResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject


class StudentRepository @Inject constructor(
        private val studentDao: StudentDao,
        private val studentApi: StudentApi,
        private val context: Application
) {
    suspend fun register(email : String, password :String)  = withContext(Dispatchers.IO) { // osiguravamao da se koristi IO Dispećer
        try {
            val response = studentApi.register(AccountRequest(email, password))
            if(response.isSuccessful && response.body()!!.successful){
                Resource.success(response.body()?.message)
            }else{
                Resource.error(response.body()?.message ?: response.message(),null)
            }
        }catch (e: Exception){
            Resource.error("Nije moguće kontaktirati server. Provjerite internet konekciju!",null)
        }
    }

    suspend fun login(email : String, password :String)  = withContext(Dispatchers.IO) {
        try {
            val response = studentApi.login(AccountRequest(email, password))
            if(response.isSuccessful && response.body()!!.successful){
                Resource.success(response.body()?.message)
            }else{
                Resource.error(response.body()?.message ?: response.message(),null)
            }
        }catch (e: Exception){
            Resource.error("Nije moguće kontaktirati server. Provjerite internet konekciju!",null)
        }
    }

    fun getAllStudents() : Flow<Resource<List<Student>>> {
        return networkBoundResource(
                query = {
                    studentDao.getAllStudents()
                },
                fetch = {
                    studentApi.getStudnets()
                },
                saveFetchResult = { response ->   // što smo dobili sa api-ja
                    response.body()?.let{
                        //insertNotes(it.onEach { note -> note.isSynced = true })
                        insertStudents(it)
                    }
                },
                shouldFetch = {
                    checkForInternetConnection(context)
                }
        )
    }

    suspend fun insertStudent(student: Student) {
        val response = try {
            studentApi.addStudent(student)
        }catch (e: Exception){
            null
        }
        if (response != null && response.isSuccessful){
            studentDao.insertStudent(student.apply { isSynced = true })
        }else{
            studentDao.insertStudent(student)
        }
    }

    suspend fun insertStudents (studets: List<Student>){
        studets.forEach{student -> insertStudent(student)}
    }

    suspend fun getStudentById(studentID: String) = studentDao.getStudentById(studentID)
}