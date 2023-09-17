// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    val kotlin_version = "1.8.0"
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:8.0.2")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.44")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}
plugins {
    id("com.android.application") version "8.1.0" apply false
    id ("com.android.library") version "8.0.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
}