
### 网络请求图片。

- 使用ListView加载网络图片
	1. 优先使用内存中，, 速度最快
	2. 使用本地SD卡, 速度快
	3. 网络加载，速度慢,浪费流量

- 将图片保存到内存中，如何避免oom?
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

	- 由于ListView的缓存机制。
	- 不同的item复用了convertView的同一个ImageView对象。
	- 当item没有请求完成，就被滚出屏幕。那么他将很快背复用起来。
	- 而当复用的时候，该imageView又去请求图片，这是复用的item的正好有了响应，就会将图片设置给imageView。
	- 新的ImageView请求成功，重新设置。就造成了ImageView的多次设置。
	- 解决办法，给ImageView个Url绑定，在设置图片的时候取出tag，一样才去设置图片。


## 极光推送 ##

所有需要客户端被动接收信息的功能模块,都可以用推送实现
	
	- ListView的被动刷新
	- 聊天软件的实现

## 推送原理 ##

- xmpp 是一种基于TCP/IP的协议, 这种协议更适合消息发送
- socket 套接字, 发送和接收网络请求
- 长连接 keep-alive, 服务器基于长连接找到设备,发送消息
- 心跳包 , 客户端会定时(30秒一次)向服务器发送一段极短的数据,作为心跳包, 服务器定时收到心跳,证明客户端或者,才会发消息.否则将消息保存起来,等客户端活了之后(重新连接),重新发送.

> 其他推送
	- 客户端轮询（客户端定时主动拉取数据），浪费流量，浪费性能。
	- 谷歌推送 （有一种墙）
	- 在中国销售的手机必须没有google应用。