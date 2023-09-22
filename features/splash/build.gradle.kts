plugins {
    id("sleepover.android.library.compose")
    id("sleepover.android.kotlin")
    id("sleepover.android.common")
    id("sleepover.unit.test")
    id("kotlin-kapt")
}

android {
    namespace = "com.ulises.features.splash"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(project(":design:theme"))
    implementation(project(":design:components"))
    implementation(project(":usecases"))
    implementation(project(":domain"))
    implementation(project(":common:dispatcher-core"))
    //
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    //
    implementation(libs.compose.material3)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.compose.lifecycle.runtime)
    //
    testImplementation(project(":common:test-core"))
}