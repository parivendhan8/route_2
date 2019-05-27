package com.example.route;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class splach_screen extends AppCompatActivity {

    Animation animation;
    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splach_screen);

        animation = AnimationUtils.loadAnimation(splach_screen.this, R.anim.bounce);
        imageView = (ImageView) findViewById(R.id.imageView);


        imageView.startAnimation(animation);



        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(splach_screen.this, MainActivity.class));
                        finish();
                    }
                },
                5000
        );
    }
}
