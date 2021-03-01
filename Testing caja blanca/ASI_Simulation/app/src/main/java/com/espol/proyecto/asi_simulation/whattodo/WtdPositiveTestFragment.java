package com.espol.proyecto.asi_simulation.whattodo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.espol.proyecto.asi_simulation.R;
import com.espol.proyecto.asi_simulation.inform.InformActivity;

public class WtdPositiveTestFragment extends Fragment {

    public static WtdPositiveTestFragment newInstance() {
        return new WtdPositiveTestFragment();
    }

    public WtdPositiveTestFragment() {
        super(R.layout.fragment_what_to_do_test);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Toolbar toolbar = view.findViewById(R.id.wtd_test_toolbar);
        toolbar.setNavigationOnClickListener(v -> getParentFragmentManager().popBackStack());

        //Botón Reportar Contactos
        view.findViewById(R.id.wtd_inform_button).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), InformActivity.class);
            startActivity(intent);
        });

        //Botón Más Información
        view.findViewById(R.id.wtd_inform_faq_button).setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.faq_button_url)));
            startActivity(browserIntent);
        });

    }

}