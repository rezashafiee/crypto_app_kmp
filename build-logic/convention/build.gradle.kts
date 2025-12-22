plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.kotlin.gradle.plugin)
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