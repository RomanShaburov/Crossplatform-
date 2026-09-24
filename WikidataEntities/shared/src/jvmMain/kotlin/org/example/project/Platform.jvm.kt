package org.example.project

import java.util.Locale

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()

actual fun changeLanguageAtRuntime(lang: String) {
    Locale.setDefault(Locale(lang))
}
