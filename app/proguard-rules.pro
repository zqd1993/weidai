# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/wanglei/DevTools/Android/SDK/tools/proguard/proguard-android.txt
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
-obfuscationdictionary bt-proguard.txt
-classobfuscationdictionary bt-proguard.txt
-packageobfuscationdictionary bt-proguard.txt

# Bean类不能混淆
-keep public class com.sdyqwjqwias.fdpwejqwdjew.jiedianqianmodel.**{*;}

-keep public class com.sdyqwjqwias.fdpwejqwdjew.jiedianqianutils.JieDianQianSharedPreferencesUtilis
-keep class com.umeng.** {*;}

-keep class org.repackage.** {*;}

-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
