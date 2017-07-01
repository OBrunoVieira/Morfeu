package com.brunovieira.morpheus.test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.brunovieira.morpheus.Anim;
import com.brunovieira.morpheus.Morpheus;
import com.brunovieira.morpheus.Theme;

public class HomeActivity extends AppCompatActivity implements Morpheus.OnClickListener {

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

    @Override
    public void onClickDialog(@NonNull Morpheus dialog, @NonNull View view) {
    }

    private void createDialog() {
        new Morpheus.Builder(this)
                .contentView(R.layout.view_dialog)
                .theme(Theme.TRANSLUCENT_THEME)
                .addText(R.id.dialog_title, "Lorem ipsum dolor sit amet.")
                .addText(R.id.dialog_subtitle, "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...")
                .addText(R.id.dialog_dimiss, "Lorem")
                .addClickToView(R.id.dialog_main_button, this)
                .addTag(R.id.dialog_main_button, new Morpheus.Tag(TAG))
                .addButton(R.id.dialog_main_button, android.R.drawable.alert_dark_frame, "Lorem Ipsum")
                .addViewToAnim(R.id.dialog_content_main, android.R.anim.fade_in)
                .addViewToAnim(R.id.feedback_dialog_frame_content, Anim.ANIM_SPRING_IN)
                .dismissListener(new Morpheus.OnDismissListener() {
                    @Override
                    public void onDismissDialog(@NonNull Morpheus dialog) {
                        Toast.makeText(HomeActivity.this, "Dismiss Listener", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }
}
