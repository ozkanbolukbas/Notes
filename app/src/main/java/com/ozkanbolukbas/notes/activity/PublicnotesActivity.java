package com.ozkanbolukbas.notes.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ozkanbolukbas.notes.NoteAdapter;
import com.ozkanbolukbas.notes.R;
import com.ozkanbolukbas.notes.api.SimpleCallback;
import com.ozkanbolukbas.notes.model.Note;

import java.util.ArrayList;
import java.util.List;

public class PublicnotesActivity extends BaseActivity implements NoteAdapter.NoteClickListener {
    public static final int REQUEST_CODE_ADD_EDIT_NOTE = 1001;

    public static final int RESULT_NO_CHANGE = 9000;
    public static final int RESULT_NOTE_ADDED = 9001;
    public static final int RESULT_NOTE_DELETED = 9002;
    public static final int RESULT_NOTE_UPDATE = 9003;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;

    private NoteAdapter noteAdapter = new NoteAdapter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToAddNote();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchNotes(false);
            }
        });

        noteAdapter.setListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(noteAdapter);

        fetchNotes(true);
    }


    private void navigateToAddNote() {
        Intent intent = new Intent(this, AddEditNoteActivity.class);
        startActivityForResult(intent, REQUEST_CODE_ADD_EDIT_NOTE);
    }

    private void navigateToEditNote(Note note) {
        Intent intent = new Intent(this, AddEditNoteActivity.class);
        intent.putExtra(AddEditNoteActivity.EXTRA_NOTE, note);
        startActivityForResult(intent, REQUEST_CODE_ADD_EDIT_NOTE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_ADD_EDIT_NOTE) {
            Note note = null;
            if (data != null) {
                note = (Note) data.getSerializableExtra(AddEditNoteActivity.EXTRA_NOTE);
            }

            switch (resultCode) {
                case RESULT_NOTE_ADDED:
                    noteAdapter.addNote(note);
                    break;
                case RESULT_NOTE_DELETED:
                    noteAdapter.deleteNote(note);
                    break;
                case RESULT_NOTE_UPDATE:
                    noteAdapter.updateNote(note);
                    break;
                default:
                    break;
            }
        }
    }

    private void fetchNotes(boolean forceRefresh) {

        if(forceRefresh) {
            swipeRefreshLayout.setRefreshing(true);
        }

        getApi().getPublicNotes().enqueue(new SimpleCallback<List<Note>>() {
            @Override
            public void onSuccess(List<Note> notes) {
                noteAdapter.setNoteList((ArrayList<Note>) notes);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onNoteClick(Note clickedNote) {
        navigateToEditNote(clickedNote);
    }
}
