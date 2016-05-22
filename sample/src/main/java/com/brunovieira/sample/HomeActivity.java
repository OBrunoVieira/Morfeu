package com.brunovieira.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.brunovieira.morfeu.Morpheus;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        new Morpheus.Builder(this)
                .show();
    }
}
