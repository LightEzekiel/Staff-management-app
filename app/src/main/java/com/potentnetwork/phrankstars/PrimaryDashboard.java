package com.potentnetwork.phrankstars;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class PrimaryDashboard extends AppCompatActivity {
    


    CardView cardviewleft1,cardViewTop, Cardviewleft2, cardViewRight,cardViewRight2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary_dashboard);




        cardViewTop = findViewById(R.id.cardTop);
        cardViewRight = findViewById(R.id.cardRight);
        cardviewleft1 = findViewById(R.id.cardLeft);
        Cardviewleft2 = findViewById(R.id.cardViewLeft2);
        cardViewRight2 = findViewById(R.id.cardRight2);


        Animation animBottomToTop = AnimationUtils.loadAnimation(this,R.anim.anim_bottom_to_top);
        Animation animTopToBottom = AnimationUtils.loadAnimation(this,R.anim.anim_top_to_bottom);
        Animation animLeftToRight = AnimationUtils.loadAnimation(this,R.anim.anim_left_to_right);
        Animation animRightToLeft = AnimationUtils.loadAnimation(this,R.anim.anim_right_to_left);

        Cardviewleft2.setAnimation(animBottomToTop);
        cardViewTop.setAnimation(animTopToBottom);
        cardViewRight.setAnimation(animLeftToRight);
        cardviewleft1.setAnimation(animRightToLeft);
        cardViewRight2.setAnimation(animLeftToRight);

        cardViewTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent teacherIntent = new Intent(PrimaryDashboard.this,Teachers.class);
                startActivity(teacherIntent);
            }
        });
    }
}