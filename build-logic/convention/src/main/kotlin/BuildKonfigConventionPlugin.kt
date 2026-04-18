import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.BOOLEAN
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import com.codingfeline.buildkonfig.gradle.BuildKonfigExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import java.util.Properties

class BuildKonfigConventionPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply("com.codingfeline.buildkonfig")
        }

        extensions.configure<BuildKonfigExtension> {

            packageName = target.pathToPackageName()

            defaultConfigs {

                val properties = Properties().apply {
                    rootProject.file("local.properties")
                        .takeIf { it.exists() }
                        ?.inputStream()
                        ?.use(::load)
                }

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