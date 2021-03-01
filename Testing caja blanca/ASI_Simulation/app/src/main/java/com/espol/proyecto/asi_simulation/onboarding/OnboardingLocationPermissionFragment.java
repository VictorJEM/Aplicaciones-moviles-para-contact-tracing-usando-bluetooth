package com.espol.proyecto.asi_simulation.onboarding;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.espol.proyecto.asi_simulation.R;
import com.espol.proyecto.asi_simulation.onboarding.util.PermissionButtonUtil;
import com.espol.proyecto.asi_simulation.util.DeviceFeatureHelper;

public class OnboardingLocationPermissionFragment extends Fragment {

    //public static final int REQUEST_CODE_ASK_PERMISSION_FINE_LOCATION = 123;
    public static final int REQUEST_CODE_ASK_PERMISSION_FINE_LOCATION = 1;

    private Button locationButton;
    private Button continueButton;

    private static final int REQUEST_ENABLE_BLUETOOTH = 1;

    public static OnboardingLocationPermissionFragment newInstance() {
        return new OnboardingLocationPermissionFragment();
    }

    public OnboardingLocationPermissionFragment() {
        super(R.layout.fragment_onboarding_permission_gaen);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        locationButton = view.findViewById(R.id.onboarding_gaen_button);
        locationButton.setOnClickListener(v -> {
            String[] permissions = new String[] { Manifest.permission.ACCESS_FINE_LOCATION };
            requestPermissions(permissions, REQUEST_CODE_ASK_PERMISSION_FINE_LOCATION);
            //new
            BluetoothAdapter bt = BluetoothAdapter.getDefaultAdapter();
            if(!bt.isEnabled()){
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent,REQUEST_ENABLE_BLUETOOTH);
            }
        });
        continueButton = view.findViewById(R.id.onboarding_gaen_continue_button);
        continueButton.setOnClickListener(v -> {
            ((OnboardingActivity) getActivity()).continueToNextPage();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        updateFragmentState();
    }

    private void updateFragmentState() {
        boolean locationPermissionGranted = DeviceFeatureHelper.isLocationPermissionGranted(requireContext());
        if (locationPermissionGranted) {
            PermissionButtonUtil.setButtonOk(locationButton, R.string.onboarding_gaen_button_activated);
        } else {
            PermissionButtonUtil.setButtonDefault(locationButton, R.string.android_onboarding_battery_permission_button);
        }
        continueButton.setVisibility(locationPermissionGranted ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_ASK_PERMISSION_FINE_LOCATION) {
            if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                if (!ActivityCompat
                        .shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                    new AlertDialog.Builder(requireActivity())
                            .setTitle("Permitir la localización")
                            .setMessage("Para que la aplicación utilice Bluetooth, debe habilitar el uso de su GPS. La aplicación en sí no reconoce su ubicación en ningún momento.")
                            .setPositiveButton(getString(R.string.android_button_ok),
                                    (dialogInterface, i) -> {
                                        DeviceFeatureHelper.openApplicationSettings(requireActivity());
                                        dialogInterface.dismiss();
                                    })
                            .create()
                            .show();
                }
            }
            ((OnboardingActivity) getActivity()).continueToNextPage();
        }
    }

}
