package com.dev.projet_annuaire;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;

public class InfosUsersActivity extends AppCompatActivity {

    TextInputEditText nomtxt,emailtxt,pwdtxt,teltxt;
    int position;
    SwitchCompat switchCompat;
    //int active;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME ="mypref";
    private static final String ID ="myid";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infos_users);

        nomtxt = findViewById(R.id.nomField);
        emailtxt = findViewById(R.id.emailField);
        pwdtxt = findViewById(R.id.pwdField);
        teltxt = findViewById(R.id.telField);

        switchCompat = findViewById(R.id.switchbtn);

        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");

        nomtxt.setText("  "+ListUtilisateursActivity.utilisateurArrayList.get(position).getNom_user()+" "+ListUtilisateursActivity.utilisateurArrayList.get(position).getPrenom_user());
        emailtxt.setText("  "+ListUtilisateursActivity.utilisateurArrayList.get(position).getEmail_user());
        pwdtxt.setText("  "+ListUtilisateursActivity.utilisateurArrayList.get(position).getPassword_user());
        teltxt.setText("  "+ListUtilisateursActivity.utilisateurArrayList.get(position).getTel_user());


        /*
        //Save state of switch btn in shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("save",MODE_PRIVATE);
        switchCompat.setChecked(sharedPreferences.getBoolean("value",true));
        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchCompat.isChecked()){
                    //if switch btn is checked:
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value", true);
                    editor.apply();
                    switchCompat.setChecked(true);
                }
                else{
                    //if switch btn is not checked:
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value", false);
                    editor.apply();
                    switchCompat.setChecked(false);
                }
            }
        });
        */

    }
}