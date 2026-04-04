plugins {
    alias(libs.plugins.sleep.kotlin.library)
    id("sleepover.unit.test")
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