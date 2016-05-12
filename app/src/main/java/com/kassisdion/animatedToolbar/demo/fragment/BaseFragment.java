package com.kassisdion.animatedToolbar.demo.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Abstract class for Fragment
 * it make a little abstraction of the life cycle
 * it add a butterKnife support and some useful field/method
 */
public abstract class BaseFragment extends Fragment {

    protected final String TAG = getClass().getSimpleName();
    @Nullable
    protected Context mContext;

    /*
    ** Method that every child will have to implement
     */

    /**
     * get the layout id
     * it will be use under onCreateView()
     * as inflater.inflate(getLayoutResource(), ...)
     */
    protected abstract int getLayoutResource();

    /**
     * init the fragment, this is the equivalent of onCreateView
     */
    protected abstract void init(View rootView, Bundle savedInstanceState);

    /*
    ** Life cycle
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutResource(), container, false);
        ButterKnife.bind(this, rootView);
        init(rootView, savedInstanceState);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    /*
    ** Utils
     */
    public String toString() {
        return TAG;
    }

    protected void startActivity(Class clazz, Bundle bundle) {
        try {
            final Intent intent = new Intent(mContext, clazz);
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
