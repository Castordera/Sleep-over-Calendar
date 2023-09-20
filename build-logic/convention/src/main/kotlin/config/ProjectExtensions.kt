package config

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun VersionCatalog.library(libraryName: String) = findLibrary(libraryName).get()

internal fun VersionCatalog.version(versionName: String): String {
    return findVersion(versionName).get().requiredVersion
}
