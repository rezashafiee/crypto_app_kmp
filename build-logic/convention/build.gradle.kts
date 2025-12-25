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

        register("buildKonfig") {
            id = libs.plugins.convention.buildKonfig.get().pluginId
            implementationClass = "BuildKonfigConventionPlugin"
        }
    }
}