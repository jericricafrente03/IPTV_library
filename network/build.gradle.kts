@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.bittelasia.network"
    compileSdk = 34
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(projects.coreDatastore)
    implementation(projects.serviceXmpp)

    implementation(libs.bundles.retrofit.lib)

    implementation(libs.bundles.room.lib)
    ksp(libs.room.compiler)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}