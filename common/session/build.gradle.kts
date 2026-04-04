plugins {
    alias(libs.plugins.sleep.kotlin.library)
}

dependencies {
    implementation(libs.coroutines.core)
    implementation(libs.javax.inject)
    implementation(project(":domain"))
}