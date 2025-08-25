package com.example.fooddeliveryapk;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils; // Import this line
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;

public class Chefloginphone extends AppCompatActivity {
    private EditText numberJ;
    private Button otp, btnemaiL;
    private TextView signupH;
    private CountryCodePicker countrycode;
    private FirebaseAuth fAuth;
    private String number;
    private ConstraintLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chefloginphone);

        // Initialize views
        main = findViewById(R.id.main);
        numberJ = findViewById(R.id.numberJ);
        otp = findViewById(R.id.otp);
        countrycode = findViewById(R.id.countrycode);
        btnemaiL = findViewById(R.id.btnemaiL);
        signupH = findViewById(R.id.signupH);

        // Set up animation
        AnimationDrawable animationDrawable = new AnimationDrawable();
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img1), 2000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img4), 2000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img6), 2000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.alaaa), 2000);

        animationDrawable.setOneShot(false);
        animationDrawable.setEnterFadeDuration(900);
        animationDrawable.setExitFadeDuration(1600);
        main.setBackground(animationDrawable);
        animationDrawable.start();

        // Initialize Firebase Auth
        fAuth = FirebaseAuth.getInstance();

        // Handle OTP button click
        otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                number=numberJ.getText().toString().trim();
                String Phonenumber = countrycode.getSelectedCountryCodeWithPlus()+number;
                Intent b = new Intent(Chefloginphone.this,ChefSendOtp.class);

                b.putExtra("Phonenumber",Phonenumber);
                startActivity(b);
                finish();

            }
        });
        signupH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Chefloginphone.this,ChefRegistration.class));
                finish();
            }
        });
        btnemaiL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Chefloginphone.this,Cheflogin.class));
                finish();
            }
        });

    }
}