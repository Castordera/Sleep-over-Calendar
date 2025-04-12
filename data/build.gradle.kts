plugins {
    alias(libs.plugins.sleep.kotlin.library)
}

dependencies {
    implementation(project(":domain"))
    implementation(libs.javax.inject)
    implementation(libs.coroutines.core)
}