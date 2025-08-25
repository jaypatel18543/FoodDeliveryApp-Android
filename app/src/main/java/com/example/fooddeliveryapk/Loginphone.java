package com.example.fooddeliveryapk;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;

public class Loginphone extends AppCompatActivity {
    private EditText numbeR;
    private Button OTP, btnemaiLin;
    private TextView signuppp;
    private CountryCodePicker countrycOde;
    private FirebaseAuth fAuth;
    private String number;
    private Intent intent;
    private String type;
    private ConstraintLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loginphone);
        main = findViewById(R.id.main);
        numbeR = findViewById(R.id.numbeR);
        OTP = findViewById(R.id.OTP);
        countrycOde = findViewById(R.id.countrycOde);
        btnemaiLin = findViewById(R.id.btnemaiLin);
        signuppp = findViewById(R.id.signuppp);

        AnimationDrawable animationDrawable = new AnimationDrawable();
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img1), 2000);
        // animationDrawable.addFrame(getResources().getDrawable(R.drawable.img2), 2000);
        // animationDrawable.addFrame(getResources().getDrawable(R.drawable.img3), 2000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img4), 2000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img6), 2000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.alaaa), 2000);

        animationDrawable.setOneShot(false);
        animationDrawable.setEnterFadeDuration(900);
        animationDrawable.setExitFadeDuration(1600);

        main = findViewById(R.id.main);
        main.setBackground(animationDrawable);
        animationDrawable.start();

        OTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                number=numbeR.getText().toString().trim();
                String Phonenum = countrycOde.getSelectedCountryCodeWithPlus()+number;
                Intent b = new Intent(Loginphone.this,sendOtp.class);

                b.putExtra("Phonenumber",Phonenum);
                startActivity(b);
                finish();

            }
        });
        signuppp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Loginphone.this,Registration.class));
                finish();
            }
        });
        btnemaiLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Loginphone.this,Login.class));
                finish();
            }
        });
    }
}