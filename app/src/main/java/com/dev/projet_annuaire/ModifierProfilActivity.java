package com.dev.projet_annuaire;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ModifierProfilActivity extends AppCompatActivity {

    Button btn_modifier;
    EditText txtnomComplet, txtEmail, txtPass, txtTel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_profil);
        btn_modifier = findViewById(R.id.btn_modifier);
        txtnomComplet = findViewById(R.id.txtnomcomplet);
        txtEmail = findViewById(R.id.txtemail);
        txtPass = findViewById(R.id.txtpass);
        txtTel = findViewById(R.id.txttel);

        getInfos();
    }

       /* btn_modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        String idU = textid.getText().toString();
                        String emailU = textEmail.getText().toString();
                        String pwdU = textPsswd.getText().toString();
                        String telU = textTel.getText().toString();

                        StringRequest request = new StringRequest(Request.Method.POST, "https://marproj.000webhostapp.com/updateInfos_annuaire.php",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();

                                        emailtext.setText(emailU);
                                        mdptext.setText(pwdU);
                                        contacttext.setText(telU);

                                        saveUpdate();

                                        Log.i("msg:","Informations modifiés");
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }){
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("id_user", idU);
                                params.put("email_user", emailU);
                                params.put("password_user", pwdU);
                                params.put("tel_user", telU);
                                return params;

                            }
                        };
                        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
                    }
                });
            }
        });
            }
        });
    }*/

    private void getInfos() {
        final int ID = getIntent().getIntExtra(Fragment4.EXTRA_IDUSER, 0);
        final int NOM = getIntent().getIntExtra(Fragment4.EXTRA_NOM, 0);
        final int PRENOM = getIntent().getIntExtra(Fragment4.EXTRA_PRENOM, 0);
        final int EMAIL = getIntent().getIntExtra(Fragment4.EXTRA_EMAIL, 0);
        final int MDP = getIntent().getIntExtra(Fragment4.EXTRA_MDP, 0);
        final int TEL = getIntent().getIntExtra(Fragment4.EXTRA_TEL, 0);

        txtnomComplet.setText(NOM);
        txtEmail.setText(PRENOM);
        txtPass.setText(EMAIL);
        txtTel.setText(TEL);
    }

        /*StringRequest request = new StringRequest(Request.Method.POST, "https://marhost.000webhostapp.com/GetInfos_annuaire.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject userResponse = jsonObject.getJSONObject("InfosUser");
                            Log.i("response:", response);
                            if (response.contains("InfosUser")) {
                                String nom = userResponse.getString("nom_user");
                                String prenom = userResponse.getString("prenom_user");
                                String email = userResponse.getString("email_user");
                                String password = userResponse.getString("password_user");
                                String tel = userResponse.getString("tel_user");

                                txtnomComplet.setText(NOM);
                                txtEmail.setText(PRENOM);
                                txtPass.setText(EMAIL);
                                txtTel.setText(TEL);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("msg:","Erreur de modification");
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_user",String.valueOf(ID));
                return params;
            }
        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }*/
}