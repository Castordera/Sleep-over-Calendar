plugins {
    alias(libs.plugins.castorena.bl.kotlin.lib)
}

dependencies {
    implementation(libs.javax.inject)
    implementation(libs.coroutines.core)
}