package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.io.IOException;
import java.util.HashSet;

public class ContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        final EditText noteEditText = findViewById(R.id.noteEditText);
        noteEditText.setText("");
        final Intent intent1 = new Intent(this, MainActivity.class);

        final Intent intent = getIntent();
        noteEditText.setText(intent.getStringExtra("Content"));

        noteEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                MainActivity.note.set(intent.getIntExtra("Index",0),s.toString());
                MainActivity.arrayAdapter.notifyDataSetChanged();
                 SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<>(MainActivity.note);
                sharedPreferences.edit().putStringSet("notes",set).apply();

            }
        });




    }
}