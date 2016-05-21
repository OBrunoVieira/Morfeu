package com.brunovieira.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.brunovieira.morfeu.Morfeu;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        new Morfeu.Builder(this)
                .show();
    }
}
