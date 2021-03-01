package com.espol.proyecto.asi_simulation.onboarding;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.espol.proyecto.asi_simulation.R;
import com.espol.proyecto.asi_simulation.triage.TriageActivity;
import com.espol.proyecto.asi_simulation.triage.TriageLocationActivity;

public class OnboardingFinishedFragment extends Fragment {

    public static OnboardingFinishedFragment newInstance() {
        return new OnboardingFinishedFragment();
    }

    public OnboardingFinishedFragment() {
        super(R.layout.fragment_onboarding_finished);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.onboarding_continue_button)
                .setOnClickListener(v -> {
                    //Inicia el ingreso de provincia, cantón y parroquia
                    startActivityForResult(new Intent(getActivity(), TriageLocationActivity.class), TriageActivity.LOCATION_REQUEST_CODE);
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == TriageActivity.LOCATION_REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK) ((OnboardingActivity) getActivity()).continueToNextPage();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
