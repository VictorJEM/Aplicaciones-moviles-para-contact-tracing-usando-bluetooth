package com.espol.proyecto.asi_simulation.onboarding;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.espol.proyecto.asi_simulation.R;

import org.dpppt.android.sdk.DP3T;


public class OnboardingActivity extends FragmentActivity {

    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        viewPager = findViewById(R.id.pager);
        viewPager.setUserInputEnabled(false);
        pagerAdapter = new OnboardingSlidePageAdapter(this);
        viewPager.setAdapter(pagerAdapter);

    }

    public void continueToNextPage() {
        int currentItem = viewPager.getCurrentItem();
        if (currentItem < pagerAdapter.getItemCount() - 1) {
            viewPager.setCurrentItem(currentItem + 1, true);
        } else {
            //new
            DP3T.start(this);
            setResult(RESULT_OK);
            finish();
        }
    }

}