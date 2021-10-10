package com.dev.projet_annuaire;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText motdepasse;
    Button cnxBtn;
    public static final String EXTRA_ID ="id_user";
    public static final String EXTRA_NOM ="nom_user";
    public static final String EXTRA_PRENOM ="prenom_user";
    public static final String EXTRA_EMAIL ="email_user";
    public static final String EXTRA_MDP ="password_user";
    public static final String EXTRA_TEL ="tel_user";
    public static final String EXTRA_PHOTO ="photo";
    public static final String EXTRA_FILIALE ="nom_filiale";
    public static ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.emailText);
        motdepasse =  findViewById(R.id.passText);
        cnxBtn = findViewById(R.id.btnCnx);


        /*mdpoublie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ForgotPasswordActivity.class);
                startActivity(i);
                finish();
            }
        });*/

        cnxBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email_utili = email.getText().toString().trim();
                String pwd_utili =  motdepasse.getText().toString().trim();

                if(email_utili.isEmpty() && !pwd_utili.isEmpty()){
                    email.setError("Ce champs est obligatoire.");
                }
                else if(pwd_utili.isEmpty() && !email_utili.isEmpty()){
                    motdepasse.setError("Ce champs est obligatoire.");
                }
                else if(email_utili.isEmpty() && pwd_utili.isEmpty()){
                    email.setError("Ce champs est obligatoire.");
                    motdepasse.setError("Ce champs est obligatoire.");
                }
                else {
                    Login();
                }
            }
        });

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if (progressDialog!=null && progressDialog.isShowing()){
            progressDialog.cancel();
        }
    }

    private void Login() {

        String email_utili = email.getText().toString();
        String pwd_utili =  motdepasse.getText().toString();


        StringRequest request = new StringRequest(Request.Method.POST, "https://marhost.000webhostapp.com/Login_annuaire.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject obj = new JSONObject(response);
                            JSONObject userResponse = obj.getJSONObject("user");
                            Log.i("Response", response);
                            if(response.contains("user")) {
                                if (userResponse.getString("role").equals("EMPLOYE")) {
                                    int id = userResponse.getInt("id_user");
                                    String nom = userResponse.getString("nom_user");
                                    String prenom = userResponse.getString("prenom_user");
                                    String email = userResponse.getString("email_user");
                                    String mdp = userResponse.getString("password_user");
                                    String contact = userResponse.getString("tel_user");
                                    String photoProfil = userResponse.getString("photo");
                                    String keyCnx = userResponse.getString("key_cnx");
                                    String filiale = userResponse.getString("nom_filiale");

                                    if (keyCnx.contains("Bloqué")) {
                                        Toast.makeText(getApplicationContext(), "Adresse mail ou mot de passe invalide.", Toast.LENGTH_LONG).show();
                                        Log.i("msg", "Utilisateur introuvable");
                                    } else {
                                        progressDialog = new ProgressDialog(LoginActivity.this);
                                        progressDialog.setCancelable(false);
                                        progressDialog.setIndeterminate(false);
                                        progressDialog.setMessage("Connexion ...");
                                        progressDialog.show();
                                        Intent i = new Intent(LoginActivity.this, CompteClient.class);
                                        i.putExtra(EXTRA_ID, id);
                                        i.putExtra(EXTRA_NOM, nom);
                                        i.putExtra(EXTRA_PRENOM, prenom);
                                        i.putExtra(EXTRA_EMAIL, email);
                                        i.putExtra(EXTRA_MDP, mdp);
                                        i.putExtra(EXTRA_TEL, contact);
                                        i.putExtra(EXTRA_PHOTO, photoProfil);
                                        i.putExtra(EXTRA_FILIALE, filiale);
                                        startActivity(i);
                                        finish();
                                        Log.i("msg", "Client connecté");
                                    }
                                    }else if (userResponse.getString("role").equals("ADMIN")) {
                                        progressDialog = new ProgressDialog(LoginActivity.this);
                                        progressDialog.setCancelable(false);
                                        progressDialog.setIndeterminate(false);
                                        progressDialog.setMessage("Connexion ...");
                                        progressDialog.show();
                                        Intent i = new Intent(LoginActivity.this, ListUtilisateursActivity.class);
                                        i.putExtra("KEY_EMAIL", email_utili);
                                        i.putExtra("KEY_PWD", pwd_utili);
                                        startActivity(i);
                                        finish();
                                        Log.i("msg", "Admin connecté");
                                    }
                                }
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),"Adresse mail ou mot de passe invalide.",Toast.LENGTH_LONG).show();
                            Log.i("msg", "Utilisateur introuvable");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error response:","Erreur");
            }
        }){
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String,String> params = new HashMap<>();
                params.put("email_user",email_utili);
                params.put("password_user",pwd_utili);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }
}
