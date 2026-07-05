plugins {
    alias(libs.plugins.castorena.bl.kotlin.lib)
}

dependencies {
    implementation(libs.coroutines.core)
    implementation(libs.javax.inject)
    implementation(project(":domain"))
}