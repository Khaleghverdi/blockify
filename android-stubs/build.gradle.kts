plugins {
    id("java-library")
}

dependencies {
    compileOnlyApi(files("libs/android.jar"))

    compileOnly("androidx.annotation:annotation:1.6.0")
}

java {
    sourceCompatibility = ModulesConfig.sourceCompatibility
    targetCompatibility = ModulesConfig.targetCompatibility
}
