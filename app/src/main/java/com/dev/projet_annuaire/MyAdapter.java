package com.dev.projet_annuaire;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends ArrayAdapter<Utilisateur> {

    Context context;
    ArrayList<Utilisateur> listUsers;

    public MyAdapter(@NonNull Context context, ArrayList<Utilisateur> listUsers) {
        super(context, R.layout.user_list_item, listUsers);
        this.context = context;
        this.listUsers = listUsers;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, null, true);

        TextView nom = view.findViewById(R.id.lastnameuser);
        TextView prenom = view.findViewById(R.id.firstnameuser);
        ImageView photo = view.findViewById(R.id.userpicture);
        TextView fil = view.findViewById(R.id.filialeuser);
        TextView email = view.findViewById(R.id.emailuser);
        TextView pass = view.findViewById(R.id.mdpuser);
        TextView tel = view.findViewById(R.id.contactuser);
        TextView key = view.findViewById(R.id.keyid);
        CardView card = view.findViewById(R.id.card);
        //Button modifier = view.findViewById(R.id.modifier);
        /*img = view.findViewById(R.id.imageRight);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InfosUsersActivity.class).putExtra("position", position);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });*/
        nom.setText(listUsers.get(position).getNom_user());
        prenom.setText(listUsers.get(position).getPrenom_user());

        if(listUsers.get(position).getPhoto().equals("")){
            Picasso.with(getContext()).load("https://marhost.000webhostapp.com/photos/usericone.png").into(photo);
        }else {
            Picasso.with(getContext()).load("https://marhost.000webhostapp.com/photos/" + listUsers.get(position).getPhoto()).into(photo);
        }

        fil.setText(listUsers.get(position).getNom_filiale());
        email.setText("Email:" + " " + listUsers.get(position).getEmail_user());
        pass.setText("Mot de passe:" + " " + listUsers.get(position).getPassword_user());
        tel.setText("Contact:" + " " + listUsers.get(position).getTel_user());

        if(listUsers.get(position).getKey_cnx().contains("Bloqué")){
            card.setCardBackgroundColor(Color.rgb(255,204,203));
            key.setText(listUsers.get(position).getKey_cnx());
            key.setTextColor(Color.rgb(231,24,55));
        }
        else{
            card.setCardBackgroundColor(Color.rgb(211,237,219));
            key.setText(listUsers.get(position).getKey_cnx());
            key.setTextColor(Color.rgb(73,181,117));
        }

        return view;
    }
}