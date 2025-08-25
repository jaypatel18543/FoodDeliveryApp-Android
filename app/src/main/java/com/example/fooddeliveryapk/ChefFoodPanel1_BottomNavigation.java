package com.example.fooddeliveryapk;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.fooddeliveryapk.chefFoodPanel.ChefHomeFragment;
import com.example.fooddeliveryapk.chefFoodPanel.ChefPendingOrderFragment;
import com.example.fooddeliveryapk.chefFoodPanel.ChefProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class ChefFoodPanel1_BottomNavigation extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_activity2_chef_food_panel1_bottom_navigation);
        BottomNavigationView navigationView = findViewById(R.id.chef_bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
    }


        @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        int itemId = item.getItemId();

        if (itemId == R.id.chefHome) {
            fragment = new ChefHomeFragment();
        } else if (itemId == R.id.pendingOrders) {
            fragment = new ChefPendingOrderFragment();
        } else if (itemId == R.id.Orders) {
            fragment = new ChefHomeFragment(); // Use the appropriate fragment for "Orders"
        } else if (itemId == R.id.chefProfile) {
            fragment = new ChefProfileFragment();
        }

        return loadcheffragment(fragment);
    }

    private boolean loadcheffragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            return true;
        }
        return false;
    }
}
