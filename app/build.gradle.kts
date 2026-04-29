plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.ashutosh.radiosathi"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.ashutosh.radiosathi"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.media3:media3-exoplayer:1.5.1")
    implementation("androidx.media3:media3-common:1.5.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
}
