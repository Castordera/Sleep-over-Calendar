package config

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        dependencies {
            implementation(libs.library("compose-ui"))
            implementation(libs.library("compose-ui-preview"))
            implementation(libs.library("compose-material3"))
            debugImplementation(libs.library("compose-ui-tooling"))
        }
    }
}