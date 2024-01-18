plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("com.squareup.sqldelight")
    id("kotlin-parcelize")
}

kotlin {
    androidTarget()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {

                // region Compose multiplatform

                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
//                implementation("androidx.compose.runtime:runtime-tracing:1.0.0-alpha03")
//                runtimeOnly("androidx.compose.material:material-icons-core:1.6.0-alpha06")
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)

                // endregion

                // region Koin

                api("io.insert-koin:koin-core:3.4.3")
                api("io.insert-koin:koin-compose:1.0.4")

                // endregion

                // region Ktor

                val ktor = "2.2.2"

                implementation("io.ktor:ktor-client-core:${ktor}")
                implementation("io.ktor:ktor-client-json:${ktor}")
                implementation("io.ktor:ktor-client-logging:${ktor}")
                implementation("io.ktor:ktor-client-auth:${ktor}")
                implementation("io.ktor:ktor-client-content-negotiation:${ktor}")
                implementation("io.ktor:ktor-serialization-kotlinx-json:${ktor}")
                implementation("io.ktor:ktor-client-resources:${ktor}")

                // endregion

                // region Sqldelight

                implementation("com.squareup.sqldelight:runtime:1.5.3")
                implementation("com.squareup.sqldelight:coroutines-extensions:1.5.3")

                // endregion

                // region moko

                api("dev.icerock.moko:mvvm-core:0.16.1") // only ViewModel, EventsDispatcher, Dispatchers.UI
                api("dev.icerock.moko:mvvm-compose:0.16.1") // api mvvm-core, getViewModel for Compose Multiplatfrom

                // endregion

                // region Accompanist

                val accompanist = "0.33.1-alpha"

//                implementation("com.google.accompanist:accompanist-insets:${accompanist}")
                implementation("com.google.accompanist:accompanist-systemuicontroller:${accompanist}")
//                implementation("com.google.accompanist:accompanist-pager:${accompanist}")
//                implementation("com.google.accompanist:accompanist-swiperefresh:${accompanist}")
//                implementation("com.google.accompanist:accompanist-pager-indicators:${accompanist}")
//                implementation("com.google.accompanist:accompanist-navigation-material:${accompanist}")
//                implementation("com.google.accompanist:accompanist-navigation-animation:${accompanist}")
//                implementation("com.google.accompanist:accompanist-permissions:${accompanist}")

                // endregion

                // region arrow

                val arrow = "1.0.1"

                implementation("io.arrow-kt:arrow-core:${arrow}")
                implementation("io.arrow-kt:arrow-fx-coroutines:${arrow}")
                implementation("io.arrow-kt:arrow-fx-stm:${arrow}")

                // endregion

                // region setting (dataStore in kmm)

                val settings = "1.0.0-RC"

                implementation("com.russhwolf:multiplatform-settings:${settings}")
                implementation("com.russhwolf:multiplatform-settings-coroutines:${settings}")

                // endregion

                // region Kotlinx

                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
                implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5")

                // endregion

                // region Decompose

                implementation("io.github.xxfast:decompose-router:0.4.0")
                implementation("com.arkivanov.decompose:decompose:2.1.2")
                implementation("com.arkivanov.decompose:extensions-compose-jetbrains:2.1.0-compose-experimental-alpha-07")
                implementation("com.arkivanov.essenty:parcelable:1.2.0")

                // endregion

                // region Kamel image loader

                implementation("media.kamel:kamel-image:0.7.3")

                // endregion

                // region compose dialogs

                implementation("ca.gosyer:compose-material-dialogs-datetime:0.9.4")

                // endregion

                implementation("com.splendo.kaluga:location:1.2.1")

                implementation("dev.icerock.moko:geo-compose:0.6.0")

//                implementation("dev.materii.pullrefresh:pullrefresh:1.1.0")
            }
        }
        val androidMain by getting {
            dependsOn(commonMain)
            dependencies {
                api("androidx.activity:activity-compose:1.7.2")
                api("androidx.appcompat:appcompat:1.6.1")
                api("androidx.core:core-ktx:1.10.1")

                api("io.insert-koin:koin-android:3.4.3")

                implementation("io.ktor:ktor-client-android:2.2.2")
                implementation("io.ktor:ktor-client-okhttp:2.2.2")

                implementation("com.squareup.sqldelight:android-driver:1.5.3")

                implementation("com.arkivanov.decompose:extensions-compose-jetbrains:2.1.0-compose-experimental-alpha-07")

                implementation("androidx.activity:activity-compose:1.7.2")
                implementation("io.github.xxfast:decompose-router:0.4.0")
                implementation("com.arkivanov.decompose:decompose:2.1.0-compose-experimental-alpha-07")
                implementation("androidx.compose.material3:material3-window-size-class:1.1.1")
                implementation("com.arkivanov.decompose:extensions-compose-jetbrains:2.1.0-compose-experimental-alpha-07")

                implementation("androidx.constraintlayout:constraintlayout:2.1.4")

                implementation("com.google.accompanist:accompanist-pager:0.28.0")
                implementation("com.google.accompanist:accompanist-swiperefresh:0.28.0")
                implementation("com.google.accompanist:accompanist-insets:0.28.0")
                implementation("com.google.accompanist:accompanist-systemuicontroller:0.28.0")
                implementation("com.google.accompanist:accompanist-navigation-animation:0.28.0")
                implementation("com.google.accompanist:accompanist-permissions:0.28.0")
                implementation("com.google.accompanist:accompanist-pager-indicators:0.28.0")
                implementation("com.google.accompanist:accompanist-navigation-material:0.28.0")

                implementation("org.osmdroid:osmdroid-android:6.1.16")
                implementation("tech.utsmankece:osm-android-compose:0.0.5")

                implementation("androidx.camera:camera-core:1.3.0")
                implementation("androidx.camera:camera-view:1.3.0")
                implementation("androidx.camera:camera-lifecycle:1.3.0")
                implementation("androidx.camera:camera-camera2:1.3.0")

                implementation("com.journeyapps:zxing-android-embedded:4.3.0")

                implementation("com.google.firebase:firebase-bom:32.1.0")
                implementation("com.google.firebase:firebase-messaging:22.0.0")
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation("io.ktor:ktor-client-darwin:2.2.2")

                implementation("com.squareup.sqldelight:native-driver:1.5.3")

                implementation("io.github.xxfast:decompose-router:0.4.0")
                implementation("com.arkivanov.decompose:decompose:2.1.2")
                implementation("com.arkivanov.decompose:extensions-compose-jetbrains:2.1.0-compose-experimental-alpha-07")
                implementation("com.arkivanov.essenty:parcelable:1.2.0")
            }
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.myapplication.common"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}
