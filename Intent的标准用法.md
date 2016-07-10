## Android系统的Intent

- 启动浏览器

```Java
Intent  intent =new Intent("android.intent.action.VIEW");
intent.setData(Uri.parse("http://www.baidu.com"));
startActivity(intent);
```
- 启动拨号器 

```Java
Intent  intent =new Intent(Intent.ACTION_DIAL);
intent.setData(Uri.parse("tel://10086");
startActivity(intent);
```

- 显示联系人列表

```Java
Intent intent = new Intent(Intent.ACTION_VIEW);
intent.setData(Uri.parse("content://contacts/people"));
startActivity(intent);
```
- 选择联系人

```Java
Intent intent = new Intent(Intent.ACTION_PICK, 
	ContactsContract.Contacts.CONTENT_URI);
startActivityForResult(intent,1);

intent.getData();
```

- 打电话,直接拨号

```Java
Intent intent = new Intent(Intent.ACTION_CALL);
intent.setData(Uri.parse("tel:10086"));
startActivity(intent);
```

- 启动短信界面

```Java
Intent intent = new Intent(Intent.ACTION_SENDTO);
intent.setData(Uri.parse("smsto:"));
startActivity(intent);
```

- 启动选择照片的Intent。

```java
Intent intent = new Intent(Intent.ACTION_PICK);
//获取所有的image类型。
intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                      "image/*");
startActivityForResult(intent,1);
//获取图片,返回Uri
intent.getData();
```

- 启动拍照的Intent

```Java
Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//第二参数,为保存的路径
intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File("sdcard/1	.jpg")));
startActivityForResult(intent, 10);
```
- 启动录像的Intent

```Java
intent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File
	("sdcard/2.3gp")));
intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
startActivityForResult(intent, 20);
```

- 启动安装程序的Intent /卸载程序的Intent

```Java
Intent intent = new Intent(Intent.ACTION_VIEW);
intent.setDataAndType(Uri.fromFile(new File(downloadFile)),
		"application/vnd.android.package-archive");
startActivityForResult(intent, REQUEST_INSTALL);
```

- 卸载程序的Intent

```Java
Intent uninstallIntent = new Intent();
uninstallIntent.setAction(Intent.ACTION_DELETE);	uninstallIntent.setData(Uri.parse("package:"+.packageName));
startActivity(uninstallIntent);
```

- 启动应用的Intent

```Java
Intent startIntent = getPackageManager().getLaunchIntentForPackage(packName);
startActivity(startIntent);
```

- 打开应用程序详细信息的Intent

```Java
Intent settingIntent = new Intent();	settingIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
settingIntent.setData(Uri.parse("package:" +packageName));
 startActivity(settingIntent);
```

## Acticity启动相关Flag ##

1. FLAG_ACTIVITY_NEW_TASK

2. FLAG_ACTIVITY_CLEAR_TOP    

3. FLAG_ACTIVITY_SINGLE_TOP  

4. FLAG_ACTIVITY_RESET_TASK_IF_NEEDED 
