/*
 * CatSettingsState.kt
 *
 * Created on 2026-04-10
 *
 * Copyright (C) 2026 Volkswagen AG, All rights reserved.
 */

package com.simon.cat.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@State(
    name = "CatPluginSettings",
    storages = [Storage("catPlugin.xml")]
      )
class CatSettingsState : PersistentStateComponent<CatSettingsState.State> {
    data class State(
        var aiApiKey: String = "",
        var aiEndpoint: String = "",
        var selectedModel: String = "gpt-3.5-turbo",
        var catEnabled: Boolean = true,
        var autoHideEnabled: Boolean = true,
        var motivationalQuotesEnabled: Boolean = true,
        var aiNewsEnabled: Boolean = true,
        var lastQuoteTime: Long = 0L,
        var quoteIntervalMinutes: Int = 1,
        var chatHistorySize: Int = 10
                    )

    private var myState = State()

    override fun getState(): State = myState
    override fun loadState(state: State) {
        myState = state
    }

    companion object {
        fun getInstance(): CatSettingsState =
            ApplicationManager.getApplication().getService(CatSettingsState::class.java)
    }

    var aiApiKey: String
        get() = myState.aiApiKey
        set(value) {
            myState.aiApiKey = value
        }

    var aiEndpoint: String
        get() = myState.aiEndpoint
        set(value) {
            myState.aiEndpoint = value
        }

    var selectedModel: String
        get() = myState.selectedModel
        set(value) {
            myState.selectedModel = value
        }

    var catEnabled: Boolean
        get() = myState.catEnabled
        set(value) {
            myState.catEnabled = value
        }

    var autoHideEnabled: Boolean
        get() = myState.autoHideEnabled
        set(value) {
            myState.autoHideEnabled = value
        }

    var motivationalQuotesEnabled: Boolean
        get() = myState.motivationalQuotesEnabled
        set(value) {
            myState.motivationalQuotesEnabled = value
        }

    var aiNewsEnabled: Boolean
        get() = myState.aiNewsEnabled
        set(value) {
            myState.aiNewsEnabled = value
        }

    var lastQuoteTime: Long
        get() = myState.lastQuoteTime
        set(value) {
            myState.lastQuoteTime = value
        }

    var quoteIntervalMinutes: Int
        get() = myState.quoteIntervalMinutes
        set(value) {
            myState.quoteIntervalMinutes = value
        }

    var chatHistorySize: Int
        get() = myState.chatHistorySize
        set(value) {
            myState.chatHistorySize = value
        }
}
