package com.rides.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.rides.R;
import com.rides.enums.RequestCode;
import com.rides.fragments.VehicleListFragment;
import com.rides.helper.GenericView;
import com.rides.interfaces.ClickEvent;
import com.rides.interfaces.OnBackPressedEvent;
import com.rides.interfaces.RequestListener;
import com.rides.util.Debug;

public class MainActivity extends AppCompatActivity implements RequestListener, ClickEvent {

    private FrameLayout frameLayout;
    private FragmentManager fragmentManager;

    private ClickEvent onClickEvent;
    private OnBackPressedEvent onBackPressedEvent;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        loadFragment();
    }

    public void init() {
        frameLayout = GenericView.findViewById(this, R.id.fragment_holder);
        fragmentManager = getSupportFragmentManager();

    }

    @Override
    public void onRequestComplete(RequestCode requestCode, Object object) {

    }

    @Override
    public void onRequestError(String error, String status, RequestCode requestCode) {

    }

    public void loadFragment() {
        pushFragments(new VehicleListFragment(), true, true);
    }

    public void pushFragments(Fragment fragment, boolean shouldAnimate, boolean shouldAddBackStack) {
        try {

            onClickEvent = (ClickEvent) fragment;
            onBackPressedEvent = (OnBackPressedEvent) fragment;

            FragmentTransaction ft = fragmentManager.beginTransaction();
            currentFragment = fragment;

            if (shouldAnimate) {
                ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
//					ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_right);
            }

            Debug.trace("pushFragment", "Fragment class Name: " + fragment.getClass().getName());
            ft.replace(R.id.fragment_holder, fragment, fragment.getClass().getName());
            if (shouldAddBackStack) {
                ft.addToBackStack(fragment.getClass().getName());
            }
            ft.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void popFragment() {
        try {
            int backStackCount = fragmentManager.getBackStackEntryCount();
            if (backStackCount > 1) {
                FragmentManager.BackStackEntry backEntry = fragmentManager.getBackStackEntryAt(backStackCount - 2);
                String str = backEntry.getName();
                Fragment fragment = fragmentManager.findFragmentByTag(str);

                currentFragment = fragment;

                onClickEvent = (ClickEvent) fragment;
                onBackPressedEvent = (OnBackPressedEvent) fragment;

                fragmentManager.popBackStack();
            } else
                finish();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        popFragment();
    }

    @Override
    public void onClickEvent(View view) {
        onClickEvent.onClickEvent(view);
    }
}