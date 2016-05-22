package com.brunovieira.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Toast;

import com.brunovieira.morfeu.Morpheus;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        View view = findViewById(R.id.home_fab);
        if (view != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Morpheus.Builder(v.getContext())
                            .contentView(R.layout.view_dialog)
                            .theme(Morpheus.TRANSLUCENT_THEME)
                            .addText(R.id.dialog_title, "AnyTitle")
                            .addText(R.id.dialog_subtitle, "WERTYUIOPSDFGHJKLSADFGH SADFGHJKLSADFGHJK DSFGHDSFG")
                            .addText(R.id.dialog_dimiss, "Cancel")
                            .addViewToAnim(R.id.dialog_content_main, android.R.anim.fade_in)
                            .addViewToAnim(R.id.dialog_card_view, Morpheus.ANIM_SPRING_IN, new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    Toast.makeText(getBaseContext(), "Animation End", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            })
                            .show();
                }
            });
        }
    }
}
