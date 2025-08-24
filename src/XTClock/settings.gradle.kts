/*
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
    }
}

rootProject.name = "study1"
include(":app")


*/

pluginManagement {
    repositories {

        maven { url = uri("https://repo.huaweicloud.com/repository/maven/") }

        maven { url = uri("https://mirrors.163.com/maven/repository/maven-central") }
        maven { url = uri("https://mirrors.163.com/maven/repository/maven-public") }

        maven { url = uri("https://maven.aliyun.com/repository/google") }
        maven { url = uri("https://maven.aliyun.com/repository/central") }


        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven { url = uri("https://repo.huaweicloud.com/repository/maven/") }
        maven { url = uri("https://mirrors.163.com/maven/repository/maven-central") }
        maven { url = uri("https://mirrors.163.com/maven/repository/maven-public") }

        maven { url = uri("https://maven.aliyun.com/repository/google") }
        maven { url = uri("https://maven.aliyun.com/repository/central") }



        google()
        mavenCentral()
    }
}

rootProject.name = "XTClock"
include(":app")