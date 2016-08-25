### View事件体系

##### 1. View的位置参数

View的位置属性，top、left、right、bottom，是相对于自己的父容器来取值的，在父容器执行完layout方法即可使用getTop、getLeft、getRight、getBottom获取它们的值。另外通过这四个属性可以方便得道View的width和height属性，与之对应的方法分别为getWidth()和getHeight()方法。

在Android3.0以后，View新增了`x`、`y` 、`translationX` 、`translationY` 属性，其中`x` 、`y` 分别对应了View左上角的坐标，这个坐标同样是相对于父容器的，并且`x = left + translationX `  ，需要注意的是，View在平移的过程中，top和left表示的是原始View的位置信息，其值不会反生改变，改变的是`x`、`y` 、`translationX`    、`translationY` 。

如果想获取View在屏幕上的坐标`view.getLocationOnScreen(int[] arr);` 。

##### 2. MotionEvent、TouchSlop、VelocityTracker、GestrueDetector、Scroller

（1）MotionEvent：触控事件

`getX` 、`getY` 获取相对于当前View的触控坐标。

`getRawX()` 、`getRawY()` 获取相对于手机屏幕触控坐标。

（2）TouchSlop：系统识别的被认为是滑动的最小距离.

我们可通过`ViewConfiguration.get(context).getScaledTouchSlop()`获取该常量的值。我们可以使用该常量在自定义View处理获取动事件时过滤掉小于这个值的事件进而提升用户体验。我们也可以在系统的源码中找到这个常量的定义，framework/base/core/res/res/values/config.xml，其中config_viewConfigurationTouchSlop就是系统定义的常量，这个常量的值一般为8dp。

（3）VelocityTracker：追踪手指在滑动过程中的速度。

```java
//获取对象
VelocityTracker velocityTracker = VelocityTracker.obtain();
velocityTracker.addMovement(event);
//在获取速度前必须调用computeCirrentVelocity方法，获取当前速度的计算时间。
velocityTracker.computeCurrentVelocity(1000);
velocityTracker.getXVelocity();
velocityTracker.getYVelocity();
//当不使用它的时候，进行回收。（场景？）
velocityTracker.clear();
velocityTracker.recycle();
```

（4）GestureDetector：辅助检验用户的单击、滑动、长按、双击行为。

