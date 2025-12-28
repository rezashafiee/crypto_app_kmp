import com.android.build.api.dsl.androidLibrary
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = getLibs()

        extensions.configure<KotlinMultiplatformExtension> {
            /*namespace = "com.tilda.${modulePackageName}"
            compileSdk = libs.findVersion("android.compileSdk").get().requiredVersion.toInt()

            defaultConfig.minSdk = libs.findVersion("android.minSdk").get().requiredVersion.toInt()

            testOptions {
                unitTests {
                    isIncludeAndroidResources = true
                }
            }

            defaultConfig {
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }*/

            androidLibrary {
                namespace = "com.tilda.crypto.presentation"
                compileSdk = 36
                minSdk = 28

                withHostTestBuilder {
                }

                withDeviceTestBuilder {
                    sourceSetTreeName = "test"
                }.configure {
                    instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
            }
        }

    }
}