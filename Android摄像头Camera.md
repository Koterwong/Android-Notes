## Android调用摄像头

#### 启动相机：

- 隐示intent：注册该action的IntentFliter都是响应该intent。

  ```java
  Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
  //第二参数,为保存的路径
  intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File("sdcard/1	.jpg")));
  startActivityForResult(intent, 10);
  ```

启动相机获取拍摄图片：

- `startActivityForResult()`在返回的方法中，返回压缩后的照片。

-   将拍摄照片保存到本地缓存。然后读取文件进行压缩。

  ```Java
  Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
  File file = new File(
    			getExternalCacheDir().getAbsolutePath()+"/1.jpg");
  Uri uri = Uri.fromFile(file);
  //指定图片保存路径的Uri
  intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
  startActivity(intent);
  ```

#### 自定义相机

- Camera：camera包下，5.0之后被废弃
- CameraDevice：camera2包下，google提供更为全面的相机api。

