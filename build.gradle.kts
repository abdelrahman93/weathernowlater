// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
}

// In your project-level build.gradle
buildscript {
    dependencies {
        // your existing dependencies
        classpath("com.squareup:javapoet:1.13.0")
    }
}

// Also add this configuration block
configurations.all {
    resolutionStrategy {
        force("com.squareup:javapoet:1.13.0")
    }
}