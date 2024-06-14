plugins {
    id("sleepover.android.library.compose")
    id("sleepover.android.kotlin")
    id("sleepover.android.common")
    id("sleepover.unit.test")
    id("com.google.devtools.ksp")
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
    implementation(libs.compose.material3)
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