package com.dev.projet_annuaire;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_favoris extends ArrayAdapter<Utilisateur> {

    Context context;
    ArrayList<Utilisateur> usersListFavoris;

    public Adapter_favoris(@NonNull Context context, ArrayList<Utilisateur> usersListFavoris) {
        super(context, R.layout.favoris_item, usersListFavoris);
        this.context = context;
        this.usersListFavoris = usersListFavoris;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favoris_item, null, true);

        TextView nom = view.findViewById(R.id.nom_fav);
        TextView prenom = view.findViewById(R.id.prenom_fav);
        ImageView image = view.findViewById(R.id.profilfav);
        TextView filiale = view.findViewById(R.id.filiale_fav);

        nom.clearComposingText();
        prenom.clearComposingText();
        image.clearColorFilter();
        filiale.clearComposingText();

            nom.setText(usersListFavoris.get(position).getNom_user());
            prenom.setText(usersListFavoris.get(position).getPrenom_user());

            if (usersListFavoris.get(position).getPhoto().equals("")) {
                Picasso.with(getContext()).load("https://marhost.000webhostapp.com/photos/usericone.png").into(image);
            } else {
                Picasso.with(getContext()).load("https://marhost.000webhostapp.com/photos/" + usersListFavoris.get(position).getPhoto()).into(image);
            }

            filiale.setText(usersListFavoris.get(position).getNom_filiale());

        return view;
    }
}

