package com.espol.proyecto.asi_simulation.onboarding;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import com.espol.proyecto.asi_simulation.R;

public class OnboardingContentFragment extends Fragment {

    private static final String ARG_RES_TITLE = "RES_TITLE";
    private static final String ARG_RES_SUBTITLE = "RES_SUBTITLE";
    private static final String ARG_RES_DESCRIPTION_1 = "RES_DESCRIPTION_1";
    private static final String ARG_RES_DESCRIPTION_2 = "RES_DESCRIPTION_2";
    private static final String ARG_RES_DESCRIPTION_3 = "RES_DESCRIPTION_3";
    private static final String ARG_RES_DESCR_ICON_1 = "ARG_RES_DESCR_ICON_1";
    private static final String ARG_RES_DESCR_ICON_2 = "ARG_RES_DESCR_ICON_2";
    private static final String ARG_RES_DESCR_ICON_3 = "ARG_RES_DESCR_ICON_3";
    private static final String ARG_RES_ILLUSTRATION = "RES_ILLUSTRATION";
    private static final String ARG_STYLE_GREEN = "ARG_STYLE_GREEN";

    public static OnboardingContentFragment newInstance(@StringRes int title, @StringRes int subtitle,
                                                        @DrawableRes int illustration, @StringRes int description1, @DrawableRes int iconDescription1,
                                                        @StringRes int description2, @DrawableRes int iconDescription2,
                                                        @StringRes Integer description3, @DrawableRes Integer iconDescription3, boolean greenStyle) {
        Bundle args = new Bundle();
        args.putInt(ARG_RES_TITLE, title);
        args.putInt(ARG_RES_SUBTITLE, subtitle);
        args.putInt(ARG_RES_ILLUSTRATION, illustration);
        args.putInt(ARG_RES_DESCR_ICON_1, iconDescription1);
        args.putInt(ARG_RES_DESCRIPTION_1, description1);
        args.putInt(ARG_RES_DESCR_ICON_2, iconDescription2);
        args.putInt(ARG_RES_DESCRIPTION_2, description2);
        args.putInt(ARG_RES_DESCR_ICON_3, iconDescription3);
        args.putInt(ARG_RES_DESCRIPTION_3, description3);

        args.putBoolean(ARG_STYLE_GREEN, greenStyle);
        OnboardingContentFragment fragment = new OnboardingContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public OnboardingContentFragment() {
        super(R.layout.fragment_onboarding_content);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = requireArguments();

        ((TextView) view.findViewById(R.id.onboarding_title)).setText(args.getInt(ARG_RES_TITLE));

        TextView subtitle = view.findViewById(R.id.onboarding_subtitle);
        subtitle.setText(args.getInt(ARG_RES_SUBTITLE));
        if (args.getBoolean(ARG_STYLE_GREEN))
            subtitle.setTextColor(getResources().getColor(R.color.green_main, null));

        ((ImageView) view.findViewById(R.id.onboarding_illustration)).setImageResource(args.getInt(ARG_RES_ILLUSTRATION));

        ImageView icon1 = view.findViewById(R.id.onboarding_description_1_icon);
        icon1.setImageResource(args.getInt(ARG_RES_DESCR_ICON_1));
        if (args.getBoolean(ARG_STYLE_GREEN))
            icon1.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.green_main, null)));
        ((TextView) view.findViewById(R.id.onboarding_description_1)).setText(args.getInt(ARG_RES_DESCRIPTION_1));

        ImageView icon2 = view.findViewById(R.id.onboarding_description_2_icon);
        icon2.setImageResource(args.getInt(ARG_RES_DESCR_ICON_2));
        if (args.getBoolean(ARG_STYLE_GREEN))
            icon2.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.green_main, null)));
        ((TextView) view.findViewById(R.id.onboarding_description_2)).setText(args.getInt(ARG_RES_DESCRIPTION_2));

        if (args.getInt(ARG_RES_DESCRIPTION_3) > 0){
            ((LinearLayout) view.findViewById(R.id.onboarding_description_3_container)).setVisibility(View.VISIBLE);
            ImageView icon3 = view.findViewById(R.id.onboarding_description_3_icon);
            icon3.setImageResource(args.getInt(ARG_RES_DESCR_ICON_3));
            if (args.getBoolean(ARG_STYLE_GREEN))
                icon3.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.green_main, null)));
            ((TextView) view.findViewById(R.id.onboarding_description_3)).setText(args.getInt(ARG_RES_DESCRIPTION_3));
        }

        Button continueButton = view.findViewById(R.id.onboarding_continue_button);
        continueButton.setOnClickListener(v -> {
            ((OnboardingActivity) getActivity()).continueToNextPage();
        });
    }

}

