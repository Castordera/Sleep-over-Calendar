plugins {
    alias(libs.plugins.castorena.bl.compose.lib)
}

android {
    namespace = "com.ulises.components"
}

dependencies {
    implementation(project(":design:theme"))
}