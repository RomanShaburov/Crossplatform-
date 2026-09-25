package org.example.project

import android.os.Build
import java.util.Locale

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun changeLanguageAtRuntime(lang: String) {
    Locale.setDefault(Locale.forLanguageTag(lang))
}
