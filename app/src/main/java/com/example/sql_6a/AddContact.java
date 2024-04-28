package com.example.sql_6a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddContact extends AppCompatActivity {

    EditText etName, etPhone;
    Button btnAdd, btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        init();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveContact();
                startActivity(new Intent(AddContact.this, MainActivity.class));
                finish();
            }
        });
    }

    private void saveContact()
    {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString();

        ContactsDB database = new ContactsDB(this);
        database.open();

        database.insert(name, phone);

        database.close();

    }
    private void init()
    {
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);
    }
}