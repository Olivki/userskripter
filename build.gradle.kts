plugins {
    id("me.him188.maven-central-publish") version "1.0.0-dev-3"
    kotlin("multiplatform") version "1.7.21"
}

group = "net.ormr.userskripter"
description = "Kotlin/JS library for easily creating userscripts"
version = "0.2.1"

repositories {
    mavenLocal()
    mavenCentral()
}

mavenCentralPublish {
    useCentralS01()
    singleDevGithubProject("userskripter", "userskripter")
    licenseApacheV2()
}

kotlin {
    explicitApi()

    js(IR) {
        binaries.library()
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled = true
                }
            }
        }
    }

    sourceSets {
        val jsMain by getting {
            languageSettings {
                optIn("kotlin.contracts.ExperimentalContracts")
            }

            dependencies {
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}