package com.kassisdion.lib.toolbar;

import com.kassisdion.lib.toolbarAnimator.ToolbarAnimator;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class AnimatedToolbar extends android.support.v7.widget.Toolbar {

    private final static String TAG = AnimatedToolbar.class.getSimpleName();

    private final ToolbarAnimator mToolbarAnimator;

    /*
    ** Constructor
     */
    public AnimatedToolbar(Context context) {
        this(context, null);
    }

    public AnimatedToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.toolbarStyle);
    }

    public AnimatedToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mToolbarAnimator = new ToolbarAnimator(this);
    }

    /*
    ** Public method
     */
    public ToolbarAnimator getAnimator() {
        return mToolbarAnimator;
    }
}