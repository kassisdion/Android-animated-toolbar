package com.kassisdion.animatedtoolbar.activity;

import com.kassisdion.animatedtoolbar.R;
import com.kassisdion.animatedtoolbar.lib.toolbar.AnimatedToolbar;
import com.kassisdion.animatedtoolbar.lib.toolbarAnimator.ToolbarAnimator;

import android.os.Bundle;

import butterknife.Bind;

public class MainActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    AnimatedToolbar mToolbar;


    /*
    ** Life cycle
     */
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        setUpToolbar();
        mToolbar.startAnimation(10 * 1000, ToolbarAnimator.AnimationType.FADE_IN);
    }

    @Override
    protected void initLogic(Bundle savedInstanceState) {

    }

    /*
    ** Utils
     */
    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
    }
}
