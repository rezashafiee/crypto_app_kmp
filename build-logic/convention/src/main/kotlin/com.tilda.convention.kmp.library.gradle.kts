import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.tilda.convention.android.library")
}

extensions.configure<KotlinMultiplatformExtension> {
    iosX64()
    iosArm64()
    iosSimulatorArm64()
}

