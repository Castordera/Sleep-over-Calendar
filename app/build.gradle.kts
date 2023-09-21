plugins {
    id("sleepover.android.app.compose")
    id("sleepover.android.kotlin")
    id("sleepover.android.common")
    id("com.google.gms.google-services")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.sleepschedule"

    defaultConfig {
        applicationId = "com.ulises.sleepschedule"
        targetSdk = 33
        versionCode = 2
        versionName = "1.1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        buildConfig = true
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    //  Firebase
    implementation(platform("com.google.firebase:firebase-bom:31.2.2"))
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")

    //  Android
//    implementation(libs.bundles.android.core)
    //  Compose
    implementation(libs.compose.activity)
    implementation(libs.compose.material)
    implementation(libs.compose.material3)
    implementation(libs.compose.lifecycle.runtime)

    //  Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Coil
    implementation(libs.bundles.coil)

    //  Navigation
    implementation(libs.bundles.navigation)

    //  Room
    implementation(libs.bundles.room)
    ksp(libs.room.compiler)

    implementation(project(":data"))
    implementation(project(":usecases"))
    implementation(project(":domain"))
    implementation(project(":design:theme"))

    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.android.test)
    androidTestImplementation(libs.compose.ui.test.junit4)
    testImplementation(libs.bundles.compose.test)
}