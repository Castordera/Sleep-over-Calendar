pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        includeBuild("build-logic")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

includeBuild("build-logic")

rootProject.name = "SleepSchedule"
include(":app")
include(":usecases")
include(":domain")
include(":data")
include(":design:theme")
include(":design:components")
include(":features:splash")
include(":common:dispatcher-core")
include(":common:test-core")
include(":features:register-user")
