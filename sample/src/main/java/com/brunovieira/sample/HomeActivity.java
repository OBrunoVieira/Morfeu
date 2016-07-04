package com.brunovieira.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.brunovieira.morpheus.Morpheus;

public class HomeActivity extends AppCompatActivity implements Morpheus.ClickCallback {

    private static final int TAG = 12332;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        View view = findViewById(R.id.home_fab);
        if (view != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createDialog();
                }
            });
        }
    }

    private void createDialog() {
        new Morpheus.Builder(this)
                .contentView(R.layout.view_dialog)
                .theme(Morpheus.TRANSLUCENT_THEME)
                .addText(R.id.dialog_title, "AnyTitle")
                .addText(R.id.dialog_subtitle, "WERTYUIOPSDFGHJKLSADFGH SADFGHJKLSADFGHJK DSFGHDSFG")
                .addText(R.id.dialog_dimiss, "Cancel")
                .addClickToView(R.id.dialog_main_button, this)
                .addTag(R.id.dialog_main_button, new Morpheus.Tag(TAG))
                .addButton(R.id.dialog_main_button, "press here")
                .addViewToAnim(R.id.dialog_content_main, android.R.anim.fade_in)
                .addViewToAnim(R.id.feedback_dialog_frame_content, Morpheus.ANIM_SPRING_IN)
                .show();
    }

    @Override
    public void onClickDialog(@NonNull Morpheus dialog, @NonNull View view, Morpheus.Builder builder) {

    }
}
