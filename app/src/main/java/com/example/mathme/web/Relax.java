package com.example.mathme.web;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mathme.R;

public class Relax extends AppCompatActivity {
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.4F);
    public static final String SELECTION = "iwannawatch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relax);
    }

    public void animeTwist(View view) {
        view.startAnimation(buttonClick);
        Intent twist = new Intent(this, Anime.class);
        twist.putExtra(SELECTION, "https://twist.moe/");
        startActivity(twist);
    }

    public void animeVibe(View view) {
        view.startAnimation(buttonClick);
        Intent vibe = new Intent(this, Anime.class);
        vibe.putExtra(SELECTION, "https://www1.animevibe.tv/");
        startActivity(vibe);
    }

    public void animeFlix(View view) {
        view.startAnimation(buttonClick);
        Intent flix = new Intent(this, Anime.class);
        flix.putExtra(SELECTION, "https://animeflix.io/");
        startActivity(flix);
    }

    public void animeWs(View view) {
        view.startAnimation(buttonClick);
        Intent ws = new Intent(this, Anime.class);
        ws.putExtra(SELECTION, "https://www.wonderfulsubs.com/");
        startActivity(ws);
    }
}
