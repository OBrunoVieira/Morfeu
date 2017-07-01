package com.brunovieira.morpheus;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

@RestrictTo(RestrictTo.Scope.LIBRARY)
class Initialize {

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    static void now(@NonNull Morpheus morpheus) {
        morpheusLayoutSetup(morpheus);
        setupTextView(morpheus);
        setupBackgroundButton(morpheus);
        setupImageView(morpheus);
        setupAnimView(morpheus);
        setupClickListener(morpheus);
        setupViewTags(morpheus);
        setupStaticListeners(morpheus);
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    static void startAnimation(Morpheus morpheus) {
        if (morpheus != null) {
            setupAnimView(morpheus);
        }
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
        Morpheus.Builder builder = morpheus.builder;
        if (builder != null) {
            SparseArray<Morpheus.Tag> contentTag = builder.contentTag;
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
    }

    private static void setupClickListener(final @NonNull Morpheus morpheus) {
        Morpheus.Builder builder = morpheus.builder;
        if (builder != null) {
            SparseArray<Morpheus.OnClickListener> contentClickListener = builder.contentClickListener;
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
    }

    private static void setupTextView(@NonNull Morpheus morpheus) {
        Morpheus.Builder builder = morpheus.builder;
        if (builder != null) {
            SparseArray<CharSequence> contentText = builder.contentText;
            if (contentText != null && contentText.size() > 0) {
                for (int i = 0; i < contentText.size(); i++) {
                    int key = contentText.keyAt(i);
                    View view = morpheus.findViewById(key);

                    if (view != null && view instanceof TextView) {
                        TextView textView = (TextView) view;
                        textView.setText(contentText.get(key));

                        SparseArray<Typeface> contentTypeFace = builder.contentTypeFace;
                        if (contentTypeFace != null && contentTypeFace.size() > 0) {
                            textView.setTypeface(contentTypeFace.get(view.getId()));
                        }
                    }
                }
            }
        }
    }

    private static void setupBackgroundButton(@NonNull Morpheus morpheus) {
        Morpheus.Builder builder = morpheus.builder;
        if (builder != null) {
            SparseIntArray contentImageButton = builder.contentImageButton;
            if (contentImageButton != null && contentImageButton.size() > 0) {
                for (int i = 0; i < contentImageButton.size(); i++) {
                    int key = contentImageButton.keyAt(i);
                    View view = morpheus.findViewById(key);

                    if (view != null && view instanceof Button) {
                        Button button = (Button) view;
                        int imageValue = contentImageButton.get(key);

                        if (imageValue != 0) {
                            Drawable drawable = ContextCompat.getDrawable(morpheus.getContext(),
                                    imageValue);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                button.setBackground(drawable);
                                return;
                            }
                            button.setBackgroundDrawable(drawable);
                        }
                    }
                }
            }
        }
    }

    private static void setupImageView(@NonNull Morpheus morpheus) {
        Morpheus.Builder builder = morpheus.builder;
        if (builder != null) {
            SparseIntArray contentImage = builder.contentImage;
            if (contentImage != null && contentImage.size() > 0) {
                for (int i = 0; i < contentImage.size(); i++) {
                    int key = contentImage.keyAt(i);
                    View view = morpheus.findViewById(key);

                    if (view != null && view instanceof ImageView) {
                        ImageView imageView = (ImageView) view;
                        imageView.setImageResource(contentImage.get(key));
                    }
                }
            }

            SparseArray<Bitmap> contentBitmap = builder.contentBitmap;
            if (contentBitmap != null && contentBitmap.size() > 0) {
                for (int i = 0; i < contentBitmap.size(); i++) {
                    int key = contentBitmap.keyAt(i);
                    View view = morpheus.findViewById(key);
                    Bitmap bitmap = contentBitmap.valueAt(i);

                    if (view != null && view instanceof ImageView && bitmap != null) {
                        ImageView imageView = (ImageView) view;
                        imageView.setImageBitmap(bitmap);
                    }
                }
            }
        }

    }

    private static void setupAnimView(@NonNull Morpheus morpheus) {
        Morpheus.Builder builder = morpheus.builder;
        if (builder != null) {
            SparseIntArray contentAnimation = builder.contentAnimation;
            if (contentAnimation != null && contentAnimation.size() > 0) {
                for (int i = 0; i < contentAnimation.size(); i++) {
                    int key = contentAnimation.keyAt(i);
                    View view = morpheus.findViewById(key);
                    if (view != null) {
                        Animation animation = AnimationUtils.loadAnimation(builder.context, contentAnimation.get(key));
                        view.startAnimation(animation);

                        SparseArray<Animation.AnimationListener> contentAnimationListener = builder.contentAnimationListener;
                        if (contentAnimationListener != null && contentAnimationListener.size() > 0) {
                            Animation.AnimationListener animationListener = contentAnimationListener.get(view.getId());
                            animation.setAnimationListener(animationListener);
                        }
                    }
                }
            }
        }
    }

    private Initialize() {
    }
}
