package com.example.notesfrontend

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesfrontend.adapter.NoteListAdapter
import com.example.notesfrontend.model.NoteRepository
import com.example.notesfrontend.model.Note
import android.widget.SearchView

/**
 * Entry point: MainActivity shows list of notes and floating action button for adding notes.
 * Supports search functionality and note navigation.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var adapter: NoteListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_NotesFrontend_Light)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.notes_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = NoteListAdapter(NoteRepository.getAllNotes()) { note ->
            // On note click, open detail activity
            val intent = Intent(this, NoteDetailActivity::class.java)
            intent.putExtra("NOTE_ID", note.id)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        val fab: FloatingActionButton = findViewById(R.id.fab_add_note)
        fab.setOnClickListener {
            val intent = Intent(this, NoteEditActivity::class.java)
            startActivity(intent)
        }

        // Implement search
        val searchView: SearchView = findViewById(R.id.search_view)
        searchView.queryHint = "Search notes"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.updateNotes(NoteRepository.searchNotes(query ?: ""))
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.updateNotes(NoteRepository.searchNotes(newText ?: ""))
                return true
            }
        })
    }

    override fun onResume() {
        super.onResume()
        // Refresh notes when returning to this activity
        adapter.updateNotes(NoteRepository.getAllNotes())
    }
}
