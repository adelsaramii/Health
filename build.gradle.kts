plugins {
    kotlin("multiplatform").apply(false)
    id("com.android.application").apply(false)
    id("com.android.library").apply(false)
    id("org.jetbrains.compose").apply(false)
}

buildscript {

    val kotlinVersion = extra["kotlin.version"] as String

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven(uri("https://plugins.gradle.org/m2/")) // For kotlinter-gradle
    }

    dependencies {
        classpath("com.android.tools.build:gradle:8.0.0")

        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlinVersion}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${kotlinVersion}")

        classpath("com.google.gms:google-services:4.4.0")

        classpath("org.jmailen.gradle:kotlinter-gradle:3.4.5")

        classpath("com.squareup.sqldelight:gradle-plugin:1.5.5")

        classpath("com.github.ben-manes:gradle-versions-plugin:0.39.0")
    }

}

allprojects {
    apply(plugin = "org.jmailen.kotlinter")
}