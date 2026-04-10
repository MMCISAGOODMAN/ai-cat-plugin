/*
 * build.gradle.kts
 *
 * Created on 2026-04-10
 *
 * Copyright (C) 2026 Volkswagen AG, All rights reserved.
 */

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.1.0"
    id("org.jetbrains.intellij.platform") version "2.5.0"
}

group = "com.simon.ma"
version = "1.0.0"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

// Configure IntelliJ Platform Gradle Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin.html
dependencies {
    intellijPlatform {
        create("IC", "2025.1")
        testFramework(org.jetbrains.intellij.platform.gradle.TestFrameworkType.Platform)

        implementation("com.squareup.okhttp3:okhttp:4.12.0")
        implementation("com.google.code.gson:gson:2.10.1")
        implementation("org.slf4j:slf4j-api:2.0.9")
        implementation("ch.qos.logback:logback-classic:1.4.11")
    }
}

intellijPlatform {
    pluginConfiguration {
        changeNotes.set(provider {
            """
            ## [1.0.0] - 2026-04-08

            ### Initial Release: AI Cat Assistant

            #### 🐱 Core Features
            - **Floating Cat Interface**: A cute, interactive cat that appears anywhere in your IDE
            - **AI Chat Integration**: Seamless conversations with your configured AI models
            - **Motivational Quotes**: Daily inspiration delivered by your virtual feline friend
            - **Smart Auto-hide**: The cat automatically hides when not in use
            - **Configurable Settings**: Easy setup for multiple AI providers

            #### 🎨 User Experience
            - Professional, polished interface design
            - Smooth animations and transitions
            - Keyboard shortcuts for quick access
            - Contextual menu integration

            #### ⚙️ Technical Features
            - Lightweight and efficient performance
            - Robust error handling and recovery
            - Persistent configuration storage
            - Cross-platform compatibility
            """.trimIndent()
        })
    }

    // Disable buildSearchableOptions to avoid locale-related errors
    buildSearchableOptions = false
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "21"
        targetCompatibility = "21"
    }

    patchPluginXml {
        sinceBuild.set("243")
        untilBuild.set("253.*")
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
    }
}
