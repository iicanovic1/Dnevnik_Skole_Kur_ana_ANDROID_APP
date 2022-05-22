package com.example.dnevnikskolekur_ana.data.remote

import com.androiddevs.ktornoteapp.data.remote.responses.SimpleResponse
import com.example.dnevnikskolekur_ana.data.local.entities.Student
import com.example.dnevnikskolekur_ana.data.remote.requests.AccountRequest
import com.example.dnevnikskolekur_ana.data.remote.requests.AddAccessRequest
import com.example.dnevnikskolekur_ana.data.remote.requests.DeleteStudentRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface StudentApi {
    @POST("/register")
    suspend fun register(
        @Body registerRequest : AccountRequest // anotacija ukazuje da parsira sa JSON
    ): Response<SimpleResponse>

    @POST("/login")
    suspend fun login(
        @Body loginRequest : AccountRequest
    ): Response<SimpleResponse>

    @POST("/addStudent")
    suspend fun addStudent(
        @Body student : Student
    ): Response<ResponseBody>  // kada response nema poseban oblik nego je samo HTTP kod

    @POST("/deleteStudent")
    suspend fun deleteStudent(
        @Body deleteStudentRequest: DeleteStudentRequest
    ): Response<ResponseBody>

    @POST("/addAccessToStudent")
    suspend fun addAccessToStudent(
        @Body addAccessRequest: AddAccessRequest
    ): Response<SimpleResponse>

    @GET("/getStudents")
    suspend fun getStudnets(): Response<List<Student>>
}