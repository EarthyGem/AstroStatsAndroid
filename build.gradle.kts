// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // Keep your existing plugin aliases
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false

    // Add Firebase plugin
    id("com.google.gms.google-services") version "4.4.0" apply false
}