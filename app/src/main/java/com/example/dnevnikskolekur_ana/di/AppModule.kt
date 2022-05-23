package com.androiddevs.ktornoteapp.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.dnevnikskolekur_ana.data.local.StudentsDatabase
import com.example.dnevnikskolekur_ana.data.remote.BasicAuthInterceptor
import com.example.dnevnikskolekur_ana.data.remote.StudentApi
import com.example.dnevnikskolekur_ana.other.Constants.BASE_URL
import com.example.dnevnikskolekur_ana.other.Constants.DATABASE_NAME
import com.example.dnevnikskolekur_ana.other.Constants.ENCRYPTED_SHARED_PREF_NAME
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.inject.Singleton
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(ApplicationComponent::class) // koliko dugo ovisnosti da žive (za vrijeme života aplikacije)
object AppModule { // opisuje kako da dager piše ove ovisnosti

    @Singleton
    @Provides
    fun provideNotesDatabase(
        @ApplicationContext context : Context
    ) = Room.databaseBuilder(context, StudentsDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideNoteDao(db : StudentsDatabase) = db.studentDao()

    @Singleton
    @Provides
    fun provideBasicAuthInterceptor() = BasicAuthInterceptor()

    @Singleton
    @Provides
    fun provideOkHttpClient() : OkHttpClient.Builder {
        val trustAllCertificates: Array<TrustManager> = arrayOf(
                object : X509TrustManager {
                    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                        /* NO - OP */
                    }

                    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                        /* NO - OP */
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return  arrayOf()
                    }
                }
        )
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCertificates, SecureRandom())

        return OkHttpClient.Builder()
                .sslSocketFactory(sslContext.socketFactory, trustAllCertificates[0] as X509TrustManager)
                .hostnameVerifier(HostnameVerifier {_, _ -> true})
    }

    @Singleton
    @Provides
    fun provideNoteApi(
            okHttpClient: OkHttpClient.Builder,
        basicAuthInterceptor: BasicAuthInterceptor
    ) : StudentApi{
        val client = okHttpClient
            .addInterceptor(basicAuthInterceptor)
            .build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(StudentApi::class.java)
    }

    @Singleton
    @Provides
    fun provideEncryptedSharedPreferences(
        @ApplicationContext context : Context
    ) : SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        return  EncryptedSharedPreferences.create(
            context,
            ENCRYPTED_SHARED_PREF_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }



}