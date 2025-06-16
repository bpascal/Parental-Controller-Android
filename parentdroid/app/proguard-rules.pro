# 为项目添加ProGuard规则

# 保留Annotation不被混淆
-keepattributes *Annotation*
-keep class * extends java.lang.annotation.Annotation { *; }

# 保留JavaMail相关类
-keep class javax.mail.** { *; }
-keep class javax.activation.** { *; }
-keep class com.sun.mail.** { *; }

# 保留Google Maps相关类
-keep class com.google.android.gms.maps.** { *; }
-keep class com.google.android.gms.location.** { *; }

# 保留应用程序自身的类
-keep class parentalcontrol.parent.** { *; }

# 保留Android基本组件
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider