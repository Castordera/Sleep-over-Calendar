plugins {
    id("sleepover.kotlin.library")
}

dependencies {
    implementation(project(":common:dispatcher-core"))
    implementation(libs.coroutines.test)
    implementation(libs.junit5)
}