package com.dev.projet_annuaire;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment5 extends Fragment {

    ImageView imageView;
    TextView nom,activite,directeur;
    Infos info;
    AdapterFiliales adapterFiliales;
    ListView listView;
    public static ArrayList<Infos> infosList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment5, container,false);

        nom = rootView.findViewById(R.id.nom_filiale);
        activite = rootView.findViewById(R.id.activite);
        directeur = rootView.findViewById(R.id.directeur);
        imageView = rootView.findViewById(R.id.logopic);
        listView = rootView.findViewById(R.id.listFil);

        adapterFiliales = new AdapterFiliales(getContext(),infosList);
        listView.setAdapter(adapterFiliales);

        afficher_filiales();

        return rootView;
    }

    private void afficher_filiales() {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://marhost.000webhostapp.com/ListFiliales_annuaire.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                Log.i("response:", response);
                                JSONArray jsonArray = jsonObject.getJSONArray("Filiales");
                                if (jsonArray != null) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        int id = object.getInt("ID");
                                        String filiale = object.getString("NOM");
                                        String activite = object.getString("ACTIVITE");
                                        String lieu = object.getString("LIEU");
                                        String directeur = object.getString("DIRECTEUR");
                                        String logo = object.getString("LOGO");
                                        String locali = object.getString("LOCALISATION");

                                        info = new Infos(id, filiale, activite, lieu, directeur, logo, locali);
                                        if (adapterFiliales.getCount() == jsonArray.length()) {
                                            adapterFiliales.notifyDataSetChanged();
                                        } else {
                                            infosList.add(info);
                                            adapterFiliales.notifyDataSetChanged();
                                        }
                                    }
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "Erreur", Toast.LENGTH_LONG).show();
                }
            });
            VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
        }
    }

