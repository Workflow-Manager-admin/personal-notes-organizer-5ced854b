package com.example.notesfrontend.model

/**
 * Singleton object managing in-memory notes.
 * Handles create, update, delete, search.
 */
object NoteRepository {
    private val notes = mutableListOf<Note>()
    private var nextId = 1L

    // PUBLIC_INTERFACE
    fun getAllNotes(): List<Note> = notes.sortedByDescending { it.timestamp }

    // PUBLIC_INTERFACE
    fun getNoteById(id: Long): Note? = notes.find { it.id == id }

    // PUBLIC_INTERFACE
    fun addNote(title: String, content: String): Note {
        val note = Note(
            id = nextId++,
            title = title,
            content = content,
            timestamp = System.currentTimeMillis()
        )
        notes.add(note)
        return note
    }

    // PUBLIC_INTERFACE
    fun updateNote(id: Long, title: String, content: String): Boolean {
        val note = getNoteById(id)
        return if (note != null) {
            note.title = title
            note.content = content
            note.timestamp = System.currentTimeMillis()
            true
        } else {
            false
        }
    }

    // PUBLIC_INTERFACE
    fun deleteNote(id: Long): Boolean = notes.removeIf { it.id == id }

    // PUBLIC_INTERFACE
    fun searchNotes(query: String): List<Note> {
        if (query.isBlank()) return getAllNotes()
        val q = query.lowercase()
        return notes.filter {
            it.title.lowercase().contains(q) || it.content.lowercase().contains(q)
        }.sortedByDescending { it.timestamp }
    }
}
