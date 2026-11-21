plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    js {
        browser {
            commonWebpackConfig {
                outputFileName = "network-worker.js"
            }
        }
        binaries.executable()
    }
    sourceSets {
        jsMain {
            // Reuse the exact serialization contract; no second handwritten JSON parser.
            kotlin.srcDir("../shared/src/commonMain/kotlin/org/example/project/data/network")
            kotlin.include("**/WikidataDto.kt", "**/WorkerMain.kt")
            dependencies { implementation(libs.kotlinx.serialization.json) }
        }
    }
}
