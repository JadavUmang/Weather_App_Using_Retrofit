plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.weatherapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.weatherapp"
        minSdk = 24
        targetSdk = 35
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
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.squareup.retrofit2:retrofit:2.9.0") // Retrofit for HTTP requests
    implementation("com.squareup.retrofit2:converter-gson:2.9.0") // Gson converter for Retrofit
    implementation("com.google.code.gson:gson:2.8.8") // Gson for JSON parsing
    implementation("androidx.appcompat:appcompat:1.2.0") // AppCompat for backward compatibility
    implementation("androidx.constraintlayout:constraintlayout:2.0.4") // ConstraintLayout for UI
    implementation("androidx.recyclerview:recyclerview:1.2.0") // RecyclerView (if needed)
    implementation("com.squareup.okhttp3:okhttp:4.9.1") // OkHttp (used by Retrofit)
    implementation("com.github.bumptech.glide:glide:4.12.0") // Glide for image loading (optional for weather icons)
    implementation("androidx.cardview:cardview:1.0.0") // CardView for better UI components (optional)

}