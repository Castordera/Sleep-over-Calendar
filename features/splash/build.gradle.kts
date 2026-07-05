plugins {
    alias(libs.plugins.castorena.bl.compose.lib)
    alias(libs.plugins.castorena.bl.test.unit)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.ulises.features.splash"
}

dependencies {
    //  UI
    implementation(project(":design:theme"))
    implementation(project(":design:components"))
    //  Data
    implementation(project(":usecases:session"))
    implementation(project(":usecases:user"))
    implementation(project(":domain"))
    implementation(project(":common:session"))
    //  Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    ksp(libs.kotlin.metadata.jvm)
    //  Core
    implementation(libs.hilt.navigation.compose)
    implementation(libs.compose.lifecycle.runtime)
    implementation(project(":common:dispatcher-core"))
    //  Test
    testImplementation(project(":common:test-core"))
}