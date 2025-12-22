import org.gradle.api.Plugin
import org.gradle.api.Project

class KmpLibraryConventionPlugin: Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = getLibs()
        plugins.apply(libs.findPlugin("kotlinMultiplatform").get().get().pluginId)


    }
}