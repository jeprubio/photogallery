plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 30
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "com.rumosoft.photogallery"
        minSdk = 21
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.rumosoft.photogallery.MyTestRunner"

        buildConfigField("String", "BASE_URL", "\"https://jsonplaceholder.typicode.com/\"")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        dataBinding = true
    }
    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
    }
}

dependencies {
    implementation(project(":feature-images"))

    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:${Versions.navigation_version}")
    implementation("androidx.navigation:navigation-ui-ktx:${Versions.navigation_version}")

    // Timber (Logging lib: https://github.com/JakeWharton/timber)
    implementation("com.jakewharton.timber:timber:${Versions.timber_version}")

    // Gson + Retrofit (to perform API calls and parse the response)
    implementation("com.google.code.gson:gson:${Versions.gson_version}")
    implementation("com.squareup.retrofit2:retrofit:${Versions.retrofit_version}")
    implementation("com.squareup.retrofit2:converter-gson:${Versions.retrofit_version}")
    implementation("com.squareup.okio:okio:2.10.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.0")

    // Dagger-Hilt (dependency injection)
    implementation("com.google.dagger:hilt-android:${Versions.hilt_version}")
    kapt("com.google.dagger:hilt-android-compiler:${Versions.hilt_version}")

    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    // Room (database as cache)
    implementation("androidx.room:room-runtime:${Versions.room_version}")
    implementation("androidx.room:room-ktx:${Versions.room_version}")
    kapt("androidx.room:room-compiler:${Versions.room_version}")

    testImplementation("org.junit.jupiter:junit-jupiter:${Versions.junit5Version}")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:${Versions.junit5Version}")
    testImplementation("androidx.arch.core:core-testing:2.1.0")

    androidTestImplementation("androidx.test.ext:junit-ktx:${Versions.junit_version}")
    androidTestImplementation("androidx.test.ext:junit:${Versions.junit_version}")
    androidTestImplementation("androidx.test:core-ktx:1.4.0")
    androidTestImplementation("androidx.test:rules:1.4.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.4.0")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutine_version}")
    androidTestImplementation("com.google.dagger:hilt-android-testing:${Versions.hilt_version}")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:${Versions.hilt_version}")
}
