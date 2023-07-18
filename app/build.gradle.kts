import org.jetbrains.kotlin.ir.backend.js.compile

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "com.seedoilz.mybrowser"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.seedoilz.mybrowser"
        minSdk = 24
        targetSdk = 33
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
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(files("libs/QWeather_Public_Android_V4.17.jar"))
    testImplementation(libs.junit)
    implementation("com.squareup.okhttp3:okhttp:3.12.12")
    implementation("com.google.code.gson:gson:2.6.2")
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.google.android.material:material:1.0.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("com.github.bumptech.glide:glide:4.9.0")
    implementation("com.google.android.exoplayer:exoplayer:2.14.0")
    implementation("com.scwang.smartrefresh:SmartRefreshLayout:1.1.2")
    implementation("com.scwang.smartrefresh:SmartRefreshHeader:1.1.2")
}
