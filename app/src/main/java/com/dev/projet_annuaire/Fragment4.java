package com.dev.projet_annuaire;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class Fragment4 extends Fragment {
    RelativeLayout lay1,lay2,lay3,lay4,lay5,lay6;
    TextView active1,active2,active3,active4;
    RadioButton radio1,radio2;
    Button btnsave;
    TextView titre;
    public static final String EXTRA_IDUSER="id_user";
    public static final String EXTRA_NOM="nom_user";
    public static final String EXTRA_PRENOM="prenom_user";
    public static final String EXTRA_EMAIL="email_user";
    public static final String EXTRA_MDP="password_user";
    public static final String EXTRA_TEL="tel_user";
    EditText textEmail,textPsswd,textTel,textid;
    Button enregistrer;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment4, container, false);

        lay1 = rootView.findViewById(R.id.layout1);
        active1 = rootView.findViewById(R.id.active1);

        lay2 = rootView.findViewById(R.id.layout2);
        active2 = rootView.findViewById(R.id.active2);

        lay3 = rootView.findViewById(R.id.layout3);
        active3 = rootView.findViewById(R.id.active3);

        lay4 = rootView.findViewById(R.id.layout4);
        active4 = rootView.findViewById(R.id.active4);

        lay5 = rootView.findViewById(R.id.layout5);

        lay6 = rootView.findViewById(R.id.layout6);


        get_Setting();

        //Shared Preferences:
        /*SharedPreferences sharedPreferences = getContext().getSharedPreferences("save",MODE_PRIVATE);
        sw1.setChecked(sharedPreferences.getBoolean("value",true));
        sw1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (sw1.isChecked()) {
                        SharedPreferences.Editor editor = getContext().getSharedPreferences("save", MODE_PRIVATE).edit();
                        editor.putBoolean("value", true);
                        editor.apply();
                        sw1.setChecked(true);
                        active1.setText("Activé");
                    } else {
                        SharedPreferences.Editor editor = getContext().getSharedPreferences("save", MODE_PRIVATE).edit();
                        editor.putBoolean("value", false);
                        editor.apply();
                        sw1.setChecked(false);
                        active1.setText("Désactivé");
                    }
                    updateSetting();
                }
        });*/
        return rootView;
    }

    private void get_Setting() {
        Bundle bundle = getArguments();
        String myid = bundle.getString("id");
        StringRequest request = new StringRequest(Request.Method.POST, "https://marhost.000webhostapp.com/SettingInfos_annuaire.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject userResponse = jsonObject.getJSONObject("Setting");
                            Log.i("response:", response);
                            if (response.contains("Setting")) {
                                String set_call = userResponse.getString("set_appel");
                                String set_message = userResponse.getString("set_msg");
                                String set_whatsapp = userResponse.getString("set_wtp");
                                String set_microteams = userResponse.getString("set_teams");
                                String email = userResponse.getString("email_user");
                                String pass = userResponse.getString("password_user");
                                String tel = userResponse.getString("tel_user");

                                if (set_call.contains("Activé")) {
                                    active1.setText("Activé");
                                    active1.setTextColor(Color.BLUE);
                                } else {
                                    active1.setText("Désactivé");
                                    active1.setTextColor(Color.RED);
                                }

                                if(set_message.contains("Activé")){
                                    active2.setText("Activé");
                                    active2.setTextColor(Color.BLUE);
                                }
                                else {
                                    active2.setText("Désactivé");
                                    active2.setTextColor(Color.RED);
                                }

                                if(set_whatsapp.contains("Activé")){
                                    active3.setText("Activé");
                                    active3.setTextColor(Color.BLUE);
                                }
                                else {
                                    active3.setText("Désactivé");
                                    active3.setTextColor(Color.RED);
                                }

                                if(set_microteams.contains("Activé")){
                                    active4.setText("Activé");
                                    active4.setTextColor(Color.BLUE);
                                }
                                else {
                                    active4.setText("Désactivé");
                                    active4.setTextColor(Color.RED);
                                }

                                lay1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        final Dialog dialog = new Dialog(getContext());
                                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dialog.setContentView(R.layout.setting_layout);
                                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        //dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                                        dialog.getWindow().setGravity(Gravity.BOTTOM);

                                        radio1 = dialog.findViewById(R.id.radio1);
                                        radio2 = dialog.findViewById(R.id.radio2);
                                        btnsave = dialog.findViewById(R.id.save);

                                        if (active1.getText().equals("Activé")) {
                                            radio1.setChecked(true);
                                            radio2.setChecked(false);
                                        } else {
                                            radio2.setChecked(true);
                                            radio1.setChecked(false);
                                        }


                                        dialog.show();

                                        btnsave.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if(radio1.isChecked()){
                                                    active1.setText("Activé");
                                                }
                                                else{
                                                    active1.setText("Désactivé");
                                                }
                                                dialog.cancel();
                                                updateSetting();
                                            }
                                        });
                                    }
                                });
                                        lay2.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                final Dialog dialog = new Dialog(getContext());
                                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                dialog.setContentView(R.layout.setting_layout);
                                                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                                //dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                                                dialog.getWindow().setGravity(Gravity.BOTTOM);

                                                radio1 = dialog.findViewById(R.id.radio1);
                                                radio2 = dialog.findViewById(R.id.radio2);
                                                btnsave = dialog.findViewById(R.id.save);

                                                if (active2.getText().equals("Activé")) {
                                                    radio1.setChecked(true);
                                                    radio2.setChecked(false);
                                                } else {
                                                    radio2.setChecked(true);
                                                    radio1.setChecked(false);
                                                }


                                                dialog.show();

                                                btnsave.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        if(radio1.isChecked()){
                                                            active2.setText("Activé");
                                                            active2.setTextColor(Color.BLUE);
                                                        }
                                                        else{
                                                            active2.setText("Désactivé");
                                                            active2.setTextColor(Color.RED);
                                                        }
                                                        dialog.cancel();
                                                        updateSetting();
                                                    }
                                                });
                                            }
                                        });

                                lay3.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        final Dialog dialog = new Dialog(getContext());
                                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dialog.setContentView(R.layout.setting_layout);
                                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        //dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                                        dialog.getWindow().setGravity(Gravity.BOTTOM);

                                        radio1 = dialog.findViewById(R.id.radio1);
                                        radio2 = dialog.findViewById(R.id.radio2);
                                        btnsave = dialog.findViewById(R.id.save);

                                        if (active3.getText().equals("Activé")) {
                                            radio1.setChecked(true);
                                            radio2.setChecked(false);
                                        } else {
                                            radio2.setChecked(true);
                                            radio1.setChecked(false);
                                        }


                                        dialog.show();

                                        btnsave.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if(radio1.isChecked()){
                                                    active3.setText("Activé");
                                                    active3.setTextColor(Color.BLUE);
                                                }
                                                else{
                                                    active3.setText("Désactivé");
                                                    active3.setTextColor(Color.RED);
                                                }
                                                dialog.cancel();
                                                updateSetting();
                                            }
                                        });
                                    }
                                });

                                lay4.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        final Dialog dialog = new Dialog(getContext());
                                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dialog.setContentView(R.layout.setting_layout);
                                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        //dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                                        dialog.getWindow().setGravity(Gravity.BOTTOM);

                                        radio1 = dialog.findViewById(R.id.radio1);
                                        radio2 = dialog.findViewById(R.id.radio2);
                                        btnsave = dialog.findViewById(R.id.save);

                                        if (active4.getText().equals("Activé")) {
                                            radio1.setChecked(true);
                                            radio2.setChecked(false);
                                        } else {
                                            radio2.setChecked(true);
                                            radio1.setChecked(false);
                                        }


                                        dialog.show();

                                        btnsave.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if(radio1.isChecked()){
                                                    active4.setText("Activé");
                                                    active4.setTextColor(Color.BLUE);
                                                }
                                                else{
                                                    active4.setText("Désactivé");
                                                    active4.setTextColor(Color.RED);
                                                }
                                                dialog.cancel();
                                                updateSetting();
                                            }
                                        });
                                    }
                                });


                                lay5.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                final ProgressDialog progressDialog = new ProgressDialog(getContext());
                                                progressDialog.setCancelable(false);
                                                progressDialog.setIndeterminate(false);
                                                progressDialog.setMessage("Déconnexion ...");
                                                progressDialog.show();
                                                Intent i = new Intent(getContext(),LoginActivity.class);
                                                startActivity(i);
                                            }
                                        });

                                lay6.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Bundle bundle = getArguments();
                                        String myid = bundle.getString("id");
                                        String mymdp = bundle.getString("password");
                                        Intent i = new Intent(getContext(),UpdatePassword.class);
                                        i.putExtra(EXTRA_IDUSER, myid);
                                        i.putExtra(EXTRA_MDP, mymdp);
                                        startActivity(i);
                                        /*enregistrer.setOnClickListener(new View.OnClickListener() {
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
                                                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
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
                                                VolleySingleton.getInstance(getContext()).addToRequestQueue(request);
                                                dialog.cancel();
                                            }
                                        });*/
                                    }
                                });
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"Erreur",Toast.LENGTH_LONG).show();
            }
        }){
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("id_user",myid);
                return params;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    /*private void SaveIntoSharedPrefs(String key,boolean value){
        SharedPreferences sp = getContext().getSharedPreferences("value",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key,value);
        editor.apply();

    }
    private boolean Update(String key){

        SharedPreferences sp = getContext().getSharedPreferences("value",MODE_PRIVATE);
        return sp.getBoolean(key,false);
    }*/
    private void updateSetting() {
        String actdesAppel = active1.getText().toString();
        String actdesMsg = active2.getText().toString();
        String actdesWtp = active3.getText().toString();
        String actdesTeams = active4.getText().toString();
        Bundle bundle = getArguments();
        String myid = bundle.getString("id");
        StringRequest request = new StringRequest(Request.Method.POST, "https://marhost.000webhostapp.com/UpdateSetting_annuaire.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("msg:","Informations modifiés");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("msg:","Erreur de modification");
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("set_appel", actdesAppel);
                params.put("set_msg", actdesMsg);
                params.put("set_wtp", actdesWtp);
                params.put("set_teams", actdesTeams);
                params.put("id_user", myid);
                return params;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(request);
    }
}

