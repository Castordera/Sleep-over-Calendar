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
gradle.startParameter.excludedTaskNames.addAll(listOf(":build-logic:convention:testClasses"))
rootProject.name = "SleepSchedule"
include(":app")
include(":domain")
include(":data")
include(":design:theme")
include(":design:components")
include(":features:splash")
include(":common:dispatcher-core")
include(":common:test-core")
include(":features:user-register")
include(":features:user-login")
include(":common:navigation")
include(":features:user-detail")
include(":usecases:events")
include(":usecases:session")
include(":usecases:user")
include(":features:event-list")
include(":common:time-utils")
include(":features:event-add")
include(":common:session")
