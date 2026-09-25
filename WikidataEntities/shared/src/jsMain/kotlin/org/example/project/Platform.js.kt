package org.example.project

class JSPlatform : Platform {
    override val name: String = "Web with Kotlin/JS"
}

actual fun getPlatform(): Platform = JSPlatform()

actual fun changeLanguageAtRuntime(lang: String) {
}
