plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.android.gradlePlugin)
    implementation(libs.buildkonfig.gradlePlugin)
    implementation(libs.buildkonfig.compiler)
}

gradlePlugin {
    plugins {
        register("kmpLibrary") {
            id = libs.plugins.convention.kmp.library.get().pluginId
            implementationClass = "KmpLibraryConventionPlugin"
        }

        register("cmpLibrary") {
            id = libs.plugins.convention.cmp.library.get().pluginId
            implementationClass = "CmpLibraryConventionPlugin"
        }

        register("androidLibrary") {
            id = libs.plugins.convention.android.library.get().pluginId
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("buildKonfig") {
            id = libs.plugins.convention.buildKonfig.get().pluginId
            implementationClass = "BuildKonfigConventionPlugin"
        }
    }
}