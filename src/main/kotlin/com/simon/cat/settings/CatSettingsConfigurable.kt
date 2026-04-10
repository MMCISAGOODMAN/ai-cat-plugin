/*
 * CatSettingsConfigurable.kt
 *
 * Created on 2026-04-10
 *
 * Copyright (C) 2026 Volkswagen AG, All rights reserved.
 */

package com.simon.cat.settings

import com.intellij.openapi.options.Configurable
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPasswordField
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import java.awt.BorderLayout
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JCheckBox
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JPasswordField
import javax.swing.JSpinner
import javax.swing.SpinnerNumberModel

class CatSettingsConfigurable : Configurable {
    private var apiKeyField: JPasswordField? = null
    private var endpointField: JBTextField? = null
    private var modelField: JBTextField? = null
    private var enabledCheckBox: JCheckBox? = null
    private var autoHideCheckBox: JCheckBox? = null
    private var quotesCheckBox: JCheckBox? = null
    private var newsCheckBox: JCheckBox? = null
    private var intervalSpinner: JSpinner? = null
    private var historySizeSpinner: JSpinner? = null

    override fun getDisplayName(): String = "AI Cat Settings"

    override fun createComponent(): JComponent {
        val panel = FormBuilder.createFormBuilder()

        // Initialize components
        endpointField = JBTextField("", 30)
        modelField = JBTextField("", 20)
        enabledCheckBox = JCheckBox("Enable AI Cat").apply { isSelected = true }
        autoHideCheckBox = JCheckBox("Auto-hide when inactive")
        quotesCheckBox = JCheckBox("Show motivational quotes")
        newsCheckBox = JCheckBox("Show AI News")
        val settings = CatSettingsState.getInstance()
        // Ensure default is 1 minute if no saved setting exists
        val defaultInterval = if (settings.quoteIntervalMinutes <= 0) 1 else settings.quoteIntervalMinutes
        intervalSpinner = JSpinner(SpinnerNumberModel(defaultInterval, 1, 60, 1))
        historySizeSpinner = JSpinner(SpinnerNumberModel(10, 5, 50, 1))

        // API Key Section
        panel.addLabeledComponent(JBLabel("API Key:"), createApiKeyPanel(), 1, false)
        panel.addSeparator()

        // AI Configuration Section
        panel.addLabeledComponent(JBLabel("AI Endpoint:"), endpointField!!, 1, false)
        panel.addLabeledComponent(JBLabel("Model Name:"), modelField!!, 1, false)

        // Plugin Features Section
        panel.addSeparator()
        panel.addLabeledComponent(
            JBLabel("Plugin Features:"),
            JPanel().apply {
                layout = BoxLayout(this, BoxLayout.Y_AXIS)
                add(enabledCheckBox)
                add(autoHideCheckBox)
                add(quotesCheckBox)
                add(newsCheckBox)
            },
            1,
            false
                                 )

        // Quote Interval
        panel.addLabeledComponent(
            JBLabel("Quote interval (minutes):"),
            intervalSpinner!!,
            1,
            false
                                 )


        // Chat History Size
        panel.addLabeledComponent(
            JBLabel("Chat history size:"),
            historySizeSpinner!!,
            1,
            false
                                 )

        // Save Button
        val saveButton = JButton("Save Settings")
        saveButton.addActionListener {
            // Save settings manually
            apply()
            // Settings are automatically saved via PersistentStateComponent
        }

        panel.addComponent(saveButton)

        return panel.panel
    }

    private fun createApiKeyPanel(): JPanel {
        val panel = JPanel(BorderLayout())
        apiKeyField = JBPasswordField()
        panel.add(apiKeyField, BorderLayout.CENTER)
        return panel
    }

    override fun isModified(): Boolean {
        val settings = CatSettingsState.getInstance()
        return apiKeyField?.text != settings.aiApiKey ||
               endpointField?.text != settings.aiEndpoint ||
               modelField?.text != settings.selectedModel ||
               enabledCheckBox?.isSelected != settings.catEnabled ||
               autoHideCheckBox?.isSelected != settings.autoHideEnabled ||
               quotesCheckBox?.isSelected != settings.motivationalQuotesEnabled ||
               newsCheckBox?.isSelected != settings.aiNewsEnabled
    }

    override fun apply() {
        val settings = CatSettingsState.getInstance()
        settings.aiApiKey = apiKeyField?.text ?: ""
        settings.aiEndpoint = endpointField?.text ?: ""
        settings.selectedModel = modelField?.text ?: "gpt-3.5-turbo"
        settings.catEnabled = enabledCheckBox?.isSelected ?: true
        settings.autoHideEnabled = autoHideCheckBox?.isSelected ?: true
        settings.motivationalQuotesEnabled = quotesCheckBox?.isSelected ?: true
        settings.aiNewsEnabled = newsCheckBox?.isSelected ?: true
        settings.quoteIntervalMinutes = (intervalSpinner?.value as Number).toInt()
        settings.chatHistorySize = (historySizeSpinner?.value as Number).toInt()
    }

    override fun reset() {
        val settings = CatSettingsState.getInstance()
        apiKeyField?.text = settings.aiApiKey
        endpointField?.text = settings.aiEndpoint
        modelField?.text = settings.selectedModel
        enabledCheckBox?.isSelected = settings.catEnabled
        autoHideCheckBox?.isSelected = settings.autoHideEnabled
        quotesCheckBox?.isSelected = settings.motivationalQuotesEnabled
        newsCheckBox?.isSelected = settings.aiNewsEnabled
        intervalSpinner?.value = settings.quoteIntervalMinutes
        historySizeSpinner?.value = settings.chatHistorySize
    }

    override fun disposeUIResources() {
        // Clean up resources if needed
    }
}
