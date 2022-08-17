// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    extra.apply {
        set("kotlin_version", "1.5.10")
        set("retrofit_version", "2.9.0")
        set("glide_version", "4.11.0")
        set("nav_version", "2.5.1")
        set("lifecycle_version", "2.3.0")
    }
    repositories {
        google()
        mavenCentral()
        jcenter()
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:4.0.2")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.38.1")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:${project.extra["kotlin_version"]}")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven {url = uri("https://jitpack.io") }
        mavenCentral()
    }
}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}