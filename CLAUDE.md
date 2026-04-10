# Hello Cat - AI Assistant Plugin Development Guide

## 🏗️ Project Structure

```
ai-cat-plugin/
├── src/main/kotlin/com/simon/cat/
│   ├── actions/          # Action classes for UI interactions
│   ├── services/         # AI service integration and API communication
│   ├── settings/         # Configuration management and persistence
│   └── ui/              # User interface components and factories
│
├── src/main/resources/
│   ├── META-INF/plugin.xml     # Plugin configuration and metadata
│   ├── icons/                  # Plugin icons (cat.svg, etc.)
│   └── messages/               # Internationalization files
│
├── build/                      # Build outputs and distributions
├── docs/                       # Documentation and guides
└── test/                       # Unit and integration tests
```

## 📋 Development Standards

### Code Style

- **Language**: Kotlin with modern best practices
- **Formatting**: Use IntelliJ IDEA's built-in formatter
- **Naming**: Follow Kotlin naming conventions (camelCase for functions/variables, PascalCase for classes)
- **Documentation**: Add KDoc comments to all public functions and classes

### Git Workflow

```bash
# Feature development
git checkout -b feature/new-feature-name
git add .
git commit -m "feat: add new feature description"
git push origin feature/new-feature-name

# Bug fixes
git checkout -b fix/issue-description
git add .
git commit -m "fix: resolve issue description"
git push origin fix/issue-description

# Hotfixes
git checkout main
git pull origin main
git checkout -b hotfix/critical-bug
# ... make changes ...
git commit -m "hotfix: critical bug fix"
git push origin hotfix/critical-bug
```

### Commit Message Format

Use conventional commits:

- `feat:` New features
- `fix:` Bug fixes
- `docs:` Documentation changes
- `style:` Code style changes (formatting, etc.)
- `refactor:` Code refactoring
- `test:` Adding or updating tests
- `chore:` Maintenance tasks
- `hotfix:` Critical production fixes

## 🔧 Building & Testing

### Prerequisites

- Java 11 or higher
- IntelliJ IDEA 2023.1 or higher
- Gradle 8.5+

### Build Commands

```bash
# Build the plugin
./gradlew buildPlugin

# Run in development environment
./gradlew runIde

# Build and install to local IDE
./gradlew installPlugin

# Run tests
./gradlew test

# Run integration tests
./gradlew integrationTest

# Clean build
./gradlew clean buildPlugin
```

### Testing Strategy

- **Unit Tests**: Test individual components and services
- **Integration Tests**: Test plugin integration with IntelliJ Platform
- **UI Tests**: Test user interface interactions
- **API Tests**: Test AI service integration and error handling

## 🚀 Release Process

### Version Bumping

Update version in:

- `build.gradle.kts` (plugin version)
- `CHANGELOG.md` (new section for release)

### Creating Release

```bash
# Update changelog and bump version
# Commit changes
git add CHANGELOG.md build.gradle.kts
git commit -m "release: vX.Y.Z"

# Create tag
git tag -a vX.Y.Z -m "Release vX.Y.Z"

# Build distribution
./gradlew buildPlugin

# Push to repository
git push origin main --tags
```

### Publishing to JetBrains Marketplace

1. Prepare plugin JAR file
2. Create account at [plugins.jetbrains.com](https://plugins.jetbrains.com)
3. Upload plugin and fill in marketplace details
4. Submit for review
5. Monitor for approval notifications

## 🐛 Debugging

### Common Issues

**Plugin Not Loading**

- Check `plugin.xml` for syntax errors
- Verify dependencies are correctly specified
- Check IntelliJ logs for startup errors

**AI Service Connection Failed**

- Verify API key format and validity
- Check network connectivity and proxy settings
- Test endpoint URL manually with curl

**UI Display Issues**

- Ensure Swing components are created on EDT
- Check for threading violations
- Verify resource loading paths

### Logging

Add logging using IntelliJ's logger:

```kotlin
import com.intellij.openapi.diagnostic.Logger

private val logger = Logger.getInstance(MyClass::class.java)

logger.info("User message sent: $message")
logger.warn("API connection failed: ${e.message}")
logger.error("Unexpected error", e)
```

## 📱 Plugin Distribution

### Distribution Files

- `build/distributions/*.jar`: Plugin distribution package
- `build/libs/*.jar`: Compiled plugin artifacts
- `build/idea-sandbox/`: Sandbox for testing

### Marketplace Requirements

- Professional, descriptive name
- Comprehensive feature list
- Clear installation instructions
- Screenshots showing functionality
- Support contact information
- Privacy policy (if collecting data)

## 🔐 Security Guidelines

### API Key Management

- Never hardcode API keys in source code
- Store sensitive data in secure configuration
- Use environment variables for development secrets
- Implement proper key rotation procedures

### Network Security

- Validate all external API responses
- Implement timeout and retry logic
- Handle SSL/TLS certificate validation properly
- Sanitize user input before sending to AI services

## 🌐 Internationalization

### Adding New Languages

1. Create `messages_<lang>.properties` files in `src/main/resources/messages/`
2. Define translations following existing patterns
3. Test with different locale settings
4. Update documentation with language availability

### Translation Guidelines

- Keep UI text concise and clear
- Consider context when translating technical terms
- Test with right-to-left languages if needed
- Maintain consistency across all strings

## 📊 Performance Optimization

### Memory Management

- Dispose of Swing resources properly
- Avoid memory leaks in listeners and timers
- Limit chat history size as configured
- Clean up temporary objects

### Responsiveness

- Keep UI operations on EDT (Event Dispatch Thread)
- Use background threads for heavy computations
- Implement progress indicators for long operations
- Optimize AI response streaming timing

## 🤝 Contributing Guidelines

### Pull Request Process

1. Fork the repository
2. Create a feature branch from `main`
3. Make your changes with proper tests
4. Update documentation accordingly
5. Ensure CI/CD pipeline passes
6. Create a descriptive pull request

### Code Review Checklist

- [ ] Code follows project standards
- [ ] Tests added/updated
- [ ] Documentation updated
- [ ] No breaking changes introduced
- [ ] Performance considerations addressed
- [ ] Security implications reviewed

## 📞 Support & Contact

For bugs, feature requests, or questions:

- GitHub Issues: https://github.com/MMCISAGOODMAN/ai-cat-plugin/issues
- Email: simon.ma@example.com (update with real email)
- Discord: Join our community server (if applicable)

## 📚 Resources

- [IntelliJ Platform SDK Docs](https://plugins.jetbrains.com/docs/intellij/)
- [Kotlin Programming Language](https://kotlinlang.org/)
- [Gradle Build Tool](https://gradle.org/)
- [JetBrains Plugin Repository](https://plugins.jetbrains.com/)
