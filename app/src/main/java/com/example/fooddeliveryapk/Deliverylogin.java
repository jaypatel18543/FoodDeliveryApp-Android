package com.example.fooddeliveryapk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Deliverylogin extends AppCompatActivity {

    TextInputLayout idemail, pwrrdjui;
    private Button signin, btnphone;
    private TextView signup, forgotpass, signphone;
    FirebaseAuth fAuth;
    String emailiD, pwdd;

    private ConstraintLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_deliverylogin);

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
        idemail = findViewById(R.id.idemail12);
        pwrrdjui = findViewById(R.id.pwdrrrrd);
        signin = findViewById(R.id.buttonq);
        signup = findViewById(R.id.signup);
        btnphone = findViewById(R.id.btnphoneeer);
        signphone = findViewById(R.id.signphoneer);
        forgotpass = findViewById(R.id.forgotpasssdf);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailiD = idemail.getEditText().getText().toString().trim();
                pwdd = pwrrdjui.getEditText().getText().toString().trim();

                idemail.setError(null);
                pwrrdjui.setError(null);

                boolean isValidEmail = false, isValidPassword = false;
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                // Validate email
                if (TextUtils.isEmpty(emailiD)) {
                    idemail.setError("Email Is Required");
                } else if (!emailiD.matches(emailPattern)) {
                    idemail.setError("Invalid Email Address");
                } else {
                    isValidEmail = true;
                }

                // Validate password
                if (TextUtils.isEmpty(pwdd)) {
                    pwrrdjui.setError("Password is Required");
                } else {
                    isValidPassword = true;
                }

                // Proceed with authentication if both inputs are valid
                if (isValidEmail && isValidPassword) {
                    final ProgressDialog mDialog = new ProgressDialog(Deliverylogin.this);
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
                                    Toast.makeText(Deliverylogin.this, "Congratulations! You have Successfully Logged In", Toast.LENGTH_SHORT).show();
                                    Intent Z = new Intent(Deliverylogin.this, DeliveryFoodPanel1_BottomNavigation.class);
                                    startActivity(Z);
                                    finish();
                                } else {
                                    ReusableCodeForAll.ShowAlert(Deliverylogin.this, "Verification Failed", "You Have Not Verified Your Email");
                                }
                            } else {
                                ReusableCodeForAll.ShowAlert(Deliverylogin.this, "Error", task.getException().getMessage());
                            }
                        }
                    });
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Deliverylogin.this, DeliveryRegistration.class));
                finish();
            }
        });

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Deliverylogin.this, DeliveryForgotPassword.class));
                finish();
            }
        });

        signphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Deliverylogin.this, Deliveryloginphone.class));
                finish();
            }
        });
    }
    };

