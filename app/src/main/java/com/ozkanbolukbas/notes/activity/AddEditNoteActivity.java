package com.ozkanbolukbas.notes.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;

import com.ozkanbolukbas.notes.R;
import com.ozkanbolukbas.notes.api.SimpleCallback;
import com.ozkanbolukbas.notes.model.Note;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;

public class AddEditNoteActivity extends BaseActivity {
    private EditText titleEdit;
    private EditText contentEdit;
    private CheckBox checkBox;

    public static final String EXTRA_NOTE = "note";
    private Note note;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        titleEdit = findViewById(R.id.titleEdit);
        contentEdit = findViewById(R.id.contentEdit);
        checkBox = findViewById(R.id.public_check_box);

        note = (Note) getIntent().getSerializableExtra(EXTRA_NOTE);

        if (note != null) {
            titleEdit.setText(note.getTitle());
            contentEdit.setText(note.getContent());
        }
    }

    public boolean checkBoxChecked() {
        if (checkBox.isChecked()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            if (checkBoxChecked()) {
                saveNote(true);
            } else {
                saveNote(false);
            }

            return true;
        } else if (item.getItemId() == R.id.action_delete) {
            if (checkBoxChecked()) {
                deleteNote(true);
            } else {
                deleteNote(false);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteNote(boolean checked) {
        if (note == null) {
            setResult(WelcomeActivity.RESULT_NO_CHANGE);
            finish();
        } else {
            if (checked){
                getApi().deletePublicNote(note.getId()).enqueue(new SimpleCallback<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra(EXTRA_NOTE, note);
                        setResult(PublicnotesActivity.RESULT_NOTE_DELETED, resultIntent);
                        finish();
                    }
                });
            }else{
                getApi().deleteNote(note.getId()).enqueue(new SimpleCallback<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra(EXTRA_NOTE, note);
                        setResult(WelcomeActivity.RESULT_NOTE_DELETED, resultIntent);
                        finish();
                    }
                });
            }

        }
    }

    private void saveNote(boolean checked) {
        if (note == null) {
            createNote(checked);
        } else {
            updateNote(checked);
        }
    }

    private void createNote(boolean checked) {
        note = new Note();
        note.setTitle(titleEdit.getText().toString());
        note.setContent(contentEdit.getText().toString());
        if (checked) {
            getApi().createPublicNote(note).enqueue(new SimpleCallback<Note>() {
                @Override
                public void onSuccess(Note note) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(EXTRA_NOTE, note);
                    setResult(PublicnotesActivity.RESULT_NOTE_ADDED, resultIntent);
                    finish();
                }
            });
        } else {
            getApi().createNote(note).enqueue(new SimpleCallback<Note>() {
                @Override
                public void onSuccess(Note note) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(EXTRA_NOTE, note);
                    setResult(WelcomeActivity.RESULT_NOTE_ADDED, resultIntent);
                    finish();
                }
            });
        }
    }

    private void updateNote(boolean checked) {
        note.setTitle(titleEdit.getText().toString());
        note.setContent(contentEdit.getText().toString());
        if (checked) {
            getApi().updatePublicNote(note).enqueue(new SimpleCallback<Note>() {
                @Override
                public void onSuccess(Note note) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(EXTRA_NOTE, note);
                    setResult(PublicnotesActivity.RESULT_NOTE_UPDATE, resultIntent);
                    finish();
                }
            });
        } else {
            getApi().updateNote(note).enqueue(new SimpleCallback<Note>() {
                @Override
                public void onSuccess(Note note) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(EXTRA_NOTE, note);
                    setResult(WelcomeActivity.RESULT_NOTE_UPDATE, resultIntent);
                    finish();
                }
            });

        }
    }
}
