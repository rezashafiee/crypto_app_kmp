import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class CmpLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = getLibs()
        /*plugins.apply(libs.findPlugin("kotlinMultiplatform").get().get().pluginId)

        extensions.configure<KotlinMultiplatformExtension> {

        }*/

    }
}