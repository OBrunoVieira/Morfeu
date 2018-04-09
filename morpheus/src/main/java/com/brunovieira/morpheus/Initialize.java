package com.brunovieira.morpheus;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by bruno.vieira on 21/05/2016.
 */

class Initialize {
    static void now(@NonNull Morpheus morpheus) {
        morpheusLayoutSetup(morpheus);
        setupTextView(morpheus);
        setupBackground(morpheus);
        setupImageView(morpheus);
        setupVisibility(morpheus);
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
        final Morpheus.Builder builder = morpheus.builder;
        if (builder != null) {
            final SparseArray<Morpheus.Tag> contentTag = builder.contentTag;
            if (contentTag != null && contentTag.size() > 0) {
                for (int i = 0; i < contentTag.size(); i++) {
                    int key = contentTag.keyAt(i);
                    final Morpheus.Tag tag = contentTag.get(key);
                    final View view = morpheus.findViewById(key);

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
    }

    private static void setupClickListener(final @NonNull Morpheus morpheus) {
        final Morpheus.Builder builder = morpheus.builder;
        if (builder != null) {
            final SparseArray<Morpheus.OnClickListener> contentClickListener = builder.contentClickListener;
            if (contentClickListener != null && contentClickListener.size() > 0) {
                for (int i = 0; i < contentClickListener.size(); i++) {
                    final int key = contentClickListener.keyAt(i);
                    final View view = morpheus.findViewById(key);
                    if (view != null) {
                        view.setOnClickListener(morpheus);
                    }
                }
            }
        }
    }

    private static void setupTextView(@NonNull Morpheus morpheus) {
        final Morpheus.Builder builder = morpheus.builder;
        if (builder != null) {
            final SparseArray<CharSequence> contentText = builder.contentText;
            if (contentText != null && contentText.size() > 0) {
                for (int i = 0; i < contentText.size(); i++) {
                    final int key = contentText.keyAt(i);
                    final View view = morpheus.findViewById(key);

                    if (view != null && view instanceof TextView) {
                        final TextView textView = (TextView) view;
                        textView.setText(contentText.get(key));

                        final SparseArray<Typeface> contentTypeFace = builder.contentTypeFace;
                        if (contentTypeFace != null && contentTypeFace.size() > 0) {
                            textView.setTypeface(contentTypeFace.get(view.getId()));
                        }
                    }
                }
            }
        }
    }

    private static void setupVisibility(@NonNull Morpheus morpheus) {
        final Morpheus.Builder builder = morpheus.builder;
        if (builder != null) {
            final SparseIntArray contentVisibility = builder.contentVisibility;
            if (contentVisibility != null && contentVisibility.size() > 0) {
                for (int i = 0; i < contentVisibility.size(); i++) {
                    final int key = contentVisibility.keyAt(i);
                    final View view = morpheus.findViewById(key);

                    if (view != null) {
                        final int visibilityValue = contentVisibility.get(key);
                        view.setVisibility(visibilityValue);
                    }
                }
            }
        }
    }

    private static void setupBackground(@NonNull Morpheus morpheus) {
        final Morpheus.Builder builder = morpheus.builder;
        if (builder != null) {
            final SparseIntArray contentImageButton = builder.contentBackground;
            if (contentImageButton != null && contentImageButton.size() > 0) {
                for (int i = 0; i < contentImageButton.size(); i++) {
                    final int key = contentImageButton.keyAt(i);
                    final View view = morpheus.findViewById(key);

                    if (view != null) {
                        final int imageRes = contentImageButton.get(key);
                        if (imageRes != 0) {
                            view.setBackgroundResource(imageRes);
                        }
                    }
                }
            }
        }
    }

    private static void setupImageView(@NonNull Morpheus morpheus) {
        final Morpheus.Builder builder = morpheus.builder;
        if (builder != null) {
            final SparseIntArray contentImage = builder.contentImage;
            if (contentImage != null && contentImage.size() > 0) {
                for (int i = 0; i < contentImage.size(); i++) {
                    final int key = contentImage.keyAt(i);
                    final View view = morpheus.findViewById(key);

                    if (view != null && view instanceof ImageView) {
                        final ImageView imageView = (ImageView) view;
                        imageView.setImageResource(contentImage.get(key));
                    }
                }
            }

            final SparseArray<Bitmap> contentBitmap = builder.contentBitmap;
            if (contentBitmap != null && contentBitmap.size() > 0) {
                for (int i = 0; i < contentBitmap.size(); i++) {
                    final int key = contentBitmap.keyAt(i);
                    final View view = morpheus.findViewById(key);
                    final Bitmap bitmap = contentBitmap.valueAt(i);

                    if (view != null && view instanceof ImageView && bitmap != null) {
                        final ImageView imageView = (ImageView) view;
                        imageView.setImageBitmap(bitmap);
                    }
                }
            }
        }

    }

    private static void setupAnimView(@NonNull Morpheus morpheus) {
        final Morpheus.Builder builder = morpheus.builder;
        if (builder != null) {
            final SparseIntArray contentAnimation = builder.contentAnimation;
            if (contentAnimation != null && contentAnimation.size() > 0) {
                for (int i = 0; i < contentAnimation.size(); i++) {
                    final int key = contentAnimation.keyAt(i);
                    final View view = morpheus.findViewById(key);
                    if (view != null) {
                        final Animation animation = AnimationUtils.loadAnimation(builder.context, contentAnimation.get(key));
                        view.startAnimation(animation);

                        final SparseArray<Animation.AnimationListener> contentAnimationListener = builder.contentAnimationListener;
                        if (contentAnimationListener != null && contentAnimationListener.size() > 0) {
                            final Animation.AnimationListener animationListener = contentAnimationListener.get(view.getId());
                            animation.setAnimationListener(animationListener);
                        }
                    }
                }
            }
        }
    }

    static void startAnimation(Morpheus morpheus) {
        if (morpheus != null) {
            setupAnimView(morpheus);
        }
    }
}
