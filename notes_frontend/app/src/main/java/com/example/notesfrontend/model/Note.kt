package com.example.notesfrontend.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Data class representing a Note entity.
 * Uses Parcelable for efficient Intent transport.
 */
@Parcelize
data class Note(
    val id: Long,
    var title: String,
    var content: String,
    var timestamp: Long = System.currentTimeMillis()
) : Parcelable
