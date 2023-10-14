import org.jetbrains.compose.desktop.application.dsl.TargetFormat

//https://github.com/JetBrains/compose-multiplatform-desktop-template#before-you-start
plugins {
    kotlin("jvm") version "1.9.0"
    id("org.jetbrains.compose") version "1.5.3"
    id("com.google.devtools.ksp") version  "1.9.10-1.0.13"
}

group = "com.mattazerty"
version = "1.0-SNAPSHOT"


repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

// KSP - To use generated sources
sourceSets.main {
    java.srcDirs("build/generated/ksp/main/kotlin")
}
/*kotlin {
    //https://java73.medium.com/simple-way-to-use-common-resources-in-kotlin-multi-platform-project-95a3f886c6d9
    //https://developer.squareup.com/blog/kotlin-multiplatform-shared-test-resources/
    sourceSets {
        named("main") {
            resources.srcDirs("resources")
            // other parts
        }
    }
}*/


dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)

//Voyager for Navigation (Native one is Android only for now (10/2023): https://github.com/JetBrains/compose-multiplatform/tree/master/tutorials/Navigation)

    val voyagerVersion = "1.0.0-rc07"//SETUP: https://voyager.adriel.cafe/setup


    // Navigator
    implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")
    // BottomSheetNavigator
    implementation("cafe.adriel.voyager:voyager-bottom-sheet-navigator:$voyagerVersion")
    // TabNavigator
    implementation("cafe.adriel.voyager:voyager-tab-navigator:$voyagerVersion")
    // Transitions
    implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")
    // Koin integration
    implementation("cafe.adriel.voyager:voyager-koin:$voyagerVersion")


// Koin
    implementation("io.insert-koin:koin-core:3.5.0")
    implementation("io.insert-koin:koin-annotations:1.3.0")
    ksp("io.insert-koin:koin-ksp-compiler:1.3.0")


}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {//https://github.com/JetBrains/compose-multiplatform/tree/master/tutorials/Native_distributions_and_local_execution
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "MyDesktopApp"
            packageVersion = "1.0.0"
        }
    }
}
