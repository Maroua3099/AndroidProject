package com.dev.projet_annuaire;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

public class ModifierInfosUserActivity extends AppCompatActivity {

    ProgressBar progressBar;
    EditText lastname,firstname,email,phone;
    Spinner spinnerFiliale;
    Button btnUpdate;
    ArrayList<String> filialeList = new ArrayList<>();
    ArrayAdapter<String> filialeAdapter;
    RequestQueue requestQueueFiliale;
    JSONArray filiales;
    private int position;
    public static ArrayList<Utilisateur> utilisateurArrayList = new ArrayList<>();
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_infos_user);

        requestQueueFiliale = Volley.newRequestQueue(this);

        progressBar = findViewById(R.id.progressbar);
        lastname = findViewById(R.id.nomutili);
        firstname = findViewById(R.id.prenomutili);
        email = findViewById(R.id.emailutili);
        phone = findViewById(R.id.telutili);
        btnUpdate = findViewById(R.id.btn_updateInfos);
        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");

        lastname.setText(ListUtilisateursActivity.utilisateurArrayList.get(position).getNom_user());
        firstname.setText(ListUtilisateursActivity.utilisateurArrayList.get(position).getPrenom_user());
        email.setText(ListUtilisateursActivity.utilisateurArrayList.get(position).getEmail_user());
        phone.setText(ListUtilisateursActivity.utilisateurArrayList.get(position).getTel_user());

        //Spinner Filiale:
        requestQueueFiliale = Volley.newRequestQueue(this);
        spinnerFiliale = findViewById(R.id.spinFil);
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
                        filialeAdapter = new ArrayAdapter<>(ModifierInfosUserActivity.this,
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

        spinnerFiliale.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               spinnerFiliale.setSelection(ListUtilisateursActivity.utilisateurArrayList.get(position).getFiliale_id() - 1);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Modification();
            }
        });
    }

    private void Modification() {
        progressBar.setVisibility(View.VISIBLE);
        String nom = lastname.getText().toString().trim();
        String prenom = firstname.getText().toString().trim();
        String adresseMail = email.getText().toString().trim();
        String telephone = phone.getText().toString().trim();
        String filiale = spinnerFiliale.getSelectedItem().toString().trim();
        int idUser = ListUtilisateursActivity.utilisateurArrayList.get(position).getId_user();

        StringRequest request = new StringRequest(Request.Method.POST, "https://marhost.000webhostapp.com/UpdateInfosUser_annuaire.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("msg:", "Modifié");
                        progressBar.setVisibility(View.GONE);
                        final Dialog dialog = new Dialog(ModifierInfosUserActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.activity_main8);
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                        dialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);
                        dialog.show();
                        progressBar.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("msg:", "Erreur");
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_user", String.valueOf(idUser));
                params.put("nom_user", nom);
                params.put("prenom_user", prenom);
                params.put("email_user", adresseMail);
                params.put("tel_user", telephone);
                params.put("nom_filiale", filiale);
                return params;
            }
        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }


    @Override public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(),ListUtilisateursActivity.class);
        startActivity(i);
        finish();
    }
}