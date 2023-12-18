
/*
 *   Copyright © 2017-2023 PSPDFKit GmbH. All rights reserved.
 *
 *   The PSPDFKit Sample applications are licensed with a modified BSD license.
 *   Please see License for details. This notice may not be removed from this file.
 */

buildscript {
    repositories {
        mavenCentral()
        google()
    }
    dependencies {
        classpath(libs.androidGradle.core)
        classpath(libs.kotlin.gradle)
    }
}
plugins {
    id("com.google.devtools.ksp") version "1.9.20-1.0.14" apply false
}

allprojects {
    repositories {
        mavenCentral()
        google()

        // The PSPDFKit library is loaded from the PSPDFKit Maven repository, added by this configuration.
        maven(url = "https://customers.pspdfkit.com/maven/")
    }
}
