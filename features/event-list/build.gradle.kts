plugins {
    alias(libs.plugins.castorena.bl.compose.lib)
    alias(libs.plugins.castorena.bl.test.unit)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.ulises.features.events.list"
}

dependencies {
    //  UI
    implementation(project(":design:theme"))
    implementation(project(":design:components"))
    //  Data
    implementation(project(":usecases:events"))
    implementation(project(":domain"))
    //  Utils
    implementation(project(":common:time-utils"))
    //  Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    ksp(libs.kotlin.metadata.jvm)
    //  Core
    implementation(project(":common:dispatcher-core"))
    implementation(libs.hilt.navigation.compose)
    implementation(libs.compose.lifecycle.runtime)
    // Coil
    implementation(libs.bundles.coil)
    //  Test
    testImplementation(project(":common:test-core"))
}