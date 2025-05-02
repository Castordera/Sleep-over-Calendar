plugins {
    alias(libs.plugins.sleep.android.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.sleep.android.library.compose)
}

android {
    namespace = "com.ulises.common.navigation"
}

dependencies {
    implementation(libs.serialization.json)
}