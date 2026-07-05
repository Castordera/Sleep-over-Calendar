plugins {
    alias(libs.plugins.castorena.bl.kotlin.lib)
}

dependencies {
    implementation(project(":domain"))
    implementation(libs.javax.inject)
    implementation(libs.coroutines.core)
}