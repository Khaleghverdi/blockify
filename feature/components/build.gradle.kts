plugins {
    id("java-library")
    kotlin("jvm")
    id("maven-publish")
    publish
}

version = AppConfig.AppCoordinates.VERSION_CODE

dependencies {
    testImplementation(libs.junit)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    withSourcesJar()
    withJavadocJar()
}
