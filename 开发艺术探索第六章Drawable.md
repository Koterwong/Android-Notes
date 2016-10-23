### Drawable简介

Drawable是可以在Canvas上绘制的进行绘制的抽象物件，之所以说是抽象，是它要依附在View上去显示。而View就是显示在UI界面上实实在在存在的图形了，Drawable和View都可以进行绘制，但是drawable一般是依附在View而存在的，通常作为View的背景来使用。Drawable的种类可以分为好多种，通常通过xml的方式定义Drawable，特殊情况下也可以在代码来定义Drawable。下面将对每一种Drawable进行实例讲解。

在讲解之前需要了解Drawable两个重要的方法，getIntrinsicWidth和getIntrinsicHeight，它们表示drawable的内部宽和高，但不是所有的drawable都有内部宽高，一张图片形成的Drawable内部宽高就是Bitmap的宽高。由于drawable没有的大小的概念，当做View的背景时，会被拉伸致View的宽高。

### BitmapDrawable

BitmapDrawable代表一张图片，在开发中，我们可以直接引用原始图片，也可以通过xml的方式描述图片并且可以给图片设置更多的效果。

```xml
<bitmap xmlns:android="http://schemas.android.com/apk/res/android"
        android:src="@drawable/image" //设置bitmap的路径
        android:antialias="true"	  //设置抗锯齿效果，是图片变得平滑，开启
        android:dither="true"		  //抖动效果，在色彩模式RGB555的屏幕上不会过于时针，开启
        android:filter="true"		  //过滤效果，图片在拉伸和压缩时可以保持较好的效果。
        android:gravity="center"
        android:mipMap="false"
        android:tileMode="repeat"    //tileMode平铺模式，有repeat，mirror，clamp
    />
```

### ShapeDrawable

shapedrawable是一种常见的drawable，可以用颜色构建出图形，它的语法如下所示。

```xml
<shape xmlns:android="http://schemas.android.com/apk/res/android"
       android:shape="rectangle | oval | line | ring">
	
  <corners
      android:bottomLeftRadius="0dp"
      android:bottomRightRadius="15dp"
      android:radius="2dp"
      android:topLeftRadius="10dp"
      android:topRightRadius="15dp"/>

  <gradient 
      android:angle="0"   //渐变角度，必须45度的倍数，0表示从左到右，90表示从上到下。
      android:centerColor="#00ff00"
      android:centerX="0.5"
      android:centerY="0.5"
      android:endColor="#0000ff"
      android:startColor="#ff0000"
      android:type="linear | radial | sweep"
      android:useLevel="true"
      android:gradientRadius="3dp"   //渐变半径，仅当type为radial时有效。
      />

  <padding
      android:top="1dp"
      android:left="1dp"
      android:right="1dp"
      android:bottom="1dp"
      />

  <size
    android:height="100dp"
    android:width="100dp"/>

  <solid android:color="#ff0000"/>

  <stroke
      android:width="1dp"
      android:color="#000000"
      android:dashWidth="1dp"
      android:dashGap="1dp"/>

</shape>
```

- android:shape表示图片的形状，一般来包含四种：rectangle、oval、line、ring，需要主意的是，line和ring需要指定`<stroke>`来指定宽度和颜色信息。针对ring这个形状，还需要5个特殊属性。

  |      |      |
  | ---- | ---- |
  |      |      |
  |      |      |
  |      |      |
  |      |      |

### LayerDrawble

使用`layer-list`标签来实现，可以包含多个item，每个item代表一个drawable。下面这个例子来实现一个输入框。

```xml
<layer-list xmlns:android="http://schemas.android.com/apk/res/android">
  <item>
    <shape android:shape="rectangle">
      <solid android:color="#0ac39e"/>
    </shape>
  </item>
  <item android:bottom="6dp">
    <shape android:shape="rectangle">
      <solid android:color="#ffffff"/>
    </shape>
  </item>
  <item
      android:bottom="1dp"
      android:left="1dp"
      android:right="1dp">
    <shape android:shape="rectangle">
      <solid android:color="#ffffff"/>
    </shape>
  </item>
</layer-list>
```

### StateListDrawable

使用`selector`标签来实现，设置View背景时会根据View的状态来选择不同的drawabel。

```xml
<selector xmlns:android="http://schemas.android.com/apk/res/android">
  <item android:drawable=""/>
  <item android:state_focused="true" android:drawable=""/>
  <item android:state_pressed="true" android:drawable=""/>
  <item android:state_checked="true" android:drawable=""/>  //checkbox选中状态
</selector>
```

### LevelListDrawable

使用`level-list`来实现，drawable的集合，通过drawable的`setLevel`或者ImageView的`setImageLevel`来切换不同level的item背景。

```xml
<level-list xmlns:android="http://schemas.android.com/apk/res/android">
  <item
      android:drawable="@drawable/ic_launcher"
      android:minLevel="10"
      android:maxLevel="100"
      />
  <item android:drawable="@drawable/ic_launcher1"
        android:minLevel="101"
        android:maxLevel="102"/>
</level-list>
```

### TransitionDrawable

使用`<transition>`标签来实现，用于实现两个drawable之前的淡入淡出效果。

```xml
<transition xmlns:android="http://schemas.android.com/apk/res/android" >
  <item android:drawable="@drawable/shape_drawable_gradient_linear"/>
  <item android:drawable="@drawable/shape_drawable_gradient_radius"/>
</transition>
```

当这个drawabel作为View的背景使用时，可使用以下方式来实现View的淡入淡出切换。

```java
TransitionDrawable transitionDrawable = (TransitionDrawable)textView.getBackground();
transitionDrawable.startTransition(2000);
transitionDrawable.reverseTransition(2000);
```

### InsertDrawable

使用`<insert>`标签来实现，将Drawable镶嵌到自己的内部，当View希望自己的背景比实际区域小时可以使用InsertDrawable。

```xml
<inset xmlns:android="http://schemas.android.com/apk/res/android"
       android:insetBottom="15dp"
       android:insetLeft="15dp"
       android:insetRight="15dp"
       android:insetTop="15dp">
  <shape android:shape="rectangle">
    <solid android:color="#ff0000"/>
  </shape>
</inset>
```

### ScaleDrawable

使用`scale`标签来实现

### ClipDrawable

使用`clip`标签，它可以根据自己当前的等级来裁剪一个drawable。clipOrientation表示开始裁剪的方向，和gravity搭配使用。通过clipDrawable的setLevel方法设置裁剪的大小。等级0代表全部裁剪，10000代表不裁减，即等级越高drawable的显示范围越大。

下面这个例子会实现从上往下裁剪。

```xml
<clip xmlns:android="http://schemas.android.com/apk/res/android"
      android:clipOrientation="vertical"
      android:drawable="@drawable/image"
      android:gravity="bottom"/>
```