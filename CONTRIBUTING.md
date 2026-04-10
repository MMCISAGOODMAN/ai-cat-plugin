# Contributing to Hello Cat - AI Assistant Plugin

Thank you for your interest in contributing to Hello Cat! We welcome all types of contributions, from bug reports and
feature suggestions to code improvements and documentation updates.

## 🚀 Getting Started

### Prerequisites

- Java 11 or higher
- IntelliJ IDEA 2023.1 or higher (recommended)
- Basic knowledge of Kotlin and the IntelliJ Platform SDK

### Setting Up Development Environment

1. **Fork the Repository**
   ```bash
   git clone https://github.com/YOUR_USERNAME/ai-cat-plugin.git
   cd ai-cat-plugin
   ```

2. **Install Dependencies**
   ```bash
   ./gradlew build
   ```

3. **Run in Development Mode**
   ```bash
   ./gradlew runIde
   ```
   This launches a sandbox IntelliJ IDEA instance with your plugin installed.

4. **Start Developing**
    - Open the project in IntelliJ IDEA
    - Make changes to the source code
    - Test your changes in the development environment

## 📋 Contribution Guidelines

### Types of Contributions We Welcome

#### 🐛 Bug Reports

- Clear description of the issue
- Steps to reproduce
- Expected vs actual behavior
- Screenshots (if applicable)
- Environment details (OS, IDE version, plugin version)

#### 💡 Feature Requests

- Detailed description of the proposed feature
- Use case explanation
- Any alternative solutions considered

#### 📝 Documentation Improvements

- README updates
- API documentation
- Code comments and examples
- Tutorial content

#### 🔧 Code Contributions

- Bug fixes
- New features
- Performance improvements
- Refactoring (with clear justification)

### Coding Standards

#### Kotlin Style

- Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use meaningful variable and function names
- Keep functions small and focused
- Add KDoc comments to public APIs

#### Git Commit Messages

We use [Conventional Commits](https://www.conventionalcommits.org/):

- `feat:` - New features
- `fix:` - Bug fixes
- `docs:` - Documentation changes
- `style:` - Code style changes (formatting, etc.)
- `refactor:` - Code refactoring
- `test:` - Adding or updating tests
- `chore:` - Maintenance tasks
- `hotfix:` - Critical production fixes

Example:

```bash
git commit -m "feat: add streaming message animation"
git commit -m "fix: resolve HTML rendering issues in chat bubbles"
```

#### Pull Request Requirements

- Descriptive title and detailed description
- Reference any related issues
- Include screenshots for UI changes
- Update documentation as needed
- Ensure all tests pass
- Follow the project's coding standards

## 🧪 Testing

### Running Tests

```bash
# Unit tests
./gradlew test

# Integration tests
./gradlew integrationTest

# All tests
./gradlew check
```

### Writing Tests

#### Unit Tests

Focus on testing individual components in isolation:

- Service classes and their business logic
- Configuration handling
- Utility functions

#### Integration Tests

Test component interactions:

- UI component behavior
- API integration points
- Settings persistence

#### UI Tests

Test user interface interactions:

- Button clicks and menu actions
- Chat message sending/receiving
- Settings dialog interactions

### Test Coverage Goals

- 80%+ line coverage for new features
- 100% coverage for critical functionality
- Mock external dependencies appropriately

## 🎯 Feature Development Process

### 1. Planning

- Discuss the feature in an issue first
- Get feedback from maintainers
- Consider alternatives and edge cases

### 2. Implementation

- Create a feature branch: `git checkout -b feature/amazing-feature`
- Implement the feature following coding standards
- Write appropriate tests
- Update documentation

### 3. Review

- Submit a pull request
- Address review feedback promptly
- Ensure CI/CD pipeline passes
- Make final adjustments as needed

### 4. Release

- Merge into main branch
- Update changelog
- Tag release if needed
- Deploy to marketplace (if approved)

## 🐛 Reporting Bugs

### Before Submitting

- Search existing issues to avoid duplicates
- Check if it's a known limitation
- Verify you're using the latest version

### Bug Report Template

```markdown
**Description**
Clear and concise description of what the bug is.

**Steps to Reproduce**
1. Go to '...'
2. Click on '....'
3. Scroll down to '....'
4. See error

**Expected Behavior**
What should happen?

**Actual Behavior**
What actually happens?

**Screenshots**
If applicable, add screenshots to help explain your problem.

**Environment**
- OS: [e.g., Windows 11, macOS 13]
- IDE Version: [e.g., IntelliJ IDEA 2023.1]
- Plugin Version: [e.g., v1.1.0]

**Additional Context**
Add any other context about the problem here.
```

## 💡 Suggesting Features

### Feature Request Template

```markdown
**Is your feature request related to a problem? Please describe.**
A clear and concise description of what the problem is.

**Describe the solution you'd like**
A clear and concise description of what you want to happen.

**Describe alternatives you've considered**
A clear and concise description of any alternative solutions or features you've considered.

**Additional context**
Add any other context or screenshots about the feature request here.
```

## 📖 Documentation

### Where to Contribute

- **README.md**: Main user documentation
- **CLAUDE.md**: Developer guidelines
- **CHANGELOG.md**: Release notes
- **Code comments**: Inline documentation
- **Wiki**: Additional guides and tutorials

### Documentation Style

- Use clear, concise language
- Include practical examples
- Keep instructions step-by-step
- Update screenshots when UI changes
- Link to related documentation

## 🤝 Community Guidelines

### Code of Conduct

We are committed to providing a friendly, safe, and welcoming environment for all contributors. Please be respectful and
constructive in all interactions.

### Communication Channels

- **GitHub Issues**: For bugs, features, and support
- **Discussions**: For general conversation and Q&A (if enabled)
- **Email**: Direct contact for sensitive matters

### Recognition

Contributors will be recognized in:

- Release notes and changelogs
- GitHub contributor graphs
- Special mention in documentation (with permission)

## 🔒 Security Policy

### Reporting Security Vulnerabilities

Please do NOT create public issues for security vulnerabilities.

Instead, email the maintainers directly at security@example.com with:

- Description of the vulnerability
- Steps to reproduce
- Potential impact
- Suggested fix (if known)

### Security Best Practices

- Never commit API keys or secrets
- Validate all user input
- Use secure communication channels
- Follow OWASP guidelines for web security
- Keep dependencies updated

## 📞 Contact

For questions about contributing:

- Create a GitHub discussion or issue
- Email the maintainers
- Join our community channels (if available)

We appreciate your contribution to making Hello Cat better for everyone! 🐱✨
