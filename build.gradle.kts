// Top-level build file where you can add configuration options common to all sub-projects/modules.
val libraryGroup by rootProject.extra { "com.github.pedroSG94" }
val vCode by rootProject.extra { 236 }
val vName by rootProject.extra { "2.3.6" }
val coroutinesVersion by rootProject.extra { "1.7.3" }
val junitVersion by rootProject.extra { "4.13.2" }
val mockitoVersion by rootProject.extra { "5.2.1" }

plugins {
  id("com.android.application") version "8.3.1" apply false
  id("org.jetbrains.kotlin.android") version "1.9.22" apply false
  id("org.jetbrains.dokka") version "1.9.10" apply true
}

tasks.register("clean") {
  delete(rootProject.buildDir)
}

tasks.dokkaHtmlMultiModule.configure {
  outputDirectory.set(File("docs"))
}