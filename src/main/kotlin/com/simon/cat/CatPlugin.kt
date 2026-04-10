/*
 * CatPlugin.kt
 *
 * Created on 2026-04-10
 *
 * Copyright (C) 2026 Volkswagen AG, All rights reserved.
 */

package com.simon.cat

import com.intellij.openapi.components.Service
import com.intellij.openapi.diagnostic.Logger

/**
 * Main plugin entry point
 */
@Service
class CatPlugin {
    private val logger = Logger.getInstance(CatPlugin::class.java)

    init {
        // Initialize the plugin - show cat when ready
        println("🐱 AI Cat Plugin initializing...")
        logger.info("AI Cat Plugin initialized")

        // Initialize the plugin - components will show when project opens
    }
}
