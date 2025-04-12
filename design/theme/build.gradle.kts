plugins {
    alias(libs.plugins.sleep.android.library)
    alias(libs.plugins.sleep.android.library.compose)
}

android {
    namespace = "com.ulises.theme"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
}