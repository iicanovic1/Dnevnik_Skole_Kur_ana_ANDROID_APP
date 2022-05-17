package com.example.dnevnikskolekur_ana.data.remote.requests

import com.example.dnevnikskolekur_ana.data.local.entities.Access

data class AddAccessRequest (
    val noteID : String,
    val access : Access
)