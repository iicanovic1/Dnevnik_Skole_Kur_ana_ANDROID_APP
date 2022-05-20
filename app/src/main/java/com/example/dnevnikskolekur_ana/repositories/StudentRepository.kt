package com.androiddevs.ktornoteapp.repositories

import android.app.Application
import com.example.dnevnikskolekur_ana.data.local.StudentDao
import com.example.dnevnikskolekur_ana.data.remote.StudentApi
import com.example.dnevnikskolekur_ana.data.remote.requests.AccountRequest
import com.example.dnevnikskolekur_ana.other.Resource
import kotlinx.coroutines.Dispatchers
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
}