package com.brunovieira.morpheus;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.annotation.AnimRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatDialog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;

import java.lang.ref.WeakReference;

/**
 * Created by bruno.vieira on 20/05/2016.
 */

public class Morpheus extends AppCompatDialog implements View.OnClickListener, DialogInterface.OnCancelListener, DialogInterface.OnShowListener, DialogInterface.OnDismissListener {
    private static WeakReference<Morpheus> morpheus;
    Builder builder;

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

    public Builder getBuilder() {
        return builder;
    }

    @UiThread
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
            builder.contentClickListener.get(view.getId()).onClickDialog(this, view);
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
        builder.contentTag = null;

        builder.onCancelListener = null;
        builder.onDismissListener = null;
        builder.onShowListener = null;

        super.dismiss();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        builder.onCancelListener.onCancelDialog(this);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        builder.onCancelListener.onCancelDialog(this);
    }

    @Override
    public void onShow(DialogInterface dialog) {
        builder.onCancelListener.onCancelDialog(this);
    }

    public interface OnClickListener {
        void onClickDialog(@NonNull Morpheus dialog, @NonNull View view);
    }

    public interface OnCancelListener {
        void onCancelDialog(@NonNull Morpheus dialog);
    }

    public interface OnDismissListener {
        void onDismissDialog(@NonNull Morpheus dialog);
    }

    public interface OnShowListener {
        void onShowDialog(@NonNull Morpheus dialog);
    }

    public static class Builder {
        final Context context;
        int layoutResID;
        int themeId;

        SparseIntArray contentAnimation = new SparseIntArray();
        SparseIntArray contentImage = new SparseIntArray();
        SparseIntArray contentImageButton = new SparseIntArray();
        SparseArray<CharSequence> contentText = new SparseArray<>();
        SparseArray<Animation.AnimationListener> contentAnimationListener = new SparseArray<>();
        SparseArray<OnClickListener> contentClickListener = new SparseArray<>();
        SparseArray<Typeface> contentTypeFace = new SparseArray<>();
        SparseArray<Tag> contentTag = new SparseArray<>();

        OnCancelListener onCancelListener;
        OnDismissListener onDismissListener;
        OnShowListener onShowListener;

        public Builder(@NonNull Context context) {
            this.context = context;
            initializeSparseArray();
        }

        public Builder(@NonNull android.support.v4.app.Fragment fragment) {
            this.context = fragment.getContext();
            initializeSparseArray();
        }

        public Builder addTag(@IdRes int viewId, @NonNull Tag tag) {
            this.contentTag.put(viewId, tag);
            return this;
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
            if (contentAnimation != null) {
                contentAnimation.put(id, anim);
            }
            return this;
        }

        public Builder addViewToAnim(@IdRes int id, @AnimRes int anim, @NonNull Animation.AnimationListener animationListener) {
            if (contentAnimation != null) {
                contentAnimation.put(id, anim);
            }

            if (contentAnimationListener != null) {
                contentAnimationListener.put(id, animationListener);
            }
            return this;
        }

        public Builder addClickToView(@IdRes int id, @NonNull OnClickListener OnClickListener) {
            contentClickListener.put(id, OnClickListener);
            return this;
        }

        public Builder addImage(int id, @DrawableRes int drawable) {
            contentImage.put(id, drawable);
            return this;
        }

        public Builder cancelListener(@NonNull OnCancelListener onCancelListener) {
            this.onCancelListener = onCancelListener;
            return this;
        }

        public Builder dismissListener(@NonNull OnDismissListener onDismissListener) {
            this.onDismissListener = onDismissListener;
            return this;
        }

        public Builder showListener(@NonNull OnShowListener onShowListener) {
            this.onShowListener = onShowListener;
            return this;
        }

        @UiThread
        public Morpheus startAnimation() {
            Initialize.startAnimation(morpheus.get());
            return morpheus.get();
        }

        @UiThread
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

        private void initializeSparseArray() {
            contentAnimation = new SparseIntArray();
            contentImage = new SparseIntArray();
            contentImageButton = new SparseIntArray();
            contentText = new SparseArray<>();
            contentAnimationListener = new SparseArray<>();
            contentClickListener = new SparseArray<>();
            contentTypeFace = new SparseArray<>();
            contentTag = new SparseArray<>();
        }
    }

    public static class Tag {
        @IntegerRes
        private int key;

        private Object tag;

        public Tag(int key, Object tag) {
            this.key = key;
            this.tag = tag;
        }

        public Tag(Object tag) {
            this.tag = tag;
        }

        public Tag() {
        }

        public int getKey() {
            return key;
        }

        public Object getTag() {
            return tag;
        }
    }
}
