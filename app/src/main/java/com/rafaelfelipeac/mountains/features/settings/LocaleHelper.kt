package com.rafaelfelipeac.mountains.features.settings

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import com.rafaelfelipeac.mountains.core.persistence.sharedpreferences.Preferences
import java.util.*

object LocaleHelper {

    fun onAttach(context: Context): Context {
        val preferences = Preferences(context)

        val lang = preferences.language

        return setLocale(context, lang)
    }

    fun setLocale(context: Context, language: String?): Context {
        val preferences = Preferences(context)

        preferences.language = language!!

        return updateResources(context, language)
    }

    @SuppressLint("ObsoleteSdkInt")
    fun updateResources(context: Context, language: String): Context {
        var contextFun = context

        val locale =
            if (language == "pt_br")
                Locale("pt", "BR")
            else
                Locale(language)
        Locale.setDefault(locale)

        val resources = context.resources
        val configuration = Configuration(resources.configuration)

        if (Build.VERSION.SDK_INT >= 17) {
            configuration.setLocale(locale)
            contextFun = context.createConfigurationContext(configuration)
        } else {
            configuration.locale = locale
            resources.updateConfiguration(configuration, resources.displayMetrics)
        }

        return contextFun
    }
}