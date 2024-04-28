package com.example.sql_6a;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder>{

    DeleteContact parentActivity;

    public interface DeleteContact
    {
        public void onDeleteContact(int index);
        public void onUpdateContact(int index, String []values);
    }

    ArrayList<Contact> contacts;
    Context context;

    public ContactAdapter(Context c, ArrayList<Contact> list)
    {
        contacts = list;
        context = c;
        parentActivity = (DeleteContact) c;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.single_contact, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(contacts.get(position).getName());
        holder.tvPhone.setText(contacts.get(position).getPhone());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(holder.itemView.getContext());
                alertDialog.setMessage("Do you really want to delete?");
                alertDialog.setTitle("Delete Notification");

                alertDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ContactsDB database = new ContactsDB(context);
                        database.open();
                        database.deleteContact(contacts.get(holder.getAdapterPosition()).getId());
                        database.close();

                        parentActivity.onDeleteContact(holder.getAdapterPosition());
                    }
                });

                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alertDialog.show();


                return false;
            }
        });

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog editDialog = new AlertDialog.Builder(context).create();
                editDialog.setTitle("Edit Contact");
                View view = LayoutInflater.from(context).inflate(R.layout.edit_contact_layout, null,false);
                editDialog.setView(view);
                editDialog.show();

                // hooks for dialog view
                EditText etName, etPhone;
                Button btnEditContact, btnCancel;
                etName = view.findViewById(R.id.etName);
                etPhone = view.findViewById(R.id.etPhone);
                btnEditContact = view.findViewById(R.id.btnUpdate);
                btnCancel = view.findViewById(R.id.btnCancel);

                etName.setText(contacts.get(holder.getAdapterPosition()).getName());
                etPhone.setText(contacts.get(holder.getAdapterPosition()).getPhone());

                btnEditContact.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ContactsDB database = new ContactsDB(context);
                        database.open();
                        database.updateContact(contacts.get(holder.getAdapterPosition()).getId(),
                                etName.getText().toString().trim(),
                                etPhone.getText().toString().trim());
                        database.close();
                        String []updateContact = new String[]{etName.getText().toString().trim(),
                                etPhone.getText().toString().trim()};
                        parentActivity.onUpdateContact(holder.getAdapterPosition(), updateContact);
                        editDialog.dismiss();
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editDialog.dismiss();
                    }
                });
            }
        });


    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvName, tvPhone;
        ImageView ivEdit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            ivEdit = itemView.findViewById(R.id.ivEdit);
        }
    }
}
