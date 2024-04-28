package com.example.sql_6a;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ContactAdapter.DeleteContact {

    FloatingActionButton fabAdd, fabFilter;
    RecyclerView rvContact;
    ContactAdapter adapter;
    ArrayList<Contact> contacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fabAdd = findViewById(R.id.fabAdd);
        fabFilter = findViewById(R.id.fabFilter);
        rvContact = findViewById(R.id.rvContacts);
        rvContact.setHasFixedSize(true);
        rvContact.setLayoutManager(new LinearLayoutManager(this));



        adapter = new ContactAdapter(this, MyApplication.contacts);

        rvContact.setAdapter(adapter);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddContact.class));
                finish();
            }
        });

        fabFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FilteredContacts.class));
                finish();
            }
        });
    }

    @Override
    public void onDeleteContact(int index) {
        contacts.remove(index);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onUpdateContact(int index, String[] values) {
        contacts.get(index).setName(values[0]);
        contacts.get(index).setPhone(values[1]);
        adapter.notifyDataSetChanged();
    }
}