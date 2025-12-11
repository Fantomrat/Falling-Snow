pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()

        // Modstitch
        maven("https://maven.isxander.dev/releases/")

        // Loom platform
        maven("https://maven.fabricmc.net/")

        // MDG platform
        maven("https://maven.neoforged.net/releases/")

        // Stonecutter
        maven("https://maven.kikugie.dev/releases")
        maven("https://maven.kikugie.dev/snapshots")

        // Modstitch
        maven("https://maven.isxander.dev/releases")
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.7.11"
}

stonecutter {
    kotlinController = true
    centralScript = "build.gradle.kts"

    create(rootProject) {

        version("1.20-1.21.1-fabric", "1.20.1")
        version("1.21.2-1.21.3-fabric", "1.21.3")
        version("1.21.4-fabric", "1.21.4")
        version("1.21.5-fabric", "1.21.5")
        version("1.21.6-1.21.8-fabric", "1.21.8")
        version("1.21.9-1.21.11-fabric", "1.21.10")
        version("1.21.1-neoforge", "1.21.1")
        version("1.20.1-forge", "1.20.1")


        // This is the default target.
        // https://stonecutter.kikugie.dev/stonecutter/guide/setup#settings-settings-gradle-kts
        vcsVersion = "1.20-1.21.1-fabric"
    }
}

rootProject.name = "Falling Snow"

