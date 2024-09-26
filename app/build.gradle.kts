plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.mvpkotlin"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mvpkotlin"
        minSdk = 26
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
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    //glide for image loading
    implementation(libs.glide)
    // Network library
    implementation (libs.retrofit)
    implementation (libs.converter.gson)

    // Gson converter for parsing JSON
    implementation(libs.converter.gson)
    // OkHttp for making network requests
    implementation(libs.okhttp)
    // OkHttp Logging Interceptor for logging network requests
    implementation(libs.logging.interceptor)
    implementation(libs.showmoretextview)

}
