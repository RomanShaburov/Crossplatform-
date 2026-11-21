import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

val workerBundle = project(":networkWorker").layout.buildDirectory.dir("dist/js/productionExecutable")
val copyNetworkWorker by tasks.registering(Copy::class) {
    dependsOn(":networkWorker:jsBrowserDistribution")
    from(workerBundle) { include("network-worker.js") }
    into(layout.buildDirectory.dir("generated/workerResources"))
}
kotlin.sourceSets.named("webMain") {
    resources.srcDir(copyNetworkWorker.map { it.destinationDir })
}

kotlin {
    js {
        browser()
        binaries.executable()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":shared"))

            implementation(libs.compose.ui)
        }
    }
}
