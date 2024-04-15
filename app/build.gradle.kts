plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    //id("androidx.room")
}

android {
    namespace = "ru.itis.loverushka_app"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.itis.loverushka_app"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
//    room {
//        schemaDirectory("$projectDir/schemas")
//    }
}

dependencies {

    api(libs.core.ktx)
    api(libs.material3)
    api(libs.androidx.appcompat)
    api(libs.activity.compose)
    api(platform(libs.compose.bom))
    api(libs.compose)
    api(libs.compose.graphics)
    api(libs.compose.material.icons)
    api(libs.compose.tooling.preview)

    debugApi(libs.compose.tooling)

    implementation(libs.androidx.navigation)
    implementation(libs.accompanist.navigation.animation)

    implementation(libs.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material3)

    implementation(libs.dagger.hilt)
    kapt(libs.dagger.hilt.compiler)
    implementation(libs.dagger.hilt.navigation.compose)

    implementation(libs.room)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.room.ktx)
    kapt(libs.room.kapt)

    // viewModel
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // gson
    val gson_version = "2.10.1"
    implementation("com.google.code.gson:gson:${gson_version}")

    implementation("io.coil-kt:coil-compose:2.6.0")

//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.lifecycle.runtime.ktx)
//    implementation(libs.androidx.activity.compose)
//    implementation(platform(libs.androidx.compose.bom))
//    implementation(libs.androidx.ui)
//    implementation(libs.androidx.ui.graphics)
//    implementation(libs.androidx.ui.tooling.preview)
//    implementation(libs.androidx.material3)
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
//    androidTestImplementation(platform(libs.androidx.compose.bom))
//    androidTestImplementation(libs.androidx.ui.test.junit4)
//    debugImplementation(libs.androidx.ui.tooling)
//    debugImplementation(libs.androidx.ui.test.manifest)
//
//    //implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")
//
//    // navigation
//    implementation("androidx.navigation:navigation-compose:2.7.7")
//    //implementation("com.google.accompanist:accompanist-navigation-animation:0.28.0")
//
//    // room
//    val room_version = "2.6.1"
//    implementation("androidx.room:room-runtime:$room_version")
//    annotationProcessor("androidx.room:room-compiler:$room_version")
//    //annotationProcessor("androidx.room:room-compiler:$room_version")
//    ksp("androidx.room:room-compiler:$room_version")
//    //kapt("androidx.room:room-compiler:$room_version")
//    implementation("androidx.room:room-ktx:$room_version")
//
//    // hilt
//    implementation("com.google.dagger:hilt-android:2.44")
//    //kapt("com.google.dagger:hilt-android-compiler:2.44")
//    annotationProcessor("com.google.dagger:hilt-android-compiler:2.44")
////    implementation("com.google.dagger:hilt-android:2.44")
////    annotationProcessor("com.google.dagger:hilt-android-compiler:2.44")
////    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
////
////    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
////    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
////    annotationProcessor("androidx.hilt:hilt-compiler:1.2.0")
////    annotationProcessor("com.google.dagger:hilt-android-compiler:2.44")
//
//
//
//    // gson
//    implementation("com.google.code.gson:gson:2.10.1")

}

kapt {
    correctErrorTypes = true
}