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

public class Deliveryloginphone extends AppCompatActivity {
    private EditText numberJ;
    private Button otp, btnemaiL;
    private TextView signupH;
    private CountryCodePicker countrycode;
    private FirebaseAuth fAuth;
    private String number;
    private Intent intent;
    private String type;
    private ConstraintLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_deliveryloginphone);
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


        main = findViewById(R.id.main);
        numberJ = findViewById(R.id.numberr);
        otp = findViewById(R.id.otpp);
        countrycode = findViewById(R.id.countrycodee);
        btnemaiL = findViewById(R.id.btnemaiL14);
        signupH = findViewById(R.id.signupH);

        fAuth = FirebaseAuth.getInstance();

        // Handle OTP button click
        otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                number=numberJ.getText().toString().trim();
                String Phonenumber = countrycode.getSelectedCountryCodeWithPlus()+number;
                Intent b = new Intent(Deliveryloginphone.this,DeliverySendOtp.class);

                b.putExtra("Phonenumber",Phonenumber);
                startActivity(b);
                finish();

            }
        });
        signupH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Deliveryloginphone.this,DeliveryRegistration.class));
                finish();
            }
        });
        btnemaiL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Deliveryloginphone.this,Deliveryloginphone.class));
                finish();
            }
        });
    }
    }
