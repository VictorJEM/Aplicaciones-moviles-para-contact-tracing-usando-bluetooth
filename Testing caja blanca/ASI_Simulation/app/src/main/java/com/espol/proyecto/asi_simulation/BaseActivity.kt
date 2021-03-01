package com.espol.proyecto.asi_simulation

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.espol.proyecto.asi_simulation.extension.function.setDefaultLocale

abstract class BaseActivity : FragmentActivity() {
    protected val TAG
        get() = this::class.java.simpleName

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(setDefaultLocale(newBase!!, "es"))
    }
}