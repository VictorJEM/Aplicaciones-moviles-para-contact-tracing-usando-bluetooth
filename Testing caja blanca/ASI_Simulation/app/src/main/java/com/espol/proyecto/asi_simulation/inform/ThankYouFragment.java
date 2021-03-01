package com.espol.proyecto.asi_simulation.inform;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.espol.proyecto.asi_simulation.R;

public class ThankYouFragment extends Fragment {

    public static ThankYouFragment newInstance() {
        return new ThankYouFragment();
    }

    public ThankYouFragment() {
        super(R.layout.fragment_thank_you);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((InformActivity) requireActivity()).allowBackButton(false);

        view.findViewById(R.id.inform_thank_you_button_continue).setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_enter, R.anim.slide_exit, R.anim.slide_pop_enter, R.anim.slide_pop_exit)
                    .replace(R.id.inform_fragment_container, TracingStoppedFragment.newInstance())
                    .commit();
        });
    }

}