package com.espol.proyecto.asi_simulation.main.model;

import android.content.Context;

import java.util.List;

import org.dpppt.android.sdk.TracingStatus;
import org.dpppt.android.sdk.internal.database.models.ExposureDay;

public interface TracingStatusInterface {

    void setStatus(TracingStatus status);

    boolean isReportedAsInfected();

    List<ExposureDay> getExposureDays();

    boolean wasContactReportedAsExposed();

    TracingState getTracingState();

    NotificationState getNotificationState();

    TracingStatus.ErrorState getTracingErrorState();

    TracingStatus.ErrorState getReportErrorState();

    long getDaysSinceExposure();

    //ASI
    /*void resetInfectionStatus(Context context);
    void resetExposureDays(Context context);
    boolean canInfectedStatusBeReset(Context context);*/

}