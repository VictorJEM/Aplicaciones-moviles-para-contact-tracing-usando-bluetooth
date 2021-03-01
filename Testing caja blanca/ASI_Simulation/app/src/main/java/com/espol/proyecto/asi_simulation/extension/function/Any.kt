package com.espol.proyecto.asi_simulation.extension.function

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.*

private val TAG: String = "Any.kt"

fun setDefaultLocale(ctx : Context, locale_str : String) : Context {
    return setDefaultLocale(ctx, Locale(locale_str))
}

private fun setDefaultLocale(ctx: Context, locale : Locale) : Context{
    Locale.setDefault(locale)
    val config = Configuration()

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        config.setLocale(locale)
    } else{
        config.locale = locale
    }
    return ctx.createConfigurationContext(config)
    //baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
}