pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven ("https://jitpack.io")
    }
}

rootProject.name = "CourcesDemo"
include(":app")
include(":common:domain")
include(":common:data")
include(":common:presentation")
include(":common:di")
include(":feature:auth")
include(":feature:main")
include(":feature:home")
include(":feature:favorites")
include(":feature:profile")
