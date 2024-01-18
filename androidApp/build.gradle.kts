plugins {
    kotlin("multiplatform")
    id("com.android.application")
    id("org.jetbrains.compose")
}

kotlin {
    androidTarget()
    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(project(":shared"))
            }
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.myapplication"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        applicationId = "com.myapplication.MyApplication"
        minSdk = (findProperty("android.minSdk") as String).toInt()
        targetSdk = (findProperty("android.targetSdk") as String).toInt()
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}

dependencies {
    implementation(project(":shared"))
    implementation("androidx.compose.ui:ui:1.5.0")
    implementation("androidx.compose.runtime:runtime:1.5.0")
    implementation("androidx.compose.ui:ui-tooling:1.5.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.0")
    implementation("androidx.compose.foundation:foundation:1.5.0")
    implementation("androidx.compose.material:material:1.5.0")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("io.github.xxfast:decompose-router:0.4.0")
    implementation("com.arkivanov.decompose:decompose:2.1.0-compose-experimental-alpha-07")
    implementation("androidx.compose.material3:material3-window-size-class:1.1.1")
    implementation("com.arkivanov.decompose:extensions-compose-jetbrains:2.1.0-compose-experimental-alpha-07")
    implementation("com.bumble.appyx:appyx-navigation:2.0.0-alpha08")
    implementation("com.bumble.appyx:backstack:2.0.0-alpha08")
    implementation(platform("com.google.firebase:firebase-bom:32.6.0"))
    implementation("com.google.firebase:firebase-messaging:23.3.1")
    implementation ("com.google.firebase:firebase-messaging-ktx:23.3.1")
}