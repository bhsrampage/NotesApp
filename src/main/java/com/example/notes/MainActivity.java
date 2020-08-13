package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    ListView noteListView;
    static ArrayList<String> note = new ArrayList<String>();
    static ArrayAdapter arrayAdapter;
    SharedPreferences sharedPreferences;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        super.onOptionsItemSelected(item);
        note.add(".. ");
        noteListView.setAdapter(arrayAdapter);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = this.getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes",null);

        if(set == null){
            note.add("Example note");
        }else{
            note = new ArrayList(set);
        }
        noteListView = findViewById(R.id.noteListView);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, note);
        noteListView.setAdapter(arrayAdapter);

        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ContentActivity.class);
                intent.putExtra("Content", note.get(position));
                intent.putExtra("Index",position);
                startActivity(intent);

            }
        });

         noteListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        new AlertDialog.Builder(MainActivity.this).setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are You Sure? ")
                .setMessage("You want to delete the selected item")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        note.remove(position);
                        arrayAdapter.notifyDataSetChanged();


                        HashSet<String> set = new HashSet<>(note);
                        sharedPreferences.edit().putStringSet("notes",set).apply();
                    }
                })
                .setNegativeButton("No",null).show();
        return true;
    }
});
    }
}