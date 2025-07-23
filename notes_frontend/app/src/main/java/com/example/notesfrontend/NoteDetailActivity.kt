package com.example.notesfrontend

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.notesfrontend.model.NoteRepository

/**
 * Activity for displaying a note in detail.
 * Allows editing (opens edit activity) and deleting.
 */
class NoteDetailActivity : AppCompatActivity() {
    private var noteId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_NotesFrontend_Light)
        setContentView(R.layout.activity_note_detail)

        noteId = intent.getLongExtra("NOTE_ID", -1L)
        val note = NoteRepository.getNoteById(noteId)
        if (note == null) {
            Toast.makeText(this, "Note not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        findViewById<TextView>(R.id.detail_title).text = note.title
        findViewById<TextView>(R.id.detail_content).text = note.content
        findViewById<TextView>(R.id.detail_timestamp).text =
            android.text.format.DateFormat.format("MMM dd, yyyy HH:mm", note.timestamp)

        findViewById<ImageButton>(R.id.btn_edit).setOnClickListener {
            val intent = Intent(this, NoteEditActivity::class.java)
            intent.putExtra("NOTE_ID", noteId)
            startActivity(intent)
        }

        findViewById<ImageButton>(R.id.btn_delete).setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Delete Note")
                .setMessage("Are you sure you want to delete this note?")
                .setPositiveButton("Delete") { _, _ ->
                    NoteRepository.deleteNote(noteId)
                    Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    override fun onResume() {
        super.onResume()
        // Update display in case of edits
        val note = NoteRepository.getNoteById(noteId)
        if (note == null) {
            finish()
            return
        }
        findViewById<TextView>(R.id.detail_title).text = note.title
        findViewById<TextView>(R.id.detail_content).text = note.content
        findViewById<TextView>(R.id.detail_timestamp).text =
            android.text.format.DateFormat.format("MMM dd, yyyy HH:mm", note.timestamp)
    }
}
