@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.bittelasia.service_xmpp"
    compileSdk = 34

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(projects.coreDatastore)

    implementation(libs.smack.tcp)
    api(libs.smack.android.extensions)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    configurations {
        all {
            exclude(group = "xpp3", module = "xpp3")
        }
    }
}