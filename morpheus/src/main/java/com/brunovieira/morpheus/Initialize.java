package com.brunovieira.morpheus;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;
import android.util.SparseIntArray;
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
        morpheusLayoutSetup(morpheus);
        setupTextView(morpheus);
        setupImageView(morpheus);
        setupAnimView(morpheus);
        setupClickListener(morpheus);
        setupViewTags(morpheus);
        setupStaticListeners(morpheus);
    }

    private static void morpheusLayoutSetup(@NonNull Morpheus morpheus) {
        if (morpheus.builder.layoutResID != 0) {
            morpheus.setContentView(morpheus.builder.layoutResID);
        }
    }

    private static void setupStaticListeners(@NonNull Morpheus morpheus) {
        if (morpheus.builder.onCancelListener != null) {
            morpheus.setOnCancelListener(morpheus);
        }

        if (morpheus.builder.onDismissListener != null) {
            morpheus.setOnDismissListener(morpheus);
        }

        if (morpheus.builder.onShowListener != null) {
            morpheus.setOnShowListener(morpheus);
        }
    }

    private static void setupViewTags(@NonNull Morpheus morpheus) {
        SparseArray<Morpheus.Tag> contentTag = morpheus.builder.contentTag;
        if (contentTag != null && contentTag.size() > 0) {
            for (int i = 0; i < contentTag.size(); i++) {
                int key = contentTag.keyAt(i);
                Morpheus.Tag tag = contentTag.get(key);
                View view = morpheus.findViewById(key);

                if (view != null) {
                    if (tag.getKey() != 0 && tag.getTag() != null) {
                        view.setTag(tag.getKey(), tag.getTag());
                    } else if (tag.getTag() != null) {
                        view.setTag(tag.getTag());
                    }
                }
            }
        }
    }

    private static void setupClickListener(final @NonNull Morpheus morpheus) {
        SparseArray<Morpheus.OnClickListener> contentClickListener = morpheus.builder.contentClickListener;
        if (contentClickListener != null && contentClickListener.size() > 0) {
            for (int i = 0; i < contentClickListener.size(); i++) {
                int key = contentClickListener.keyAt(i);
                View view = morpheus.findViewById(key);
                if (view != null) {
                    view.setOnClickListener(morpheus);
                }
            }
        }
    }

    private static void setupTextView(@NonNull Morpheus morpheus) {
        SparseArray<CharSequence> contentText = morpheus.builder.contentText;
        for (int i = 0; i < contentText.size(); i++) {
            int key = contentText.keyAt(i);
            View view = morpheus.findViewById(key);

            if (view != null && view instanceof TextView) {
                TextView textView = (TextView) view;
                textView.setText(contentText.get(key));

                SparseArray<Typeface> contentTypeFace = morpheus.builder.contentTypeFace;
                if (contentTypeFace != null && contentTypeFace.size() > 0) {
                    textView.setTypeface(contentTypeFace.get(view.getId()));
                }

                SparseIntArray contentImageButton = morpheus.builder.contentImageButton;
                if (contentImageButton != null && contentImageButton.size() > 0) {
                    int imageKey = contentImageButton.keyAt(i);
                    if(imageKey != 0) {
                        int imageValue = contentImageButton.get(imageKey);
                        if(imageValue != 0) {
                            Drawable drawable = ContextCompat.getDrawable(morpheus.getContext(),
                                    imageValue);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                view.setBackground(drawable);
                                return;
                            }
                            view.setBackgroundDrawable(drawable);
                        }
                    }
                }
            }
        }
    }

    private static void setupImageView(@NonNull Morpheus morpheus) {
        SparseIntArray contentImage = morpheus.builder.contentImage;
        for (int i = 0; i < contentImage.size(); i++) {
            int key = contentImage.keyAt(i);
            View view = morpheus.findViewById(key);

            if (view != null && view instanceof ImageView) {
                ImageView imageView = (ImageView) view;
                imageView.setImageResource(contentImage.get(key));
            }
        }
    }

    private static void setupAnimView(@NonNull Morpheus morpheus) {
        SparseIntArray contentAnimation = morpheus.builder.contentAnimation;
        for (int i = 0; i < contentAnimation.size(); i++) {
            int key = contentAnimation.keyAt(i);
            View view = morpheus.findViewById(key);
            if (view != null) {
                Animation animation = AnimationUtils.loadAnimation(morpheus.builder.context, contentAnimation.get(key));
                view.startAnimation(animation);

                SparseArray<Animation.AnimationListener> contentAnimationListener = morpheus.builder.contentAnimationListener;
                if (contentAnimationListener != null && contentAnimationListener.size() > 0) {
                    Animation.AnimationListener animationListener = contentAnimationListener.get(view.getId());
                    animation.setAnimationListener(animationListener);
                }
            }
        }
    }

    static void startAnimation(@NonNull Morpheus morpheus) {
        setupAnimView(morpheus);
    }
}
