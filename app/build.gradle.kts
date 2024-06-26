plugins {
    id("sleepover.android.app.compose")
    id("sleepover.android.kotlin")
    id("sleepover.android.common")
    id("com.google.gms.google-services")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.sleepschedule"

    defaultConfig {
        applicationId = "com.ulises.sleepschedule"
        versionCode = 5
        versionName = "1.2.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    //  Firebase
    implementation(platform("com.google.firebase:firebase-bom:31.2.2"))
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")
    //  Compose
    implementation(libs.compose.activity)
    implementation(libs.compose.material3)
    implementation(libs.compose.lifecycle.runtime)
    //  Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    // Coil
    implementation(libs.bundles.coil)
    //  Navigation
    implementation(libs.bundles.navigation)
    //  Room
    implementation(libs.bundles.room)
    ksp(libs.room.compiler)

    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":design:theme"))
    implementation(project(":common:dispatcher-core"))
    implementation(project(":common:navigation"))
    implementation(project(":features:splash"))
    implementation(project(":features:user-register"))
    implementation(project(":features:user-login"))
    implementation(project(":features:user-detail"))
    implementation(project(":features:event-list"))
    implementation(project(":features:event-add"))

    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.android.test)
    androidTestImplementation(libs.compose.ui.test.junit4)
    testImplementation(libs.bundles.compose.test)
}