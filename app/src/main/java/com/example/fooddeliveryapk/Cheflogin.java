package com.example.fooddeliveryapk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Cheflogin extends AppCompatActivity {
    TextInputLayout idemail, pwrrdjui;
    private Button signin, btnphone;
    private TextView signup, forgotpass, signphone;
    FirebaseAuth fAuth;
    String emailiD, pwdd;

    private ConstraintLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheflogin);

        AnimationDrawable animationDrawable = new AnimationDrawable();
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img1), 2000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img4), 2000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img6), 2000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.alaaa), 2000);
        animationDrawable.setOneShot(false);
        animationDrawable.setEnterFadeDuration(900);
        animationDrawable.setExitFadeDuration(1600);

        main = findViewById(R.id.main);
        main.setBackground(animationDrawable);
        animationDrawable.start();

        fAuth = FirebaseAuth.getInstance();
        idemail = findViewById(R.id.idemail);
        pwrrdjui = findViewById(R.id.pwrrdjui);
        signin = findViewById(R.id.signuppp);
        signup = findViewById(R.id.signup);
        btnphone = findViewById(R.id.btnphone);
        signphone = findViewById(R.id.signphone);
        forgotpass = findViewById(R.id.forgotpass);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailiD = idemail.getEditText().getText().toString().trim();
                pwdd = pwrrdjui.getEditText().getText().toString().trim();

                idemail.setError(null);
                pwrrdjui.setError(null);

                boolean isValidEmail = false, isValidPassword = false;
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (TextUtils.isEmpty(emailiD)) {
                    idemail.setError("Email Is Required");
                } else if (!emailiD.matches(emailPattern)) {
                    idemail.setError("Invalid Email Address");
                } else {
                    isValidEmail = true;
                }


                if (TextUtils.isEmpty(pwdd)) {
                    pwrrdjui.setError("Password is Required");
                } else {
                    isValidPassword = true;
                }


                if (isValidEmail && isValidPassword) {
                    final ProgressDialog mDialog = new ProgressDialog(Cheflogin.this);
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.setCancelable(false);
                    mDialog.setMessage("Signing In Please Wait...");
                    mDialog.show();

                    fAuth.signInWithEmailAndPassword(emailiD, pwdd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            mDialog.dismiss();
                            if (task.isSuccessful()) {
                                if (fAuth.getCurrentUser().isEmailVerified()) {
                                    Toast.makeText(Cheflogin.this, "Congratulations! You have Successfully Logged In", Toast.LENGTH_SHORT).show();
                                    Intent Z = new Intent(Cheflogin.this, ChefFoodPanel1_BottomNavigation.class);
                                    startActivity(Z);
                                    finish();
                                } else {
                                    ReusableCodeForAll.ShowAlert(Cheflogin.this, "Verification Failed", "You Have Not Verified Your Email");
                                }
                            } else {
                                Log.e("SignInError", "Error: " + task.getException().getMessage());
                                ReusableCodeForAll.ShowAlert(Cheflogin.this, "Error", task.getException().getMessage());
                            }
                        }
                    });
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Cheflogin.this, ChefRegistration.class));
                finish();
            }
        });

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Cheflogin.this, ChefForgotPassword.class));
                finish();
            }
        });

        signphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Cheflogin.this, Chefloginphone.class));
                finish();
            }
        });
    }
}
