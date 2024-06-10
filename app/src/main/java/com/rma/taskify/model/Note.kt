package com.rma.taskify.model

import java.util.UUID

data class Note(
    val id: String = UUID.randomUUID().toString(),
    var content: String = ""
)
