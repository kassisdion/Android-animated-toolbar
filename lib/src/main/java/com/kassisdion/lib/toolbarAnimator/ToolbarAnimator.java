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
import android.view.MenuItem;

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
    private Drawable mAnimationBackground = null;//background drawable for the animatio

    //public field
    public enum AnimationType {
        FADE_IN,
        FADE_OUT
    }

    /*
    ** Constructor
     */
    public ToolbarAnimator(@NonNull final Toolbar toolbar) {
        mToolbar = toolbar;
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

    private void setAnimationDrawable(@NonNull final Drawable backgroundDrawable) {
        mAnimationBackground = backgroundDrawable;
    }

    /*
    ** Public method
     */
    public void stopAnimation() {
        stopScheduler();
    }

    public void animateItemActionView(final long duration, @NonNull final AnimationType animationType, final int menuItemId) {
        animateItemActionView(duration, animationType, mToolbar.getMenu().findItem(menuItemId));
    }

    public void animateItemActionView(final long duration, @NonNull final AnimationType animationType, @NonNull final MenuItem menuItem) {
        startAnimation(duration, animationType, new TargetUpdater() {
            @Override
            public Drawable getTargetBackground() {
                return menuItem.getIcon();
            }

            @Override
            public void updateTargetBackground(@NonNull Drawable newBackground) {
                menuItem.setIcon(newBackground);
            }
        });
    }

    public void animateToolbarBackground(final long duration, @NonNull final AnimationType animationType) {
        startAnimation(duration, animationType, new TargetUpdater() {
            @Override
            public Drawable getTargetBackground() {
                return mToolbar.getBackground();
            }

            @Override
            public void updateTargetBackground(@NonNull Drawable newBackground) {
                mToolbar.setBackground(newBackground);
            }
        });
    }

    /*
    ** Private method
     */
    private void startAnimation(final long duration, @NonNull final AnimationType animationType, @NonNull final TargetUpdater targetUpdater) {
        mDuration = duration;

        //Since we can't reuse a timer (see Timer.cancel()) we stop the previous animation
        if (mTimer != null) {
            stopScheduler();
        }

        //Init backgroundDrawable
        if (mAnimationBackground == null) {
            Drawable targetBackground = targetUpdater.getTargetBackground();
            if (targetBackground != null) {
                mAnimationBackground = targetBackground;
            } else {
                mAnimationBackground = new ColorDrawable(getThemeAccentColor(mToolbar.getContext()));
            }
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
                updateTargetBackground(targetUpdater);
            }
        }, mDelay, period);
    }

    //Update the target background on the main thread and check if the animation has to stop
    private void updateTargetBackground(@NonNull final TargetUpdater targetUpdater) {
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
                targetUpdater.updateTargetBackground(mAnimationBackground);

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

    private interface TargetUpdater {
        Drawable getTargetBackground();
        void updateTargetBackground(@NonNull final Drawable newBackground);
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
