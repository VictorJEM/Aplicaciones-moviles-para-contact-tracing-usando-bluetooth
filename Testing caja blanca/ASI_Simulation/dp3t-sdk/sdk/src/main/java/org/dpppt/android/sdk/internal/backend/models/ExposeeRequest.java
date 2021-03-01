/*
 * Copyright (c) 2020 Ubique Innovation AG <https://www.ubique.ch>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */
package org.dpppt.android.sdk.internal.backend.models;

import org.dpppt.android.sdk.backend.models.ExposeeAuthMethodJson;

public class ExposeeRequest {

	private String key;
	private long keyDate;
	private ExposeeAuthMethodJson authData;
	private int fake;
	private String provincia;
	private String canton;
	private String parroquia;

	public ExposeeRequest(String key, long keyDate, ExposeeAuthMethodJson authData, String provincia, String canton, String parroquia) {
		this(key, keyDate, 0, authData, provincia, canton, parroquia);
	}

	public ExposeeRequest(String key, long keyDate, int fake, ExposeeAuthMethodJson authData, String provincia, String canton, String parroquia) {
		this.key = key;
		this.keyDate = keyDate;
		this.authData = authData;
		this.fake = fake;
		this.provincia = provincia;
		this.canton = canton;
		this.parroquia = parroquia;
	}

	public String getKey() {
		return key;
	}

	public long getKeyDate() {
		return keyDate;
	}

	public int getFake() {
		return fake;
	}

	public ExposeeAuthMethodJson getAuthData() {
		return authData;
	}

	public String getProvincia() {
		return provincia;
	}

	public String getCanton() {
		return canton;
	}

	public String getParroquia() {
		return parroquia;
	}

	@Override
	public String toString(){
		String str = key + " " + keyDate + " " + fake + " " + authData.getValue();
		return str;
	}

}
