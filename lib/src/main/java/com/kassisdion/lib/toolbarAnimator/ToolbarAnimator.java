package com.kassisdion.lib.toolbarAnimator;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;

import com.kassisdion.lib.R;
import com.kassisdion.utils.LogHelper;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Simple class for animate a toolbar (fadeIn & fadeOut)
 */
public final class ToolbarAnimator {

    private final static String TAG = ToolbarAnimator.class.getSimpleName();

    //private static field
    private final static int ALPHA_MAX = 255;//just look at the documentation
    private final static int NUMBER_OF_TICK = 255;//can go from 1 to 255, it's the number of tick

    //private field we'll change under the thread
    private volatile int mCurrentAlpha;
    private volatile Timer mTimer;
    private volatile ToolbarAnimatorCallback mCallback;
    private volatile int mAlphaPerTick;//alpha we'll remove/add on every tick

    //private field
    private final Toolbar mToolbar;
    private final Context mContext;
    private long mDuration;//duration of the animation
    private long mDelay;//amount of time in milliseconds before animation execution.
    private final int mAnimationColor;//background color for the animation
    private final Drawable mAnimationBackground;//background drawable for the animation

    //public field
    public enum AnimationType {
        FADE_IN,
        FADE_OUT
    }

    /*
    ** Constructor
     */
    public ToolbarAnimator(@NonNull final Context context, @NonNull final Toolbar toolbar, final int animationColor) {
        mContext = context;
        mToolbar = toolbar;
        mAnimationColor = animationColor;
        mAnimationBackground = new ColorDrawable(animationColor);
    }

    public ToolbarAnimator(@NonNull final Context context, @NonNull final Toolbar actionBar) {
        this(context, actionBar, getThemeAccentColor(context));
    }

    /*
    ** Public method
     */
    public ToolbarAnimator setCallback(@NonNull final ToolbarAnimatorCallback callback) {
        mCallback = callback;
        return this;
    }

    public ToolbarAnimator setDelay(final int delay) {
        mDelay = delay;
        return this;
    }

    public void startAnimation(final long duration, @NonNull final AnimationType animationType) {
        mDuration = duration;

        //If we don't have a timer, we instantiate a new one
        if (mTimer == null) {
            mTimer = new Timer();
        }

        //Check animationType and init variable in regard of the type
        switch (animationType) {
            case FADE_IN:
                mAlphaPerTick = ALPHA_MAX / NUMBER_OF_TICK;
                mCurrentAlpha = 0;
                break;
            case FADE_OUT:
                mAlphaPerTick = -1 * ALPHA_MAX / NUMBER_OF_TICK;
                mCurrentAlpha = 255;
                break;
        }

        //calculation of the time between 2 run() call
        long period = mDuration / NUMBER_OF_TICK;

        //init a timer as a scheduler for calling updateActionBar() on every each period
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                //update the actionBar
                updateActionBar();
            }
        }, mDelay, period);
    }

    /*
    ** Private method
     */
    private void updateActionBar() {
        //We have to go to the main thread for updating the interface.
        ((Activity) mContext).runOnUiThread(new TimerTask() {
            @Override
            public void run() {
                //check if the animation is ended
                if (mCurrentAlpha > 255 || mCurrentAlpha < 0) {
                    LogHelper.d(TAG, "cancel timer");
                    finishAnimation();
                    return;
                }

                //create the new backgroundColorDrawable
                mAnimationBackground.setAlpha(mCurrentAlpha);

                //apply the new drawable on the actionBar
                mToolbar.setBackgroundDrawable(mAnimationBackground);

                //upgrade alpha
                mCurrentAlpha += mAlphaPerTick;
            }
        });
    }

    private void finishAnimation() {
        if (mTimer == null) {
            return;
        }
        mTimer.cancel();
        mTimer.purge();
        mTimer = null;

        if (mCallback != null) {
            mCallback.hasEnded();
        }
    }

    /*
    ** Utils
     */
    private static int getThemeAccentColor(final Context context) {
        TypedValue typedValue = new TypedValue();

        TypedArray a = context.obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorAccent});
        int color = a.getColor(0, Color.BLUE);
        a.recycle();

        return color;
    }
}
