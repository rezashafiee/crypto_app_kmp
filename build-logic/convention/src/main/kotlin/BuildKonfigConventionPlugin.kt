import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.BOOLEAN
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import com.codingfeline.buildkonfig.gradle.BuildKonfigExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class BuildKonfigConventionPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply("com.codingfeline.buildkonfig")
        }

        extensions.configure<BuildKonfigExtension> {

            packageName = target.pathToPackageName()

            defaultConfigs {

                val properties = gradleLocalProperties(rootDir, rootProject.providers)

                val debugMode = properties.getProperty("debugMode") ?: throw IllegalStateException("gradle property \"debugMode\" is missing")

                buildConfigField(
                    type = STRING,
                    name = "BASE_URL",
                    value = "https://data-api.coindesk.com/"
                )

                buildConfigField(
                    type = BOOLEAN,
                    name = "DEBUG_MODE",
                    value = debugMode
                )
            }

        }
    }
}