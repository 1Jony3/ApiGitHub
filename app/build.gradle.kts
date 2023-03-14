plugins {
    id ("com.android.application")
    id ("kotlin-android")
    kotlin ("kapt")
    id ("dagger.hilt.android.plugin")
}

android {
    compileSdkVersion (30)
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "dev.icerock.education.apigithub"
        minSdkVersion (21)
        targetSdkVersion (30)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled  = false
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
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    val kotlinVersion = rootProject.extra["kotlin_version"]
    implementation ("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    implementation ("androidx.core:core-ktx:1.5.0")
    implementation ("androidx.appcompat:appcompat:1.3.0")
    implementation ("com.google.android.material:material:1.3.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.3")
    testImplementation ("junit:junit:4.+")
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")

    val retrofitVersion = rootProject.extra["retrofit_version"]
    val glideVersion = rootProject.extra["glide_version"]
    implementation ("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation ("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation ("com.github.bumptech.glide:glide:$glideVersion")
    annotationProcessor ("com.github.bumptech.glide:compiler:$glideVersion")

    implementation ("androidx.fragment:fragment-ktx:1.4.1")
    implementation ("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation ("androidx.navigation:navigation-ui-ktx:2.3.5")
    implementation ("androidx.legacy:legacy-support-v4:1.0.0")

    implementation ("androidx.recyclerview:recyclerview:1.1.0")
    implementation ("androidx.recyclerview:recyclerview:1.2.0-alpha04")
    implementation ("androidx.constraintlayout:constraintlayout:2.0.4")

    val lifecycleVersion = rootProject.extra["lifecycle_version"]
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")

    implementation ("com.squareup.okhttp3:okhttp:3.9.1")
    implementation("com.google.dagger:hilt-android:2.38.1")
    kapt("com.google.dagger:hilt-android-compiler:2.38.1")

    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}