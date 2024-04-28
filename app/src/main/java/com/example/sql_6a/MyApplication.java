package com.example.sql_6a;

import android.app.Application;

import java.util.ArrayList;

public class MyApplication extends Application {

    public static ArrayList<Contact> contacts;

    @Override
    public void onCreate() {
        super.onCreate();
        ContactsDB database = new ContactsDB(this);
        database.open();
        contacts = database.readAllContacts();
        database.close();
    }
}
