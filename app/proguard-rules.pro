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
# Please add these rules to your existing keep rules in order to suppress warnings.
# This is generated automatically by the Android Gradle plugin.
-dontwarn com.example.core.data.Resource$Error
-dontwarn com.example.core.data.Resource$Loading
-dontwarn com.example.core.data.Resource$Success
-dontwarn com.example.core.data.Resource
-dontwarn com.example.core.di.CoreModuleKt
-dontwarn com.example.core.domain.model.DetailRestaurant
-dontwarn com.example.core.domain.model.Restaurant
-dontwarn com.example.core.domain.repository.IRestaurantRepository
-dontwarn com.example.core.domain.usecase.RestaurantInteractor
-dontwarn com.example.core.domain.usecase.RestaurantUseCase
-dontwarn com.example.core.ui.RestaurantsAdapter$OnItemClickBack
-dontwarn com.example.core.ui.RestaurantsAdapter
-dontwarn com.example.core.ui.ReviewsAdapter

# Keep the CoreModuleKt class and its methods
-keep class com.example.core.di.CoreModuleKt
-keepclassmembers class com.example.core.di.CoreModuleKt