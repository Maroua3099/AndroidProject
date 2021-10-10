package com.dev.projet_annuaire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CompteClient extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte_client);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new Fragment2()).commit();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        final int ID = getIntent().getIntExtra(LoginActivity.EXTRA_ID,0);
        final String NOM = getIntent().getStringExtra(LoginActivity.EXTRA_NOM);
        final String PRENOM = getIntent().getStringExtra(LoginActivity.EXTRA_PRENOM);
        final String EMAIL = getIntent().getStringExtra(LoginActivity.EXTRA_EMAIL);
        final String PASSWORD = getIntent().getStringExtra(LoginActivity.EXTRA_MDP);
        final String TELEPHONE = getIntent().getStringExtra(LoginActivity.EXTRA_TEL);
        final String PHOTO = getIntent().getStringExtra(LoginActivity.EXTRA_PHOTO);
        final String FILIALE = getIntent().getStringExtra(LoginActivity.EXTRA_FILIALE);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.contacts:
                        fragment = new Fragment2();
                        /*Bundle bundle2 = new Bundle();
                        bundle2.putString("id", String.valueOf(ID));
                        bundle2.putString("lastname",NOM);
                        bundle2.putString("firstname",PRENOM);
                        bundle2.putString("email",EMAIL);
                        bundle2.putString("password",PASSWORD);
                        bundle2.putString("phone",TELEPHONE);
                        bundle2.putString("photoUser",PHOTO);
                        fragment.setArguments(bundle2);*/
                        break;
                    case R.id.infos:
                        fragment = new Fragment3();
                        Bundle bundle3 = new Bundle();
                        bundle3.putString("id", String.valueOf(ID));
                        bundle3.putString("lastname",NOM);
                        bundle3.putString("firstname",PRENOM);
                        bundle3.putString("email",EMAIL);
                        bundle3.putString("password",PASSWORD);
                        bundle3.putString("phone",TELEPHONE);
                        bundle3.putString("photoUser",PHOTO);
                        bundle3.putString("filialeUser",FILIALE);
                        fragment.setArguments(bundle3);
                        break;

                    case R.id.filiales:
                        fragment = new Fragment5();
                        break;
                    case R.id.setting:
                        fragment = new Fragment4();
                        Bundle bundle4 = new Bundle();
                        bundle4.putString("id", String.valueOf(ID));
                        bundle4.putString("lastname",NOM);
                        bundle4.putString("firstname",PRENOM);
                        bundle4.putString("email",EMAIL);
                        bundle4.putString("password",PASSWORD);
                        bundle4.putString("phone",TELEPHONE);
                        bundle4.putString("photoUser",PHOTO);
                        fragment.setArguments(bundle4);
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
                return true;
            }
        });
    }
}
