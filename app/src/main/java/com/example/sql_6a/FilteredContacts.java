package com.example.sql_6a;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class FilteredContacts extends AppCompatActivity {

    SearchView svContact;
    ListView lvContact;
    FilterContactAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered_contacts);
        svContact = findViewById(R.id.svContact);
        lvContact = findViewById(R.id.lvContacts);

        ArrayList<Contact> contacts = MyApplication.contacts;


        svContact.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Contact> filteredData = new ArrayList<>();

                for (Contact c: contacts)
                {
                    if(c.getName().toLowerCase().contains(newText.toLowerCase()))
                    {
                        filteredData.add(c);
                    }
                }

                adapter = new FilterContactAdapter(FilteredContacts.this, filteredData);
                lvContact.setAdapter(adapter);


                return false;
            }
        });



        adapter = new FilterContactAdapter(this, contacts);
        lvContact.setAdapter(adapter);

    }
}