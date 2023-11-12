plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "me.levitate.auctions"
version = "1.0.0"

repositories {
    mavenCentral()

    maven("https://repo.mattstudios.me/artifactory/public/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.aikar.co/content/groups/aikar/")
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.11.3")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7.1")

    compileOnly("org.projectlombok:lombok:1.18.28")
    annotationProcessor("org.projectlombok:lombok:1.18.28")

    implementation("dev.triumphteam:triumph-gui:3.1.2")
    implementation("co.aikar:acf-paper:0.5.1-SNAPSHOT")
}

tasks.shadowJar {
    relocate("dev.triumphteam.gui", "me.levitate.gui")
}