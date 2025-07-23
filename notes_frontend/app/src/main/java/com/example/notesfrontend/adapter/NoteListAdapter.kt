package com.example.notesfrontend.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesfrontend.R
import com.example.notesfrontend.model.Note
import com.google.android.material.card.MaterialCardView
import android.widget.TextView

/**
 * Adapter for displaying notes in a RecyclerView.
 */
class NoteListAdapter(
    private var notes: List<Note>,
    private val onItemClick: (Note) -> Unit
) : RecyclerView.Adapter<NoteListAdapter.NoteViewHolder>() {

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card: MaterialCardView = itemView.findViewById(R.id.note_card)
        val title: TextView = itemView.findViewById(R.id.note_title)
        val content: TextView = itemView.findViewById(R.id.note_content)
        val timestamp: TextView = itemView.findViewById(R.id.note_timestamp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.title.text = note.title
        holder.content.text = if (note.content.length > 50) note.content.substring(0, 50) + "..." else note.content
        holder.timestamp.text = android.text.format.DateFormat.format("MMM dd, yyyy HH:mm", note.timestamp)
        holder.card.setOnClickListener { onItemClick(note) }
        // Optional: set accent border
        holder.card.strokeColor = Color.parseColor("#FFAB00")
        holder.card.strokeWidth = 0
    }

    override fun getItemCount() = notes.size

    // PUBLIC_INTERFACE
    fun updateNotes(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }
}
