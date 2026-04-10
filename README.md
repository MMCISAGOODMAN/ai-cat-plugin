# Hello Cat - Professional AI Coding Assistant

<div align="center">

![Hello Cat Logo](src/main/resources/icons/cat.svg)

**An intelligent, user-friendly AI assistant integrated directly into your IntelliJ IDEA development environment**

[![IntelliJ Plugin](https://img.shields.io/jetbrains/plugin/v/com.simon.ma.ai-cat-plugin)](https://plugins.jetbrains.com)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/com.simon.ma.ai-cat-plugin)](https://plugins.jetbrains.com)
[![Rating](https://img.shields.io/jetbrains/plugin/r/rating/com.simon.ma.ai-cat-plugin)](https://plugins.jetbrains.com)

</div>

## 🚀 Overview

Hello Cat is a sophisticated AI-powered coding assistant designed for developers who value both productivity and an
engaging user experience. Unlike generic chat tools, Hello Cat integrates seamlessly into your development workflow with
intelligent context awareness, professional UI design, and robust AI integration.

## ✨ Key Features

### 🤖 **Advanced AI Integration**

- **Real-time streaming responses** with natural typing animation
- **Multi-provider support**: OpenAI GPT models, Claude, and custom endpoints
- **Smart fallback system** when AI services are unavailable
- **Configurable API keys and model selection** for maximum flexibility

### 🎨 **Professional UI Design**

- **Optimized chat interface** for maximum productivity
- **Perfect message alignment**: User messages right-aligned, AI responses left-aligned
- **Smooth rounded bubbles** with content-based sizing
- **Automatic scrolling** during conversations for seamless experience
- **Monospace font optimization** for code readability
- **Enhanced quote & news display** with yellow/white backgrounds for better visibility

### 💻 **Code-Focused Intelligence**

- **Intelligent code block detection** with proper formatting
- **Inline code highlighting** and syntax awareness
- **Markdown support** for headers, lists, bold/italic text
- **Special markers** for visual separation of different content types

### ⚡ **Developer Experience**

- **Keyboard shortcut** (Ctrl+Alt+K) for quick access
- **Persistent sidebar tab** for ongoing conversations
- **Toolbar integration** with intuitive controls
- **Auto-hide functionality** to keep workspace clean
- **Motivational quotes** system to boost productivity
- **AI News updates** with latest programming and AI developments

## 📥 Installation

### From JetBrains Marketplace (Recommended)

1. In IntelliJ IDEA: **File → Settings → Plugins**
2. Search for "Hello Cat" in the Marketplace tab
3. Click **Install** and restart IDE

### Manual Installation

1. Download the latest JAR from [Releases](https://github.com/MMCISAGOODMAN/ai-cat-plugin/releases)
2. In IntelliJ IDEA: **File → Settings → Plugins → Install Plugin from Disk**
3. Select the downloaded JAR file
4. Restart IntelliJ IDEA

## ⚙️ Configuration

### AI Service Setup

1. Go to **Tools → Hello Cat Settings**
2. Enter your API key (required for AI features)
3. Configure the AI endpoint URL:
    - **OpenAI**: `https://api.openai.com/v1/chat/completions`
    - **Custom endpoints**: Must follow OpenAI-compatible API format
4. Select your preferred model name
5. Click **Save Settings**

### Plugin Features Configuration

| Feature                  | Description                            | Default     |
|--------------------------|----------------------------------------|-------------|
| Enable AI Cat            | Toggle main plugin functionality       | Enabled     |
| Auto-hide when inactive  | Hide cat after 5 seconds of inactivity | Enabled     |
| Show motivational quotes | Display inspirational messages         | Enabled     |
| Quote interval (minutes) | Frequency of motivational quotes       | 1 minute    |
| Show AI News             | Display latest AI/Programming news     | Enabled     |
| Chat history size        | Maximum conversation messages stored   | 10 messages |

## 🎮 Usage Guide

### Quick Start

1. **Toggle Interface**: Use **Ctrl+Alt+K** or **Tools → Hello Cat**
2. **Position Window**: Drag the cat window to your preferred location
3. **Start Chatting**: Type your questions in the input field
4. **Get Quotes**: Click the "Quote" button for inspiration
5. **Read News**: Click the "AI News" button for latest updates

### Chat Capabilities

Hello Cat excels at various programming-related interactions:

```markdown
# Programming Questions
"Explain this Java code..."
"How do I implement recursion?"
"What's the difference between var and let in JavaScript?"

# Debugging Help
"I have a NullPointerException..."
"Why is my loop not terminating?"
"Help me understand this stack trace"

# Code Review
"Review this function for bugs"
"Suggest improvements for this algorithm"
"Check if this code follows best practices"

# Learning & Concepts
"Teach me about design patterns"
"Explain async/await in JavaScript"
"What are closures in functional programming?"
```

### Keyboard Shortcuts

| Shortcut     | Action                      |
|--------------|-----------------------------|
| `Ctrl+Alt+K` | Toggle Hello Cat visibility |
| `Enter`      | Send message in chat        |
| `Esc`        | Close chat window           |

## 🔧 Technical Architecture

### Project Structure

```
src/main/kotlin/com/simon/cat/
├── actions/          # Action classes for UI interactions
│   ├── ToggleCatAction.kt      # Main toggle action
│   └── ShowSettingsAction.kt   # Settings dialog action
├── services/         # AI service integration
│   └── AIService.kt            # AI API communication
├── settings/         # Configuration management
│   ├── CatSettingsState.kt     # Persistent settings storage
│   └── CatSettingsConfigurable.kt # Settings UI
└── ui/              # User interface components
    ├── CatComponent.kt         # Main chat interface
    ├── CatToolWindowFactory.kt # Sidebar integration
    └── components/             # Reusable UI elements

src/main/resources/
├── META-INF/plugin.xml           # Plugin configuration
├── icons/cat.svg                 # Plugin icon
└── messages/                     # Internationalization files
```

### Core Technologies

- **Kotlin** - Modern, type-safe language
- **Swing** - Cross-platform UI framework
- **OkHttp** - Robust HTTP client
- **Gson** - JSON processing
- **IntelliJ Platform SDK** - Plugin development

## 🛠️ Development

### Building from Source

```bash
# Clone the repository
git clone https://github.com/MMCISAGOODMAN/ai-cat-plugin.git
cd ai-cat-plugin

# Build the plugin
./gradlew buildPlugin

# The plugin JAR will be generated in build/distributions/
```

### Running in Development Environment

```bash
# Run in development mode
./gradlew runIde
```

This launches a sandbox IntelliJ IDEA instance with your plugin installed.

### Testing

```bash
# Run tests
./gradlew test

# Run integration tests
./gradlew integrationTest
```

## 🐛 Troubleshooting

### Common Issues & Solutions

#### **API Connection Failed**

- Verify your API key is correct and has sufficient credits
- Check that the endpoint URL is accessible from your network
- Ensure firewall settings allow outbound HTTPS connections
- Test with a simple curl command first

#### **Cat Interface Not Appearing**

- Verify plugin is enabled in **Settings → Plugins**
- Check that "Enable AI Cat" is checked in Hello Cat Settings
- Try restarting IntelliJ IDEA completely
- Disable other plugins temporarily to check for conflicts

#### **Streaming Animation Not Working**

- Ensure you have configured valid AI credentials
- Check network connectivity and proxy settings
- Verify the AI service supports streaming responses
- Try with a simpler model (e.g., gpt-3.5-turbo)

#### **Auto-hide Not Functioning**

- Confirm "Auto-hide when inactive" is enabled in settings
- Check that mouse movement tracking is working properly
- Verify no other applications are intercepting mouse events
- Try adjusting the auto-hide delay setting

### Getting Help

If you encounter persistent issues:

1. **Check Error Logs**: Help → Show Log in Finder/Explorer
2. **Verify Configuration**: Double-check all settings in Hello Cat Settings
3. **Network Diagnostics**: Test API endpoint accessibility manually
4. **Plugin Compatibility**: Temporarily disable other plugins

## 📋 Changelog

### Version 1.0.0 (2026-04-08)

- Initial release
- Basic cat interface with chat functionality
- AI service integration with OpenAI compatibility
- Motivational quotes system
- Auto-hide functionality
- Multi-language support

### Version 1.1.0 (2026-04-09)

- **Enhanced UI**: Improved message alignment and streaming animation
- **Code Formatting**: Added intelligent code block detection and formatting
- **Performance**: Optimized scroll behavior during streaming
- **Branding**: Updated to "Hello Cat" with professional naming
- **Bug Fixes**: Resolved HTML rendering and timer issues

### Version 1.2.0 (2026-04-10)

- **Enhanced Visibility**: Yellow/white backgrounds for quotes and news
- **News Feature**: Added AI News button with 3-item display
- **Improved UX**: Better scrollbar handling and message formatting
- **Configuration**: Added news toggle in settings panel

## 🤝 Contributing

We welcome contributions! Here's how you can help:

1. **Fork the Repository**
2. **Create a Feature Branch**: `git checkout -b feature/amazing-feature`
3. **Make Your Changes**: Follow our coding standards
4. **Add Tests**: Ensure your changes don't break existing functionality
5. **Commit Your Changes**: `git commit -m 'Add amazing feature'`
6. **Push to Branch**: `git push origin feature/amazing-feature`
7. **Open a Pull Request**: Include detailed description and screenshots

### Development Guidelines

- Follow Kotlin coding conventions
- Write meaningful commit messages
- Add unit tests for new features
- Update documentation accordingly
- Ensure cross-platform compatibility

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Built on the IntelliJ Platform SDK
- Uses OkHttp for robust networking
- Inspired by modern AI coding assistants
- Thanks to the open-source community for valuable libraries

---

<div align="center">

**Made with ❤️ for developers everywhere**

[Report Bug](https://github.com/MMCISAGOODMAN/ai-cat-plugin/issues) · [Request Feature](https://github.com/MMCISAGOODMAN/ai-cat-plugin/issues) · [Contribute](https://github.com/MMCISAGOODMAN/ai-cat-plugin/pulls)

</div>
