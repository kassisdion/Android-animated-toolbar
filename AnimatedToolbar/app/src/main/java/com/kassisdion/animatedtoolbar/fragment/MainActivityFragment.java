package com.kassisdion.animatedtoolbar.fragment;

import com.kassisdion.animatedtoolbar.R;
import com.kassisdion.animatedtoolbar.activity.MainActivity;
import com.kassisdion.animatedtoolbar.lib.toolbar.AnimatedToolbar;
import com.kassisdion.animatedtoolbar.lib.toolbarAnimator.ToolbarAnimator;
import com.kassisdion.animatedtoolbar.lib.toolbarAnimator.ToolbarAnimatorCallback;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Button;

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
    protected void initUI(Bundle savedInstanceState) {

    }

    @Override
    protected void initLogic(Bundle savedInstanceState) {

    }

    /*
    ** Butterknife callback
     */
    @OnClick(R.id.button_fade_in)
    public void fade_in() {
        setButtonEnable(false);

        final AnimatedToolbar toolbar = ((MainActivity) mContext).getToolbar();
        toolbar.getAnimator()
                .setCallback(mToolbarAnimatorCallback)
                .startAnimation(2 * 1000, ToolbarAnimator.AnimationType.FADE_IN);
    }

    @OnClick(R.id.button_fade_out)
    public void fade_out(final Button button) {
        setButtonEnable(false);


        final AnimatedToolbar toolbar = ((MainActivity) mContext).getToolbar();
        toolbar.getAnimator()
                .setCallback(mToolbarAnimatorCallback)
                .startAnimation(2 * 1000, ToolbarAnimator.AnimationType.FADE_IN);
    }

    /*
    ** Utils
     */
    final ToolbarAnimatorCallback mToolbarAnimatorCallback = new ToolbarAnimatorCallback() {
        @Override
        public void hasEnded() {
            setButtonEnable(true);
        }
    };

    private void setButtonEnable(final Boolean enable) {
        buttonFadeIn.setEnabled(enable);
        buttonFadeOut.setEnabled(enable);
    }
}
