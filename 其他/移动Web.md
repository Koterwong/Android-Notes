### 像素尺寸

- px：css pixels，逻辑像素，浏览器使用的抽象单位。
- dp,pt：devices independent pixels 设备无关像素，物理像素。
- dpr：devices Pixels Ratio 设备像素缩放比。
- ppi：每英寸像素的数量，iphone4发布会乔布斯详细较少过什么是ppi。不同ppi的屏幕具有不同的dpr，iphoen4的Retina屏幕ppi为326，它的dpr为2。

> 以上设备的尺寸换算关系：dpr = dp / px ，（这个公式适用于平面纬度）

### Viewport

网页在渲染到手机屏幕上的时候，会预先渲染在一个固定像素的Viewport上，iphone的Viewport一般为980px，安卓的viewport差异性就比较大。手机屏幕的px的不可能有这个大（**注意iphone5的像素为320*568，iphone6的像素为375 * 627**），所以会通过缩放来显示这个px为980的ViewPort。需要注意的是，我们开发一般不使用默认980px的viewport，原因主要有以下几点：

- 不同设备的viewport可能不同。
- 页面缩放，交互不友好。
- 链接不可点，有缩放，缩放后又有滚动。
- 移动设备的px宽度一般在320px，如果以980px开发，字体的大小可能要设定为40px才是12px的大小。

> 默认的布局viewport（网页渲染在多大的px宽度的屏幕上），可以通过document.body.clientWidth获取。

### Viewport的`<meta>` 标签

使用默认的980px的viewport开发移动应用是不规范的，我们可以通过`meta`标签改变默认的viewport来达到在移动设备上定制化的效果。`<meta>`标签的语法格式为`<meta name = "viewport" content = "name = value,name = value">` 。name和value的值有一下几种：

- width：设置布局viewport的宽度特定值（device - width）。
- initial - scale：设置页面的初始缩放。
- minimum - scale ：最少缩放。
- maximun - scale ：最大缩放。
- user - scalabel：用户是否能缩放。

进行web开发，我们一般将meta标签设置为以下的代码，即网页选择显然在我们设备宽度的viewport上，并且不viewport = 设备宽度。

```
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no">
```

### Flexbox弹性盒子布局







