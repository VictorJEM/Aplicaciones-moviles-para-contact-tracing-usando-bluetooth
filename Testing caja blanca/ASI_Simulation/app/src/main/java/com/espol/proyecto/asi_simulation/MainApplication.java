package com.espol.proyecto.asi_simulation;

import android.app.Application;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import org.dpppt.android.sdk.DP3T;
import org.dpppt.android.sdk.InfectionStatus;
import org.dpppt.android.sdk.TracingStatus;
import org.dpppt.android.sdk.backend.models.ApplicationInfo;
import org.dpppt.android.sdk.internal.database.models.ExposureDay;
import org.dpppt.android.sdk.internal.util.ProcessUtil;
import org.dpppt.android.sdk.util.SignatureUtil;

import androidx.core.app.NotificationCompat;
import okhttp3.CertificatePinner;

import java.security.PublicKey;


import com.espol.proyecto.asi_simulation.storage.SecureStorage;
import com.espol.proyecto.asi_simulation.util.NotificationUtil;


public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (ProcessUtil.isMainProcess(this)) {
            registerReceiver(contactUpdateReceiver, DP3T.getUpdateIntentFilter());

            PublicKey publicKey = SignatureUtil.getPublicKeyFromBase64OrThrow("LS0tLS1CRUdJTiBQVUJMSUMgS0VZLS0tLS0NCk1Ga3dFd1lIS29aSXpqMENBUVlJS29aSXpqMERBUWNEUWdBRTUxb0ZVUTVCTXlXdGRuUEkwUHVZc3QyNXE2dXQNCklRMTU3Yy9uYXN1TkozbEN2T0lFU0lDZFhwT1FUUUdWNisxWDh1WStOWmZ5WlFTR090R3hxTFdOcmc9PQ0KLS0tLS1FTkQgUFVCTElDIEtFWS0tLS0tDQo=");
            DP3T.init(this, new ApplicationInfo("org.dpppt.demo", "http://192.168.1.5:8085", "http://192.168.1.5:8085"), publicKey);
            /*CertificatePinner certificatePinner = new CertificatePinner.Builder()
                    .add("demo.dpppt.org", "sha256/YLh1dUR9y6Kja30RrAn7JKnbQG/uEtLMkBgFF2Fuihg=")
                    .build();
            DP3T.setCertificatePinner(certificatePinner);*/
        }
    }

    private BroadcastReceiver contactUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            SecureStorage secureStorage = SecureStorage.getInstance(context);
            TracingStatus status = DP3T.getStatus(context);
            if (status.getInfectionStatus() == InfectionStatus.EXPOSED) {
                ExposureDay exposureDay = null;
                long dateNewest = 0;
                for (ExposureDay day : status.getExposureDays()) {
                    if (day.getExposedDate().getStartOfDayTimestamp() > dateNewest) {
                        exposureDay = day;
                        dateNewest = day.getExposedDate().getStartOfDayTimestamp();
                    }
                }
                if (exposureDay != null && secureStorage.getLastShownContactId() != exposureDay.getId()) {
                    createNewContactNotifaction(context, exposureDay.getId());
                }
            }
        }
    };

    private void createNewContactNotifaction(Context context, int contactId) {
        SecureStorage secureStorage = SecureStorage.getInstance(context);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationUtil.createNotificationChannel(context);
        }

        Intent resultIntent = new Intent(context, MainActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        resultIntent.setAction(MainActivity.ACTION_EXPOSED_GOTO_REPORTS);

        PendingIntent pendingIntent =
                PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification =
                new NotificationCompat.Builder(context, NotificationUtil.NOTIFICATION_CHANNEL_ID)
                        .setContentTitle(context.getString(R.string.push_exposed_title))
                        .setContentText(context.getString(R.string.push_exposed_text))
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setSmallIcon(R.drawable.ic_begegnungen)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .build();

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NotificationUtil.NOTIFICATION_ID_CONTACT, notification);

        secureStorage.setHotlineCallPending(true);
        secureStorage.setReportsHeaderAnimationPending(true);
        secureStorage.setLastShownContactId(contactId);
    }

}