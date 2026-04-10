#!/bin/bash

#
# verify_plugin.sh
#
# Created on 2026-04-10
#
# Copyright (C) 2026 Volkswagen AG, All rights reserved.
#

echo "🐱 AI Cat Plugin Verification"
echo "================================"

PLUGIN_DIR="/Users/simon.ma/ownproject/ai/ai-cat-plugin"
cd "$PLUGIN_DIR"

echo "📁 Checking plugin structure..."
REQUIRED_FILES=(
    "src/main/resources/META-INF/plugin.xml"
    "src/main/kotlin/com/simon/cat/settings/CatSettingsState.kt"
    "src/main/kotlin/com/simon/cat/ui/CatComponent.kt"
    "src/main/kotlin/com/simon/cat/services/AIService.kt"
    "build.gradle.kts"
    "settings.gradle.kts"
)

MISSING_FILES=()
for file in "${REQUIRED_FILES[@]}"; do
    if [ -f "$file" ]; then
        echo "✅ $file"
    else
        echo "❌ Missing: $file"
        MISSING_FILES+=("$file")
    fi
done

if [ ${#MISSING_FILES[@]} -gt 0 ]; then
    echo ""
    echo "❌ Plugin verification failed!"
    echo "Missing files:"
    for file in "${MISSING_FILES[@]}"; do
        echo "  - $file"
    done
    exit 1
fi

echo ""
echo "🔍 Checking build configuration..."

# Check if Gradle wrapper exists
if [ -f "gradlew" ] && [ -x "gradlew" ]; then
    echo "✅ Gradle wrapper ready"
else
    echo "❌ Gradle wrapper missing or not executable"
    exit 1
fi

# Check Java version (minimum requirement)
JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -ge 21 ]; then
    echo "✅ Java version OK (${JAVA_VERSION})"
else
    echo "⚠️  Java version might be too old (need 21+)"
fi

echo ""
echo "🎉 AI Cat Plugin verification PASSED!"
echo ""
echo "🚀 Next steps:"
echo "   1. Build the plugin: ./gradlew buildPlugin"
echo "   2. Install in IntelliJ IDEA"
echo "   3. Configure AI settings via Tools → AI Cat Settings"
echo "   4. Toggle cat with Ctrl+Alt+K"
echo ""
echo "🐱 Your cute AI companion is ready! 😸"
