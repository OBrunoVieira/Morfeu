package com.brunovieira.morfeu;

import android.content.Context;
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

import com.brunovieira.morpheus.R;

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

    static WeakReference<Morpheus> morpheus;


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

    public static class Builder {
        final Context context;
        int layoutResID;
        int themeId;
        int animStart;
        int animEnd;

        HashMap<Integer, Integer> contentAnimation = new HashMap<>();
        HashMap<Integer, Integer> contentImage = new HashMap<>();
        HashMap<Integer, CharSequence> contentText = new HashMap<>();
        HashMap<Integer, Animation.AnimationListener> contentAnimationListener = new HashMap<>();
        HashMap<Integer, ClickCallback> contentClickListener = new HashMap<>();

        public Builder(@NonNull Context context) {
            this.context = context;
        }


        public Builder addText(@IdRes int viewId, @StringRes int intRes) {
            addText(viewId, this.context.getString(intRes));
            return this;
        }

        public Builder addText(@IdRes int viewId, @NonNull CharSequence charSequence) {
            contentText.put(viewId, charSequence);
            return this;
        }

        public Builder addText(@IdRes int viewId, @NonNull String string) {
            contentText.put(viewId, string);
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

        public Builder dismissListener(@NonNull OnDismissListener onDismissListener) {
            return this;
        }

        public Builder addImage(int id, @DrawableRes int drawable) {
            contentImage.put(id, drawable);
            return this;
        }

        public Builder anim(int animStart, int animEnd) {
            this.animStart = animStart;
            this.animEnd = animEnd;
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
