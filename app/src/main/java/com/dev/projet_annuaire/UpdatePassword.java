package com.dev.projet_annuaire;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UpdatePassword extends AppCompatActivity {

    EditText currentPwd,newPwd,confirmNewPwd;
    Button btnvalide;
    ProgressBar progressBar;
    TextView error;
    ImageView imgError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        currentPwd = findViewById(R.id.currentPwd);
        newPwd = findViewById(R.id.newPwd);
        confirmNewPwd = findViewById(R.id.confirmNewPwd);
        btnvalide = findViewById(R.id.btn_valide);
        progressBar = findViewById(R.id.progbar);
        error = findViewById(R.id.errortext);
        imgError = findViewById(R.id.imgError);

        currentPwd.addTextChangedListener(currentpasswordWatcher);
        confirmNewPwd.addTextChangedListener(newpasswordWatcher);


        btnvalide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPwdValue = newPwd.getText().toString();
                String ID = getIntent().getStringExtra(Fragment4.EXTRA_IDUSER);
                if (confirmNewPwd.getText().toString().equals(newPwd.getText().toString())) {
                    progressBar.setVisibility(View.VISIBLE);
                    StringRequest request = new StringRequest(Request.Method.POST, "https://marhost.000webhostapp.com/ModifierMdp_annuaire.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.i("msg:", "Modifié");
                                    progressBar.setVisibility(View.GONE);
                                    final Dialog dialog = new Dialog(UpdatePassword.this);
                                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog.setContentView(R.layout.activity_main7);
                                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                                    dialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);
                                    dialog.show();
                                    error.setVisibility(View.GONE);
                                    imgError.setVisibility(View.GONE);
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
                            params.put("id_user", ID);
                            params.put("password_user", newPwdValue);
                            return params;
                        }
                    };
                    VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

                }else{
                    Toast toast = new Toast(getApplicationContext());
                    toast.setText("Les mots de passe saisis \n ne correspondent pas.");
                    toast.show();
                    Log.i("msg:", "Invalid passwords");
                }
            }
        });
    }

    private final TextWatcher currentpasswordWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            imgError.setVisibility(View.GONE);
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            imgError.setVisibility(View.VISIBLE);
        }

        public void afterTextChanged(Editable s) {
            String PASSWORD = getIntent().getStringExtra(Fragment4.EXTRA_MDP);
            if (!(currentPwd.getText().toString().equals(PASSWORD))) {
                imgError.setImageDrawable(getResources().getDrawable(R.drawable.pwdwrong));
            }

            else{
                imgError.setImageDrawable(getResources().getDrawable(R.drawable.pwdvalide));
            }
        }
    };

    private final TextWatcher newpasswordWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            error.setVisibility(View.GONE);
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            error.setVisibility(View.VISIBLE);
        }

        public void afterTextChanged(Editable s) {

            if (!(confirmNewPwd.getText().toString().equals(newPwd.getText().toString()))) {
                error.setText(" "+"Les mots de passe saisis ne correspondent pas.");
                error.setTextColor(Color.parseColor("#cc0000"));
                error.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pwdwrong, 0, 0, 0);
            }

            else{
                error.setText(" "+"Mots de passe identiques.");
                error.setTextColor(Color.parseColor("#3cb371"));
                error.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pwdvalide, 0, 0, 0);
            }
        }
    };
}