import org.gradle.api.JavaVersion

object Global {
    const val targetSdk = 35
    const val compileSdk = 35
    const val minSdk = 24
    val sourceCompatibility = JavaVersion.VERSION_11
    val targetCompatibility = JavaVersion.VERSION_11
}