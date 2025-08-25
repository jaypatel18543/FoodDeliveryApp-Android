plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.fooddeliveryapk"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.fooddeliveryapk"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // ABI Filters
//        ndk {
//            abiFilters "armeabi-v7a", "arm64-v8a", "x86", "x86_64"
//        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation (enforcedPlatform("com.google.firebase:firebase-bom:33.3.0"))

    // Firebase libraries
//    implementation ("com.google.firebase:firebase-core")
    implementation ("com.google.firebase:firebase-firestore")
    implementation ("com.google.firebase:firebase-database")
    implementation ("com.google.firebase:firebase-storage")
    implementation ("com.google.firebase:firebase-auth")
    implementation ("androidx.fragment:fragment:1.5.7")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation ("androidx.appcompat:appcompat:1.7.0")
    implementation ("androidx.core:core:1.13.1")
    implementation ("com.theartofdev.edmodo:android-image-cropper:2.8.0")
    implementation ("com.squareup.picasso:picasso:2.5.2")
    implementation ("de.hdodenhof:circleimageview:3.1.0")
//    implementation("com.squareup.picasso:picasso:2.71828")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.hbb20:ccp:2.7.3")
    implementation ("com.google.android.gms:play-services-tasks:18.2.0")
    implementation("com.github.bumptech.glide:glide:4.14.2")
    implementation ("com.github.corouteam:GlideToVectorYou:2.0.0")
    implementation(libs.firebase.messaging)
    annotationProcessor("com.github.bumptech.glide:compiler:4.14.2")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
