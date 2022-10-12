package com.rides;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.rides.activity.MainActivity;
import com.rides.enums.RequestCode;
import com.rides.helper.CustomDialog;
import com.rides.interfaces.ClickEvent;
import com.rides.interfaces.OnBackPressedEvent;
import com.rides.interfaces.RequestListener;

import java.lang.reflect.Field;

public class BaseFragment extends Fragment implements RequestListener, ClickEvent, OnBackPressedEvent {

    public MainActivity mActivityHome;
    public CustomDialog customDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        mActivityHome=(MainActivity) getActivity();

        customDialog=CustomDialog.getInstance();

    }
    @Override
    public void onBackPressed() {

    }

    @Override
    public void onRequestComplete(RequestCode requestCode, Object object) {

    }

    @Override
    public void onRequestError(String error, String status, RequestCode requestCode) {

    }

    @Override
    public void onClickEvent(View view) {

    }
    // Arbitrary value; set it to some reasonable default
    private static final int DEFAULT_CHILD_ANIMATION_DURATION = 250;

    @Nullable
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        final Fragment parent = getParentFragment();

        // Apply the workaround only if this is a child fragment, and the parent
        // is being removed.
        if (!enter && parent != null && parent.isRemoving()) {
            // This is a workaround for the bug where child fragments disappear when
            // the parent is removed (as all children are first removed from the parent)
            // See https://code.google.com/p/android/issues/detail?id=55228
            Animation doNothingAnim = new AlphaAnimation(1, 1);
            doNothingAnim.setDuration(getNextAnimationDuration(parent, DEFAULT_CHILD_ANIMATION_DURATION));
            return doNothingAnim;
        } else {
            return super.onCreateAnimation(transit, enter, nextAnim);
        }
    }

    private static long getNextAnimationDuration(Fragment fragment, long defValue) {
        try {
            // Attempt to get the resource ID of the next animation that
            // will be applied to the given fragment.
            Field nextAnimField = Fragment.class.getDeclaredField("mNextAnim");
            nextAnimField.setAccessible(true);
            int nextAnimResource = nextAnimField.getInt(fragment);
            if(nextAnimResource>0)
            {
                Animation nextAnim = AnimationUtils.loadAnimation(fragment.getActivity(), nextAnimResource);
                // ...and if it can be loaded, return that animation's duration
                return (nextAnim == null) ? defValue : nextAnim.getDuration();
            }
            else
                return defValue;
        } catch (NoSuchFieldException|IllegalAccessException| Resources.NotFoundException ex) {
            //Log.w("getNextAnimationDuration", "Unable to load next animation from parent.", ex);
            ex.printStackTrace();
            return defValue;
        }
    }
}
