plugins {
    id("sleepover.android.library.compose")
    id("sleepover.android.kotlin")
    id("sleepover.android.common")
}

android {
    namespace = "com.ulises.components"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(project(":design:theme"))
    implementation(libs.compose.material)
}