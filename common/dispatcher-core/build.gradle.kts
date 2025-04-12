plugins {
    alias(libs.plugins.sleep.kotlin.library)
}

dependencies {
    implementation(libs.javax.inject)
    implementation(libs.coroutines.core)
}