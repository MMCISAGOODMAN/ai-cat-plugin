# Hello Cat - Changelog

All notable changes to the Hello Cat AI Assistant plugin will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.1.0] - 2026-04-09

### ✨ Added

- **Professional Branding**: Updated from "AI Cat" to "Hello Cat" for more professional appearance
- **Advanced Code Formatting**: Intelligent detection and formatting of code blocks, inline code, markdown headers,
  lists, bold/italic text
- **Monospace Font Optimization**: Special monospace font for better code readability in AI responses
- **Special Content Markers**: Visual indicators for different content types (headers, code blocks, etc.)
- **Enhanced Streaming Animation**: Real-time character-by-character typing with improved timing
- **Auto-scroll During Streaming**: Automatic scrolling during message streaming for better UX
- **Professional UI Design**: Improved rounded bubble design with content-based sizing

### 🔧 Changed

- **Message Alignment**: Fixed user messages right-aligned, AI messages left-aligned
- **Status Indicator**: Updated from "🤖 Online & Ready!" to "🚀 Ready for Coding!" for more professional tone
- **Typing Indicator**: Changed from "AI Cat is typing..." to "Hello Cat is thinking..."
- **Plugin Configuration**: Updated all references from "AI Cat" to "Hello Cat"
- **Settings Interface**: Improved settings panel with better organization and descriptions
- **Scroll Behavior**: Enhanced scroll-to-bottom functionality during streaming

### 🐛 Fixed

- **HTML Rendering Issues**: Resolved CSS parsing errors by switching to JTextArea approach
- **String Concatenation Errors**: Fixed Kotlin compilation issues with HTML string building
- **Timer Null Safety**: Resolved nullable ActionEvent issues in timer callbacks
- **Message Display**: Fixed long content truncation and improved wrapping behavior
- **Streaming Implementation**: Ensured ALL AI responses stream instead of showing complete at once

### ⚡ Performance

- **Reduced Memory Usage**: Optimized timer management and cleanup
- **Improved Responsiveness**: Faster character streaming animation
- **Better Scroll Reliability**: More robust scroll pane detection and positioning

## [1.0.0] - 2026-04-08

### 🎉 Initial Release

- Basic cat interface with chat functionality
- AI service integration with OpenAI compatibility
- Motivational quotes system with configurable intervals
- Auto-hide functionality after inactivity
- Keyboard shortcut support (Ctrl+Alt+K)
- Multi-language support foundation
- Professional plugin configuration
- Settings dialog for API key and endpoint configuration
