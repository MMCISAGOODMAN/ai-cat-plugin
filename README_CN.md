# Hello Cat - 专业 AI 编程助手

<div align="center">

![Hello Cat Logo](src/main/resources/icons/cat.svg)

**直接集成到 IntelliJ IDEA 开发环境的智能友好型 AI 助手**

[![IntelliJ Plugin](https://img.shields.io/jetbrains/plugin/v/com.simon.ma.ai-cat-plugin)](https://plugins.jetbrains.com)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/com.simon.ma.ai-cat-plugin)](https://plugins.jetbrains.com)
[![Rating](https://img.shields.io/jetbrains/plugin/r/rating/com.simon.ma.ai-cat-plugin)](https://plugins.jetbrains.com)

</div>

## 🚀 概述

Hello Cat 是一款为重视生产力和用户体验的开发人员设计的复杂 AI 驱动编程助手。与通用聊天工具不同，Hello Cat 通过智能上下文感知、专业
UI 设计和强大的 AI 集成，无缝集成到您的开发工作流程中。

## ✨ 主要功能

### 🤖 **高级 AI 集成**

- **实时流式响应** 配合自然打字动画
- **多提供商支持**: OpenAI GPT 模型、Claude 和自定义端点
- **AI 服务不可用时的智能回退系统**
- **可配置的 API 密钥和模型选择** 提供最大灵活性

### 🎨 **专业 UI 设计**

- **为最大生产力优化的聊天界面**
- **完美的消息对齐**: 用户消息右对齐，AI 响应左对齐
- **内容尺寸调整的平滑圆角气泡**
- **对话期间的自动滚动** 实现流畅体验
- **代码可读性的等宽字体优化**
- **增强的引用和新闻显示** 黄色/白色背景提高可见性

### 💻 **面向代码的智能**

- **智能代码块检测** 配合正确格式化
- **内联代码高亮** 和语法感知
- **标题、列表、粗体/斜体的 Markdown 支持**
- **不同类型内容的视觉分离特殊标记**

### ⚡ **开发者体验**

- **快速访问的键盘快捷键** (Ctrl+Alt+K)
- **持续对话的持久侧边栏选项卡**
- **直观控制的工具栏集成**
- **保持工作区整洁的自动隐藏功能**
- **提升生产力的励志引用系统**
- **最新编程和 AI 发展的 AI 新闻更新**

## 📥 安装

### 从 JetBrains 市场 (推荐)

1. 在 IntelliJ IDEA 中: **文件 → 设置 → 插件**
2. 在市场标签页搜索 "Hello Cat"
3. 点击 **安装** 并重启 IDE

### 手动安装

1. 从 [发布页面](https://github.com/MMCISAGOODMAN/ai-cat-plugin/releases) 下载最新的 JAR 文件
2. 在 IntelliJ IDEA 中: **文件 → 设置 → 插件 → 从磁盘安装插件**
3. 选择下载的 JAR 文件
4. 重启 IntelliJ IDEA

## ⚙️ 配置

### AI 服务设置

1. 转到 **工具 → Hello Cat 设置**
2. 输入您的 API 密钥 (AI 功能必需)
3. 配置 AI 端点 URL:
    - **OpenAI**: `https://api.openai.com/v1/chat/completions`
    - **自定义端点**: 必须遵循 OpenAI 兼容 API 格式
4. 选择首选模型名称
5. 点击 **保存设置**

### 插件功能配置

| 功能        | 描述            | 默认值    |
|-----------|---------------|--------|
| 启用 AI Cat | 切换主插件功能       | 已启用    |
| 不活动时自动隐藏  | 5 秒不活动后隐藏猫    | 已启用    |
| 显示励志引用    | 显示鼓舞人心的消息     | 已启用    |
| 引用间隔 (分钟) | 励志引用的频率       | 1 分钟   |
| 显示 AI 新闻  | 显示最新的 AI/编程新闻 | 已启用    |
| 聊天记录大小    | 存储的最大对话消息数    | 10 条消息 |

## 🎮 使用指南

### 快速开始

1. **切换界面**: 使用 **Ctrl+Alt+K** 或 **工具 → Hello Cat**
2. **定位窗口**: 将猫窗口拖动到您喜欢的位置
3. **开始聊天**: 在输入字段中键入您的问题
4. **获取引用**: 点击 "引用" 按钮获取灵感
5. **阅读新闻**: 点击 "AI 新闻" 按钮获取最新更新

### 聊天能力

Hello Cat 在各种编程相关交互方面表现出色:

```markdown
# 编程问题
"解释这段 Java 代码..."
"我如何实现递归?"
"JavaScript 中 var 和 let 的区别是什么?"

# 调试帮助
"我有空指针异常..."
"为什么我的循环没有终止?"
"帮我理解这个堆栈跟踪"

# 代码审查
"审查这个函数的错误"
"建议这个算法的改进"
"检查此代码是否符合最佳实践"

# 学习和概念
"教我关于设计模式的内容"
"解释 JavaScript 中的 async/await"
"函数式编程中的闭包是什么?"
```

### 键盘快捷键

| 快捷键          | 操作               |
|--------------|------------------|
| `Ctrl+Alt+K` | 切换 Hello Cat 可见性 |
| `Enter`      | 发送聊天消息           |
| `Esc`        | 关闭聊天窗口           |

## 🔧 技术架构

### 项目结构

```
src/main/kotlin/com/simon/cat/
├── actions/          # UI 交互的操作类
│   ├── ToggleCatAction.kt      # 主切换操作
│   └── ShowSettingsAction.kt   # 设置对话框操作
├── services/         # AI 服务集成
│   └── AIService.kt            # AI API 通信
├── settings/         # 配置管理
│   ├── CatSettingsState.kt     # 持久设置存储
│   └── CatSettingsConfigurable.kt # 设置 UI
└── ui/              # 用户界面组件
    ├── CatComponent.kt         # 主聊天界面
    ├── CatToolWindowFactory.kt # 侧边栏集成
    └── components/             # 可重用 UI 元素

src/main/resources/
├── META-INF/plugin.xml           # 插件配置
├── icons/cat.svg                 # 插件图标
└── messages/                     # 国际化文件
```

### 核心技术

- **Kotlin** - 现代、类型安全的语言
- **Swing** - 跨平台 UI 框架
- **OkHttp** - 健壮的 HTTP 客户端
- **Gson** - JSON 处理
- **IntelliJ Platform SDK** - 插件开发

## 🛠️ 开发

### 从源代码构建

```bash
# 克隆仓库
git clone https://github.com/MMCISAGOODMAN/ai-cat-plugin.git
cd ai-cat-plugin

# 构建插件
./gradlew buildPlugin

# 插件 JAR 将生成在 build/distributions/
```

### 在开发环境中运行

```bash
# 以开发模式运行
./gradlew runIde
```

这将启动一个包含您插件安装的沙盒 IntelliJ IDEA 实例。

### 测试

```bash
# 运行测试
./gradlew test

# 运行集成测试
./gradlew integrationTest
```

## 🐛 故障排除

### 常见问题 & 解决方案

#### **API 连接失败**

- 验证您的 API 密钥正确且有足够信用额度
- 确保端点 URL 可从您的网络访问
- 确认防火墙设置允许出站 HTTPS 连接
- 先用简单的 curl 命令测试

#### **猫界面未出现**

- 验证在 **设置 → 插件** 中启用了插件
- 检查 Hello Cat 设置中是否勾选了 "启用 AI Cat"
- 尝试完全重启 IntelliJ IDEA
- 暂时禁用其他插件以检查冲突

#### **流媒体动画无法工作**

- 确保您已配置有效的 AI 凭证
- 检查网络连接和代理设置
- 验证 AI 服务是否支持流式响应
- 尝试使用更简单的模型 (如 gpt-3.5-turbo)

#### **自动隐藏功能不正常**

- 确认设置中启用了 "不活动时自动隐藏"
- 检查鼠标移动跟踪是否正常工作
- 验证没有其他应用程序拦截鼠标事件
- 尝试调整自动隐藏延迟设置

### 获取帮助

如果遇到持续性问题:

1. **检查错误日志**: 帮助 → 在资源管理器中显示日志
2. **验证配置**: 仔细检查 Hello Cat 设置中的所有设置
3. **网络诊断**: 手动测试 API 端点可访问性
4. **插件兼容性**: 暂时禁用其他插件

## 📋 更新日志

### 版本 1.0.0 (2026-04-08)

- 初始发布
- 带聊天功能的基本猫界面
- 与 OpenAI 兼容的 AI 服务集成
- 励志引用系统
- 自动隐藏功能
- 多语言支持

### 版本 1.1.0 (2026-04-09)

- **增强 UI**: 改进的消息对齐和流媒体动画
- **代码格式化**: 添加智能代码块检测和格式化
- **性能**: 优化流媒体期间滚动行为
- **品牌**: 更新为专业的 "Hello Cat" 命名
- **错误修复**: 解决 HTML 渲染和计时器问题

### 版本 1.2.0 (2026-04-10)

- **增强可见性**: 引用和新闻的黄色/白色背景
- **新闻功能**: 添加带 3 项显示的 AI 新闻按钮
- **改进 UX**: 更好的滚动条处理和消息格式化
- **配置**: 在设置面板中添加新闻切换选项

## 🤝 贡献

我们欢迎贡献! 以下是您可以如何提供帮助:

1. **Fork 仓库**
2. **创建特性分支**: `git checkout -b feature/amazing-feature`
3. **进行更改**: 遵循我们的编码标准
4. **添加测试**: 确保您的更改不会破坏现有功能
5. **提交更改**: `git commit -m 'Add amazing feature'`
6. **推送到分支**: `git push origin feature/amazing-feature`
7. **打开拉取请求**: 包括详细描述和屏幕截图

### 开发准则

- 遵循 Kotlin 编码约定
- 编写有意义的提交信息
- 为新功能添加单元测试
- 相应更新文档
- 确保跨平台兼容性

## 📄 许可证

本项目根据 MIT 许可证授权 - 有关详细信息，请参见 [LICENSE](LICENSE) 文件。

## 🙏 致谢

- 基于 IntelliJ Platform SDK
- 使用 OkHttp 进行稳健的网络通信
- 受现代 AI 编程助手启发
- 感谢开源社区提供的宝贵库

---

<div align="center">

**用 ❤️ 为所有开发者制作**

[报告错误](https://github.com/MMCISAGOODMAN/ai-cat-plugin/issues) · [请求功能](https://github.com/MMCISAGOODMAN/ai-cat-plugin/issues) · [贡献](https://github.com/MMCISAGOODMAN/ai-cat-plugin/pulls)

</div>
