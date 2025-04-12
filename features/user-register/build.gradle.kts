plugins {
    alias(libs.plugins.sleep.android.library)
    alias(libs.plugins.sleep.android.library.compose)
    id("sleepover.unit.test")
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.ulises.register.user"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    //  UI
    implementation(project(":design:theme"))
    implementation(project(":design:components"))
    //  Data
    implementation(project(":usecases:session"))
    implementation(project(":usecases:user"))
    implementation(project(":domain"))
    //  Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    //  Core
    implementation(project(":common:dispatcher-core"))
    implementation(libs.hilt.navigation.compose)
    implementation(libs.compose.lifecycle.runtime)
    //  Test
    testImplementation(project(":common:test-core"))
}