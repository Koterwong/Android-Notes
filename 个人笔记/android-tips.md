- TextView字放大后字体变细：`android:fontFamily="sans-serif-light"`


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

- 设置EditText字体`mEditText.setTypeface(Typeface.createFromAssets(getAssets(),1.ttf)`


- ftb设置背景色`backgroundTint`解决阴影`borderWidth="0"`5.0以上设置`dimen`


- 给控件设置`clickable="true"`则该控件就会消费点击事件。

