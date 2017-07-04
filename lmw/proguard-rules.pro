# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\Administrator\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
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
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontoptimize
-verbose
-dontwarn **HoneycombMR2
-dontwarn **CompatICS
-dontwarn **Honeycomb
-dontwarn **CompatIcs*
-dontwarn **CompatFroyo
-dontwarn **CompatGingerbread
-dontwarn **JellyBean
-dontwarn com.umeng.analytics.*
-dontwarn com.umeng.common.*
-dontwarn com.umeng.common.a.*
-dontwarn com.umeng.common.b.*
-dontwarn com.umeng.common.net.*
-dontwarn android.webkit.*
-ignorewarnings

-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-keep class com.umeng.analytics.** {*;}
-keep class com.umeng.common.** {*;}
-keep class com.umeng.common.a.** {*;}
-keep class com.umeng.common.b.** {*;}
-keep class com.umeng.common.d.** {*;}
-keep class com.umeng.common.net.** {*;}

-dontoptimize
-dontpreverify
#不混淆极光
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
#不混淆注解
-keepattributes *Annotation*
-keep class * extends java.lang.annotation.Annotation { *; }

-dontwarn  android.**
-dontwarn  pl.droidsonroids.gif.**
-dontwarn android.support.v4.**
-dontwarn com.alipay.apmobilesecuritysdk.**
-dontwarn com.nhaarman.listviewanimations.**
-dontwarn com.squareup.picasso.OkHttpDownloader.**
-dontwarn com.squareup.picasso.**
#可能运行不了
-dontwarn com.alipay.mobile.framework.service.annotation**

-keep class android.support.v4.**{*;}
-keep interface android.support.v4.app.** { *; }
#内部类不混淆
-keepattributes Exceptions,InnerClasses

-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.v4.**
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.app.Fragment
-keep public class * extends android.support.v4.app.Fragment
-keep public class android.** { public protected private *; }
-keep public class android.support.v4.** { public protected private *; }
-keep public class android.support.v7.** { public protected private *; }


#不混淆第三方jar包中的类
-keep class com.google.gson.** { *; }
-keep class com.tencent.** { *; }
-keep class android.support.** { *; }
-keep class com.squareup.** { *; }
-keep class com.squareup.picasso.** { *; }
-keep class com.github.mikephil.charting.** { *; }
-keep class com.squareup.retrofit2.** { *; }
-dontwarn com.google.**
-keep class com.google.gson.** {*;}
-keep class com.google.protobuf.** {*;}

#不混淆模型
-keep class com.syd.oden.gesturelock.** { *; }
-keep class com.example.administrator.lmw.**.entity.**{*;}
-keep class com.example.administrator.lmw.entity.**{*;}
-keep class com.example.administrator.lmw.http{*;}
-keep class com.example.administrator.lmw.**{*;}
#-keep class com.example.administrator.lmw{*;}
-keep class com.orhanobut.** { *; }
-keep class android.** { *; }
-keep class org.** { *; }
-keep class javax.** { *; }
-keep class rx.** { *; }


-keep class pl.droidsonroids.** { *; }
-keep class com.jni.** { *; }

-keepattributes *Annotation*
-keepattributes Signature

#所有native的方法不能去混淆.
-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

 #某些构造方法不能去混淆
-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);

}

#枚举类不能去混淆.
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#aidl文件不能去混淆.
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepclasseswithmembers class *{
    public *;
}



-keepclassmembers class * extends android.app.Activity {

   public void *(android.view.View);

}

-keepclassmembers class * extends android.support.v4.app.Activity {

   public void *(android.view.View);

}


-keepclasseswithmembers class * {
     public <init>(android.content.Context, android.util.AttributeSet);
 }

-keepclasseswithmembers class * {
     public <init>(android.content.Context, android.util.AttributeSet, int);
 }


-keepclassmembers public class * extends android.view.View {

   void set*(***);

   *** get*();

}



-keep public class * extends android.widget.Adapter {*;}

-keep public class * extends android.widget.CusorAdapter{*;}

-keepnames class * implements java.io.Serializable

-keepclassmembers class * implements java.io.Serializable {

*;

}

#不要混淆序列化
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

#不要混淆R文件
-keep public class com.example.administrator.lmw.R$*{
 public static final int *;
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepattributes *Annotation*

#友盟混淆配置
-dontwarn u.aly.**
-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}

-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

-ignorewarnings

#EventBus
-keepclassmembers class **

#分享混淆
-dontshrink
-dontoptimize
-dontwarn com.google.android.maps.**
-dontwarn android.webkit.WebView
-dontwarn com.umeng.**
-dontwarn com.tencent.weibo.sdk.**
-dontwarn com.facebook.**
-keep public class javax.**
-keep public class android.webkit.**
-dontwarn android.support.v4.**
-keep enum com.facebook.**
-keepattributes Exceptions,InnerClasses,Signature
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable

-keep public interface com.facebook.**
-keep public interface com.tencent.**
-keep public interface com.umeng.socialize.**
-keep public interface com.umeng.socialize.sensor.**
-keep public interface com.umeng.scrshot.**

-keep public class com.umeng.socialize.* {*;}


-keep class com.facebook.**
-keep class com.facebook.** { *; }
-keep class com.umeng.scrshot.**
-keep public class com.tencent.** {*;}
-keep class com.umeng.socialize.sensor.**
-keep class com.umeng.socialize.handler.**
-keep class com.umeng.socialize.handler.*
-keep class     UMMoreHandler{*;}
-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
-keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
-keep class im.yixin.sdk.api.YXMessage {*;}
-keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}

-dontwarn twitter4j.**
-keep class twitter4j.** { *; }

-keep class com.tencent.** {*;}
-dontwarn com.tencent.**
-keep public class com.umeng.soexample.R$*{
public static final int *;
}
-keep public class com.umeng.soexample.R$*{
public static final int *;
}
-keepclassmembers enum * {
public static **[] values();
public static ** valueOf(java.lang.String);
}

-keep class com.tencent.open.TDialog$*
-keep class com.tencent.open.TDialog$* {*;}
-keep class com.tencent.open.PKDialog
-keep class com.tencent.open.PKDialog {*;}
-keep class com.tencent.open.PKDialog$*
-keep class com.tencent.open.PKDialog$* {*;}

-keep class com.sina.** {*;}
-dontwarn com.sina.**
-keep class  com.alipay.share.sdk.** {
    *;
}
-keepnames class * implements android.os.Parcelable {
public static final ** CREATOR;
}

-keep class com.linkedin.** { *; }
-keepattributes Signature

#Udesk客服系统混淆
#udesk
-keep class udesk.** {*;}
-keep class cn.udesk.**{*; }
#七牛
-keep class com.qiniu.android.dns.** {*; }
-keep class okhttp3.** {*;}
-keep class okio.** {*;}
-keep class com.qiniu.android.** {*;}
#smack
-keep class org.jxmpp.** {*;}
-keep class de.measite.** {*;}
-keep class org.jivesoftware.** {*;}
-keep class org.xmlpull.** {*;}
#Android M 权限
-keep class rx.** {*;}
-keep class com.tbruyelle.rxpermissions.** {*;}

#其它
-keep class com.tencent.bugly.** {*; }
-keep class com.nostra13.universalimageloader.** {*;}
-keep class de.hdodenhof.circleimageview.** {*;}
#Retrofit 混淆
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on RoboVM on iOS. Will not be used at runtime.
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions

# glide 的混淆代码
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# banner 的混淆代码
-keep class com.youth.banner.** {
    *;
 }