package com.example.fooddeliveryapk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private TextInputLayout idemaIl, Paswordfgh;
    private Button log, btnphoneno;
    private TextView signup, forgotpasswrd, signphoneno;
    private FirebaseAuth fAuth;
    private String emailiD, pwdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fAuth = FirebaseAuth.getInstance();
        idemaIl = findViewById(R.id.idemaIl);
        Paswordfgh = findViewById(R.id.Paswordfgh);
        log = findViewById(R.id.log);
        signup = findViewById(R.id.signup);
        btnphoneno = findViewById(R.id.btnphoneno);
        signphoneno = findViewById(R.id.signphoneno);
        forgotpasswrd = findViewById(R.id.forgotpasswrd);

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                emailiD = idemaIl.getEditText().getText().toString().trim();
                pwdd = Paswordfgh.getEditText().getText().toString().trim();

                if (isValid()) {
                    try {
                        final ProgressDialog mDialog = new ProgressDialog(Login.this);
                        mDialog.setCanceledOnTouchOutside(false);
                        mDialog.setCancelable(false);
                        mDialog.setMessage("Sign In Please Wait.......");
                        mDialog.show();

                        fAuth.signInWithEmailAndPassword(emailiD, pwdd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    mDialog.dismiss();

                                    if (fAuth.getCurrentUser().isEmailVerified()) {
                                        mDialog.dismiss();
                                        Toast.makeText(Login.this, "Congratulation! You Have Successfully Logged In", Toast.LENGTH_SHORT).show();
                                        Intent Z = new Intent(Login.this, CustomerFoodPanel1_BottomNavigation.class);
                                        startActivity(Z);
                                        finish();

                                    } else {
                                        ReusableCodeForAll.ShowAlert(Login.this, "Verification Failed", "You Have Not Verified Your Email");
                                    }
                                } else {
                                    mDialog.dismiss();
                                    ReusableCodeForAll.ShowAlert(Login.this, "Error", task.getException().getMessage());
                                }
                            }
                        });
                    } catch (Exception e) {
                        Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Registration.class));
                finish();
            }
        });

        forgotpasswrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, ForgotPassword.class));
                finish();
            }
        });

        signphoneno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Loginphone.class));
                finish();
            }
        });
    }

    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public boolean isValid() {
        idemaIl.setErrorEnabled(false);
        idemaIl.setError("");
        Paswordfgh.setErrorEnabled(false);
        Paswordfgh.setError("");

        boolean isvalid = false, isvalidemail = false, isvalidpassword = false;

        if (TextUtils.isEmpty(emailiD)) {
            idemaIl.setErrorEnabled(true);
            idemaIl.setError("Email is required");
        } else {
            if (emailiD.matches(emailpattern)) {
                isvalidemail = true;
            } else {
                idemaIl.setErrorEnabled(true);
                idemaIl.setError("Invalid Email Address");
            }
        }

        if (TextUtils.isEmpty(pwdd)) {
            Paswordfgh.setErrorEnabled(true);
            Paswordfgh.setError("Password is Required");
        } else {
            isvalidpassword = true;
        }

        isvalid = (isvalidemail && isvalidpassword);
        return isvalid;
    }
}
