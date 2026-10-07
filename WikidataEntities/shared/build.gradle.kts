plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("org.jetbrains.kotlin.plugin.serialization") version "2.4.20"
}

kotlin {
    // В этой вехе один таргет — desktop (JVM). Web и Android вернутся в следующих вехах.
    jvm {
        compilerOptions {
            // По умолчанию имя модуля — «WikidataEntities:shared». Компилятор Compose называет
            // отчёты по нему, а двоеточие в имени файла на Windows означает скрытый поток файла:
            // отчёт пропадает внутри пустого файла «WikidataEntities». Имя без двоеточия это чинит.
            moduleName.set("shared")
        }
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
        }
    }
}

composeCompiler {
    // Отчёты о стабильности и пропускаемости composable-функций:
    // build/compose_compiler/shared-composables.txt
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    metricsDestination = layout.buildDirectory.dir("compose_compiler")
}

compose.resources {
    // Класс Res по умолчанию internal, и из модуля desktopApp его не видно.
    // Открываем его, чтобы заголовок окна тоже был ресурсом, а не строкой в коде.
    publicResClass = true
}
