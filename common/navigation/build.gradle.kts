plugins {
    alias(libs.plugins.castorena.bl.compose.lib)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.ulises.common.navigation"
}

dependencies {
    implementation(libs.serialization.json)
}