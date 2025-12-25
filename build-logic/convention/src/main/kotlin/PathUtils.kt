import org.gradle.api.Project

fun Project.pathToPackageName(): String {
    val relativePackageName = path
        .replace(':', '.')
        .lowercase()

    return "com.tilda$relativePackageName"
}