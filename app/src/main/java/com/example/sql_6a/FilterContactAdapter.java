package com.example.sql_6a;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FilterContactAdapter extends ArrayAdapter<Contact> {

    Context context;
    public FilterContactAdapter(@NonNull Context context, @NonNull ArrayList<Contact> objects) {
        super(context, 0, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;
        if(v == null)
        {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.single_contact, parent, false);
        }

        TextView tvName, tvPhone;
        ImageView ivCall;

        tvName = v.findViewById(R.id.tvName);
        tvPhone = v.findViewById(R.id.tvPhone);
        ivCall = v.findViewById(R.id.ivEdit);
        ivCall.setImageResource(R.drawable.ic_call);

        Contact c = getItem(position);
        tvName.setText(c.getName());
        tvPhone.setText(c.getPhone());

        ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("tel:"+c.getPhone()));
                context.startActivity(i);
            }
        });

        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder deleteDialog
                        = new AlertDialog.Builder(context);
                deleteDialog.setTitle("Confirmation");
                deleteDialog.setMessage("Do you really want to delete "+c.getName()+"?");
                deleteDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ContactsDB db = new ContactsDB(context);
                        db.open();
                        db.deleteContact(c.getId());
                        db.close();
                        Toast.makeText(context, c.getName()+" has deleted", Toast.LENGTH_SHORT).show();

                        remove(c);
                        notifyDataSetChanged();
                    }
                });
                deleteDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                deleteDialog.show();

                return false;
            }
        });

        return v;
    }
}
