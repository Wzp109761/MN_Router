## 组件化开发优点：
1. 编译速度：可以按需测试单一模块  
2. 超级解耦：极度的降低了模块之间的耦合，便于后期维护与更新  
3. 功能重用：某一模块的功能在另外的组件化项目中使用只需要单独依赖这一模块即可  
4. 便于团队开发：组件化架构是团队开发必然会选择的一种开发模式，有效的使团队更好的合作  


##.注意事项

1. 需要注意包名和资源文件命名冲突问题；  
2. Gradle中的版本号要进行统一管理  
3. 组件在Application和library之间如何随意切换  
4. Android Manifest.xml文件的区分  
5. Library 不能再Gradle文件中有applicationID  

## 版本号统一管理：
1. gradle.properties中添加，字符串需要进行类型转换  
   CompileSdk = 32  
   build.gradle引入：
   compileSdk CompileSdk.toInteger()  

2. 全局build.gradle 尾部添加如下
ext{
android=[
compileSdkVersion:32,
buildToolsVersion:"29.0.0",
targetSdkVersion:32,
minSdkVersion:21,
versionCode:1,
versionName:"1.0",
is_application:false
]
}

* build.gradle引入：
  minSdk rootProject.ext.android.minSdkVersion
  targetSdk rootProject.ext.android.targetSdkVersion
  versionCode rootProject.ext.android.versionCode
  versionName rootProject.ext.android.versionName


3. 根目录自定义config.gradle 文件
* 全局build.gradle中加载config.gradle文件，添加如下语句
  apply from:"config.gradle"
* config.build文件中定义如下：
  ext{
  android=[
  compileSdkVersion:32,
  buildToolsVersion:"29.0.0",
  targetSdkVersion:32,
  minSdkVersion:21,
  versionCode:1,
  versionName:"1.0",
  is_application:false
  ]
  }

* build.gradle引入：
  minSdk rootProject.ext.android.minSdkVersion
  targetSdk rootProject.ext.android.targetSdkVersion
  versionCode rootProject.ext.android.versionCode
  versionName rootProject.ext.android.versionName


## 编译时技术

APT:注解处理器，是一种处理注解的工具
注解是标记，能够标记我们程序中的某些东西
注解处理器 就相当于大脑，去找注解，然后识别注解，然后执行相对应的指令
1. 创建注解，让注解把所有要加入到路由表中的类都标记出来
2. 创建注解处理器，去代码中找到专门用来标记Activity的类，得到他所标记的类
3. 生成ActivityUtil的类，

@Retention(RetentionPolicy.CLASS) 源码期SOURCE  编译期CLASS  运行期RunTime

### 注解处理器引入如下依赖
annotationProcessor 'com.google.auto.service:auto-service:1.0-rc4'
compileOnly 'com.google.auto.service:auto-service:1.0-rc4'