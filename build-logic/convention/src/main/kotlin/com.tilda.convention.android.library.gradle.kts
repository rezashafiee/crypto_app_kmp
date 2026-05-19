import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

plugins {
    id("com.android.kotlin.multiplatform.library")
    id("com.android.lint")
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

afterEvaluate {
    configurations.findByName("commonTestImplementation")?.let {
        dependencies.add(it.name, libs.findLibrary("kotlin-test").get())
    }

    configurations.findByName("androidDeviceTestImplementation")?.let {
        dependencies.add(it.name, libs.findLibrary("androidx-runner").get())
        dependencies.add(it.name, libs.findLibrary("androidx-core").get())
        dependencies.add(it.name, libs.findLibrary("androidx-testExt-junit").get())
    }
}




