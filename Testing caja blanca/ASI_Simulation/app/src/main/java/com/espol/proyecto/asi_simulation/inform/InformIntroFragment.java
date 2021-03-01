package com.espol.proyecto.asi_simulation.inform;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.espol.proyecto.asi_simulation.R;


public class InformIntroFragment extends Fragment {

    public static InformIntroFragment newInstance() {
        return new InformIntroFragment();
    }

    public InformIntroFragment() {
        super(R.layout.fragment_inform_intro);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button cancelButton = view.findViewById(R.id.inform_intro_cancel_button);
        cancelButton.setOnClickListener(v -> {
            getActivity().finish();
        });
        ((InformActivity) requireActivity()).allowBackButton(true);

        //Botón Entendido
        Button continueButton = view.findViewById(R.id.inform_intro_button_continue);
        continueButton.setOnClickListener(v -> {
            getParentFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_enter, R.anim.slide_exit, R.anim.slide_pop_enter, R.anim.slide_pop_exit)
                    .replace(R.id.inform_fragment_container, InformFragment.newInstance())
                    .addToBackStack(InformFragment.class.getCanonicalName())
                    .commit();
        });
    }

}

