import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("org.jetbrains.kotlin.plugin.serialization") version "2.4.20"
    alias(libs.plugins.androidKmp)
    alias(libs.plugins.buildkonfig)
}

kotlin {
    android {
        namespace = "org.example.project.shared"
        compileSdk = 37
        minSdk = 26
        androidResources.enable = true
    }
    jvm {
        compilerOptions {
            moduleName.set("shared")
        }
    }

    js {
        browser()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.jetbrains.navigation3)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.ktor.core)
        }
        jvmMain.dependencies { implementation(libs.ktor.cio) }
        androidMain.dependencies { implementation(libs.ktor.okhttp) }
        webMain.dependencies { implementation(libs.ktor.js) }
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.ktor.mock)
        }
    }
}

val localConfig = Properties().apply {
    rootProject.file("local.properties").takeIf { it.exists() }?.inputStream()?.use { load(it) }
}
val apiUrl = providers.environmentVariable("WIKIDATA_API_URL")
    .orElse(localConfig.getProperty("wikidata.api.url", "")).get()
require(apiUrl.startsWith("https://") && !apiUrl.contains('"') && !apiUrl.contains('\n')) {
    "Set wikidata.api.url in local.properties or WIKIDATA_API_URL (HTTPS Wikidata Action API). See README."
}
buildkonfig {
    packageName = "org.example.project.data.network"
    defaultConfigs { buildConfigField(STRING, "API_URL", apiUrl) }
}

tasks.withType<Test>().configureEach {
    systemProperty("wikidata.live", providers.gradleProperty("wikidata.live").getOrElse("false"))
}

composeCompiler {
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    metricsDestination = layout.buildDirectory.dir("compose_compiler")
}

compose.resources {
    publicResClass = true
}
