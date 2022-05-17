package com.example.dnevnikskolekur_ana.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dnevnikskolekur_ana.data.local.entities.Student
import kotlinx.coroutines.flow.Flow

@Dao // DAo za dobavljanje iz lokalne baze
interface StudentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) // insert i update u jednom
    suspend fun insertStudent(note:Student)

    @Query("DELETE FROM students WHERE id = :studentID")
    suspend fun deleteStudentById(studentID : String)

    @Query("DELETE FROM students WHERE isSynced = 1")
    suspend fun deleteAllSyncedStudents()

    @Query("DELETE FROM students")
    suspend fun deleteAllStudents()

    @Query("SELECT * FROM students WHERE id = :studentID")
    fun observeStudentById(studentID: String) : LiveData<Student> // ovo nije u suspend zato što je LiveData već suspend funkcija

    @Query("SELECT * FROM students WHERE id = :studentID")
    suspend fun getStudentById(studentID: String) : Student?

    @Query("SELECT * FROM students ORDER BY date DESC")
    fun getAllStudents(): Flow<List<Student>> // pomaže kod keširanja i odlučivanja odakle uzeti podatke

    @Query("SELECT * FROM students WHERE isSynced = 0")
    suspend fun getAllUnsyncedStudents():List<Student>
}