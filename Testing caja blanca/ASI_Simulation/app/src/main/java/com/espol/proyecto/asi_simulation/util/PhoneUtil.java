package com.espol.proyecto.asi_simulation.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.espol.proyecto.asi_simulation.R;

public class PhoneUtil {

    public static void callHelpline(Context context) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + context.getString(R.string.infoline_tel_number)));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

}
