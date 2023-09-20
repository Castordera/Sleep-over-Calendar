import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import config.configureAndroidKotlin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType

class AndroidKotlinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.jetbrains.kotlin.android")

            extensions.findByType<ApplicationExtension>()?.apply {
                configureAndroidKotlin(this)
            }

            extensions.findByType<LibraryExtension>()?.apply {
                configureAndroidKotlin(this)
            }
        }
    }
}