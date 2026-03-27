pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "nexfin-wallet"

include(":backend")
include(":frontend")

project(":backend").projectDir = file("Backend")
project(":frontend").projectDir = file("Frontend")
