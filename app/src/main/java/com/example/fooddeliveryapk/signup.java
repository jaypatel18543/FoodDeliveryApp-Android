package com.example.fooddeliveryapk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.core.view.WindowInsetsCompat;

public class signup extends AppCompatActivity {
    private TextView textView4, textView5, or;
    private Button Email, phone, signup;
    private ImageView bgimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final Animation zoomin = AnimationUtils.loadAnimation(this, R.anim.zoomin);
        final Animation zoomout = AnimationUtils.loadAnimation(this, R.anim.zoomout);

        bgimage = findViewById(R.id.bgimage);
        bgimage.setAnimation(zoomin);
        bgimage.setAnimation(zoomout);

        zoomout.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                bgimage.startAnimation(zoomin);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        zoomin.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                bgimage.startAnimation(zoomout);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        Email = findViewById(R.id.Email);
        phone = findViewById(R.id.phone);
        signup = findViewById(R.id.signup);

        Email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(signup.this, ChooseOne.class);
                email.putExtra("Home", "Email");
                startActivity(email);
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signinphone = new Intent(signup.this, ChooseOne.class);
                signinphone.putExtra("Home", "Phone");
                startActivity(signinphone);
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Signup = new Intent(signup.this, ChooseOne.class);
                Signup.putExtra("Home", "Signup");
                startActivity(Signup);
                finish();
            }
        });
    }

//    private void configureEdgeToEdgeDisplay() {
//        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
//        WindowInsetsControllerCompat insetsController = ViewCompat.getWindowInsetsController(getWindow().getDecorView());
//
//        if (insetsController != null) {
//            insetsController.hide(WindowInsetsCompat.Type.statusBars());
//            insetsController.hide(WindowInsetsCompat.Type.navigationBars());
//        }
//    }
}
