/*
 * CatDemo.kt
 *
 * Created on 2026-04-10
 *
 * Copyright (C) 2026 Volkswagen AG, All rights reserved.
 */

package com.simon.cat

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.Logger
import javax.swing.JOptionPane

/**
 * Demo class to showcase AI Cat plugin capabilities
 */
class CatDemo {
    private val logger = Logger.getInstance(CatDemo::class.java)

    fun showWelcomeMessage() {
        logger.info("🐱 Welcome to AI Cat Plugin Demo!")
        logger.info("✨ Features:")
        logger.info("   • Floating interactive cat interface")
        logger.info("   • AI-powered chat assistant")
        logger.info("   • Motivational quotes and wisdom")
        logger.info("   • Smart auto-hide functionality")
        logger.info("   • Configurable settings")
        logger.info("   • Multi-language support")
        logger.info("   • Draggable and customizable UI")
        logger.info("")
        logger.info("🚀 Ready to bring joy to your coding sessions!")

        // Show demo dialog
        ApplicationManager.getApplication().invokeLater {
            JOptionPane.showMessageDialog(
                null,
                """
                🐱 AI Cat Plugin Demo Complete! 🎉

                Your cute AI companion is ready to:
                • Answer programming questions
                • Share motivational wisdom
                • Keep you company while coding
                • Provide smart assistance

                To get started:
                1. Toggle the cat with Ctrl+Alt+K
                2. Configure AI settings in Tools menu
                3. Start chatting with your virtual feline friend!

                Enjoy your enhanced coding experience! 😸
                """.trimIndent(),
                "AI Cat Plugin - Demo Complete",
                JOptionPane.INFORMATION_MESSAGE
                                         )
        }
    }
}
