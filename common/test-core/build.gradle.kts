plugins {
    alias(libs.plugins.sleep.kotlin.library)
}

dependencies {
    implementation(project(":common:dispatcher-core"))
    implementation(libs.coroutines.test)
    implementation(libs.junit5)
}