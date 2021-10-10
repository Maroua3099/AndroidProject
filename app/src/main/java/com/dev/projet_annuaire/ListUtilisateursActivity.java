package com.dev.projet_annuaire;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListUtilisateursActivity extends AppCompatActivity {
    FloatingActionButton Add_button;
    ListView listView;
    MyAdapter adapter;
    Utilisateur user;
    public static ArrayList<Utilisateur> utilisateurArrayList = new ArrayList<>();
    SearchView search;
    TextView keyTxt;
    TextView txt1,txt2,txt3,txt4;
    CardView card;
    Button btnDropdown;
    public static final String EXTRA_POSITION ="position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_utilisateurs);

        Add_button = findViewById(R.id.addButton);

        listView = findViewById(R.id.mylistview);
        search = findViewById(R.id.searchBar);
        btnDropdown = findViewById(R.id.btnmenu);
        keyTxt = findViewById(R.id.keyid);
        adapter = new MyAdapter(getApplicationContext(), utilisateurArrayList);
        listView.setAdapter(adapter);

        afficher_listUsers();

        //--Ajouter un utilisateur:
        Add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListUtilisateursActivity.this, AjouterUserActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(ListUtilisateursActivity.this, btnDropdown);
                popup.getMenuInflater().inflate(R.menu.menudecnx, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        final ProgressDialog progressDialog = new ProgressDialog(ListUtilisateursActivity.this);
                        progressDialog.setCancelable(false);
                        progressDialog.setIndeterminate(false);
                        progressDialog.setMessage("Déconnexion ...");
                        progressDialog.show();
                        Intent i = new Intent(ListUtilisateursActivity.this,LoginActivity.class);
                        startActivity(i);
                        return true;
                    }
                });
            }
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Utilisateur> filteredUsers = new ArrayList<Utilisateur>();
                for (Utilisateur utilisateur : utilisateurArrayList) {
                    if (utilisateur.getNom_user().toLowerCase().contains(newText.toLowerCase())) {
                        filteredUsers.add(utilisateur);
                    }
                }
                MyAdapter arrayAdapter = new MyAdapter(getApplicationContext(), filteredUsers);
                listView.setAdapter(arrayAdapter);
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String key = utilisateurArrayList.get(position).getKey_cnx();
                int userId = utilisateurArrayList.get(position).getId_user();
                if(key.equals("Débloqué")) {
                    final Dialog dialog = new Dialog(ListUtilisateursActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.activity_main6);
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    //dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                    dialog.getWindow().setGravity(Gravity.BOTTOM);
                    dialog.show();

                    txt3 = dialog.findViewById(R.id.txt3);
                    txt4 = dialog.findViewById(R.id.txt4);
                    txt4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String bloque = "Bloqué";
                            card = findViewById(R.id.card);
                            StringRequest request = new StringRequest(Request.Method.POST, "https://marhost.000webhostapp.com/Updatecnx_annuaire.php",
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Log.i("msg:", "Modifié");
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.i("msg:", "Erreur");
                                }
                            }) {
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("key_cnx", bloque);
                                    params.put("id_user", String.valueOf(userId));
                                    return params;
                                }
                            };
                            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
                            utilisateurArrayList.get(position).setKey_cnx("Bloqué");
                            dialog.cancel();
                        }
                    });

                    txt3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getApplicationContext(),ModifierInfosUserActivity.class).putExtra("position",position));
                        }
                    });

                }else{
                        final Dialog dialog = new Dialog(ListUtilisateursActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.activity_main5);
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        //dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                        dialog.getWindow().setGravity(Gravity.BOTTOM);
                        dialog.show();

                        txt1 = dialog.findViewById(R.id.txt1);
                        txt2 = dialog.findViewById(R.id.txt2);

                        txt2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String debloque = "Débloqué";
                            card = findViewById(R.id.card);
                            StringRequest request = new StringRequest(Request.Method.POST, "https://marhost.000webhostapp.com/Updatecnx_annuaire.php",
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Log.i("msg:", "Modifié");
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.i("msg:", "Erreur");
                                }
                            }) {
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("key_cnx", debloque);
                                    params.put("id_user", String.valueOf(userId));
                                    return params;
                                }
                            };
                            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
                            utilisateurArrayList.get(position).setKey_cnx("Débloqué");
                            dialog.cancel();
                        }
                    });
                    txt1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getApplicationContext(),ModifierInfosUserActivity.class).putExtra("position",position));
                        }
                    });
                }
            }
        });
    }

    private void afficher_listUsers() {
        card = findViewById(R.id.card);
        adapter.clear();
        adapter.notifyDataSetChanged();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://marhost.000webhostapp.com/ListUsers_annuaire.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.i("response:", response);
                            JSONArray jsonArray = jsonObject.getJSONArray("AllUsers");
                            if (jsonArray != null) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    int id = object.getInt("ID");
                                    int filiale = object.getInt("FILIALE");
                                    String nom = object.getString("NOM");
                                    String prenom = object.getString("PRENOM");
                                    String email = object.getString("EMAIL");
                                    String passwd = object.getString("MDP");
                                    String tel = object.getString("TEL");
                                    String photo = object.getString("PHOTO");
                                    String filname = object.getString("NOM_FIL");
                                    String key = object.getString("KEY");

                                    user = new Utilisateur(id, nom, prenom, email, passwd, tel, photo, key, filiale, filname);
                                    if (adapter.getCount() == jsonArray.length()) {
                                        adapter.notifyDataSetChanged();
                                    } else {
                                        utilisateurArrayList.add(user);
                                        adapter.notifyDataSetChanged();
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
                Toast.makeText(getApplicationContext(), "Erreur", Toast.LENGTH_LONG).show();
            }
        });
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}

