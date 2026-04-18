import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        // Intentionally empty: this convention plugin is currently not used by any module.
    }
}

