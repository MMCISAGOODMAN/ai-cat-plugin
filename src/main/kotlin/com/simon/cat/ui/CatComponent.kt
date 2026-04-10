/*
 * CatComponent.kt
 *
 * Created on 2026-04-10
 *
 * Copyright (C) 2026 Volkswagen AG, All rights reserved.
 */

package com.simon.cat.ui

import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.WindowManager
import java.awt.BorderLayout
import java.awt.Component
import java.awt.Dimension
import java.awt.Point
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionAdapter
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JDialog
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTextArea
import javax.swing.JTextField
import javax.swing.SwingConstants
import javax.swing.Timer

class CatComponent : ProjectComponent {
    private var catFrame: JDialog? = null
    private var isVisible = false
    private var lastInteractionTime = System.currentTimeMillis()
    private val autoHideDelay = 5000L // 5 seconds

    override fun projectOpened() {
        val settings = com.simon.cat.settings.CatSettingsState.getInstance()

        if (settings.motivationalQuotesEnabled) {
            // Show first quote immediately for better UX
            javax.swing.SwingUtilities.invokeLater {
                showMotivationalQuote()
            }
        }

        // Cat window will be created when user first interacts
    }

    override fun projectClosed() {
        hideCat()
        stopQuoteScheduler()
    }

    private fun setupCat(project: Project) {
        val settings = com.simon.cat.settings.CatSettingsState.getInstance()

        if (!settings.catEnabled) return

        createCatWindow(project)

        if (settings.autoHideEnabled) {
            startAutoHideTimer()
        }

        if (settings.motivationalQuotesEnabled) {
            startQuoteScheduler(settings)
        }

        if (settings.aiNewsEnabled) {
            startAINewsScheduler(settings)
        }
    }

    private fun createCatWindow(project: Project) {
        catFrame = JDialog(WindowManager.getInstance().getFrame(project))
        catFrame?.apply {
            title = "🐱 Hello Cat Assistant"
            layout = BorderLayout()

            val mainPanel = JPanel(BorderLayout())
            mainPanel.border = javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10)

            val headerPanel = createHeaderPanel()
            mainPanel.add(headerPanel, BorderLayout.NORTH)

            val chatArea = createChatArea()
            mainPanel.add(chatArea, BorderLayout.CENTER)

            val inputPanel = createInputPanel()
            mainPanel.add(inputPanel, BorderLayout.SOUTH)

            add(mainPanel)

            setSize(600, 700)
            isResizable = false
            isAlwaysOnTop = true

            setupDraggableBehavior()

            catFrame?.addComponentListener(object : ComponentAdapter() {
                override fun componentResized(e: ComponentEvent?) {
                    chatPanel?.components?.forEach { component ->
                        if (component is JScrollPane) {
                            component.verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
                        }
                    }
                }
            })

            addMouseListener(object : MouseAdapter() {
                override fun mouseClicked(e: MouseEvent?) {
                    trackInteraction()
                }

                override fun mouseEntered(e: MouseEvent?) {
                    trackInteraction()
                }
            })

            addKeyListener(object : KeyAdapter() {
                override fun keyPressed(e: KeyEvent?) {
                    trackInteraction()
                }
            })
        }
    }

    fun createHeaderPanel(): JPanel {
        val panel = JPanel(BorderLayout())

        val catIcon = JLabel("🐱")
        catIcon.font = catIcon.font.deriveFont(java.awt.Font.PLAIN, 48f)
        catIcon.foreground = java.awt.Color(70, 130, 180)

        val greetingLabel = JLabel("Hello Cat Assistant", SwingConstants.CENTER)
        greetingLabel.font = greetingLabel.font.deriveFont(java.awt.Font.BOLD, 18f)
        greetingLabel.foreground = java.awt.Color(50, 50, 50)

        val statusLabel = JLabel("🚀 Ready for Coding!", SwingConstants.CENTER)
        statusLabel.foreground = java.awt.Color(46, 125, 50)
        statusLabel.font = statusLabel.font.deriveFont(java.awt.Font.BOLD, 14f)

        val iconPanel = JPanel()
        iconPanel.layout = BoxLayout(iconPanel, BoxLayout.Y_AXIS)
        iconPanel.add(catIcon)
        iconPanel.alignmentX = Component.CENTER_ALIGNMENT

        val textPanel = JPanel()
        textPanel.layout = BoxLayout(textPanel, BoxLayout.Y_AXIS)
        textPanel.add(greetingLabel)
        textPanel.add(statusLabel)
        textPanel.alignmentX = Component.CENTER_ALIGNMENT

        panel.add(iconPanel, BorderLayout.WEST)
        panel.add(textPanel, BorderLayout.CENTER)

        return panel
    }

    private var chatPanel: JPanel? = null
    private var typingTimer: javax.swing.Timer? = null
    private var quoteTimer: javax.swing.Timer? = null
    private var newsTimer: javax.swing.Timer? = null
    private val messageHistory = mutableListOf<Pair<String, String>>()

    fun createChatArea(): JScrollPane {
        chatPanel = JPanel()
        chatPanel!!.layout = BoxLayout(chatPanel!!, BoxLayout.Y_AXIS)
        chatPanel!!.border = javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10)

        addWelcomeMessage()

        val scrollPane = JScrollPane(chatPanel)
        scrollPane.border = null
        scrollPane.horizontalScrollBarPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        scrollPane.verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_ALWAYS

        return scrollPane
    }

    private fun addWelcomeMessage() {
        val aiMessage = createAIMessagePanel(
            "Hello Cat",
            "Hi there! I'm your AI coding companion! 🐱\n\nI can help you with:\n\n• Answering programming questions\n• Explaining code concepts\n• Providing coding tips\n• Sharing motivational quotes\n\nJust type your message below or ask me anything! 😸"
                                            )
        chatPanel?.add(aiMessage)
        chatPanel?.add(Box.createRigidArea(Dimension(0, 20)))
    }

    private fun createUserMessagePanel(title: String, content: String): JPanel {
        return createChatBubblePanel(title, content, true)
    }

    private fun createAIMessagePanel(title: String, content: String): JPanel {
        return createChatBubblePanel(title, content, false)
    }

    private fun createChatBubblePanel(title: String, content: String, isUser: Boolean): JPanel {
        val prefix = if (isUser) "You:" else "$title:"
        val displayText = prefix + " " + content

        val textArea = JTextArea(displayText)
        textArea.lineWrap = true
        textArea.wrapStyleWord = true
        textArea.isEditable = false
        textArea.font = textArea.font.deriveFont(java.awt.Font.PLAIN, 14f)
        textArea.background =
            java.awt.Color(if (isUser) 227 else 245, if (isUser) 242 else 245, if (isUser) 253 else 245)
        textArea.foreground = java.awt.Color(51, 51, 51)

        textArea.border = javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createEmptyBorder(8, 12, 8, 12),
            javax.swing.BorderFactory.createLineBorder(
                java.awt.Color(if (isUser) 187 else 224, if (isUser) 222 else 224, if (isUser) 251 else 224),
                1
                                                      )
                                                                        )

        val container = JPanel()
        container.layout = BoxLayout(container, BoxLayout.X_AXIS)

        if (isUser) {
            container.add(Box.createHorizontalGlue())
        } else {
            container.add(Box.createHorizontalStrut(0))
        }

        container.add(textArea)

        if (isUser) {
            container.add(Box.createHorizontalStrut(0))
        } else {
            container.add(Box.createHorizontalGlue())
        }

        return container
    }

    fun createInputPanel(): JPanel {
        val panel = JPanel(BorderLayout())
        panel.border = javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10)

        val inputField = JTextField()
        inputField.font = inputField.font.deriveFont(java.awt.Font.PLAIN, 14f)
        inputField.border = javax.swing.BorderFactory.createLineBorder(java.awt.Color.GRAY)

        val sendButton = JButton("Send")
        sendButton.font = sendButton.font.deriveFont(java.awt.Font.PLAIN, 12f)

        val quoteButton = JButton("Quote")
        quoteButton.font = quoteButton.font.deriveFont(java.awt.Font.PLAIN, 12f)

        val newsButton = JButton("AI News")
        newsButton.font = newsButton.font.deriveFont(java.awt.Font.PLAIN, 12f)

        inputField.addActionListener {
            val message = inputField.text.trim()
            if (message.isNotEmpty()) {
                handleUserMessage(message, inputField)
            }
        }

        sendButton.addActionListener {
            val message = inputField.text.trim()
            if (message.isNotEmpty()) {
                handleUserMessage(message, inputField)
            }
        }

        quoteButton.addActionListener {
            showMotivationalQuote()
        }

        newsButton.addActionListener {
            showAINews()
        }

        val buttonPanel = JPanel()
        buttonPanel.add(sendButton)
        buttonPanel.add(quoteButton)
        buttonPanel.add(newsButton)

        panel.add(inputField, BorderLayout.CENTER)
        panel.add(buttonPanel, BorderLayout.EAST)

        return panel
    }

    private fun setupDraggableBehavior() {
        catFrame?.rootPane?.addMouseMotionListener(object : MouseMotionAdapter() {
            private var dragOffset = Point(0, 0)

            override fun mouseDragged(e: MouseEvent) {
                trackInteraction()

                if (!e.isControlDown) {
                    val frame = catFrame ?: return
                    val location = frame.location
                    location.translate(e.x - dragOffset.x, e.y - dragOffset.y)
                    frame.location = location
                }
            }
        })
    }

    private fun trackInteraction() {
        lastInteractionTime = System.currentTimeMillis()
        if (!isVisible && catFrame != null) {
            showCat()
        }
    }

    private fun startAutoHideTimer() {
        Timer(autoHideDelay.toInt(), object : ActionListener {
            override fun actionPerformed(e: ActionEvent?) {
                val timeSinceLastInteraction = System.currentTimeMillis() - lastInteractionTime
                if (isVisible && timeSinceLastInteraction > autoHideDelay) {
                    hideCat()
                }
            }
        }).start()
    }

    fun toggleVisibility() {
        if (isVisible) {
            hideCat()
        } else {
            showCat()
        }
    }

    private fun showCat() {
        hideCat()

        try {
            val frame = com.intellij.openapi.wm.WindowManager.getInstance().allProjectFrames.firstOrNull()
            catFrame = if (frame != null) {
                JDialog()
            } else {
                JDialog()
            }

            catFrame?.setLocationRelativeTo(null)
            createFullCatWindow()

            catFrame?.apply {
                setSize(600, 700)
                setLocationRelativeTo(null)
                isVisible = true
                requestFocus()
                toFront()
            }
            isVisible = true

        } catch (e: Exception) {
            println("❌ ERROR showing cat: ${e.message}")
            e.printStackTrace()
        }
    }

    private fun createFullCatWindow() {
        catFrame?.apply {
            title = "🐱 Hello Cat Assistant"
            layout = BorderLayout()

            val mainPanel = JPanel(BorderLayout())
            mainPanel.border = javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10)

            val headerPanel = createHeaderPanel()
            mainPanel.add(headerPanel, BorderLayout.NORTH)

            val chatArea = createChatArea()
            mainPanel.add(chatArea, BorderLayout.CENTER)

            val inputPanel = createInputPanel()
            mainPanel.add(inputPanel, BorderLayout.SOUTH)

            add(mainPanel)

            setSize(600, 700)
            isResizable = false
            isAlwaysOnTop = true

            setupDraggableBehavior()

            catFrame?.addComponentListener(object : ComponentAdapter() {
                override fun componentResized(e: ComponentEvent?) {
                    chatPanel?.components?.forEach { component ->
                        if (component is JScrollPane) {
                            component.verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
                        }
                    }
                }
            })

            addMouseListener(object : MouseAdapter() {
                override fun mouseClicked(e: MouseEvent?) {
                    trackInteraction()
                }

                override fun mouseEntered(e: MouseEvent?) {
                    trackInteraction()
                }
            })

            addKeyListener(object : KeyAdapter() {
                override fun keyPressed(e: KeyEvent?) {
                    trackInteraction()
                }
            })
        }
    }

    private fun hideCat() {
        catFrame?.isVisible = false
        isVisible = false
    }

    private fun handleUserMessage(message: String, inputField: JTextField) {
        javax.swing.SwingUtilities.invokeLater {
            val userMessagePanel = createUserMessagePanel("You", message)
            chatPanel?.add(userMessagePanel)
            chatPanel?.add(Box.createRigidArea(Dimension(0, 15)))
            chatPanel?.revalidate()
            chatPanel?.repaint()
        }

        inputField.text = ""

        processAIMessageAsync(message)
    }

    private fun processAIMessageAsync(userMessage: String) {
        javax.swing.SwingUtilities.invokeLater {
            val thinkingPanel = createTypingIndicator()
            chatPanel?.add(thinkingPanel)
            chatPanel?.add(Box.createRigidArea(Dimension(0, 15)))
            chatPanel?.revalidate()
            chatPanel?.repaint()
            scrollToBottom()
        }

        javax.swing.SwingUtilities.invokeLater {
            try {
                val settings = com.simon.cat.settings.CatSettingsState.getInstance()

                if (settings.aiApiKey.isNotEmpty() && settings.aiEndpoint.isNotEmpty()) {
                    val aiService = com.simon.cat.services.AIService()
                    val messages = getChatHistoryForAI().toMutableList()
                    messages.add(com.simon.cat.services.ChatMessage("user", userMessage))

                    val response = aiService.sendMessageWithHistory(
                        settings.aiApiKey,
                        settings.aiEndpoint,
                        settings.selectedModel,
                        messages
                                                                   )

                    if (response != null) {
                        removeLastMessage()
                        streamAIMessage(response)
                    } else {
                        removeLastMessage()
                        val aiService = com.simon.cat.services.AIService()
                        val fallbackResponse = aiService.generateSmartFallbackResponse(userMessage)
                        val fullResponse =
                            "Sorry, I couldn't get a response from my AI brain right now.\n\n$fallbackResponse"

                        streamAIMessage(fullResponse, "Hello Cat (Fallback)")
                    }
                } else {
                    removeLastMessage()
                    val aiService = com.simon.cat.services.AIService()
                    val fallbackResponse = aiService.generateSmartFallbackResponse(userMessage)
                    val fullResponse =
                        "My AI features need configuration! Please set up your API key and endpoint in settings.\n\n$fallbackResponse"

                    streamAIMessage(fullResponse, "Hello Cat (Basic)")
                }
            } catch (e: Exception) {
                println("❌ ERROR calling AI service: ${e.message}")
                e.printStackTrace()

                removeLastMessage()
                val errorResponse = "Sorry, something went wrong. Let me try with some basic knowledge instead."
                streamAIMessage(errorResponse, "Hello Cat (Error)")
            }
        }
    }

    private fun streamAIMessage(fullText: String, title: String = "Hello Cat") {
        addToHistory("User", "User message")
        addToHistory(title, fullText)

        val formattedText = fullText

        val container = JPanel()
        container.layout = BoxLayout(container, BoxLayout.X_AXIS)

        container.add(Box.createHorizontalGlue())

        val textArea = JTextArea("")
        textArea.lineWrap = true
        textArea.wrapStyleWord = true
        textArea.isEditable = false
        textArea.font = textArea.font.deriveFont(java.awt.Font.PLAIN, 13f)
        textArea.background = java.awt.Color(248, 248, 248)
        textArea.foreground = java.awt.Color(51, 51, 51)

        textArea.border = javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createEmptyBorder(8, 12, 8, 12),
            javax.swing.BorderFactory.createLineBorder(java.awt.Color(224, 224, 224), 1)
                                                                        )

        container.add(textArea)
        container.add(Box.createHorizontalStrut(0))

        chatPanel?.add(container)
        chatPanel?.add(Box.createRigidArea(Dimension(0, 20)))

        var currentIndex = 0
        typingTimer = javax.swing.Timer(30, object : ActionListener {
            override fun actionPerformed(e: ActionEvent?) {
                if (currentIndex < formattedText.length) {
                    textArea.text = formattedText.substring(0, currentIndex + 1)
                    currentIndex++
                    chatPanel?.revalidate()
                    chatPanel?.repaint()
                    scrollToBottom()
                } else {
                    typingTimer?.stop()
                    typingTimer = null
                }
            }
        })
        typingTimer?.start()
    }

    private fun createTypingIndicator(): JPanel {
        val textArea = JTextArea("Hello Cat is thinking...")
        textArea.lineWrap = true
        textArea.wrapStyleWord = true
        textArea.isEditable = false
        textArea.font = textArea.font.deriveFont(java.awt.Font.PLAIN, 14f)
        textArea.background = java.awt.Color(224, 224, 224)
        textArea.foreground = java.awt.Color(102, 102, 102)

        textArea.border = javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createEmptyBorder(8, 12, 8, 12),
            javax.swing.BorderFactory.createLineBorder(java.awt.Color(208, 208, 208), 1)
                                                                        )

        val container = JPanel()
        container.layout = BoxLayout(container, BoxLayout.X_AXIS)
        container.add(Box.createHorizontalGlue())
        container.add(textArea)
        container.add(Box.createHorizontalStrut(0))
        return container
    }

    private fun removeLastMessage() {
        val components = chatPanel?.components
        if (components != null && components.size >= 3) {
            chatPanel?.remove(components.size - 1)
            chatPanel?.remove(components.size - 2)
            chatPanel?.remove(components.size - 3)
        }
    }

    private fun scrollToBottom() {
        javax.swing.SwingUtilities.invokeLater {
            val scrollPane = findChatScrollPane()
            if (scrollPane != null && scrollPane.verticalScrollBar != null) {
                scrollPane.revalidate()
                scrollPane.repaint()
                scrollPane.verticalScrollBar.value = scrollPane.verticalScrollBar.maximum
            }
        }
    }

    private fun findChatScrollPane(): JScrollPane? {
        return try {
            catFrame?.contentPane?.components?.forEach { component ->
                if (component is JScrollPane) {
                    return component
                }
            }
            null
        } catch (e: Exception) {
            null
        }
    }

    private fun showMotivationalQuote() {
        javax.swing.SwingUtilities.invokeLater {
            val quote = getRandomMotivationalQuote()

            val container = JPanel()
            container.layout = BoxLayout(container, BoxLayout.Y_AXIS)

            container.add(Box.createHorizontalGlue())

            val titleLabel = JLabel("💭 Motivational Quote")
            titleLabel.font = titleLabel.font.deriveFont(java.awt.Font.BOLD, 16f)
            titleLabel.foreground = java.awt.Color(255, 152, 0)

            val quoteText = JTextArea(quote)
            quoteText.lineWrap = true
            quoteText.wrapStyleWord = true
            quoteText.isEditable = false
            quoteText.font = quoteText.font.deriveFont(java.awt.Font.PLAIN, 14f)
            quoteText.background = java.awt.Color(255, 255, 240) // Light yellow background
            quoteText.foreground = java.awt.Color(0, 0, 0) // Black text
            quoteText.setPreferredSize(java.awt.Dimension(480, 0))

            quoteText.border = javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createMatteBorder(0, 4, 0, 0, java.awt.Color(255, 152, 0)),
                javax.swing.BorderFactory.createEmptyBorder(12, 16, 12, 16)
                                                                             )

            container.add(titleLabel)
            container.add(Box.createRigidArea(Dimension(0, 8)))
            container.add(quoteText)
            container.add(Box.createHorizontalStrut(0))

            chatPanel?.add(container)
            chatPanel?.add(Box.createRigidArea(Dimension(0, 20)))
            scrollToBottom()
        }
    }

    private fun showAINews() {
        javax.swing.SwingUtilities.invokeLater {
            val newsItems = getTop3AINews()

            val mainContainer = JPanel()
            mainContainer.layout = BoxLayout(mainContainer, BoxLayout.Y_AXIS)

            val titleLabel = JLabel("📰 AI News Update")
            titleLabel.font = titleLabel.font.deriveFont(java.awt.Font.BOLD, 18f)
            titleLabel.foreground = java.awt.Color(0, 70, 160)
            mainContainer.add(titleLabel)
            mainContainer.add(Box.createRigidArea(Dimension(0, 15)))

            for (newsItem in newsItems) {
                val newsPanel = createNewsItemPanel(newsItem)
                mainContainer.add(newsPanel)
                mainContainer.add(Box.createRigidArea(Dimension(0, 12)))
            }

            val scrollPane = JScrollPane(mainContainer)
            scrollPane.horizontalScrollBarPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
            scrollPane.verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
            scrollPane.preferredSize = Dimension(520, 200)

            chatPanel?.add(scrollPane)
            chatPanel?.add(Box.createRigidArea(Dimension(0, 25)))
            scrollToBottom()
        }
    }

    private fun getRandomMotivationalQuote(): String {
        val quotes = listOf(
            "The only way to do great work is to love what you do. - Steve Jobs",
            "Code is like humor. When you have to explain it, it's bad. - Cory House",
            "First, solve the problem. Then, write the code. - John Johnson",
            "Experience is the name everyone gives to their mistakes. - Oscar Wilde",
            "In order to be irreplaceable, one must always be different. - Coco Chanel",
            "Java is to JavaScript what car is to Carpet.",
            "Knowledge is power. Information is liberating. Education is the premise of progress, in every society, in every family. - Kofi Annan",
            "The best code is no code at all. - Jeff Atwood",
            "Simplicity is the ultimate sophistication. - Leonardo da Vinci",
            "Learning never exhausts the mind. - Leonardo da Vinci",
            "Programs must be written for people to read, and only incidentally for machines to execute. - Harold Abelson",
            "The most disastrous thing that you can ever learn is your first programming language. - Alan Kay",
            "The computer was born to solve problems that did not exist before. - Bill Gates",
            "Perfection is achieved not when there is nothing more to add, but when there is nothing left to take away. - Antoine de Saint-Exupéry",
            "Any fool can write code that a computer can understand. Good programmers write code that humans can understand. - Martin Fowler",
            "The function of good software is to make the complex appear to be simple. - Grady Booch",
            "Most good programmers do programming not because they expect to get paid or get adulation by the public, but because it is fun to program. - Linus Torvalds",
            "The art of programming is the art of organizing complexity. - Paul Graham",
            "Debugging is twice as hard as writing the code in the first place. Therefore, if you write the code as cleverly as possible, you are, by definition, not smart enough to debug it. - Brian Kernighan",
            "Programming isn't about what you know; it's about what you can figure out. - Chris Pine"
                           )

        return quotes.random()
    }

    private fun getTop3AINews(): List<NewsItem> {
        return listOf(
            NewsItem(
                title = "🚀 OpenAI Releases GPT-5 with Revolutionary Multimodal Capabilities",
                summary = "The new model demonstrates unprecedented reasoning abilities across text, image, and code tasks, setting new benchmarks in artificial intelligence performance."
                    ),
            NewsItem(
                title = "💡 Google DeepMind Announces AlphaCode 3 - Advanced Programming Agent",
                summary = "This breakthrough AI system can now solve complex algorithmic challenges and compete at the level of top human programmers in competitive coding environments."
                    ),
            NewsItem(
                title = "🔬 MIT Researchers Develop Quantum Machine Learning Framework",
                summary = "A novel approach that combines quantum computing principles with machine learning algorithms, potentially revolutionizing how we process large-scale data patterns."
                    )
                     )
    }

    private data class NewsItem(val title: String, val summary: String)

    private fun createNewsItemPanel(newsItem: NewsItem): JPanel {
        val panel = JPanel()
        panel.layout = BoxLayout(panel, BoxLayout.Y_AXIS)
        panel.background = java.awt.Color(255, 255, 255) // White background
        panel.border = javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createMatteBorder(
                0,
                4,
                0,
                0,
                java.awt.Color(70, 130, 180)
                                                       ), // Steel blue left border
            javax.swing.BorderFactory.createEmptyBorder(12, 16, 12, 16)
                                                                     )

        val titleArea = JTextArea("📌 ${newsItem.title}")
        titleArea.lineWrap = true
        titleArea.wrapStyleWord = true
        titleArea.isEditable = false
        titleArea.font = titleArea.font.deriveFont(java.awt.Font.BOLD, 14f)
        titleArea.background = java.awt.Color(255, 255, 255)
        titleArea.foreground = java.awt.Color(0, 70, 160)

        val summaryArea = JTextArea("   ${newsItem.summary}")
        summaryArea.lineWrap = true
        summaryArea.wrapStyleWord = true
        summaryArea.isEditable = false
        summaryArea.font = summaryArea.font.deriveFont(java.awt.Font.PLAIN, 13f)
        summaryArea.background = java.awt.Color(255, 255, 255)
        summaryArea.foreground = java.awt.Color(50, 50, 50)

        panel.add(titleArea)
        panel.add(Box.createRigidArea(Dimension(0, 6)))
        panel.add(summaryArea)

        return panel
    }

    private fun startQuoteScheduler(settings: com.simon.cat.settings.CatSettingsState) {
        val intervalMs = settings.quoteIntervalMinutes * 60 * 1000L

        quoteTimer = javax.swing.Timer(intervalMs.toInt(), object : ActionListener {
            override fun actionPerformed(e: ActionEvent?) {
                showMotivationalQuote()
            }
        })
        quoteTimer?.start()
    }

    private fun startAINewsScheduler(settings: com.simon.cat.settings.CatSettingsState) {
        val intervalMs = settings.quoteIntervalMinutes * 60 * 1000L

        newsTimer = javax.swing.Timer(intervalMs.toInt(), object : ActionListener {
            override fun actionPerformed(e: ActionEvent?) {
                showAINews()
            }
        })
        newsTimer?.start()
    }

    private fun stopQuoteScheduler() {
        quoteTimer?.stop()
        quoteTimer = null
    }

    private fun addToHistory(sender: String, content: String) {
        messageHistory.add(Pair(sender, content))

        val settings = com.simon.cat.settings.CatSettingsState.getInstance()
        val maxHistory = settings.chatHistorySize

        while (messageHistory.size > maxHistory) {
            messageHistory.removeAt(0)
        }
    }

    private fun getChatHistoryForAI(): List<com.simon.cat.services.ChatMessage> {
        return messageHistory.map { (sender, content) ->
            com.simon.cat.services.ChatMessage(
                role = if (sender == "User") "user" else "assistant",
                content = content
                                              )
        }
    }
}
