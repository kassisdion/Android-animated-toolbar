package com.kassisdion.animatedToolbar.demo.activity;

import com.kassisdion.animatedToolbar.R;
import com.kassisdion.animatedToolbar.demo.fragment.MainActivityFragment;
import com.kassisdion.lib.toolbar.AnimatedToolbar;

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
        MainActivityFragment.load(getSupportFragmentManager(), false);
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

    public AnimatedToolbar getToolbar() {
        return mToolbar;
    }
}
