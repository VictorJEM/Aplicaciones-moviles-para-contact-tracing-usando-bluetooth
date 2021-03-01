package com.espol.proyecto.asi_simulation.inform;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.espol.proyecto.asi_simulation.R;
import com.espol.proyecto.asi_simulation.storage.SecureStorage;
import com.espol.proyecto.asi_simulation.util.OtpGenerator;
import com.espol.proyecto.asi_simulation.util.InfoDialog;

import org.dpppt.android.sdk.DP3T;
import org.dpppt.android.sdk.backend.ResponseCallback;
import org.dpppt.android.sdk.backend.models.ExposeeAuthMethodJson;
import org.dpppt.android.sdk.internal.backend.StatusCodeException;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class InformFragment extends Fragment{

    private static final long TIMEOUT_VALID_CODE = 1000L * 60 * 5;

    private TextView newCodeText;
    private AlertDialog progressDialog;
    private Button buttonSend;
    private ImageView buttonReloadCode;

    private SecureStorage secureStorage;

    public static InformFragment newInstance() {
        return new InformFragment();
    }

    public InformFragment() {
        super(R.layout.fragment_inform);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        secureStorage = SecureStorage.getInstance(getContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((InformActivity) requireActivity()).allowBackButton(true);
        buttonSend = view.findViewById(R.id.trigger_fragment_button_trigger);
        buttonReloadCode = view.findViewById(R.id.trigger_fragment_button_code_reload_icon);
        newCodeText = view.findViewById(R.id.inform_code_text);
        buttonSend.setEnabled(true);

        buttonReloadCode.setOnClickListener(v-> showChangeCodeAlert());

        OtpGenerator gen = new OtpGenerator(new SecureRandom());
        String genPretty = gen.prettify(secureStorage.getLastInformCode(), " ");
        newCodeText.setText(genPretty);

        buttonSend.setOnClickListener(v -> {
            DialogInterface.OnClickListener listener = (dialog, which) -> {
                buttonSend.setEnabled(false);
                buttonReloadCode.setEnabled(false);
                String authCode = secureStorage.getLastInformCode();
                progressDialog = createProgressDialog();
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE, -14);
                informExposed(calendar.getTime(), authCode);
            };
            showAuthCodeAlert(listener);
        });

        view.findViewById(R.id.cancel_button).setOnClickListener(v -> {
            getActivity().finish();
        });

    }

    private void informExposed(Date onsetDate, String authCode) {

        HashMap<String, String> params = new HashMap<>();
        params.put("provincia", secureStorage.getProvinceName());
        params.put("canton", secureStorage.getCantonName());
        params.put("parroquia", secureStorage.getParishName());

        DP3T.sendIAmInfected(getContext(), onsetDate,
                new ExposeeAuthMethodJson(authCode), new ResponseCallback<Void>() {
                    @Override
                    public void onSuccess(Void response) {
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        secureStorage.clearInformTimeAndCodeAndToken();
                        getParentFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.slide_enter, R.anim.slide_exit, R.anim.slide_pop_enter,
                                        R.anim.slide_pop_exit)
                                .replace(R.id.inform_fragment_container, ThankYouFragment.newInstance())
                                .commit();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        if(throwable instanceof StatusCodeException){
                            String message = "Su código no tiene la validación de una institución o médico autorizado, por favor autentíquelo para notificarse anónimamente";
                            String code = "El código NO es válido";
                            showErrorAuthDialog(code,message);
                        }else{
                            showErrorDialog(getString(R.string.network_error), null);
                            throwable.printStackTrace();
                        }
                        buttonSend.setEnabled(true);
                        buttonReloadCode.setEnabled(true);
                    }
                },params);
    }

    private AlertDialog createProgressDialog() {
        return new AlertDialog.Builder(getContext())
                .setView(R.layout.dialog_loading)
                .show();
    }

    private void showErrorDialog(String error, @Nullable String errorCode) {
        InfoDialog.newInstanceWithDetail(error, errorCode).show(getChildFragmentManager(), InfoDialog.class.getCanonicalName());
    }

    private void showErrorAuthDialog(@Nullable String message, @Nullable String addErrorCode) {
        AlertDialog.Builder errorDialogBuilder = new AlertDialog.Builder(getContext(), R.style.NextStep_AlertDialogStyle)
                .setMessage(message)
                .setPositiveButton(R.string.android_button_ok, (dialog, which) -> {});
        String errorCode = addErrorCode;
        TextView errorCodeView =
                (TextView) getLayoutInflater().inflate(R.layout.view_dialog_error_code, (ViewGroup) getView(), false);
        errorCodeView.setText(errorCode);
        errorDialogBuilder.setView(errorCodeView);
        errorDialogBuilder.show();
    }

    private void showChangeCodeAlert(){
        AlertDialog dialog = new AlertDialog.Builder(getContext(), R.style.NextStep_AlertDialogStyle)
                .setTitle("¡Atención!")
                .setMessage("Si vuelves a generar el código, tendrás que autorizarlo nuevamente.")
                .setPositiveButton("Generar", ((dialog1, which) -> {
                    OtpGenerator gen = new OtpGenerator(new SecureRandom());
                    String newCode = gen.nextOtpCode();
                    secureStorage.setLastInformCode(newCode);
                    newCodeText.setText(gen.prettify(newCode, " "));
                }))
                .setNegativeButton("Cancelar", (dialog1, which) -> dialog1.dismiss())
                .create();
        dialog.setOnShowListener(dialog1 -> {
            TextView titleView = dialog.findViewById(R.id.alertTitle);
            titleView.setMaxLines(5);
        });
        dialog.show();
    }

    private void showAuthCodeAlert(DialogInterface.OnClickListener positiveListener){
        AlertDialog dialog = new AlertDialog.Builder(getContext(), R.style.NextStep_AlertDialogStyle)
                .setTitle("¡Atención!")
                .setMessage("¿Ya fue autorizado tu código por un operador del Ministerio de Salud Pública?")
                .setPositiveButton("Si", positiveListener)
                .setNegativeButton("No", (dialog1, which) -> dialog1.dismiss())
                .create();
        dialog.setOnShowListener(dialog1 -> {
            TextView titleView = dialog.findViewById(R.id.alertTitle);
            titleView.setMaxLines(5);
        });
        dialog.show();
    }

}