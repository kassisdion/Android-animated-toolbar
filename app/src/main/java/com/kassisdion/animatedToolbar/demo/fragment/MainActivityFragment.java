package com.kassisdion.animatedToolbar.demo.fragment;

import com.kassisdion.animatedToolbar.R;
import com.kassisdion.animatedToolbar.demo.activity.MainActivity;
import com.kassisdion.lib.toolbar.AnimatedToolbar;
import com.kassisdion.lib.toolbarAnimator.ToolbarAnimator;
import com.kassisdion.lib.toolbarAnimator.ToolbarAnimatorCallback;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends BaseFragment {

    public final static String FRAGMENT_NAME = MainActivityFragment.class.getSimpleName();

    public static void load(@NonNull final FragmentManager fragmentManager, final Boolean toBackStack) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, new MainActivityFragment());
        if (toBackStack) {
            fragmentTransaction.addToBackStack(FRAGMENT_NAME);
        }
        fragmentTransaction.commit();
    }

    /*
    ** Life cycle
     */
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_main;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    protected void init(View rootView, Bundle savedInstanceState) {
        //Force onCreateOptionsMenu call
        setHasOptionsMenu(true);
    }

    /*
    ** Butterknife callback
     */
    @OnClick(R.id.button_fade_in_toolbar)
    public void fadeInToolbar() {
        //check if fragment was detach
        if (mContext == null) {
            return;
        }

        AnimatedToolbar toolbar = ((MainActivity)mContext).getToolbar();
        toolbar.getAnimator()
                .setCallback(mToolbarAnimatorCallback)
                .animateToolbarBackground(2 * 1000, ToolbarAnimator.AnimationType.FADE_IN);
    }

    @OnClick(R.id.button_fade_out_toolbar)
    public void fadeOuToolbar() {
        //check if fragment was detach
        if (mContext == null) {
            return;
        }

        AnimatedToolbar toolbar = ((MainActivity) mContext).getToolbar();
        toolbar.getAnimator()
                .setCallback(mToolbarAnimatorCallback)
                .animateToolbarBackground(2 * 1000, ToolbarAnimator.AnimationType.FADE_OUT);
    }

    @OnClick(R.id.button_fade_in_item)
    public void fadeInItem() {
        //check if fragment was detach
        if (mContext == null) {
            return;
        }

        AnimatedToolbar toolbar = ((MainActivity)mContext).getToolbar();
        toolbar.getAnimator()
                .setCallback(mToolbarAnimatorCallback)
                .animateItemActionView(2 * 1000, ToolbarAnimator.AnimationType.FADE_IN, R.id.action_test);
    }

    @OnClick(R.id.button_fade_out_item)
    public void fadeOutItem(final Button button) {
        //check if fragment was detach
        if (mContext == null) {
            return;
        }

        AnimatedToolbar toolbar = ((MainActivity) mContext).getToolbar();
        toolbar.getAnimator()
                .setCallback(mToolbarAnimatorCallback)
                .animateItemActionView(2 * 1000, ToolbarAnimator.AnimationType.FADE_OUT, R.id.action_test);
    }

    /*
    ** Private
     */
    private final ToolbarAnimatorCallback mToolbarAnimatorCallback = new ToolbarAnimatorCallback() {
        @Override
        public void hasEnded() {
            //check if fragment was detach
            if (mContext == null) {
                return;
            }
            Toast.makeText(mContext, "Animation ended :D", Toast.LENGTH_LONG).show();
        }
    };
}
