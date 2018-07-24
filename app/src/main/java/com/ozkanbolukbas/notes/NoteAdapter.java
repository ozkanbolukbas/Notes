package com.ozkanbolukbas.notes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ozkanbolukbas.notes.model.Note;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private ArrayList<Note> noteList = new ArrayList<>();
    private NoteClickListener listener;


    public ArrayList<Note> getNoteList() {
        return noteList;
    }

    public void setNoteList(ArrayList<Note> noteList) {
        this.noteList = noteList;
        notifyDataSetChanged();
    }

    public void addNote(Note note) {
        noteList.add(note);
        notifyItemInserted(getItemCount() - 1);
    }

    public void deleteNote(Note note) {
        int index = noteList.indexOf(note);
        noteList.remove(index);
        notifyItemRemoved(index);
    }

    public void updateNote(Note note) {
        int index = noteList.indexOf(note);
        noteList.set(index, note);
        notifyItemChanged(index);
    }

    public NoteClickListener getListener() {
        return listener;
    }

    public void setListener(NoteClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public Note getItem(int position) {
        return noteList.get(position);
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private TextView titleText;
        private TextView contentText;

        public NoteViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            titleText = itemView.findViewById(R.id.titleText);
            contentText = itemView.findViewById(R.id.contentText);
        }

        public void bind(final Note note) {
            titleText.setText(note.getTitle());
            contentText.setText(note.getContent());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getListener().onNoteClick(note);
                }
            });
        }
    }

    public interface NoteClickListener {
        void onNoteClick(Note clickedNote);
    }
}
