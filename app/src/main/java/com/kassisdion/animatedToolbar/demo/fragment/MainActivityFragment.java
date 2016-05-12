package com.kassisdion.animatedToolbar.demo.fragment;

import com.kassisdion.animatedToolbar.R;
import com.kassisdion.animatedToolbar.demo.activity.MainActivity;
import com.kassisdion.lib.toolbar.AnimatedToolbar;
import com.kassisdion.lib.toolbarAnimator.ToolbarAnimator;
import com.kassisdion.lib.toolbarAnimator.ToolbarAnimatorCallback;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends BaseFragment {
    public final static String FRAGMENT_NAME = MainActivityFragment.class.getSimpleName();

    @Bind(R.id.button_fade_in)
    Button buttonFadeIn;

    @Bind(R.id.button_fade_out)
    Button buttonFadeOut;

    public static void load(final FragmentManager fragmentManager, final Boolean toBackStack) {
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
    protected void init(View rootView, Bundle savedInstanceState) {

    }

    /*
    ** Butterknife callback
     */
    @OnClick(R.id.button_fade_in)
    public void fade_in() {
        //check if fragment was detach
        if (mContext == null) {
            return;
        }

        final AnimatedToolbar toolbar = ((MainActivity)mContext).getToolbar();
        toolbar.getAnimator()
                .setCallback(mToolbarAnimatorCallback)
                .startAnimation(2 * 1000, ToolbarAnimator.AnimationType.FADE_IN);
    }

    @OnClick(R.id.button_fade_out)
    public void fade_out(final Button button) {
        //check if fragment was detach
        if (mContext == null) {
            return;
        }

        final AnimatedToolbar toolbar = ((MainActivity) mContext).getToolbar();
        toolbar.getAnimator()
                .setCallback(mToolbarAnimatorCallback)
                .startAnimation(2 * 1000, ToolbarAnimator.AnimationType.FADE_OUT);
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
