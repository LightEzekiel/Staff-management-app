package com.potentnetwork.phrankstars;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class ChooseCategory extends AppCompatActivity {
    CardView btnPrimary,btnSecondary;
    float v = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);

        btnPrimary = findViewById(R.id.primary);
        btnSecondary = findViewById(R.id.secondary);


        Animation animLeftToRight = AnimationUtils.loadAnimation(this,R.anim.anim_left_to_right);
        Animation animRightToLeft = AnimationUtils.loadAnimation(this,R.anim.anim_right_to_left);

        btnPrimary.setAnimation(animRightToLeft);
        btnSecondary.setAnimation(animLeftToRight);
;


            btnSecondary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ChooseCategory.this,HighSchoolLogin.class);
                    startActivity(i);
                    finish();
                }
            });


                btnPrimary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseIntent = new Intent(ChooseCategory.this,LoginActivity.class);
                startActivity(chooseIntent);
                finish();
            }
        });
    }
}