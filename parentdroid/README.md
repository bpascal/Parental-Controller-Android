# Parentdroid - Android Studio 项目

## 项目转换说明

本项目已从Eclipse ADT格式转换为Android Studio格式。以下是转换的主要变更：

### 项目结构变更

- 添加了Gradle构建系统配置文件
  - `build.gradle`（项目级）
  - `app/build.gradle`（应用级）
  - `settings.gradle`
  - `gradle.properties`

- 源代码结构保持不变
  - 所有Java源文件仍位于原始`src`目录
  - 资源文件仍位于原始`res`目录
  - AndroidManifest.xml保持在原位置

### 依赖项管理

- 原有JAR库依赖已配置在Gradle中
- Google Maps依赖已更新为Google Play Services Maps
- 邮件相关库已更新为最新版本

### 如何使用

1. 使用Android Studio打开项目目录
2. 等待Gradle同步完成
3. 如有需要，更新AndroidManifest.xml中的Google Maps API配置

### 注意事项

- 项目最低SDK版本保持为API 15
- 目标SDK版本已更新为API 30
- 已启用AndroidX支持
- 如遇到构建问题，请检查Gradle配置和依赖项