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
        maven { url = uri("https://jitpack.io") }
        jcenter(){
            content{
                includeModule("com.theartofdev.edmodo","android-image-cropper")
            }
    }
    }

dependencyResolutionManagement {
//    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        jcenter() {
            content {
                includeModule("com.theartofdev.edmodo", "android-image-cropper")
            }
        }
    }
}
    }


rootProject.name = "Fooddeliveryapk"
include(":app")
