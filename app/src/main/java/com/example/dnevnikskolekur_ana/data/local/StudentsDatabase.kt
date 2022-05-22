package com.example.dnevnikskolekur_ana.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dnevnikskolekur_ana.data.local.entities.LocallyDeletedStudentID
import com.example.dnevnikskolekur_ana.data.local.entities.Student

@Database(
    entities = [Student::class, LocallyDeletedStudentID::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class StudentsDatabase : RoomDatabase() {
    abstract fun studentDao():StudentDao
}