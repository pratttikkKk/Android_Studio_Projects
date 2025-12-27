plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose") // required with Kotlin 2.0+
}

android {
    namespace = "com.example.notesapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.notesapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    // 🔴 IMPORTANT: Java tasks target 17 (fixes compileDebugJavaWithJavac=1.8)
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }


    buildFeatures {
        compose = true

        // Optional with Kotlin 2.0+, but fine to keep if you’re using a specific compiler ext
        composeOptions {
            // keep consistent with your Compose version if you set this
            kotlinCompilerExtensionVersion = "1.5.14"
        }
    }
}
// 🔴 IMPORTANT: Kotlin tasks target 17 (matches Java)
// 🔴 IMPORTANT: Kotlin tasks target 21 (matches Java)
kotlin {
    jvmToolchain(17)
}

// If you prefer, you can also keep (not required when using toolchain):
// android { kotlinOptions { jvmTarget = "17" } }

dependencies {
    implementation(platform("androidx.compose:compose-bom:2024.10.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation(libs.androidx.activity.ktx)
    debugImplementation("androidx.compose.ui:ui-tooling")

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.activity:activity-compose:1.9.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")

    // Retrofit / OkHttp
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
}
