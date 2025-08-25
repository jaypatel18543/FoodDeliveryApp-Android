plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.googleGmsGoogleServices) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
//        maven { url= ("https://jitpack.io") }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.2.2")
        classpath("com.google.gms:google-services:4.3.15")
        // Add any other classpath dependencies here
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
//        maven { url "https://jitpack.io" }
//        maven { url= ("https://jitpack.io") }
    }
}
