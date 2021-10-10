package com.dev.projet_annuaire;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.SyncStateContract;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;
import static android.provider.Settings.System.AIRPLANE_MODE_ON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class Fragment3 extends Fragment {
    TextView emailtext, mdptext, contacttext, idtext;
    TextView fullnametxt;
    TextView btn_update;
    FloatingActionButton btn_savePhoto;
    ImageView imgView;
    Bitmap bitmap;
    String encodeImage;


    public static final String SHARED_PREF="shared_preferences";
    public static final String KEY_EMAIL ="email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_TEL ="telephone";
    public static final String KEY_ID ="id";
    public static final String KEY_PHOTO="photo";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment3, container, false);

        fullnametxt = rootView.findViewById(R.id.txtFullName);
        emailtext = rootView.findViewById(R.id.editEmail);
        mdptext = rootView.findViewById(R.id.editPwd);
        contacttext = rootView.findViewById(R.id.editTel);
        idtext = rootView.findViewById(R.id.id);
        imgView = rootView.findViewById(R.id.imgView);
        //btn_addPhoto = rootView.findViewById(R.id.btnSelectImage);
        btn_savePhoto = rootView.findViewById(R.id.btnUploadImage);

        //InfosUser();

        Bundle bundle = getArguments();
        String lname = bundle.getString("lastname");
        String fname = bundle.getString("firstname");
        String em = bundle.getString("email");
        //String pwd = bundle.getString("password");
        String tel = bundle.getString("phone");
        String ph = bundle.getString("photoUser");
        String fil = bundle.getString("filialeUser");
        String idd = bundle.getString("id");


        fullnametxt.setText(lname + " " + fname);
        idtext.setText(idd);

            emailtext.setText(em);
            mdptext.setText(fil);
            contacttext.setText(tel);

        idtext.setText(idd);

        if(ph.equals("")) {
            Picasso.with(getContext()).load("https://marhost.000webhostapp.com/photos/usericone.png").into(imgView);
        }
        else {
            Picasso.with(getContext()).load("https://marhost.000webhostapp.com/photos/" + ph).into(imgView);
        }

        //InfosUser();

        /*SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        String email = sharedPreferences.getString(KEY_EMAIL, "");
        String mdp = sharedPreferences.getString(KEY_PASSWORD, "");
        String contact = sharedPreferences.getString(KEY_TEL, "");
        String photo = sharedPreferences.getString(KEY_PHOTO, "");

        emailtext.setText(email);
        mdptext.setText(mdp);
        contacttext.setText(contact);

        if (ph.equals("")) {
            Picasso.with(getContext()).load("https://marproj.000webhostapp.com/Images/usericone.png").into(imgView);
        } else {
            Picasso.with(getContext()).load("https://marproj.000webhostapp.com/Images/" + photo).into(imgView);
        }*/

        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPhoto();
            }
        });

        btn_savePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePhoto();
            }
        });

        return rootView;
    }


    private void InfosUser() {
        //String idUser = idtext.getText().toString();
        Bundle bundle = getArguments();
        String idd = bundle.getString("id");
        StringRequest request = new StringRequest(Request.Method.POST, "https://marproj.000webhostapp.com/InfosUser_annuaire.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject userResponse = jsonObject.getJSONObject("Infos");
                            Log.i("response:", response);
                            if (response.contains("Infos")) {
                                String nom = userResponse.getString("nom_user");
                                String prenom = userResponse.getString("prenom_user");
                                String email = userResponse.getString("email_user");
                                String motdepasse = userResponse.getString("password_user");
                                String telephone = userResponse.getString("tel_user");
                                String ph = userResponse.getString("photo");


                                SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(KEY_EMAIL, email);
                                editor.putString(KEY_PASSWORD, motdepasse);
                                editor.putString(KEY_TEL, telephone);
                                editor.putString(KEY_PHOTO,ph);
                                editor.apply();

                                //fullnametxt.setText(nom+" "+prenom);
                                emailtext.setText(email);
                                mdptext.setText(motdepasse);
                                contacttext.setText(telephone);
                                //idtext.setText(idd);

                                if(ph.equals("")) {
                                    Picasso.with(getContext()).load("https://marproj.000webhostapp.com/Images/usericone.png").into(imgView);
                                }
                                else {
                                    Picasso.with(getContext()).load("https://marproj.000webhostapp.com/Images/" + ph).into(imgView);
                                }

                    }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }){
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("id_user",idd);
                return params;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(request);
    }



    private void savePhoto() {
        String idUser = idtext.getText().toString();
        Bundle bundle = getArguments();
        String ph = bundle.getString("photoUser");
        StringRequest request = new StringRequest(Request.Method.POST, "https://marhost.000webhostapp.com/uploadPhoto.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(),response,Toast.LENGTH_LONG).show();
                        //Picasso.with(getContext()).load("https://marproj.000webhostapp.com/Images/" + ph).into(imgView);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"Veuillez ajouter une photo.",Toast.LENGTH_LONG).show();
            }
        }){
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("id_user",idUser);
                params.put("photo",encodeImage);
                return params;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    private void addPhoto() {
        Dexter.withContext(getContext())
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(Intent.createChooser(intent,"Select Image"),1);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 1 && resultCode == Activity.RESULT_OK && data!=null){
            Uri filepath = data.getData();
            try {
                InputStream inputStream = getContext().getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imgView.setImageBitmap(bitmap);
                imageStore(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void imageStore(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] imageBytes = stream.toByteArray();
        encodeImage = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private void saveUpdate() {
        String idU = idtext.getText().toString();
        String emailU = emailtext.getText().toString();
        String pwdU = mdptext.getText().toString();
        String telU = contacttext.getText().toString();

        StringRequest request = new StringRequest(Request.Method.POST, "https://marproj.000webhostapp.com//updateInfos_annuaire.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                        Log.i("msg:","Informations modifiés");
                        /*emailtext.setText(emailU);
                        mdptext.setText(pwdU);
                        contacttext.setText(telU);*/
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
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
    }
}

