package org.example.project

class WasmPlatform : Platform {
    override val name: String = "Web with Kotlin/Wasm"
}

actual fun getPlatform(): Platform = WasmPlatform()

actual fun changeLanguageAtRuntime(lang: String) {
    // На Wasm пока оставляем заглушку для автономного каркаса
}
