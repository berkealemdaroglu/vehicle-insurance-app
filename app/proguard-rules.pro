# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keep class com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.** { *; }
-keep class com.ersinberkealemdaroglu.arackaskodegerlistesi.domain.model.** { *; }

# Kotlin
-keep class kotlin.** { *; }
-dontwarn kotlin.**

# AndroidX
-keep class androidx.** { *; }
-dontwarn androidx.**

# Google Material and Android Support Libraries
-keep class com.google.android.material.** { *; }
-keep class android.support.** { *; }
-dontwarn com.google.android.material.**

# Retrofit and OkHttp
-keep class retrofit2.** { *; }
-keep class okio.** { *; }
-keep class com.squareup.okhttp3.** { *; }
-dontwarn retrofit2.**
-dontwarn okio.**

# Gson
-keep class com.google.gson.** { *; }
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
}

# Hilt and Dagger
-keep class dagger.** { *; }
-keep class javax.inject.** { *; }
-keep class javax.annotation.** { *; }
-keepclasseswithmembernames class * {
    @javax.inject.* <fields>;
}
-keepclasseswithmembernames class * {
    @javax.inject.* <methods>;
}
-keepclasseswithmembernames class * {
    @dagger.* <fields>;
}
-keepclasseswithmembernames class * {
    @dagger.* <methods>;
}
-dontwarn javax.inject.**
-dontwarn dagger.**

# Coil
-keep class coil.** { *; }
-dontwarn coil.**

# Android Navigation Component
-keep class androidx.navigation.** { *; }

# Lottie
-keep class com.airbnb.lottie.** { *; }
-keepclassmembers class ** {
    @com.airbnb.lottie.LottieAnimationView *;
}

# Coroutines
-keep class kotlinx.coroutines.** { *; }
-dontwarn kotlinx.coroutines.**

# DataStore
-keep class androidx.datastore.** { *; }

# Chucker
-keep class com.github.chuckerteam.chucker.** { *; }
-dontwarn com.github.chuckerteam.chucker.**
