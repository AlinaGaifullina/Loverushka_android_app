// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
//    alias(libs.plugins.androidApplication) apply false
//    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
//    id("com.google.dagger.hilt.android") version "2.44" apply false
//    id("org.jetbrains.kotlin.jvm") version "1.8.0" apply false
//    id("com.android.library") version "7.4.2" apply false
//    id("androidx.room") version "2.6.1" apply false
//    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false

    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.androidLibrary) apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
    kotlin("plugin.serialization") version "1.9.0" apply false
}