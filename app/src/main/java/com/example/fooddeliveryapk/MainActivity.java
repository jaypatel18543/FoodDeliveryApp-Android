package com.example.fooddeliveryapk;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private TextView textView, textView2;
    private ImageView imageView;
    FirebaseAuth fAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        imageView = findViewById(R.id.imageView);

        imageView.animate().alpha(0f).setDuration(0);
        textView.animate().alpha(0f).setDuration(0);
        textView2.animate().alpha(0f).setDuration(0);

        imageView.animate().alpha(1f).setDuration(4000).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                textView.animate().alpha(1f).setDuration(2000);
                textView2.animate().alpha(1f).setDuration(3000);
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fAuth = FirebaseAuth.getInstance();
                if (fAuth.getCurrentUser() != null) {
                    if (fAuth.getCurrentUser().isEmailVerified()) {
                        databaseReference = FirebaseDatabase.getInstance().getReference("User").child(fAuth.getUid() + "/Role");
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String role = snapshot.getValue(String.class);
                                if (role != null) {
                                    switch (role) {
                                        case "Chef":
                                            startActivity(new Intent(MainActivity.this, ChefFoodPanel1_BottomNavigation.class));
                                            break;
                                        case "Customer":
                                            startActivity(new Intent(MainActivity.this, CustomerFoodPanel1_BottomNavigation.class));
                                            break;
                                        case "DeliveryPerson":
                                            startActivity(new Intent(MainActivity.this, DeliveryFoodPanel1_BottomNavigation.class));
                                            break;
                                        default:
                                            Toast.makeText(MainActivity.this, "Role not recognized.", Toast.LENGTH_LONG).show();
                                            break;
                                    }
                                    finish();
                                } else {
                                    Toast.makeText(MainActivity.this, "Role not found.", Toast.LENGTH_LONG).show();
                                    fAuth.signOut();
                                    startActivity(new Intent(MainActivity.this, signup.class));
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        fAuth.signOut();
                    }
                } else {
                    Intent intent = new Intent(MainActivity.this, signup.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000);
    }
}
