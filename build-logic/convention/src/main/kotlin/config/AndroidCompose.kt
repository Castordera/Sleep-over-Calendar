package config

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *>
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = libs.version("composeCompiler")
        }

        packaging {
            resources.excludes.add("META-INF/LICENSE-notice.md")
            resources.excludes.add("META-INF/LICENSE.md")
        }

        dependencies {
            add("implementation", libs.library("compose-ui"))
            add("implementation", libs.library("compose-ui-preview"))
            add("debugImplementation", libs.library("compose-ui-tooling"))
        }
    }
}