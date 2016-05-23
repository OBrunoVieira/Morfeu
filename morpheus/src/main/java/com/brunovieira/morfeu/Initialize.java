package com.brunovieira.morfeu;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by bruno.vieira on 21/05/2016.
 */

public class Initialize {
    static void now(@NonNull Morpheus morpheus) {
        if (morpheus.builder.layoutResID != 0)
            morpheus.setContentView(morpheus.builder.layoutResID);

        textViewSetup(morpheus);
        imageViewSetup(morpheus);
        animViewSetup(morpheus);
        clickListenerSetup(morpheus);
    }

    private static void clickListenerSetup(final @NonNull Morpheus morpheus) {
        if (morpheus.builder.contentClickListener.size() > 0 && morpheus.builder.contentClickListener != null) {
            Iterator iterator = morpheus.builder.contentClickListener.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry pairs = (Map.Entry) iterator.next();
                View view = morpheus.findViewById((Integer) pairs.getKey());
                if (view != null) {
                    view.setOnClickListener(morpheus);
                }
            }
        }
    }

    private static void textViewSetup(@NonNull Morpheus morpheus) {
        Iterator iterator = morpheus.builder.contentText.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pairs = (Map.Entry) iterator.next();
            TextView textView = (TextView) morpheus.findViewById((Integer) pairs.getKey());
            if (textView != null) {
                textView.setText((CharSequence) pairs.getValue());
            }
            iterator.remove();
        }
    }

    private static void imageViewSetup(@NonNull Morpheus morpheus) {
        Iterator iterator = morpheus.builder.contentImage.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pairs = (Map.Entry) iterator.next();
            ImageView drawable = (ImageView) morpheus.findViewById((Integer) pairs.getKey());
            if (drawable != null) {
                drawable.setImageResource((Integer) pairs.getValue());
            }
            iterator.remove();
        }
    }

    private static void animViewSetup(@NonNull Morpheus morpheus) {
        Iterator iterator = morpheus.builder.contentAnimation.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pairs = (Map.Entry) iterator.next();
            View view = morpheus.findViewById((Integer) pairs.getKey());
            if (view != null) {
                Animation animation = AnimationUtils.loadAnimation(morpheus.builder.context, (Integer) pairs.getValue());
                view.startAnimation(animation);
                if (morpheus.builder.contentAnimationListener != null && morpheus.builder.contentAnimationListener.size() > 0) {
                    Animation.AnimationListener animationListener = morpheus.builder.contentAnimationListener.get(pairs.getKey());
                    animation.setAnimationListener(animationListener);
                }
            }
        }
    }

    public static void startAnimation(@NonNull Morpheus morpheus) {
        animViewSetup(morpheus);
    }
}
