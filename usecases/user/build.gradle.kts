plugins {
    alias(libs.plugins.castorena.bl.kotlin.lib)
    alias(libs.plugins.castorena.bl.test.unit)
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":common:session"))
    //
    implementation(libs.javax.inject)
    implementation(libs.coroutines.core)
    testImplementation(project(":common:test-core"))
}