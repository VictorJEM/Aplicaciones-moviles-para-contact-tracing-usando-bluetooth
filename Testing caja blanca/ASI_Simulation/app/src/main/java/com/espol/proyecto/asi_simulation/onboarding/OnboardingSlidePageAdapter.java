package com.espol.proyecto.asi_simulation.onboarding;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.espol.proyecto.asi_simulation.R;

public class OnboardingSlidePageAdapter extends FragmentStateAdapter {

    public OnboardingSlidePageAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return OnboardingContentFragment.newInstance(
                        R.string.onboarding_prinzip_title,
                        R.string.onboarding_prinzip_heading,
                        R.drawable.ill_prinzip,
                        R.string.onboarding_prinzip_text1,
                        R.drawable.ic_begegnungen,
                        R.string.onboarding_prinzip_text2,
                        R.drawable.ic_message_alert,
                        R.string.onboarding_prinzip_text3,
                        R.drawable.ic_message_alert,
                        false);
            case 1:
                return OnboardingContentFragment.newInstance(
                        R.string.onboarding_privacy_title,
                        R.string.onboarding_privacy_heading,
                        R.drawable.ill_privacy,
                        R.string.onboarding_privacy_text1,
                        R.drawable.ic_key,
                        R.string.onboarding_privacy_text2,
                        R.drawable.ic_lock, -1, -1,
                        true);
            case 2:
                return OnboardingContentFragment.newInstance(
                        R.string.onboarding_begegnungen_title,
                        R.string.onboarding_begegnungen_heading,
                        R.drawable.ill_bluetooth,
                        R.string.onboarding_begegnungen_text1,
                        R.drawable.ic_begegnungen,
                        R.string.onboarding_begegnungen_text2,
                        R.drawable.ic_bluetooth,
                        R.string.onboarding_begegnungen_text3,
                        R.drawable.ic_person,
                        false);
            case 3:
                return OnboardingContentFragment.newInstance(
                        R.string.onboarding_meldung_title,
                        R.string.onboarding_meldung_heading,
                        R.drawable.ill_meldung,
                        R.string.onboarding_meldung_text1,
                        R.drawable.ic_message_alert,
                        R.string.onboarding_meldung_text2,
                        R.drawable.ic_home,-1, -1,
                        false);
            case 4:
                return OnboardingBatteryPermissionFragment.newInstance();
            case 5:
                return OnboardingLocationPermissionFragment.newInstance();
            case 6:
                return OnboardingFinishedFragment.newInstance();
        }
        throw new IllegalArgumentException("There is no fragment for view pager position " + position);
    }

    @Override
    public int getItemCount() {
        return 7;
    }

}
