plugins {
    alias(libs.plugins.castorena.bl.compose.lib)
}

android {
    namespace = "com.ulises.theme"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(libs.android.core.ktx)
    implementation(libs.androidx.ui.text.google.fonts)
}