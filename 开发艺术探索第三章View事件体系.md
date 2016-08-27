### View事件体系

#### 1. View的位置参数

View的位置属性，top、left、right、bottom，是相对于自己的父容器来取值的，在父容器执行完layout方法即可使用getTop、getLeft、getRight、getBottom获取它们的值。另外通过这四个属性可以方便得道View的width和height属性，与之对应的方法分别为getWidth()和getHeight()方法。

在Android3.0以后，View新增了`x`、`y` 、`translationX` 、`translationY` 属性，其中`x` 、`y` 分别对应了View左上角的坐标，这个坐标同样是相对于父容器的，并且它们之间的关系可以通过`x = left + translationX `公式表示  。需要注意的是，View在平移的过程中，top和left表示的是原始View的位置信息，其值不会反生改变，改变的是`x`、`y` 、`translationX`    、`translationY` 。

如果想获取View在屏幕上的坐标`view.getLocationOnScreen(int[] arr);` 。

#### 2. MotionEvent、TouchSlop、VelocityTracker、GestrueDetector

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
//获取速度，这里需要注意从右想做滑动时速度为负
velocityTracker.getXVelocity();
velocityTracker.getYVelocity();
//当不使用它的时候，进行回收。（场景？）
velocityTracker.clear();
velocityTracker.recycle();
```

（4）GestureDetector：辅助检验用户的单击、滑动、长按、双击行为。

使用GestureDetector并不复杂，只需要创建GestureDetector对象，并实现onGestureListener接口，最后需要接管View的onTouchEvent事件。

```java
GestureDetector gestureDetector = new GestureDetector(context, this);
//解决长按屏幕后无法拖动现象。
gestureDetector.setIsLongpressEnabled(false);
//接管onTouchEvent事件
@Override
public boolean onTouchEvent(MotionEvent event) {
    boolean consume = gestureDetector.onTouchEvent(event);
    return consume;
}
```
onDestureListener有一系列的回调方法，我们可根据回调方法，处理不同的View的事件。

- onSingleTapUp：单击行为，相当于ACTION_DOWN和ACTION_UP的触发。
- onFling：触摸屏幕，快速滑动松开。一个ACTION_DOWN，多个ACTION_MOVE和一个ACTION_UP触发。
- onScorll：拖动事件。一个ACTION_DOWN，多个ACTION_MOVE和一个ACTION_UP触发。
- onLoogPress：长按。
- onDoubleTap：双击。

在日常的开发中，完全可以不使用onDestrueDetector，监听相关滑动完全可以在onTouchEvent中实现。如果要监听用户的双击行为，可以使用onDoubleTap实现。

#### 3.View的滑动。

（1）使用ScrollTo/ScrollBy

View类提供了`ScrollTo` 和`ScrollBy` 来实现View的滑动，需要注意的是，它们实现View的滑动**实现的是View内容区域的滑动，View本身的位置并没有经过滑动，也就是说不可能将View滑动附近View所在的区域。**

使用`ScrollTo` 和`ScrollBy` 实现View的滑动，需要注意两个重要的参数，`mSrollX`和`mSrollY`这连个参数表示View内容区域滑动的偏移量，仅当View的内容区域在View原位置的左边缘的时候这个值为正。

```java
/**
 * 使View滑动的偏移量。如果要实现内容区域向右滑动，那么传入参数的X的值为负。
 *
 * Set the scrolled position of your view. This will cause a call to
 * {@link #onScrollChanged(int, int, int, int)} and the view will be
 * invalidated.
 * @param x the x position to scroll to
 * @param y the y position to scroll to
 */
public void scrollTo(int x, int y) {
    if (mScrollX != x || mScrollY != y) {
        int oldX = mScrollX;
        int oldY = mScrollY;
        mScrollX = x;   //可以看出mScrollX就等于我们传入的参数。   
        mScrollY = y;
        invalidateParentCaches();
        onScrollChanged(mScrollX, mScrollY, oldX, oldY);
        if (!awakenScrollBars()) {
            postInvalidateOnAnimation();
        }
    }
}
/**
 * 实现相对位置的滑动。同样是向右传负
 *
 * Move the scrolled position of your view. This will cause a call to
 * {@link #onScrollChanged(int, int, int, int)} and the view will be
 * invalidated.
 * @param x the amount of pixels to scroll by horizontally
 * @param y the amount of pixels to scroll by vertically
 */
public void scrollBy(int x, int y) {
    scrollTo(mScrollX + x, mScrollY + y);
}
```

（2）使用动画

主要通过View的`translationX`和`translationY`属性来实现View的平移。需要注意的是使用View动画，是对View的影响操作，并不能改变View的属性。使用属性动画，只能兼容到3.0以上，要向兼容到低版本需要使用nineOldAndroid动画库，但是在3.0以下实现的还是View动画。

这里提供一个使用View动画平移之后，同样能响应点击的方案。可以在View平移的目标位置，新建一个和View一模一样的View，在动画执行完成后，将旧的View设置为GONE。

（3）改变布局参数，LayoutParams。

通过改变View的LeftMargin参数，实现View的滑动。

> 这个本提到的实现View的滑动方法不是很全，可参考Android群英传第五章，使用Layout方法和ViewDragHelper。

#### 4.Scroller：弹性滑动对象。

在我们自定义View的过程中，有时候处理滑动事件，当手指松开的时，我们需要让View移动到指定的位置。如果使用ScrollTo/ScrollBy或者其他方法（layout方法），那么这个时候我们就需要使用弹性滚动对象Scroller。

Scroller的基本使用：

```java
Scroller scroller = new Scroller(getContext());

private void smoothScrollTo(int dx, int dy, int duration) {
    //在duration的时间内，缓慢滑动到dx + getScrollX,dy + getScrollY.
    scroller.startScroll(getScrollX(), getScrollY(), dx, dy, duration);
    //Scroller本身不能使View进行滑动，需要使View重绘。
    invalidate();
}
//重写View的computeScroll方法
@Override public void computeScroll() {
    super.computeScroll();
    if(scroller.computeScrollOffset()){
        scrollTo(scroller.getCurrX(),scroller.getCurrY());
        postInvalidate();
    }
}
```

以上就是Scroller的经典使用场景，我们调用srcoller的`startScroll()`方法来让View开始弹性滚动，其他startScroll方法并没有让View弹性滚动的功能，下面来看startScroll的实现。

```java
/**
 * Start scrolling by providing a starting point, the distance to travel,
 * and the duration of the scroll.
 */
public void startScroll(int startX, int startY, int dx, int dy, int duration) {
    mMode = SCROLL_MODE;
    mFinished = false;
    mDuration = duration;
    mStartTime = AnimationUtils.currentAnimationTimeMillis();
    mStartX = startX;
    mStartY = startY;
    mFinalX = startX + dx;
    mFinalY = startY + dy;
    mDeltaX = dx;
    mDeltaY = dy;
    mDurationReciprocal = 1.0f / (float) mDuration;
}
```

以上就是`startScroll()`的实现，可以看到，它只是对View的滑动参数做了记录，并没有实现滑动，而真正实现滑动的就是我们手动调用`invalidate()`方法来使View重新重绘，在View的`onDraw()`方法中又会调用到View默认预留的`computeScroll()`方法。而这个时候我们只需要重写`computeScroll()`方法，在`computeScroll()`方法中去调用`scroller.computeScrollOffset()`方法判断scroller对象是否完成滚动，如果未完成，继续调用srcollTo方法，参数需要传入scroller对象所计算的需要滑动的距离  ，接下来继续调用`postInvalidate`  方法，来引起View的重绘，不断重复这个过程知道Scroller滑动完成。Scroller对像的作用就是不断的引起View的重绘，并计算出View重绘时所需要的滑动的距离。其中还有一个重要的方法就是Scroller计算滑动距离是否完成的方法。我们来看一下`scroller.computeScrollOffset()`的实现。

```java
/**
 * Call this when you want to know the new location.  If it returns true,
 * the animation is not yet finished.
 */ 
public boolean computeScrollOffset() {
    if (mFinished) {
        return false;
    }
 	//计算滑动经过的时间，如果时间小时滑动需要执行的时间，那么继续计算mCurrX、mCurrY。
  	int timePassed = (int)(AnimationUtils.currentAnimationTimeMillis() - mStartTime)
    if (timePassed < mDuration) {
        switch (mMode) {
        case SCROLL_MODE:
            //通过默认插补器获取需要进行滑动的距离。
            final float x = mInterpolator.getInterpolation(timePassed * mDurationReciprocal);
            mCurrX = mStartX + Math.round(x * mDeltaX);
            mCurrY = mStartY + Math.round(x * mDeltaY);
            break;
        case FLING_MODE:
			......//这里不再分析
    }
    else {
        mCurrX = mFinalX;
        mCurrY = mFinalY;
        mFinished = true;
    }
    return true;
}
```

看到这里，Scroller的设计简直不要太棒。我们也能很明显的看出来Scroller对象真正职责，它会根据我们需要滑动的距离和时间计算出下一次小幅度滑动的距离，并配合View的`computeScroll`方法完成View的滑动。

实现View的弹性滑动还可以使用动画和采用一定的延时策略这里就不再意义做介绍啦。

### 5.View的事件分发机制

（1）View事件分发的概述。

所谓View事件的分发就是对MotionEvent事件的分发过程，即当一个MotionEvent事件产生，系统会把这个事件传递给一个具体的View。

（2）事件分发的三个方法概述。

- `onDispatchTouchEvent()`：	
- `onInterceptTouchEvent()`：
- `onTouchEvent()`：