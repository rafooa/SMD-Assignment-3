package com.example.sql_6a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ContactsDB {
    private final String DATABASE_NAME = "ContactsDB";
    private final String DATABASE_TABLE = "Contacts_Table";
    private final String KEY_ID = "_id";
    private final String KEY_NAME = "_name";
    private final String KEY_PHONE = "_phone";

    private final int DATABASE_VERSION = 2;

    Context context;

    MyDatabaseHelper helper;
    SQLiteDatabase sqLiteDatabase;

    public ContactsDB(Context c)
    {
        context = c;
    }

    public void open()
    {
        helper = new MyDatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        sqLiteDatabase = helper.getWritableDatabase();
    }

    public void insert(String name, String phone)
    {
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, name);
        cv.put(KEY_PHONE, phone);

        long temp = sqLiteDatabase.insert(DATABASE_TABLE, null, cv);
        if(temp == -1)
        {
            Toast.makeText(context, "Contact not added", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Contact Added", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteContact(int id)
    {
       int rows = sqLiteDatabase.delete(DATABASE_TABLE, KEY_ID+"=?", new String[]{id+""});
        if(rows > 0)
        {
            Toast.makeText(context, "Contact deleted successfully", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Contact not deleted", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<Contact> readAllContacts()
    {
       Cursor c =  sqLiteDatabase.rawQuery("SELECT * FROM "+DATABASE_TABLE, null);
       ArrayList<Contact> contacts = new ArrayList<>();
       int id_index = c.getColumnIndex(KEY_ID);
       int id_name = c.getColumnIndex(KEY_NAME);
       int id_phone = c.getColumnIndex(KEY_PHONE);

       if(c.moveToFirst())
       {
           do{
               Contact contact = new Contact();
               contact.setId(c.getInt(id_index));
               contact.setName(c.getString(id_name));
               contact.setPhone(c.getString(id_phone));

               contacts.add(contact);
           }while(c.moveToNext());
       }

       c.close();
       return contacts;
    }
    public void close()
    {
        sqLiteDatabase.close();
        helper.close();
    }

    public void updateContact(int id, String newName, String newPhone) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, newName);
        cv.put(KEY_PHONE, newPhone);

        int rows = sqLiteDatabase.update(DATABASE_TABLE, cv, KEY_ID+"=?", new String[]{id+""});
        if(rows>0)
        {
            Toast.makeText(context, "Contact updated successfully", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Failed to update contact", Toast.LENGTH_SHORT).show();
        }
    }


    private class MyDatabaseHelper extends SQLiteOpenHelper
    {
        public MyDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
           db.execSQL("CREATE TABLE "+DATABASE_TABLE+"("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+KEY_NAME+" TEXT NOT NULL, "+KEY_PHONE+" TEXT NOT NULL);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
            onCreate(db);
        }
    }

}
