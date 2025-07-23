package com.example.notesfrontend

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.notesfrontend.model.NoteRepository

/**
 * Activity for adding or editing a note.
 * Used for both creation and update, based on intent extra.
 */
class NoteEditActivity : AppCompatActivity() {
    private var noteId: Long = -1L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_NotesFrontend_Light)
        setContentView(R.layout.activity_note_edit)

        noteId = intent.getLongExtra("NOTE_ID", -1L)
        val isEdit = noteId != -1L
        val titleEdit: EditText = findViewById(R.id.edit_title)
        val contentEdit: EditText = findViewById(R.id.edit_content)
        val saveButton: Button = findViewById(R.id.btn_save)
        val cancelButton: Button = findViewById(R.id.btn_cancel)

        if (isEdit) {
            val note = NoteRepository.getNoteById(noteId)
            if (note == null) {
                Toast.makeText(this, "Note not found", Toast.LENGTH_SHORT).show()
                finish()
                return
            }
            titleEdit.setText(note.title)
            contentEdit.setText(note.content)
        }

        saveButton.setOnClickListener {
            val title = titleEdit.text.toString().trim()
            val content = contentEdit.text.toString().trim()
            if (title.isEmpty()) {
                Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (isEdit) {
                NoteRepository.updateNote(noteId, title, content)
                Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show()
            } else {
                NoteRepository.addNote(title, content)
                Toast.makeText(this, "Note created", Toast.LENGTH_SHORT).show()
            }
            finish()
        }
        cancelButton.setOnClickListener {
            finish()
        }
    }
}
