### 加载图片实现目标

##### 1、尽可能避免内存溢出

- 根据图片实际显示的大小压缩图片
- 使用LruCache对我们的图片进行缓存

##### 2、用户操作的必须充分流畅

- getView方法禁止做耗时操作(异步加载+回调显示)

##### 3、用户预期显示的图片尽可能的快

- 图片加载策略的选择`FIFO`,`LIFO`


### 网络请求图片

- 加载图片
  1. 优先使用内存中，速度最快。
  2. 使用本地SD卡, 速度快。
  3. 网络加载，速度慢，浪费流量。
- 将Bitmap进行缓存。
  1. 使用LruCache
     - 内部使用LinkedHashMap
     - least recentlly use 最少最近使用算法
     - 会将内存控制在一定的大小内, 超出最大值时会自动回收, 这个最大值开发者自己定
  2. 在加载图片时将图片压缩
     - 即使使用了LruChche。当大量请求图片时，在请求过程中也容易照成oom。
       - 所以在请求图片时，将图片压缩。
       - BitmapFactory.Options 设置解析参数。
       - BitmapFactory.decodeStream(is,options)
- java中的引用(Android 中使用LruCache)
  - 强引用 垃圾回收器不会回收, java默认引用都是强引用
  - 软引用 SoftReference   在内存不够时,垃圾回收器会考虑回收
  - 弱引用 WeakReference  在内存不够时,垃圾回收器会优先回收
  - 虚引用 PhantomReference  在内存不够时,垃圾回收器最优先回收

注意: Android2.3+, 系统会优先将SoftReference的对象提前回收掉, 即使内存够用

### BaseAdapter显示图片乱序问题

```
- 由于ListView的缓存机制。
- 不同的item复用了convertView的同一个ImageView对象。
- 当item没有请求完成，就被滚出屏幕。那么他将很快背复用起来。
- 而当复用的时候，该imageView又去请求图片，这是复用的item的正好有了响应，就会将图片设置给imageView。
- 新的ImageView请求成功，重新设置。就造成了ImageView的多次设置。
- 解决办法，给ImageView个Url绑定，在设置图片的时候取出tag，一样才去设置图片。
```