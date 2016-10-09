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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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
    private final static int NUMBER_OF_TICK = 255;//Number of tick, can go from 1 to 255 (cause alpha is an integer)

    //private field we'll change under the thread
    private volatile int mCurrentAlpha;
    private volatile Timer mTimer;
    private volatile ToolbarAnimatorCallback mCallback;
    private volatile int mAlphaPerTick;//alpha we'll remove/add on every tick

    //private field
    private final Toolbar mToolbar;
    private long mDuration;//duration of the animation
    private long mDelay;//amount of time in milliseconds before animation execution.
    private int mAnimationColor;//background color for the animation
    private final Drawable mAnimationBackground;//background drawable for the animation

    //public field
    public enum AnimationType {
        FADE_IN,
        FADE_OUT
    }

    /*
    ** Constructor
     */
    public ToolbarAnimator(@NonNull final Toolbar toolbar, final int animationColor) {
        mToolbar = toolbar;
        mAnimationColor = animationColor;
        mAnimationBackground = new ColorDrawable(animationColor);
    }

    public ToolbarAnimator(@NonNull final Context context, @NonNull final Toolbar actionBar) {
        this(actionBar, getThemeAccentColor(context));
    }

    /*
    ** Getter and setter
     */
    public ToolbarAnimator setCallback(@NonNull final ToolbarAnimatorCallback callback) {
        mCallback = callback;
        return this;
    }

    public ToolbarAnimator setDelay(final long delay) {
        mDelay = delay;
        return this;
    }

    public long getDelay() {
        return mDelay;
    }

    public void stopAnimation() {
        stopScheduler();
    }

    public ToolbarAnimator setAnimationColor(final int color) {
        mAnimationColor = color;
        return this;
    }

    public int getAnimationColor() {
        return mAnimationColor;
    }

    /*
    ** Public method
     */
    public void animateItem(final long duration, @NonNull final AnimationType animationType, @NonNull final int menuItemId) {
        startAnimation(duration, animationType, mToolbar.getMenu().findItem(menuItemId).getActionView());
    }

    public void animateItem(final long duration, @NonNull final AnimationType animationType, @NonNull final MenuItem menuItem) {
        startAnimation(duration, animationType, menuItem.getActionView());
    }

    public void animateToolbar(final long duration, @NonNull final AnimationType animationType) {
        startAnimation(duration, animationType, mToolbar);
    }

    /*
    ** Private method
     */
    private void startAnimation(final long duration, @NonNull final AnimationType animationType, @NonNull final View target) {
        mDuration = duration;

        //Since we can't reuse a timer (see Timer.cancel()) we stop the previous animation
        if (mTimer != null) {
            stopScheduler();
        }

        //Instantiate a new timer
        mTimer = new Timer();

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
                //update the target background
                updateTargetBackground(target);
            }
        }, mDelay, period);
    }

    //Update the target background on the main thread and check if the animation has to stop
    private void updateTargetBackground(@NonNull final View target) {
        //We have to go to the main thread for updating the interface.
        ((Activity) mToolbar.getContext()).runOnUiThread(new TimerTask() {
            @Override
            public void run() {
                //check if the animation is ended
                if (mCurrentAlpha > 255 || mCurrentAlpha < 0) {
                    LogHelper.d(TAG, "cancel timer");

                    stopScheduler();
                    return;
                }

                //create the new backgroundColorDrawable
                mAnimationBackground.setAlpha(mCurrentAlpha);

                //apply the new drawable on the actionBar
                target.setBackground(mAnimationBackground);

                //upgrade alpha
                mCurrentAlpha += mAlphaPerTick;
            }
        });
    }

    //Stop the scheduler and call the listener
    private void stopScheduler() {
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
    private static int getThemeAccentColor(@NonNull final Context context) {
        TypedValue typedValue = new TypedValue();

        TypedArray a = context.obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorAccent});
        int color = a.getColor(0, Color.BLUE);
        a.recycle();

        return color;
    }
}
