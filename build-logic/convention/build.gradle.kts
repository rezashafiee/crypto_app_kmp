plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.kotlin.gradle.plugin)
    implementation("org.jetbrains.kotlin.plugin.compose:org.jetbrains.kotlin.plugin.compose.gradle.plugin:${libs.versions.kotlin.get()}")
    implementation(libs.android.gradlePlugin)
    implementation("org.jetbrains.compose:compose-gradle-plugin:${libs.versions.composeMultiplatform.get()}")
    implementation(libs.buildkonfig.gradlePlugin)
    implementation(libs.buildkonfig.compiler)
}

gradlePlugin {
    plugins {

        register("buildKonfig") {
            id = libs.plugins.convention.buildKonfig.get().pluginId
            implementationClass = "BuildKonfigConventionPlugin"
        }
    }
}