plugins {
    alias(libs.plugins.nowinandroid.android.library)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.v4vic.model"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

}

dependencies {
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.javax.inject)

    androidTestImplementation(libs.androidx.test.espresso.core)
}