/*
 * CatPluginValidator.kt
 *
 * Created on 2026-04-10
 *
 * Copyright (C) 2026 Volkswagen AG, All rights reserved.
 */

package com.simon.cat

import com.intellij.openapi.diagnostic.Logger

/**
 * Simple validator to check plugin structure and dependencies
 */
object CatPluginValidator {
    private val logger = Logger.getInstance(CatPluginValidator::class.java)

    fun validate(): Boolean {
        logger.info("Validating AI Cat Plugin structure...")

        // Check required files exist
        val requiredFiles = listOf(
            "src/main/resources/META-INF/plugin.xml",
            "src/main/kotlin/com/simon/cat/settings/CatSettingsState.kt",
            "src/main/kotlin/com/simon/cat/ui/CatComponent.kt",
            "src/main/kotlin/com/simon/cat/services/AIService.kt"
                                  )

        var allValid = true
        for (filePath in requiredFiles) {
            if (javaClass.classLoader.getResource(filePath) == null) {
                logger.warn("Missing required file: $filePath")
                allValid = false
            }
        }

        if (allValid) {
            logger.info("✅ AI Cat Plugin validation passed!")
        } else {
            logger.error("❌ AI Cat Plugin validation failed!")
        }

        return allValid
    }
}
