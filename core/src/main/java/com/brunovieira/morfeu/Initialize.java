package com.brunovieira.morfeu;

import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by bruno.vieira on 21/05/2016.
 */

public class Initialize {
    static void now(Morfeu morfeu) {
        if (morfeu.builder.layoutResID != 0)
            morfeu.setContentView(morfeu.builder.layoutResID);

        textViewSetup(morfeu);
        imageViewSetup(morfeu);
        animViewSetup(morfeu);
    }

    private static void textViewSetup(Morfeu morfeu) {
        Iterator iterator = morfeu.builder.contentText.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pairs = (Map.Entry) iterator.next();
            TextView textView = (TextView) morfeu.findViewById((Integer) pairs.getKey());
            if (textView != null) {
                textView.setText((CharSequence) pairs.getValue());
            }
            iterator.remove();
        }
    }

    private static void imageViewSetup(Morfeu morfeu) {
        Iterator iterator = morfeu.builder.contentImage.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pairs = (Map.Entry) iterator.next();
            ImageView drawable = (ImageView) morfeu.findViewById((Integer) pairs.getKey());
            if (drawable != null) {
                drawable.setImageResource((Integer) pairs.getValue());
            }
            iterator.remove();
        }
    }

    private static void animViewSetup(Morfeu morfeu) {
        Iterator iterator = morfeu.builder.contentAnimation.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pairs = (Map.Entry) iterator.next();
            View view = morfeu.findViewById((Integer) pairs.getKey());
            if (view != null)
                view.startAnimation(AnimationUtils.loadAnimation(morfeu.builder.context, (Integer) pairs.getValue()));
            iterator.remove();
        }
    }
}
