package com.kassisdion.animatedtoolbar.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import butterknife.ButterKnife;

/**
 * Abstract class for AppCompatActivity
 * it make a little abstraction of the life cycle
 * it add a butterKnife support and some useful field/method
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected final String TAG = getClass().getSimpleName();
    protected Context mContext;

    /*
    ** Method that every child will have to implement
     */

    /**
     * get the layout id
     * it will be use under onCreate()
     * as setContentView(getLayoutResource());
     */
    protected abstract int getLayoutResource();

    /**
     * init the graphical part
     * like putting text under Editext
     */
    protected abstract void initUI(Bundle savedInstanceState);

    /**
     * init the logical part
     * like retrieving date from an API
     */
    protected abstract void initLogic(Bundle savedInstanceState);

    /*
    ** Life cycle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        ButterKnife.bind(this);
        mContext = getApplicationContext();
        initUI(savedInstanceState);
        initLogic(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    /*
    ** Utils
     */
    public String toString() {
        return TAG;
    }

    protected void startActivity(Class clazz, Bundle bundle) {
        try {
            final Intent intent = new Intent(this, clazz);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            startActivity(intent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void startActivity(Class clazz) {
        startActivity(clazz, null);
    }
}

