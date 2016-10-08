## View相关

- `view.isShown()`判断view和父View是在显示。


- 给View控件设置`clickable="true"`则该控件就会消费点击事件。
- View类中的`callOnClick()，performClick()，performLongClick()，`用于触发View的点击事件。
- 关于?attr/xxx  的解释，当我们的activity或者dialog的布局文件通过?attr/xxx  引入了某个属性这个属性的值就是我们当前activity或者dialog的theme主题样式的某个属性的值。
#### TextView、EditText
- TextView字放大后字体变细：`android:fontFamily="sans-serif-light"`
- TextView.setError()。可以设置textView的错误图片，和弹出错误信息。
- TextView：`append(CharSequence)`方法，添加文本。一些特殊文本直接用+连接会变成String
- TextView你真的了解吗？[我是链接](http://blog.csdn.net/sdkfjksf/article/details/51317204)
- 设置EditText字体`mEditText.setTypeface(Typeface.createFromAssets(getAssets(),1.ttf)`
- fab设置背景色`backgroundTint`。
## 系统相关

- 直接发送短信API

> 权限`send_mss`部分机器需要`read_phone_state`

```java
//直接使用发送短信的api，无需启动系统的短信应用
SmsManager sm = SmsManager.getDefault();
ArrayList<String> sms = sm.divideMessage(content);
for (String string : sms) {
  //arg0:目标号码
  //arg1:短信中心号码，null表示使用默认
  //arg2:短信正文
  sm.sendTextMessage(phone, null, string, null, null);
}
```
- 放个知乎连接[Android开发工具和技巧](https://www.zhihu.com/question/27140400)