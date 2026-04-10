/*
 * CatToolWindowFactory.kt
 *
 * Created on 2026-04-10
 *
 * Copyright (C) 2026 Volkswagen AG, All rights reserved.
 */

package com.simon.cat.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import java.awt.BorderLayout
import javax.swing.JPanel

class CatToolWindowFactory : ToolWindowFactory {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val catComponent = CatComponent()
        val contentFactory = ContentFactory.getInstance()

        // Create the complete chat interface
        val mainPanel = JPanel(BorderLayout())

        // Create header panel
        val headerPanel = catComponent.createHeaderPanel()
        mainPanel.add(headerPanel, BorderLayout.NORTH)

        // Create chat area
        val chatArea = catComponent.createChatArea()
        mainPanel.add(chatArea, BorderLayout.CENTER)

        // Create input panel
        val inputPanel = catComponent.createInputPanel()
        mainPanel.add(inputPanel, BorderLayout.SOUTH)

        // Add the complete panel to the tool window
        val content = contentFactory.createContent(mainPanel, "🐱 AI Chat", false)
        toolWindow.contentManager.addContent(content)

        // Make sure the component is initialized for this project
        catComponent.projectOpened()
    }

    override fun shouldBeAvailable(project: Project): Boolean {
        return true
    }
}
