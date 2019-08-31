package com.example.mathme.web;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mathme.R;

public class Relax extends AppCompatActivity {
    public static final String SELECTION = "iwannawatch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relax);
    }

    public void animeTwist(View view) {
        Intent twist = new Intent(this, Anime.class);
        twist.putExtra(SELECTION, "https://twist.moe/");
        startActivity(twist);
    }

    public void animeVibe(View view) {
        Intent vibe = new Intent(this, Anime.class);
        vibe.putExtra(SELECTION, "https://www1.animevibe.tv/");
        startActivity(vibe);
    }

    public void animeFlix(View view) {
        Intent flix = new Intent(this, Anime.class);
        flix.putExtra(SELECTION, "https://animeflix.io/");
        startActivity(flix);
    }

    //does not work on ishitas stupid phone
    public void animeWs(View view) {
        Intent ws = new Intent(this, Anime.class);
        ws.putExtra(SELECTION, "https://www.wonderfulsubs.com/");
        startActivity(ws);
    }


    public void animeUltima(View view) {
        Intent au = new Intent(this, Anime.class);
        au.putExtra(SELECTION, "https://www12.animeultima.eu");
        startActivity(au);
    }
}
