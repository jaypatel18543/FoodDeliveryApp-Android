package com.example.fooddeliveryapk.chefFoodPanel;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.fooddeliveryapk.R;
import com.example.fooddeliveryapk.chef_postDish;

public class ChefProfileFragment extends Fragment {

    Button button;
    ConstraintLayout back1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chef_profile, null);
        getActivity().setTitle("Post Dish");
        // Create the animation
        AnimationDrawable animationDrawable = new AnimationDrawable();
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img1), 2000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img4), 2000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img6), 2000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.alaaa), 2000);
        animationDrawable.setOneShot(false);
        animationDrawable.setEnterFadeDuration(900);
        animationDrawable.setExitFadeDuration(1600);

        // Bind views
        back1 = v.findViewById(R.id.backimg);
        button = v.findViewById(R.id.button);
        Log.d("ChefProfileFragment", "Button visibility: " + button.getVisibility());


        // Start the animation
        back1.setBackground(animationDrawable);
        animationDrawable.start();

        // Set button click listener
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), chef_postDish.class));
            }
        });
        return v;
    }
}

