plugins {
    id("dev.kikugie.stonecutter")
}
stonecutter active "1.18.2-fabric"

allprojects {
    repositories {
        mavenCentral()
        mavenLocal()
        maven("https://maven.neoforged.net/releases")
        maven("https://maven.fabricmc.net/")
    }
}