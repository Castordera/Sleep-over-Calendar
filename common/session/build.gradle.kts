plugins {
    alias(libs.plugins.sleep.android.library)
}

android {
    namespace = "com.ulises.session"
}

dependencies {
    implementation(libs.coroutines.core)
    implementation(libs.javax.inject)
    implementation(project(":domain"))
}