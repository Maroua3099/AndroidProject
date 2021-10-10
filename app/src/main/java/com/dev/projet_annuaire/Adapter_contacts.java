package com.dev.projet_annuaire;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_contacts extends ArrayAdapter<Utilisateur> {

    Context context;
    ArrayList<Utilisateur> usersList;

    public Adapter_contacts(@NonNull Context context, ArrayList<Utilisateur> usersList) {
        super(context, R.layout.contacts_item, usersList);
        this.context = context;
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_item, null, true);

        TextView nom = view.findViewById(R.id.nom_txt);
        TextView prenom = view.findViewById(R.id.prenom_txt);
        ImageView image = view.findViewById(R.id.profilpic);
        TextView filiale = view.findViewById(R.id.filiale_name);

        nom.setText(usersList.get(position).getNom_user());
        prenom.setText(usersList.get(position).getPrenom_user());
        if(usersList.get(position).getPhoto().equals("")){
            Picasso.with(getContext()).load("https://marhost.000webhostapp.com/photos/usericone.png").into(image);
        }else {
            Picasso.with(getContext()).load("https://marhost.000webhostapp.com/photos/" + usersList.get(position).getPhoto()).into(image);
        }
        filiale.setText(usersList.get(position).getNom_filiale());
        return view;
    }
}
