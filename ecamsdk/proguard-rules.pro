# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Android\AndroidSDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

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
-libraryjars "D:\Android\AndroidSDK\platforms\android-25\android.jar"
-libraryjars "D:\Android\workspace\EcamNetSDK\ecamsdk\libs\ksoap2-android-assembly-2.4-jar-with-dependencies.jar"
-libraryjars "D:\Android\workspace\EcamNetSDK\ecamsdk\libs\javastruct-0.1.jar"
-libraryjars "D:\Android\workspace\EcamNetSDK\ecamsdk\libs\jackson-mapper-asl-1.9.7.jar"
-libraryjars "D:\Android\workspace\EcamNetSDK\ecamsdk\libs\jackson-core-asl-1.9.7.jar"
-dontwarn
-keep class com.howell.jni.JniUtil{
    <methods>;
}

-keep class com.howell.protocol.soap.SoapManager{
     <methods>;
}

-keep class com.howell.protocol.http.HttpManager{
     <methods>;
}

-keep class com.howell.protocol.turn.TurnManager{
     <methods>;
}

-keep class com.howell.protocol.websocket.WebSocketManager{
     <methods>;
}

-keep class com.howell.bean.** {*;}

-optimizationpasses 5
-dontusemixedcaseclassnames