package com.brunovieira.morfeu;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by bruno.vieira on 21/05/2016.
 */

public class Initialize {
    static void now(@NonNull Morpheus morpheus) {
        morpheusLayoutSetup(morpheus);
        textViewSetup(morpheus);
        imageViewSetup(morpheus);
        animViewSetup(morpheus);
        clickListenerSetup(morpheus);
    }

    private static void morpheusLayoutSetup(@NonNull Morpheus morpheus) {
        if (morpheus.builder.layoutResID != 0)
            morpheus.setContentView(morpheus.builder.layoutResID);
    }

    private static void clickListenerSetup(final @NonNull Morpheus morpheus) {
        if (morpheus.builder.contentClickListener.size() > 0 && morpheus.builder.contentClickListener != null) {
            for (Object object : morpheus.builder.contentClickListener.entrySet()) {
                Map.Entry pairs = (Map.Entry) object;
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
            View view = morpheus.findViewById((Integer) pairs.getKey());
            if (view != null) {
                if (view instanceof TextView) {
                    ((TextView) view).setText((CharSequence) pairs.getValue());
                    if (morpheus.builder.contentTypeFace.size() > 0 && morpheus.builder.contentTypeFace != null) {
                        ((TextView) view).setTypeface(morpheus.builder.contentTypeFace.get(view.getId()));
                    }

                    if (morpheus.builder.contentImageButton.size() > 0
                            && morpheus.builder.contentImageButton != null
                            && morpheus.builder.contentImageButton.get(pairs.getKey()) != null) {
                        view.setBackgroundResource(morpheus.builder.contentImageButton.get(pairs.getKey()));
                    }
                }
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
        for (Object object : morpheus.builder.contentAnimation.entrySet()) {
            Map.Entry pairs = (Map.Entry) object;
            View view = morpheus.findViewById((Integer) pairs.getKey());
            if (view != null) {
                Animation animation = AnimationUtils.loadAnimation(morpheus.builder.context, (Integer) pairs.getValue());
                view.startAnimation(animation);
                if (morpheus.builder.contentAnimationListener != null && morpheus.builder.contentAnimationListener.size() > 0) {
                    Animation.AnimationListener animationListener = morpheus.builder.contentAnimationListener.get(view.getId());
                    animation.setAnimationListener(animationListener);
                }
            }
        }
    }

    public static void startAnimation(@NonNull Morpheus morpheus) {
        animViewSetup(morpheus);
    }
}
