package com.dev.projet_annuaire;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fragment2 extends Fragment {
    ListView listView;
    Adapter_contacts adapter_contacts;
    Utilisateur user;
    public static ArrayList<Utilisateur> utilisateurList = new ArrayList<>();
    SearchView search;
    ImageButton call,message,wtp,teams;
    LinearLayout lin1,lin2,lin3,lin4,lin5;
    TextView fonct;
    FloatingActionButton listfav;
    public static final String EXTRA_IDUSER ="id_user";
    public static final String EXTRA_FILIALEID ="filiale_id";
    public static final String EXTRA_NOM ="nom_user";
    public static final String EXTRA_PRENOM ="prenom_user";
    public static final String EXTRA_EMAIL ="email_user";
    public static final String EXTRA_MDP ="password_user";
    public static final String EXTRA_CONTACT ="tel_user";
    public static final String EXTRA_PICTURE ="photo";
    public static final String EXTRA_FILIALENAME ="nom_filiale";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment2, container,false);

        listView = rootView.findViewById(R.id.listView);
        search = rootView.findViewById(R.id.rechercher);
        listfav = rootView.findViewById(R.id.btn_listfavoris);

        final int ID = getActivity().getIntent().getIntExtra(LoginActivity.EXTRA_ID,0);

        adapter_contacts = new Adapter_contacts(getContext(),utilisateurList);
        listView.setAdapter(adapter_contacts);

        afficher_listUsers();

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Utilisateur> UsersFilter = new ArrayList<Utilisateur>();
                for(Utilisateur utilisateur : utilisateurList){
                    if(utilisateur.getNom_user().toLowerCase().contains(newText.toLowerCase()) ||
                            utilisateur.getNom_filiale().toLowerCase().contains(newText.toLowerCase())){
                        UsersFilter.add(utilisateur);
                    }
                }
                Adapter_contacts my_adapter = new Adapter_contacts(getContext(),UsersFilter);
                listView.setAdapter(my_adapter);
                return false;
            }
        });

        listfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),FavorisActivity.class);
                i.putExtra(EXTRA_IDUSER,ID);
                startActivity(i);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.activity_main2);
                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);
                getSetting();
                //ImageView:
                call = dialog.findViewById(R.id.Call);
                message = dialog.findViewById(R.id.Message);
                wtp = dialog.findViewById(R.id.Whats);
                teams = dialog.findViewById(R.id.Msteams);
                //LinearLayout
                lin1 = dialog.findViewById(R.id.Appel);
                lin2 = dialog.findViewById(R.id.Msg);
                lin3 = dialog.findViewById(R.id.Wtp);
                lin4 = dialog.findViewById(R.id.Teams);
                lin5 = dialog.findViewById(R.id.Favorite);

                fonct = dialog.findViewById(R.id.fonct);


                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dexter.withContext(getContext())
                                .withPermission(Manifest.permission.CALL_PHONE)
                                .withListener(new PermissionListener() {
                                    @Override
                                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                        String number = utilisateurList.get(position).getTel_user();
                                        String appel = "tel:" + number;
                                        Log.i("msg:",number);
                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                        intent.setData(Uri.parse(appel));
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                                    }

                                    @Override
                                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                        permissionToken.continuePermissionRequest();
                                    }
                                }).check();

                    }
                });

                message.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String number = utilisateurList.get(position).getTel_user();
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms",number,null));
                        startActivity(intent);
                    }
                });

                wtp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String phonenumber = utilisateurList.get(position).getTel_user();

                        boolean installed = appInstalledOrNot("com.whatsapp");
                        if(installed){
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+phonenumber));
                            startActivity(intent);
                        }else{
                            Toast.makeText(getContext(),"Wtp n'est pas installé",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                teams.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String email = utilisateurList.get(position).getEmail_user();

                        boolean installed = appInstalledOrNot("com.microsoft.teams");
                        if(installed){
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("https://teams.microsoft.com/l/chat/0/0?users="+email));
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(getContext(),"MS Teams n'est pas installé",Toast.LENGTH_LONG).show();
                        }

                    }
                });

               lin5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            int numUserFavoris = utilisateurList.get(position).getId_user();
                            final int ID = getActivity().getIntent().getIntExtra(LoginActivity.EXTRA_ID,0);

                            int id = utilisateurList.get(position).getId_user();
                            int filiale = utilisateurList.get(position).getFiliale_id();
                            String nom = utilisateurList.get(position).getNom_user();
                            String prenom = utilisateurList.get(position).getPrenom_user();
                            String email = utilisateurList.get(position).getEmail_user();
                            String passwd = utilisateurList.get(position).getPassword_user();
                            String tel = utilisateurList.get(position).getTel_user();
                            String photo = utilisateurList.get(position).getPhoto();
                            String nom_fil = utilisateurList.get(position).getNom_filiale();

                            StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://marhost.000webhostapp.com/AddFavoris_annuaire.php",
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                Toast toast = Toast.makeText(getContext(),response, Toast.LENGTH_LONG);
                                                toast.show();
                                                FavorisActivity.utilisateurListFavoris.add(new Utilisateur(id,filiale,nom,prenom,email,passwd,tel,photo,nom_fil));
                                                Log.i("msg:","Ajouté aux favoris");
                                            } catch (Exception e) {
                                                Toast toast = Toast.makeText(getContext(),response, Toast.LENGTH_LONG);
                                                toast.show();
                                                Log.i("msg:","Erreur d'ajout");
                                            }

                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                                }
                            }) {
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("num_user", String.valueOf(ID));
                                    params.put("num_fav", String.valueOf(numUserFavoris));
                                    return params;
                                }
                            };
                            VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
                        }

                });
            }
        });
        return rootView;
    }

    private void getSetting() {
        //Bundle bundle = getArguments();
        //String myid = bundle.getString("id");
        final int ID = getActivity().getIntent().getIntExtra(LoginActivity.EXTRA_ID,0);
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

                                /*if(set_call.contains("Désactivé") && set_message.contains("Désactivé")
                                && set_whatsapp.contains("Désactivé") && set_microteams.contains("Désactivé")){
                                    fonct.setText("Aucune fonctionnalité n'est disponible.");
                                }*/

                                if (set_call.contains("Activé")) {
                                    lin1.setVisibility(View.VISIBLE);
                                    lin5.setVisibility(View.VISIBLE);
                                } else {
                                    lin1.setVisibility(View.GONE);
                                    lin5.setVisibility(View.VISIBLE);
                                }

                                if (set_message.contains("Activé")) {
                                    lin2.setVisibility(View.VISIBLE);
                                    lin5.setVisibility(View.VISIBLE);
                                } else {
                                    lin2.setVisibility(View.GONE);
                                    lin5.setVisibility(View.VISIBLE);
                                }

                                if (set_whatsapp.contains("Activé")) {
                                    lin3.setVisibility(View.VISIBLE);
                                    lin5.setVisibility(View.VISIBLE);
                                } else {
                                    lin3.setVisibility(View.GONE);
                                    lin5.setVisibility(View.VISIBLE);
                                }

                                if (set_microteams.contains("Activé")) {
                                    lin4.setVisibility(View.VISIBLE);
                                    lin5.setVisibility(View.VISIBLE);
                                } else {
                                    lin4.setVisibility(View.GONE);
                                    lin5.setVisibility(View.VISIBLE);
                                }

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
                params.put("id_user",String.valueOf(ID));
                return params;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(request);
    }


    private boolean appInstalledOrNot(String url) {

        PackageManager packageManager = getContext().getPackageManager();
        boolean app_installed;
        try{
            packageManager.getPackageInfo(url, PackageManager.GET_ACTIVITIES);
            app_installed = true;

        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    private void afficher_listUsers() {
        //Bundle bundle = getArguments();
        //String myid = bundle.getString("id");
        final int ID = getActivity().getIntent().getIntExtra(LoginActivity.EXTRA_ID,0);
        StringRequest request = new StringRequest(Request.Method.POST, "https://marhost.000webhostapp.com/ListContacts_annuaire.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            Log.i("response:",response);
                            JSONArray jsonArray = jsonObject.getJSONArray("Contacts");
                            if(jsonArray != null) {
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
                                    String nom_fil = object.getString("NOM_FILIALE");

                                    user = new Utilisateur(id, filiale, nom, prenom, email, passwd, tel, photo, nom_fil);
                                    if (adapter_contacts.getCount() == jsonArray.length()) {
                                        adapter_contacts.notifyDataSetChanged();
                                    } else {
                                        utilisateurList.add(user);
                                        adapter_contacts.notifyDataSetChanged();
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
                Toast.makeText(getContext(),"Erreur",Toast.LENGTH_LONG).show();
            }
        }){
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("id_user",String.valueOf(ID));
                return params;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(request);
    }
}