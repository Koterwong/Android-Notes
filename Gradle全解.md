## Gradle学习整理。

Gradle是Android Studio默认编辑工具，它在项目的依赖管理、库管理渠道管理有着非常强大的功能。Gradle是基于Groovy（Groovy语言基于JVM）脚本语言进行构建的通过DSL（领域特定语言）语言进行描述和控制构建逻辑。

使用Android Studio创建工程会默认帮我们创建Gradle相关文件，并做基本的配置。在Project层级的文件结构目录层级下会有如下四个Gradel文件：

- build.gradle : 全局的Gradle配置。
- gradle.properties : 一般用来配置build.gradle脚本中的动态参数，例如signingConfigs信息。
- local.properties : 主要配置SDK和NDK的路径，例如`sdk.dir  =D\:\\Android\\sdk\\ndk-bundle`
- settings.gradle : 引入module，配置module工程目录。

在每个module中会有一个关于我们module的gradle配置文件：

- build.gradle :

module中gradle文件配置信息相对其他较复杂，我们的项目最核心的配置信息也就是在这里，前面提到gradle使用的是DSL语言进行控制构建逻辑，那么我们分析项目module的向gradle文件也从每个领域进行分析即可。这其中主要包括一下领域：**apply  plugin领域**、**android领域**、**dependencies领域** 。以上领域分别描述了gradle所引入的插件、androidModule在构建时所用到的参数、androidModule在构建时所依赖的库。更多下详细的可查看[Android Gradle DSL 的文档](http://google.github.io/android-gradle-dsl/current/index.html) 。

Gradle编辑过程图解。

<div align=center>
<img src="https://developer.android.com/images/tools/studio/build-process_2x.png"  width="360" height="540"  alt=""/>
</div>

上图展示了一个App编译的过程，主要包括一下几步：

1. 编译源代码转化成dex文件，编译项目中的资源文件。
2. APK Packager 整dex文件和资源文件，并对apk进行签名。
3. 在最终生成Apk文件之前，APK Packager 会使用zipalign工具优化整合App，以便App在使用过程中更加的节省内存。

### Gradle进阶

##### 1、更改项目的目录结构

Android Studio默认对项目的目录是有一定约束的，其默认结构是`src/main` ，我们可以在android领域配置以下信息来让gradle识别我们的目录。

```Java
sourceSets {
    main {
        jniLibs.srcDirs = ['libs']   //配置jniLibs的默认文件夹
        res.srcDirs = ['src/main/res', 'src/main/res/layout/activity', 
                       'src/main/res/layout/fragment']  
    }
}
```

##### 2、构建全局的配置信息

对于全局多处使用的变量，我们可以提取出来，例如：`compileSdkVersion` 、`buildToolsVersion` 。通过ext领域可以指定全局的配置信息，通过ext指定全局信息有多种方式我们可以根目录的build.gradle中，通过`rootProject.ext.变量名` 来引用全局的信息 。也可以直接在`allprojects`领域配置ext领域变量信息，这样就可以直接在module中引用这个变量了，不过这种写法Gradle的版本更新通知检查机制就无效了，但是还是利大于弊的。其实，个人觉得最喜欢下面这种配置方式，也详细介绍这种方式。我们可以将全局的配置配置信息写在一个单独的config.gradle文件中，这个文件配置信息如下所示:

```Java
ext {
	//android领域相关配置信息
    android = [compileSdkVersion: 24,
               buildToolsVersion: "24.0.2",
               applicationId    : "me.research",
               minSdkVersion    : 14,
               targetSdkVersion : 24,
               versionCode      : 1,
               versionName      : "1.0"]
	//dependencies领域相关配置信息
    dependencies = ["appcompat-v7"        : "com.android.support:appcompat-v7:23.3.0",
                    "design"              : "com.android.support:design:23.3.0",
                    "recyclerview-v7"     : "com.android.support:recyclerview-v7:23.3.0",
                    "butterknife"         : "com.jakewharton:butterknife:7.0.1",
                    "otto"                : "com.squareup:otto:1.3.8",
                    "glide"               : "com.github.bumptech.glide:glide:3.6.1"]
}
```

配置完我们需要的全局信息，我们还需要将这个config.gradle文件引入到根目录的build.gradle文件中`apply from 'config.gradle'` ，这样我们就可以在所有的module中使用这些参数了。使用方法如下所示：

```Java
android {

    compileSdkVersion rootProject.ext.android.compileSdkVersion
    buildToolsVersion rootProject.ext.android.buildToolsVersion

    defaultConfig {
        applicationId rootProject.ext.android.applicationId
        minSdkVersion rootProject.ext.android.minSdkVersion
        targetSdkVersion rootProject.ext.android.targetSdkVersion
        versionCode rootProject.ext.android.versionCode
        versionName rootProject.ext.android.versionName
    }
    ...
}

dependencies {
    compile rootProject.ext.dependencies["design"]
    compile rootProject.ext.dependencies["appcompat-v7"]
    compile rootProject.ext.dependencies["recyclerview-v7"]
    compile rootProject.ext.dependencies["glide"]
}
```

##### 3、Build Types - 编译类型

一般包括release和debug两种不同的构建类型，我们也可以根据这两种类型定义出更多的类型，从而生成不同类型的Apk文件。下面的kw就是自定义的构建类型。

```java
buildTypes {
    release {
        minifyEnabled false
        proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        //配置签名信息
        signingConfig signingConfigs.kw
    }
    debug{
        applicationIdSuffix ".debug"  //app-debug.apk
    }	

    //kw继承了debug构建类型，同时还添加了自定义的配置。
    kw.initWith(buildTypes.debug)
    kw{
        applicationIdSuffix ".kwdebug"
        jniDebuggable true  //开启jnidebug
    }
}
```

构建类型的参数配置信息还有很多，我们可以使用android studio工具在Project Structure中的Build Types中进行快捷配置，其中各个参数在release和debug版本中有着不同的默认值。

##### 4、构建signingConfigs

```java
signingConfigs {
    kw {
        keyAlias 'koterwong'
        keyPassword '123456'
        storeFile file('mKeystore.jks')
        storePassword '123456'
    }
}
```

以上就我们配置的签名信息，很简单，使用这个签名信息的话需要在我们的buildType中添加`signingConfig signingConfigs.kw` 引入我们签名即可。需要注意的是一般不会将该信息直接卸载build.gradle中，一方面是程序的通用性考虑，另一方面是安全行考虑，这个时候就需要我们构建动态的设置参数了，我们通常会在gradle.propertiesz中配置这些参数.

- System.properties方式获取参数。

  ```java
  //在gradle.properties加入如下配置，将参数配置到systemProp中。
  systemProp.keyStorePassword=123456
  systemProp.keyAlias=koterwong
  systemProp.keyAliasPassword=123456
  systemProp.keyStore=koterwong.jks

  //引入配置参数。通过System.properties[KEY]的方式获取。
  signingConfigs {
      app {
          storeFile file(System.properties['keyStore'])
          storePassword System.properties['keyStorePassword']
          keyAlias System.properties['keyAlias']
          keyPassword System.properties['keyAliasPassword']
      }
  }
  ```

##### 5、ProductFlavors - 多渠道打包

在manifest文件application节点写添加如下代码

```java
<meta-data
    android:name="PRODUCT"
    android:value="${CHANNEL_VALUE}"   //要进行替换的渠道占位符
    />
```

在build.gradle脚本中添加productFlavors领域，并自定义渠道名。代码如下：

```java
productFlavors{
    product1{
        //替换起到占位符。
        manifestPlaceholders = [CHANNEL_VALUE:"product1"]
    }
    product2{
        manifestPlaceholders = [CHANNEL_VALUE:"product2"]
    }
}
```

通过以上的步骤，我们在打包apk的时候就会生成不同渠道的apk。不过我们还可以对productFlavors进行优化增加`productFlavors.all` 领域对每一个productFlavors进行遍历，并使用其name作为渠道名。

```
productFlavors.all{ flavor->
	flavor.manifestPlaceholders =[CHANNEL_VALUE:name]
}
```

##### 6、生成重命名包名

```java
applicationVariants.all { variant ->
    variant.outputs.each { output ->
        def outputFile = output.outputFile
        if (outputFile != null && outputFile.name.endsWith('.apk')) {
            output.outputFile = new File(
                    outputFile.getParent(),
                    "kw_${variant.flavorName}_version${variant.versionName}.apk")
        }
    }
}
```

##### 7、gradle加速

在gradle.properties文件增加如下代码：

```java
org.gradle.daemon = true   //开启多个线程编译
org.gradle.parallel = true   //并行构建
org.gradle.configureondemand =true   //按需配置，目前在孵化阶段。
```

在Module的gradle文件添加

```java
dexOptions{
  	incremental = true
  	javaMaxHeapSize = "4g"  //增加编译的内存到4G
}
```

