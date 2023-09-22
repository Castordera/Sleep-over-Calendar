package config

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType

internal fun Project.configureUnitTestCommon(
    extension: CommonExtension<*, *, *, *, *>
) {
    extension.apply {
        configure()
    }
}

internal fun Project.configureUnitTestCommon(
    extension: JavaPluginExtension
) {
    extension.apply {
        configure()
    }
}

private fun Project.configure() {
    dependencies {
        add("testImplementation", libs.library("junit5"))
        add("testImplementation", libs.library("mockk"))
        add("testImplementation", libs.library("coroutines-test"))
        add("testImplementation", libs.library("turbine"))
    }
    //  Enable jUnit5
    tasks.withType<Test> {
        useJUnitPlatform()
    }
}