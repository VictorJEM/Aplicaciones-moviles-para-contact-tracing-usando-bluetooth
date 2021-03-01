package com.espol.proyecto.asi_simulation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import android.util.Log;

import com.espol.proyecto.asi_simulation.main.HomeFragment;
import com.espol.proyecto.asi_simulation.onboarding.OnboardingActivity;
import com.espol.proyecto.asi_simulation.reports.ReportsFragment;
import com.espol.proyecto.asi_simulation.storage.SecureStorage;
import com.espol.proyecto.asi_simulation.viewmodel.TracingViewModel;


public class MainActivity extends BaseActivity {

    public static final String ACTION_EXPOSED_GOTO_REPORTS = "ACTION_EXPOSED_GOTO_REPORTS";
    public static final String ACTION_INFORMED_GOTO_REPORTS = "ACTION_INFORMED_GOTO_REPORTS";
    public static final String ACTION_TIME_NO_EXPOSURE_CLICK = "ACTION_TIME_NO_EXPOSURE_CLICK";
    public static boolean showTNENotification = true;

    private static final int REQ_ONBOARDING = 123;

    private static final String STATE_CONSUMED_EXPOSED_INTENT = "STATE_CONSUMED_EXPOSED_INTENT";
    private boolean consumedExposedIntent;

    private SecureStorage secureStorage;
    private TracingViewModel tracingViewModel;

    //DEMO
    public static final String ACTION_GOTO_REPORTS = "ACTION_GOTO_REPORTS";
    public static final String ACTION_STOP_TRACING = "ACTION_STOP_TRACING";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        secureStorage = SecureStorage.getInstance(this);

        //Guarda el estado de la aplicación para mostrar la interfaz principal.
        if (savedInstanceState == null) {
            boolean onboardingCompleted = secureStorage.getOnboardingCompleted();
            if (onboardingCompleted) {
                showHomeFragment();
            } else {
                startActivityForResult(new Intent(this, OnboardingActivity.class), REQ_ONBOARDING);
            }
        } else {
            consumedExposedIntent = savedInstanceState.getBoolean(STATE_CONSUMED_EXPOSED_INTENT);
        }

        tracingViewModel = new ViewModelProvider(this).get(TracingViewModel.class);
        tracingViewModel.sync();

    }

    @Override
    public void onResume() {
        super.onResume();

        checkIntentForActions();

        if (!consumedExposedIntent) {
            boolean isHotlineCallPending = secureStorage.isHotlineCallPending();
            if (isHotlineCallPending) {
                gotoReportsFragment();
            }
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        tracingViewModel.sync();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_CONSUMED_EXPOSED_INTENT, consumedExposedIntent);
    }

    private void checkIntentForActions() {
        Intent intent = getIntent();
        String intentAction = intent.getAction();
        boolean launchedFromHistory = (intent.getFlags() & Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY) != 0;

        if (ACTION_INFORMED_GOTO_REPORTS.equals(intentAction) && !launchedFromHistory) {
            secureStorage.setHotlineCallPending(false);
            secureStorage.setReportsHeaderAnimationPending(false);
            gotoReportsFragment();
            intent.setAction(null);
            setIntent(intent);
        } else if (ACTION_EXPOSED_GOTO_REPORTS.equals(intentAction) && !launchedFromHistory && !consumedExposedIntent) {
            consumedExposedIntent = true;
            intent.setAction(null);
            setIntent(intent);
            if (tracingViewModel.getTracingStatusInterface().wasContactReportedAsExposed()) {
                gotoReportsFragment();
            }
        } else if (ACTION_TIME_NO_EXPOSURE_CLICK.equals(intentAction)){
            MainActivity.showTNENotification = false;
        }

    }

    private void showHomeFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_fragment_container, HomeFragment.newInstance())
                .commit();
    }

    private void gotoReportsFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_container, ReportsFragment.newInstance())
                .addToBackStack(ReportsFragment.class.getCanonicalName())
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_ONBOARDING) {
            if (resultCode == RESULT_OK) {
                secureStorage.setOnboardingCompleted(true);
                showHomeFragment();
            } else {
                finish();
            }
        }
    }

}