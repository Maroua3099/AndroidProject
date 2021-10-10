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
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
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

import static android.os.Build.ID;

public class Fragment1 extends Fragment {
    ListView listViewFav;
    Adapter_favoris adapter_favoris;
    Utilisateur userFav;
    public static ArrayList<Utilisateur> utilisateurListFavoris = new ArrayList<>();
    ImageButton call,message,wtp,teams;
    LinearLayout lin1,lin2,lin3,lin4,lin5;
    TextView fonct;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment1, container, false);

        Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.detach(currentFragment);
        fragmentTransaction.attach(currentFragment);
        fragmentTransaction.commit();
        listViewFav = rootView.findViewById(R.id.listViewFavoris);
        adapter_favoris = new Adapter_favoris(getContext(), utilisateurListFavoris);
        listViewFav.setAdapter(adapter_favoris);

        afficher_listUsers();

        return rootView;
    }

        /*listViewFav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.activity_main2);
                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);
                //getSetting();
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
                                        String number = utilisateurListFavoris.get(position).getTel_user();
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
                        String number = utilisateurListFavoris.get(position).getTel_user();
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms",number,null));
                        startActivity(intent);
                    }
                });*/

                /*wtp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String phonenumber = utilisateurListFavoris.get(position).getTel_user();

                        boolean installed = appInstalledOrNot("com.whatsapp");
                        if(installed){
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+phonenumber));
                            startActivity(intent);
                        }else{
                            Toast.makeText(getContext(),"Wtp is not installed",Toast.LENGTH_LONG).show();
                        }
                    }
                });*/
                /*teams.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String email = utilisateurListFavoris.get(position).getEmail_user();

                        boolean installed = appInstalledOrNot("com.microsoft.teams");
                        if(installed){
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("https://teams.microsoft.com/l/chat/0/0?users="+email));
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(getContext(),"MS Teams is not installed",Toast.LENGTH_LONG).show();
                        }

                    }
                });*/

                /*lin5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int numUserFavoris = utilisateurListFavoris.get(position).getId_user();
                        Bundle bundle = getArguments();
                        String idd = bundle.getString("id");

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://marproj.000webhostapp.com/AddFavoris_annuaire.php",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            Toast toast = Toast.makeText(getContext(),response, Toast.LENGTH_LONG);
                                            toast.show();
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
                                params.put("num_user", idd);
                                params.put("num_fav", String.valueOf(numUserFavoris));
                                return params;
                            }
                        };
                        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
                    }

                });
            }
        });*/



    /*private void getSetting() {
        StringRequest request = new StringRequest(Request.Method.POST, "https://marproj.000webhostapp.com/SettingInfos_annuaire.php",
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

                                if(set_call.contains("Désactivé") && set_message.contains("Désactivé")
                                && set_whatsapp.contains("Désactivé") && set_microteams.contains("Désactivé")){
                                    fonct.setText("Aucune fonctionnalité n'est disponible.");
                                }

                                if(set_call.contains("Activé")) {
                                    lin1.setVisibility(View.VISIBLE);
                                }else{
                                    lin1.setVisibility(View.GONE);
                                }

                                if(set_message.contains("Activé")){
                                    lin2.setVisibility(View.VISIBLE);
                                }
                                else{
                                    lin2.setVisibility(View.GONE);
                                }

                                if(set_whatsapp.contains("Activé")){
                                    lin3.setVisibility(View.VISIBLE);
                                }
                                else{
                                    lin3.setVisibility(View.GONE);
                                }

                                if(set_microteams.contains("Activé")){
                                    lin4.setVisibility(View.VISIBLE);
                                }
                                else{
                                    lin4.setVisibility(View.GONE);
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
    }*/


    private void afficher_listUsers() {
        final int ID = getActivity().getIntent().getIntExtra(LoginActivity.EXTRA_ID,0);
        final String NOM = getActivity().getIntent().getStringExtra(LoginActivity.EXTRA_NOM);
        final String PRENOM = getActivity().getIntent().getStringExtra(LoginActivity.EXTRA_PRENOM);
        final String EMAIL = getActivity().getIntent().getStringExtra(LoginActivity.EXTRA_EMAIL);
        final String PASSWORD = getActivity().getIntent().getStringExtra(LoginActivity.EXTRA_MDP);
        final String TELEPHONE = getActivity().getIntent().getStringExtra(LoginActivity.EXTRA_TEL);
        final String PHOTO = getActivity().getIntent().getStringExtra(LoginActivity.EXTRA_PHOTO);
        StringRequest request = new StringRequest(Request.Method.POST, "https://marhost.000webhostapp.com/List_Favoris_annuaire.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            Log.i("response:",response);
                            JSONArray jsonArray = jsonObject.getJSONArray("Favoris");
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
                                    String num_utili = object.getString("NUM_USER");
                                    String num_favourite = object.getString("NUM_FAV");


                                    userFav = new Utilisateur(id, filiale, nom, prenom, email, passwd, tel, photo, nom_fil);

                                    if (adapter_favoris.getCount() == jsonArray.length()) {
                                        adapter_favoris.notifyDataSetChanged();
                                    } else {
                                        if(!(utilisateurListFavoris.contains(userFav))) {
                                            adapter_favoris.add(userFav);
                                            adapter_favoris.notifyDataSetChanged();
                                        }
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