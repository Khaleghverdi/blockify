@file:Suppress("UnstableApiUsage")

include(":library-components")


enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven("https://repo.eclipse.org/content/groups/releases/")
        maven("https://maven.aliyun.com/nexus/content/groups/public/")
    }
}
rootProject.name = "Blockify"



include(
    ":bom",
    ":app",
    ":feature:components",
    ":feature:android",
    ":feature:compose"
)
