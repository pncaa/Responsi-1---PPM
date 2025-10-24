// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false

    // Tambahkan plugin kapt (karena kita pakai Glide)
    id("org.jetbrains.kotlin.kapt") version "1.9.25" apply false
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}
