plugins {
    alias(libs.plugins.castorena.bl.kotlin.lib)
}

dependencies {
    implementation(project(":common:dispatcher-core"))
    implementation(libs.mockk)
    implementation(libs.coroutines.test)
    implementation(libs.junit5)
}