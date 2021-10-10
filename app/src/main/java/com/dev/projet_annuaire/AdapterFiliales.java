package com.dev.projet_annuaire;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterFiliales extends ArrayAdapter<Infos> {
    Context context;
    ArrayList<Infos> filialesList;

    public AdapterFiliales(@NonNull Context context, ArrayList<Infos> filialesList) {
        super(context, R.layout.filiale_item, filialesList);
        this.context = context;
        this.filialesList = filialesList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filiale_item, null, true);

        ImageView image = view.findViewById(R.id.logopic);
        TextView nom = view.findViewById(R.id.nom_filiale);
        TextView activite = view.findViewById(R.id.activite);
        TextView directeur = view.findViewById(R.id.directeur);
        TextView lieu = view.findViewById(R.id.lieu);
        FloatingActionButton locali = view.findViewById(R.id.locali);

        nom.setText(filialesList.get(position).getNom_info());
        activite.setText("Activité:"+ " "+filialesList.get(position).getActivite_info());
        directeur.setText("Directeur général:"+" "+filialesList.get(position).getDirecteur_info());
        lieu.setText("Lieu:"+" "+filialesList.get(position).getLieu_info());

        Picasso.with(getContext()).load("https://marhost.000webhostapp.com/logos/"+filialesList.get(position).getLogo_info()).into(image);

        locali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String localite = filialesList.get(position).getLocali_info();
                Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(localite));
                context.startActivity(intent);
            }
        });

        return view;
    }
}
