buildscript{
    dependencies{
        //classpath(libs.google.services)
        classpath("com.google.gms:google-services:4.3.10")
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.googleGmsGoogleServices) apply false
}