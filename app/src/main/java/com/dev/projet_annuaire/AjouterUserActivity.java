package com.dev.projet_annuaire;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AjouterUserActivity extends AppCompatActivity {

    EditText Nom;
    EditText Prenom;
    EditText Email;
    EditText Password;
    EditText Telephone;
    Button Btn_inscrip;
    Spinner spinnerFiliale;
    ArrayList<String> filialeList = new ArrayList<>();
    ArrayAdapter<String> filialeAdapter;
    RequestQueue requestQueueFiliale;
    JSONArray filiales;
    ProgressBar progbar;
    Utilisateur user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_user);

        requestQueueFiliale = Volley.newRequestQueue(this);

        Nom = findViewById(R.id.nomUser);
        Prenom = findViewById(R.id.prenomUser);
        Email = findViewById(R.id.emailUser);
        Password = findViewById(R.id.motdepasseUSer);
        Telephone = findViewById(R.id.telUser);
        Btn_inscrip = findViewById(R.id.btn_inscrip);

        //Spinner Filiale:
        requestQueueFiliale = Volley.newRequestQueue(this);
        spinnerFiliale = findViewById(R.id.spinnerFil);
        String url = "https://marhost.000webhostapp.com/SpinnerFiliale_annuaire.php";
        JsonObjectRequest jsonObjectRequestClient = new JsonObjectRequest(Request.Method.POST,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("Filiales");
                    filiales = jsonArray;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String filialeID = jsonObject.optString("id_filiale");
                        String filialeName = jsonObject.optString("nom_filiale");
                        filialeList.add(filialeName);
                        filialeAdapter = new ArrayAdapter<>(AjouterUserActivity.this,
                                android.R.layout.simple_spinner_dropdown_item, filialeList);
                        spinnerFiliale.setAdapter(filialeAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueueFiliale.add(jsonObjectRequestClient);

        Btn_inscrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ajouter_user();
            }
        });
    }

    @Override public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(),ListUtilisateursActivity.class);
        startActivity(i);
        finish();
    }

    public void Ajouter_user() {

        String nom_utili = Nom.getText().toString();
        String prenom_utili = Prenom.getText().toString();
        String email_utili = Email.getText().toString();
        String password_utili = Password.getText().toString();
        String tel_utili = Telephone.getText().toString();
        String filiale_utili = spinnerFiliale.getSelectedItem().toString().trim();
        String photo_utili = "";
        String key_utili = "Débloqué";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://marhost.000webhostapp.com/Inscrip_annuaire.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progbar = findViewById(R.id.progbar);
                            progbar.setVisibility(View.VISIBLE);
                            /*new Thread() {
                                public void run() {*/
                                    try{
                                        // just doing some long operation
                                        //Thread.sleep(200);
                                        user = new Utilisateur(nom_utili,prenom_utili,email_utili,password_utili,tel_utili,photo_utili,key_utili,filiale_utili);
                                        ListUtilisateursActivity.utilisateurArrayList.add(user);
                                        Toast.makeText(getApplicationContext(), "Nouveau utilisateur ajouté.", Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(AjouterUserActivity.this, ListUtilisateursActivity.class);
                                        startActivity(i);
                                        finish();
                                        Log.i("msg:","User ajouté");
                                    } catch (Exception e) {  }
                                    // handle the exception somehow, or do nothing
                                }
                            //}.start();
                        catch (Exception e) {
                            Toast.makeText(getApplicationContext(),"Impossible d'ajouter un utilisateur.",Toast.LENGTH_LONG).show();
                            Log.i("msg", "Erreur d'ajout");
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error response:","Erreur");
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nom_user", nom_utili);
                params.put("prenom_user", prenom_utili);
                params.put("email_user", email_utili);
                params.put("password_user", password_utili);
                params.put("tel_user", tel_utili);
                params.put("nom_filiale",filiale_utili);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}