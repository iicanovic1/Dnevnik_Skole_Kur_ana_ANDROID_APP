package com.example.dnevnikskolekur_ana.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locally_deleted_student_ids")
data class LocallyDeletedStudentID (
    @PrimaryKey(autoGenerate = false)
    val deletedStudentID: String

    )