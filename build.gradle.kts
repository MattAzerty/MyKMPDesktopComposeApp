import org.jetbrains.compose.desktop.application.dsl.TargetFormat

//https://github.com/JetBrains/compose-multiplatform-desktop-template#before-you-start
plugins {
    kotlin("jvm") version "1.9.10"
    kotlin("plugin.serialization") version "1.9.0"
    id("org.jetbrains.compose")
    id("com.google.devtools.ksp") version  "1.9.10-1.0.13"
    id("app.cash.sqldelight") version "2.0.0"
}

group = "com.mattazerty"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

sqldelight {
    databases {
        create("MyDatabase") {
            packageName.set("com.mattazerty.db")
        }
    }
}

// KSP - To use generated sources
sourceSets.main {
    java.srcDirs("build/generated/ksp/main/kotlin")
}

dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.6.4")

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


// Koin https://insert-koin.io/docs/quickstart/kotlin
    implementation("io.insert-koin:koin-core:3.5.0")
    implementation("io.insert-koin:koin-annotations:1.3.0")
    ksp("io.insert-koin:koin-ksp-compiler:1.3.0")

//ICONs https://github.com/DevSrSouza/compose-icons/blob/master/font-awesome/DOCUMENTATION.md
    implementation("br.com.devsrsouza.compose.icons:font-awesome:1.1.0") // use for custom icons -> https://github.com/DevSrSouza/svg-to-compose

//JSON https://github.com/Kotlin/kotlinx.serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

//SQLITE https://cashapp.github.io/sqldelight/2.0.0/jvm_sqlite/
    implementation("app.cash.sqldelight:sqlite-driver:2.0.0")
    implementation("app.cash.sqldelight:coroutines-extensions:2.0.0")

}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {//https://github.com/JetBrains/compose-multiplatform/tree/master/tutorials/Native_distributions_and_local_execution

            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "MyDesktopApp"
            packageVersion = "1.0.0"

            modules("java.sql") //needed for sqldelight use
            val iconsRoot = project.file("src/main/resources")

            windows {
                iconFile.set(iconsRoot.resolve("icon_quiz_windows.ico"))
                menuGroup = "MyDesktopApp"
                // see https://wixtoolset.org/documentation/manual/v3/howtos/general/generate_guids.html
                upgradeUuid = "432782c7-da73-421b-b362-ed9f11355312"
                shortcut = true
                perUserInstall = true //trying to avoid admin right privilege folders -> https://github.com/JetBrains/compose-multiplatform/issues/2625 else use https://conveyor.hydraulic.dev/11.4/configs/windows/#requesting-administrator-access
            }
            linux {
                iconFile.set(iconsRoot.resolve("icon_quiz_linux.png"))
            }
            macOS {
                iconFile.set(iconsRoot.resolve("icon_quiz_mac.icns"))
            }
        }
    }
}
