
// required for FTP task with SSL session reuse enabled
// must be set here, before Gradle instantiates SSL-related functionality
// by cloning the git repository
System.setProperty("jdk.tls.useExtendedMasterSecret", "false")

pluginManagement {
    val subktDir = providers.gradleProperty("subktDir").orNull
    if (subktDir != null) {
        includeBuild(subktDir)
    } else {
        val subktVersion = providers.gradleProperty("subktVersion").get()
        resolutionStrategy {
            eachPlugin {
                if (requested.id.id == "myaa.subkt") {
                    useModule("myaa:subkt:$subktVersion")
                }
            }
        }
    }
}

if (!providers.gradleProperty("subktDir").isPresent) {
    val subktRepo = providers.gradleProperty("subktRepo").get()
    sourceControl {
        gitRepository(uri(subktRepo)) {
            producesModule("myaa:subkt")
        }
    }
}

buildscript {
    repositories {
        mavenCentral()
    }
}
