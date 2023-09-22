import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import config.configureUnitTestCommon
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.findByType

class UnitTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.findByType<ApplicationExtension>()?.apply {
                configureUnitTestCommon(this)
            }
            extensions.findByType<LibraryExtension>()?.apply {
                configureUnitTestCommon(this)
            }
            extensions.findByType<JavaPluginExtension>()?.apply {
                configureUnitTestCommon(this)
            }
        }
    }
}