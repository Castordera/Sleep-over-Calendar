import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import config.configureAndroidAppCommon
import config.configureAndroidCommon
import config.configureCommonDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType

class AndroidCommonConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.findByType<ApplicationExtension>()?.apply {
                configureAndroidCommon(this)
                configureAndroidAppCommon(this)
                configureCommonDependencies()
            }

            extensions.findByType<LibraryExtension>()?.apply {
                configureAndroidCommon(this)
                configureCommonDependencies()
            }
        }
    }
}