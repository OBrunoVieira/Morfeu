package com.brunovieira.morpheus;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.AnimRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by bruno.vieira on 20/05/2016.
 */

public class Morpheus extends AppCompatDialog implements View.OnClickListener {
    public static final int TRANSLUCENT_THEME = R.style.DialogTranslucent;
    public static final int ANIM_SPRING_IN = R.anim.anim_spring_in;
    public static final int ANIM_SPRING_OUT = R.anim.anim_spring_out;

    Builder builder;

    private static WeakReference<Morpheus> morpheus;


    private Morpheus(Builder builder, int theme) {
        super(builder.context, theme);
        this.builder = builder;

        morpheus = new WeakReference<>(this);
        Initialize.now(this);
    }

    private Morpheus(Builder builder) {
        super(builder.context);
        this.builder = builder;

        morpheus = new WeakReference<>(this);
        Initialize.now(this);
    }

    @Override
    public void show() {
        try {
            super.show();
        } catch (WindowManager.BadTokenException e) {
            throw new WindowManager.BadTokenException("Bad window token, you cannot show a dialog before an Activity is created or after it's hidden.");
        }
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            builder.contentClickListener.get(view.getId()).onClick(this, view, builder);
        }
    }

    @Override
    public void dismiss() {
        builder.contentAnimation = null;
        builder.contentImage = null;
        builder.contentImageButton = null;
        builder.contentText = null;
        builder.contentAnimationListener = null;
        builder.contentClickListener = null;
        builder.contentTypeFace = null;
        super.dismiss();
    }

    public static class Builder {
        final Context context;
        int layoutResID;
        int themeId;

        HashMap<Integer, Integer> contentAnimation = new HashMap<>();
        HashMap<Integer, Integer> contentImage = new HashMap<>();
        HashMap<Integer, Integer> contentImageButton = new HashMap<>();
        HashMap<Integer, CharSequence> contentText = new HashMap<>();
        HashMap<Integer, Animation.AnimationListener> contentAnimationListener = new HashMap<>();
        HashMap<Integer, ClickCallback> contentClickListener = new HashMap<>();
        HashMap<Integer, Typeface> contentTypeFace = new HashMap<>();

        public Builder(@NonNull Context context) {
            this.context = context;
        }

        public Builder(@NonNull android.support.v4.app.Fragment fragment) {
            this.context = fragment.getContext();
        }

        public Builder addFontType(@IdRes int viewId, @NonNull Typeface typeface) {
            contentTypeFace.put(viewId, typeface);
            return this;
        }

        public Builder addButton(@IdRes int viewId, @StringRes int intRes, @NonNull Typeface typeface) {
            addButton(viewId, this.context.getString(intRes), typeface);
            return this;
        }

        public Builder addButton(@IdRes int viewId, @NonNull CharSequence charSequence, @NonNull Typeface typeface) {
            contentText.put(viewId, charSequence);
            contentTypeFace.put(viewId, typeface);
            return this;
        }

        public Builder addButton(@IdRes int viewId, @StringRes int intRes) {
            addButton(viewId, this.context.getString(intRes));
            return this;
        }

        public Builder addButton(@IdRes int viewId, @NonNull CharSequence charSequence) {
            contentText.put(viewId, charSequence);
            return this;
        }

        public Builder addButton(@IdRes int viewId, @DrawableRes int drawable, @StringRes int intRes) {
            addButton(viewId, drawable, this.context.getString(intRes));
            return this;
        }

        public Builder addButton(@IdRes int viewId, @DrawableRes int drawable, @NonNull CharSequence charSequence) {
            contentText.put(viewId, charSequence);
            contentImageButton.put(viewId, drawable);
            return this;
        }

        public Builder addButton(@IdRes int viewId, @DrawableRes int drawable, @StringRes int intRes, @NonNull Typeface typeface) {
            addButton(viewId, drawable, this.context.getString(intRes), typeface);
            return this;
        }

        public Builder addButton(@IdRes int viewId, @DrawableRes int drawable, @NonNull CharSequence charSequence, @NonNull Typeface typeface) {
            contentText.put(viewId, charSequence);
            contentTypeFace.put(viewId, typeface);
            contentImageButton.put(viewId, drawable);
            return this;
        }

        public Builder addText(@IdRes int viewId, @StringRes int intRes, @NonNull Typeface typeface) {
            addText(viewId, this.context.getString(intRes), typeface);
            return this;
        }

        public Builder addText(@IdRes int viewId, @NonNull CharSequence charSequence, @NonNull Typeface typeface) {
            contentText.put(viewId, charSequence);
            contentTypeFace.put(viewId, typeface);
            return this;
        }

        public Builder addText(@IdRes int viewId, @StringRes int intRes) {
            addText(viewId, this.context.getString(intRes));
            return this;
        }

        public Builder addText(@IdRes int viewId, @NonNull CharSequence charSequence) {
            contentText.put(viewId, charSequence);
            return this;
        }

        public Builder theme(@StyleRes int themeId) {
            this.themeId = themeId;
            return this;
        }

        public Builder contentView(@LayoutRes int layoutResID) {
            this.layoutResID = layoutResID;
            return this;
        }

        public Builder addViewToAnim(@IdRes int id, @AnimRes int anim) {
            contentAnimation.put(id, anim);
            return this;
        }

        public Builder addViewToAnim(@IdRes int id, @AnimRes int anim, @NonNull Animation.AnimationListener animationListener) {
            contentAnimation.put(id, anim);
            contentAnimationListener.put(id, animationListener);
            return this;
        }

        public Builder addClickToView(@IdRes int id, @NonNull ClickCallback clickCallback) {
            contentClickListener.put(id, clickCallback);
            return this;
        }

        public Builder addImage(int id, @DrawableRes int drawable) {
            contentImage.put(id, drawable);
            return this;
        }

        public Morpheus startAnimation() {
            Initialize.startAnimation(morpheus.get());
            return morpheus.get();
        }

        public Morpheus show() {
            Morpheus morpheus;
            if (themeId != 0) {
                morpheus = new Morpheus(this, themeId);
            } else {
                morpheus = new Morpheus(this);
            }
            morpheus.show();
            return morpheus;
        }
    }

    public interface ClickCallback {
        void onClick(@NonNull Morpheus dialog, @NonNull View view, Builder builder);
    }
}
