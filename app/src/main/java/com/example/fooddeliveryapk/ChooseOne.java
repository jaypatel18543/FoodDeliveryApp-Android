package com.example.fooddeliveryapk;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class ChooseOne extends AppCompatActivity {
    private Button chef, customer, deliverP;
    private Intent intent;
    private String type;
    private ConstraintLayout bggimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choose_one);

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

        bggimage = findViewById(R.id.bggimage);
        bggimage.setBackground(animationDrawable);
        animationDrawable.start();

        intent = getIntent();
        if (intent != null && intent.hasExtra("Home")) {
            type = intent.getStringExtra("Home").trim();
        }


        chef = findViewById(R.id.chef);
        customer = findViewById(R.id.customer);
        deliverP = findViewById(R.id.deliveryP);

        chef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type != null) {
                    switch (type) {
                        case "Email":
                            startActivity(new Intent(ChooseOne.this, Cheflogin.class));
                            break;
                        case "Phone":
                            startActivity(new Intent(ChooseOne.this, Chefloginphone.class));
                            break;
                        case "Signup":
                            startActivity(new Intent(ChooseOne.this, ChefRegistration.class));
                            break;
                    }
                    finish();
                }
            }
        });

        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type != null) {
                    switch (type) {
                        case "Email":
                            startActivity(new Intent(ChooseOne.this, Login.class));
                            break;
                        case "Phone":
                            startActivity(new Intent(ChooseOne.this, Loginphone.class));
                            break;
                        case "Signup":
                            startActivity(new Intent(ChooseOne.this, Registration.class));
                            break;
                    }
                    finish();
                }
            }
        });

        deliverP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type != null) {
                    switch (type) {
                        case "Email":
                            startActivity(new Intent(ChooseOne.this, Deliverylogin.class));
                            break;
                        case "Phone":
                            startActivity(new Intent(ChooseOne.this, Deliveryloginphone.class));
                            break;
                        case "Signup":
                            startActivity(new Intent(ChooseOne.this, DeliveryRegistration.class));
                            break;
                    }
                    finish();
                }
            }
        });
    }
}
