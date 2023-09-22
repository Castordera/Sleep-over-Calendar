import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.ulises.sleepschedule.convention.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplicationCompose") {
            id = "sleepover.android.app.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "sleepover.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidKotlin") {
            id = "sleepover.android.kotlin"
            implementationClass = "AndroidKotlinConventionPlugin"
        }
        register("androidCommon") {
            id = "sleepover.android.common"
            implementationClass = "AndroidCommonConventionPlugin"
        }
        register("kotlinLibraryCommon") {
            id = "sleepover.kotlin.library"
            implementationClass = "KotlinLibraryCommonConventionPlugin"
        }
        register("unitTestCommon") {
            id = "sleepover.unit.test"
            implementationClass = "UnitTestConventionPlugin"
        }
    }
}