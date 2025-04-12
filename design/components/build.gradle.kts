plugins {
    alias(libs.plugins.sleep.android.library)
    alias(libs.plugins.sleep.android.library.compose)
}

android {
    namespace = "com.ulises.components"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(project(":design:theme"))
}