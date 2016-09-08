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

所谓View事件的分发就是对MotionEvent事件的分发过程，即当一个MotionEvent事件产生，系统会把这个事件传递给一个具体的View处理。

（2）事件分发的三个方法概述。

- `dispatchTouchEvent()` ：用于事件分发，当触摸事件传递给当前View的时候此方法一定会调用。返回结果受当前View的`onTouchEvent()`和下级View的`dispatchTouchEvent()`影响，表示是否消耗当前事件。	
- `onInterceptTouchEvent()`：在`dispatchTouchEvetn()`方法的内部调用，表示是否拦截当前事件，如果当前View拦截了某个事件，那么在这个事件序列中，该方法不会在调用。拦截的事件交给`onTouchEvent`处理，如果`onTouchEvent`返回false那在事件重新交给父View的`onTouchEvent`。
- `onTouchEvent()`：处理事件，表示是否消耗当前事件，如果不消耗，同一事件序列中，当前View无法收到事件。


当一个点击事件产生后，它的传递顺序遵循如下顺序 Activity -> Window ->  View 。事件总是先传递给Activity ，Activity在传递给Window，最后Window在传递给顶级的View。顶级View接受到事件之后就会对事件进行分发。

（3）关于事件分发的总结。

- 一个事件序列是指，手指从按下屏幕的那一刻起到离开屏幕结束。中间会有一个ACTION_DOWN事件，多个ACTION_MOVE事件和一个ACTION_UP事件。
- 一个View一旦决定拦截某个事件，那么整个事件必须交给该View去处理，View的`onInterceptTouchEvent`也不会在此调用，因为View一旦决定拦截某个事件，那么这个事件序列就会交给他处理 。不过我们可以通过特殊手段将View的onTouchEvent事件强制交给其他View来处理。
- 某个View一旦开始处理某个事件，它的`onTouchEvent`就会调用，如果View不对` ACTION_DOWN`进行消费，那么接下来的事件就不会交给该View去处理，父类的`ouTouchEvent`就会调用，以此类推。如果View不消耗除了`ACTION_DOWN`以外的事件，那么这个事件就会流失。
- ViewGroup默认不拦截任何事件。它的`onInterceptTouchEvent`默认返回false。
- View没有`onInterceptEvent`方法，一旦事件传递给他，那么它的`onTouchEvent`方法就会调用。
- View的`ouTouchEvent`一般会消耗当前事件（返回true），除非它是不可点击的（clickable和longclickable同时为false）。View的longclickable属性默认都为false，click属性分情况，比如Button的clickable属性默认为true，而TextView的clickable属性为false。
- View的`enable`属性，不影响onTouchEvent的返回值。那么它是disable状态的，只要它的clickable或者longclickable属性有一个为true，那么它的onTouchEvent就返回true。也就是说View的onTouchEvent的返回默认情况只和View的`clickable`和`longclickable`属性有关。
- 当一个View处理事件时，如果他设置了`onTouchListener`那么它的`onTouch`方法就会调用，如果onTouch方法返回返回false，那么它的onTouchEvent就会调用，如果返回true那么onTouchEvent就不会调用。在`onTouchEvent`中，如果View设置了onClickListener，那么它的`onClick`方法会调用。
- `onClick`发生的前提是View是可以点击的，并且收到了down和up的事件。
- `requestDisallowInterceptTouchEvent`会干预父View的事件分发过程，但是ACTION_DOWN除外。

### 5.1View的事件分发源码解析。

前面了解到View事件分发顺序为：Activity -> Window ->View。

ViewGroup的`dispatchTouchEvent` 方法，关于事件是否拦截方面做的处理。

```java
// Handle an initial down.
if (actionMasked == MotionEvent.ACTION_DOWN) {
    // Throw away all previous state when starting a new touch gesture.
    // The framework may have dropped the up or cancel event for the previous gesture
    // due to an app switch, ANR, or some other state change.
    cancelAndClearTouchTargets(ev);
    resetTouchState();
}
// Check for interception.
final boolean intercepted;
if (actionMasked == MotionEvent.ACTION_DOWN
        || mFirstTouchTarget != null) {
    final boolean disallowIntercept = (mGroupFlags & FLAG_DISALLOW_INTERCEPT) != 0;
    if (!disallowIntercept) {
        intercepted = onInterceptTouchEvent(ev);
        ev.setAction(action); // restore action in case it was changed
    } else {
        intercepted = false;
    }
} else {
    // There are no touch targets and this action is not an initial down
    // so this view group continues to intercept touches.
    intercepted = true;
}
```

当ViewGroup不拦截事件时，在`dispatchTouchEvent`中对点击事件分发的源码如下：

```java
final View[] children = mChildren;
for (int i = childrenCount - 1; i >= 0; i--) {
    final int childIndex = customOrder
            ? getChildDrawingOrder(childrenCount, i) : i;
    final View child = (preorderedList == null)
            ? children[childIndex] : preorderedList.get(childIndex
    // If there is a view that has accessibility focus we want it
    // to get the event first and if not handled we will perform a
    // normal dispatch. We may do a double iteration but this is
    // safer given the timeframe.
    if (childWithAccessibilityFocus != null) {
        if (childWithAccessibilityFocus != child) {
            continue;
        }
        childWithAccessibilityFocus = null;
        i = childrenCount - 1;
    }
    if (!canViewReceivePointerEvents(child)
            || !isTransformedTouchPointInView(x, y, child, null)) 
        ev.setTargetAccessibilityFocus(false);
        continue;
    }
    newTouchTarget = getTouchTarget(child);
    if (newTouchTarget != null) {
        // Child is already receiving touch within its bounds.
        // Give it the new pointer in addition to the ones it is h
        newTouchTarget.pointerIdBits |= idBitsToAssign;
        break;
    }
    resetCancelNextUpFlag(child);
    if (dispatchTransformedTouchEvent(ev, false, child, idBitsToAs
        // Child wants to receive touch within its bounds.
        mLastTouchDownTime = ev.getDownTime();
        if (preorderedList != null) {
            // childIndex points into presorted list, find origina
            for (int j = 0; j < childrenCount; j++) {
                if (children[childIndex] == mChildren[j]) {
                    mLastTouchDownIndex = j;
                    break;
                }
            }
        } else {
            mLastTouchDownIndex = childIndex;
        }
        mLastTouchDownX = ev.getX();
        mLastTouchDownY = ev.getY();
        newTouchTarget = addTouchTarget(child, idBitsToAssign);
        alreadyDispatchedToNewTouchTarget = true;
        break;
    }
}  
```