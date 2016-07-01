package com.brunovieira.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;

import com.brunovieira.morpheus.Morpheus;

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
                            .addClickToView(R.id.dialog_main_button, new Morpheus.ClickCallback() {
                                @Override
                                public void onClick(@NonNull final Morpheus dialog, @NonNull View view, Morpheus.Builder builder) {
                                    builder.addViewToAnim(R.id.dialog_content_main, android.R.anim.fade_out)
                                            .addViewToAnim(R.id.feedback_dialog_frame_content, Morpheus.ANIM_SPRING_OUT, new Animation.AnimationListener() {
                                                @Override
                                                public void onAnimationStart(Animation animation) {

                                                }

                                                @Override
                                                public void onAnimationEnd(Animation animation) {
                                                    dialog.dismiss();
                                                }

                                                @Override
                                                public void onAnimationRepeat(Animation animation) {

                                                }
                                            })
                                            .startAnimation();
                                }
                            })
                            .addButton(R.id.dialog_main_button, android.R.drawable.sym_def_app_icon, "press here")
                            .addViewToAnim(R.id.dialog_content_main, android.R.anim.fade_in)
                            .addViewToAnim(R.id.feedback_dialog_frame_content, Morpheus.ANIM_SPRING_IN)
                            .show();
                }
            });
        }
    }
}
