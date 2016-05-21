package com.brunovieira.morfeu;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatDialog;
import android.view.WindowManager;

import java.util.HashMap;

/**
 * Created by bruno.vieira on 20/05/2016.
 */

public class Morfeu extends AppCompatDialog {
    Builder builder;

    private Morfeu(Builder builder, int theme) {
        super(builder.context, theme);
        this.builder = builder;
        Initialize.now(this);
    }

    private Morfeu(Builder builder) {
        super(builder.context);
        this.builder = builder;
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

    public static class Builder {
        final Context context;
        int layoutResID;
        int themeId;
        int animStart;
        int animEnd;
        HashMap<Integer, Integer> contentAnimation = new HashMap<>();
        HashMap<Integer, Integer> contentImage = new HashMap<>();
        HashMap<Integer, CharSequence> contentText = new HashMap<>();

        public Builder(@NonNull Context context) {
            this.context = context;
        }

        public Builder addText(@IdRes int viewId, @StringRes int intRes) {
            addText(viewId, this.context.getString(intRes));
            return this;
        }

        public Builder addText(@IdRes int viewId, CharSequence charSequence) {
            if (charSequence == null)
                throw new IllegalArgumentException("sub title must be not null");

            contentText.put(viewId, charSequence);
            return this;
        }

        public Builder addText(@IdRes int viewId, String string) {
            if (string == null)
                throw new IllegalArgumentException("sub title must be not null");

            contentText.put(viewId, string);
            return this;
        }

        public Builder theme(int themeId) {
            this.themeId = themeId;
            return this;
        }

        public Builder contentView(@LayoutRes int layoutResID) {
            this.layoutResID = layoutResID;
            return this;
        }

        public Builder addViewToAnim(int id, int anim) {
            contentAnimation.put(id, anim);
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


        public Morfeu show() {
            Morfeu morfeu;
            if (themeId != 0) {
                morfeu = new Morfeu(this, themeId);
            } else {
                morfeu = new Morfeu(this);
            }
            morfeu.show();
            return morfeu;
        }
    }
}
