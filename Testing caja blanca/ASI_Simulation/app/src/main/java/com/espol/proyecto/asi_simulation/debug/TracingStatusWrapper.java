/*
 * Copyright (c) 2020 Ubique Innovation AG <https://www.ubique.ch>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */
package com.espol.proyecto.asi_simulation.debug;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import com.espol.proyecto.asi_simulation.debug.debug.model.DebugAppState;
import com.espol.proyecto.asi_simulation.main.model.NotificationState;
import com.espol.proyecto.asi_simulation.main.model.TracingState;
import com.espol.proyecto.asi_simulation.main.model.TracingStatusInterface;
import com.espol.proyecto.asi_simulation.storage.SecureStorage;
import com.espol.proyecto.asi_simulation.util.DateUtils;
import com.espol.proyecto.asi_simulation.util.TracingErrorStateHelper;

import org.dpppt.android.sdk.DP3T;
import org.dpppt.android.sdk.InfectionStatus;
import org.dpppt.android.sdk.TracingStatus;
import org.dpppt.android.sdk.internal.database.models.ExposureDay;
import org.dpppt.android.sdk.internal.util.DayDate;


public class TracingStatusWrapper implements TracingStatusInterface {

	private DebugAppState debugAppState = DebugAppState.NONE;
	private TracingStatus status;

	@Override
	public void setStatus(TracingStatus status) {
		this.status = status;
	}

	@Override
	public boolean isReportedAsInfected() {
		return status.getInfectionStatus() == InfectionStatus.INFECTED;
		//return status.getInfectionStatus() == InfectionStatus.INFECTED || debugAppState == DebugAppState.REPORTED_EXPOSED;
	}

	@Override
	public List<ExposureDay> getExposureDays() {
		if (debugAppState == DebugAppState.CONTACT_EXPOSED) {
			List<ExposureDay> matches = new ArrayList<>();
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_YEAR, -3);
			matches.add(new ExposureDay(0, new DayDate(calendar.getTimeInMillis()), System.currentTimeMillis()));
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			matches.add(new ExposureDay(1, new DayDate(calendar.getTimeInMillis()), System.currentTimeMillis()));
			return matches;
		}
		return status.getExposureDays();
	}

	@Override
	public boolean wasContactReportedAsExposed() {
		return status.getInfectionStatus() == InfectionStatus.EXPOSED;
		//return status.getInfectionStatus() == InfectionStatus.EXPOSED || debugAppState == DebugAppState.CONTACT_EXPOSED;
	}

	public void setDebugAppState(Context context, DebugAppState debugAppState) {
		this.debugAppState = debugAppState;
		if (debugAppState == DebugAppState.CONTACT_EXPOSED) {
			SecureStorage secureStorage = SecureStorage.getInstance(context);
			secureStorage.setReportsHeaderAnimationPending(true);
		}
	}

	public DebugAppState getDebugAppState() {
		return debugAppState;
	}

	@Override
	public TracingState getTracingState() {
		boolean tracingOff = !(status.isAdvertising() || status.isReceiving());
		return tracingOff ? TracingState.NOT_ACTIVE : TracingState.ACTIVE;
	}

	@Override
	public TracingStatus.ErrorState getTracingErrorState() {
		boolean hasError = status.getErrors().size() > 0;
		if (hasError) {
			return TracingErrorStateHelper.getErrorState(status.getErrors());
		}
		return null;
	}

	@Override
	public TracingStatus.ErrorState getReportErrorState() {
		boolean hasError = status.getErrors().size() > 0;
		if (hasError) {
			TracingStatus.ErrorState errorState = TracingErrorStateHelper.getErrorStateForReports(status.getErrors());
			if (TracingStatus.ErrorState.SYNC_ERROR_DATABASE.equals(errorState)) {
				return errorState;
			} else {
				if (DateUtils.getDaysDiff(status.getLastSyncDate()) > 1) {
					return errorState;
				}
			}
		}
		return null;
	}

	@Override
	public long getDaysSinceExposure() {
		if (getExposureDays().size() > 0) {
			long time = getExposureDays().get(0).getExposedDate().getStartOfDay(TimeZone.getDefault());
			return DateUtils.getDaysDiff(time);
		}
		return -1;
	}

	@Override
	public NotificationState getNotificationState() {
		switch (debugAppState) {
			case NONE:
				if (isReportedAsInfected()) {
					return NotificationState.POSITIVE_TESTED;
				} else if (wasContactReportedAsExposed()) {
					return NotificationState.EXPOSED;
				} else {
					return NotificationState.NO_REPORTS;
				}
			case HEALTHY:
				return NotificationState.NO_REPORTS;
			case REPORTED_EXPOSED:
				return NotificationState.POSITIVE_TESTED;
			case CONTACT_EXPOSED:
				return NotificationState.EXPOSED;
		}
		throw new IllegalStateException("Unkown debug AppState: " + debugAppState.toString());
	}

	/*@Override
	public void resetInfectionStatus(Context context) {
		DP3T.resetInfectionStatus(context);
	}

	@Override
	public boolean canInfectedStatusBeReset(Context context) {
		return DP3T.getIAmInfectedIsResettable(context);
	}

	@Override
	public void resetExposureDays(Context context) {
		DP3T.resetExposureDays(context);
	}*/


}
