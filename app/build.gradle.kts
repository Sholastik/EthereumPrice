plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

val versionMajor = 1
val versionMinor = 0
val versionPatch = 0


android {
    namespace = "com.vyacheslavivanov.ethereumprice"

    compileSdk = 32

    defaultConfig {
        applicationId = "com.vyacheslavivanov.ethereumprice"

        minSdk = 23
        targetSdk = 32

        versionCode = versionMajor * 10000 + versionMinor * 100 + versionPatch
        versionName = "${versionMajor}.${versionMinor}.${versionPatch}"

        setProperty("archivesBaseName", "$applicationId-$versionName")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName("debug") {
            ext["alwaysUpdateBuildId"] = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
        compose = true
    }

    kapt {
        correctErrorTypes = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0-rc01"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.4.2")
    implementation("com.google.android.material:material:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.core:core-splashscreen:1.0.0")

    implementation("androidx.navigation:navigation-fragment-ktx:2.5.1")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.1")

    implementation("androidx.activity:activity-compose:1.5.1")
    implementation("androidx.compose.material:material:1.2.0")
    implementation("androidx.compose.animation:animation:1.2.0")
    implementation("androidx.compose.ui:ui-tooling:1.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.2.0")

    implementation("com.google.dagger:hilt-android:2.43")
    kapt("com.google.dagger:hilt-android-compiler:2.43")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.13.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.10")

    implementation("com.jakewharton.timber:timber:5.0.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}