plugins {
    id("com.android.application") version "8.5.2"
    kotlin("android") version "1.9.24"
}

android {
    namespace = "com.example.marblegame"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.marblegame"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug { }
    }

    buildFeatures { compose = true }
    composeOptions { kotlinCompilerExtensionVersion = "1.5.14" }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
}

kotlin {
    jvmToolchain(17)
}

configurations.all {
    resolutionStrategy {
        force(
            "androidx.compose.ui:ui:1.6.8",
            "androidx.compose.ui:ui-android:1.6.8",
            "androidx.compose.ui:ui-graphics:1.6.8",
            "androidx.compose.ui:ui-text:1.6.8",
            "androidx.compose.ui:ui-tooling:1.6.8",
            "androidx.compose.ui:ui-tooling-preview:1.6.8",
            "androidx.compose.ui:ui-tooling-data:1.6.8",
            "androidx.compose.ui:ui-tooling-data-android:1.6.8",
            "androidx.compose.foundation:foundation:1.6.8",
            "androidx.compose.foundation:foundation-layout:1.6.8",
            "androidx.compose.animation:animation:1.6.8",
            "androidx.compose.animation:animation-core:1.6.8",
            "androidx.compose.runtime:runtime:1.6.8",
            "androidx.compose.runtime:runtime-saveable:1.6.8",

            "androidx.lifecycle:lifecycle-runtime-ktx:2.8.6",
            "androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6",
            "androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6",
            "androidx.lifecycle:lifecycle-viewmodel-compose-android:2.8.6",
            "androidx.lifecycle:lifecycle-runtime-compose:2.8.6",
            "androidx.lifecycle:lifecycle-runtime-compose-android:2.8.6",

            "androidx.activity:activity-compose:1.9.3",
            "androidx.activity:activity-ktx:1.9.3",
            "androidx.activity:activity:1.9.3",
            "androidx.core:core-ktx:1.13.1",
            "androidx.core:core:1.13.1"
        )
    }
}

dependencies {
    implementation("androidx.compose.ui:ui:1.6.8")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.8")
    debugImplementation("androidx.compose.ui:ui-tooling:1.6.8")
    implementation("androidx.compose.foundation:foundation:1.6.8")
    implementation("androidx.compose.animation:animation:1.6.8")
    implementation("androidx.compose.runtime:runtime-saveable:1.6.8")
    implementation("androidx.compose.material3:material3:1.2.1")

    implementation("androidx.activity:activity-compose:1.9.3")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.6")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.6")
    implementation("androidx.core:core-ktx:1.13.1")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")

    implementation("com.google.android.material:material:1.12.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}


