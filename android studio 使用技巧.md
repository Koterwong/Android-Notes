##android studio 设置和快捷键

### 设置代码风格

可通过Editor->Code Style -> Java设置你喜欢的格式。

个人喜好

Wrapping and Braces  选中 

- Simple Method in one lines 。
- Simple Class in one lines。
- Ensure right margin is not exceeded。
- Extends/implements keyword -> wrap if long
- Class Annotations Methodd Annotation -> Do not wrap

Code Generation :

- field 前缀加m，静态field前缀加s。


Tabs and Indent:

- Tab size :2 ; 
- Indent :2 
- continuation Indent :4;

JavaDoc：

- Other -> Wrap at rigth magin
- Other -> Do not wrap one line comments 
- Other -> Preserve lines  feeds

### 提高开发效率的常用功能

- attach debug ：可在快捷工具栏中找到。使开发者可以在不编辑代码的情况下。
- Structurally Search ：可在全局查找每个类中的相关代码段，非常好用。可以使用按两下Shift，输入Search  Structurally 打开，然后进行全局搜索。
- 自定义代码模版：Editer -> Live Template -> 选择新增。

```java
/*
 * ${NAME}     ${DATE}-${MONTH}-${DAY}
 * Copyright (c) ${YEAR} KOTERWONG All right reserved
 */
```


### 设置

- 修改编辑器的字体大小 Setting -> Editor -> Color & Fonts
- 代码提示对大小是否敏感 -> Editot -> General -> CodeCompletion -> Case Sensitive Completion
- 鼠标悬浮提示：Setting -> Editor || Other -> Show quick doc on mouse move Delay ; 
- 代码自动提示：Setting -> Editor -> Code Completion 注意大小写是否敏感
- 自动导包 ：Editer -> General -> auto import
- 自动编译： compiler - make potject automatically
- 设置编码：encodeing - utf-8
- Gradle setting - 勾选offline work。不去更新下载  
  - show methed separator 显示方法分割线 ch 



- f8 单步调试，add to watch

  - f7 进入要调试的方法
- shift + f8(或点击绿色按钮)跳到下一个断点
  `Franes`方法调用栈
  `Variables` 变量信息
- 查看方法引用的位置：选中方法->右键->Find Usages。
- 设置Grandle路径：Build -> Build Tools -> Grandle。
- 显示方法分割线：Editor -> General -> Appearance -> show method separators。
- 显示行数：Editor -> General -> Appearance ->show line number。
- logcat日志自动换行 -> 在logcat日志输出左侧工具栏点击换行图标即可。

###常用的快捷键

- Ctrl + B ：快速进入方法。


- Ctrl + E ：显示最近修改的文件。


- Ctrl+F11：加BookMark，简直是非常有用的功能，不过需要去设置添加一下跳转下一个书签或上一个书签的快捷键才能发挥出该功能真正强大。

- ALT + 2 :打开书签，可搭配上面的BookMark使用非常方面。

- Ctrl+F12 : 输入关键字快速定位指定的变量或方法，支持模糊搜索。

- Ctrl +Alt+左箭头或右箭头：返回前一个或下一个光标的位置，在想回溯阅读位置的时候非常有用

- Ctrl + J 弹出一些代码模版fori 

  `ifn` `inn` 判断参数时候等于(不等于)null
  `fbc` 快速的findViewById

   `visible` 设置view的显示GONE

  `Toast` 打印Toast
  `IntentView` 快速的Intent

- Ctrl + Alt + T 弹出一些包围结构，例如if..else  while  for 。Surround With。

- Ctrl ＋ [或]可以跳到大括号的开头结尾

- Ctrl + Tab / Ctrl + E 最近打开的文件，在文件的选项卡中进行切换；

- F2 或 Shift+F2 高亮错误或警告快速定位

- Ctrl + plus 打开关闭方法

- Ctrl + 1 打开关闭工程目录

- logt  快速导入log标签

- logd  快速打印debug级别的日志

- logm  生成参数的log

- Ctrl + Alt + l ：格式化代码 

- Ctrl + Alt +  空格：代码提示

- Ctrl + 空格：代码提示

- Ctrl ＋shift + ↑  将光标处的代码移动到上一行

- Alt  + F7  ： 查找变量在哪里使用过

- Alt+  ↑ 在方法间快速移动定位

- Ctrl + N  快速打开工程中的类

- Ctrl + H 显示类结构图 

- Ctrl + Alt + H  查看一个方法的调用

- Ctrl + shift + N  快速打开一个xml文件

- Ctrl + Alt + F 变量全局

- Ctrl + Alt + V  返回引用 

- Ctrl ＋shift + i 查看一个方法在类中的实现，弹出窗口的方式

- Ctrl　+ M  提取代码到新的方法中

- Ctrl + U 查看一个类的父类

- Ctrl + Alt + O：管理包

- Alt + Enter 自动修正。Ctrl+1

- Ctrl + Shift + Enter 自动补全代码

- Ctrl + P  显示方法参数

- Ctrl + X  删除行

- Ctrl + D 复制行

- Ctrl + O　重写实现方法。

- Ctrl ＋ Q 可以看JavaDoc

- Ctrl + y  删除行

- Ctrl + R  替换文本

- Ctrl + F  查找文本

- Ctrl+/ 或 Ctrl+Shift+/  注释（// 或者 ）

- Alt+Shift+C  对比最近修改的代码

- Shift + F6  重构-重命名

- Shift ＋ Click可以关闭文件

- Ctrl＋Shift＋Backspace可以跳转到上次编辑的地方






 