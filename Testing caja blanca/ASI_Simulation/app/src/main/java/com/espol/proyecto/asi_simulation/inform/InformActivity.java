package com.espol.proyecto.asi_simulation.inform;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.espol.proyecto.asi_simulation.R;

public class InformActivity extends FragmentActivity {

    private boolean allowed = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inform);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.inform_fragment_container, InformIntroFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (allowed) {
            super.onBackPressed();
        }
    }

    public void allowBackButton(boolean allowed) {
        this.allowed = allowed;
    }

}
