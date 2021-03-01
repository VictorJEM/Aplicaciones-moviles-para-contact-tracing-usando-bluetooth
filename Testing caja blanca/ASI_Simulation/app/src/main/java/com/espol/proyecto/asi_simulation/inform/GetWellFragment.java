package com.espol.proyecto.asi_simulation.inform;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.espol.proyecto.asi_simulation.MainActivity;
import com.espol.proyecto.asi_simulation.R;

public class GetWellFragment extends Fragment {

    public static GetWellFragment newInstance() {
        return new GetWellFragment();
    }

    public GetWellFragment() {
        super(R.layout.fragment_get_well);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((InformActivity) requireActivity()).allowBackButton(false);

        view.findViewById(R.id.inform_get_well_button_continue).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.setAction(MainActivity.ACTION_INFORMED_GOTO_REPORTS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
            getActivity().finish();
        });
    }

}
