/*
 * ToggleCatAction.kt
 *
 * Created on 2026-04-10
 *
 * Copyright (C) 2026 Volkswagen AG, All rights reserved.
 */

package com.simon.cat.actions

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.simon.cat.ui.CatComponent

class ToggleCatAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val catComponent = ApplicationManager.getApplication().getService(CatComponent::class.java)

        if (catComponent != null) {
            // Try to show cat component if it has visibility methods
            try {
                val method = catComponent.javaClass.getMethod("toggleVisibility")
                method.invoke(catComponent)
            } catch (e: Exception) {
                // Fallback: create and show cat window directly
                javax.swing.SwingUtilities.invokeLater {
                    try {
                        val frameField = catComponent.javaClass.getDeclaredField("catFrame")
                        frameField.isAccessible = true
                        val catFrame = frameField.get(catComponent) as? javax.swing.JDialog
                        if (catFrame != null) {
                            catFrame.isVisible = !catFrame.isVisible
                        }
                    } catch (ex: Exception) {
                        println("Could not toggle cat visibility: ${ex.message}")
                    }
                }
            }
        }
    }

    override fun update(e: AnActionEvent) {
        val project = e.project
        e.presentation.isEnabledAndVisible = project != null
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }
}
