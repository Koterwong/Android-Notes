一般情况下，用户打开我们的App第一屏显示的是SplashActivity或者WelcomeActivity，在这个页面中我们一般用于展示我们App的基本信息和公司Log等，有时候也会获取一些广告信息和基本的资源操作等。但是现在有一个场景，用户不小心点击了两次返回键（点击两次返回键退出）退出了我们的App，在重新打开时，显示的第一屏仍是SplashActivity，这对用户来说体验是很不友好的。我们现在的解决办法是`moveTaskToBack`，没错就是只有这个方法。它的实现如下：

```java
/**
 * Move the task containing this activity to the back of the activity
 * stack.  The activity's order within the task is unchanged.
 *
 * @param nonRoot If false then this only works if the activity is the root
 *                of a task; if true it will work for any activity in
 *                a task.
 *
 * @return If the task was moved (or it was already at the
 *         back) true is returned, else false.
 */
public boolean moveTaskToBack(boolean nonRoot) {
    try {
        return ActivityManagerNative.getDefault().moveActivityTaskToBack(
                mToken, nonRoot);
    } catch (RemoteException e) {
        // Empty
    }
    return false;
}
```

在我们的程序退出前，我们可以调用该方法将我们的mainActiviry移动到返回栈，并且不会销毁当前的Activity，不执行onDestroy方法。那么当程序下次启动的时候，默认打开的就是我们MainActivity，当前这个需要保证我们的程序没有被系统回收。具体的实现方法如下：

```java
@Override
public boolean dispatchKeyEvent(KeyEvent event) {
  if (event.getAction() == KeyEvent.ACTION_DOWN
      && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
    moveTaskToBack(true);
    return true;
  }
  return super.dispatchKeyEvent(event);
}
```

我们在MainAcitivity的`dispatchKeyEvent` 中判断用户点击的是否是返回键，如果是，就调用`moveTaskToBack` 方法。这个方法也可以写到onKeyDown方法，前提onKeyDown能接收到键盘事件。但是不能写在 `onBackPressed`中，因为这个时候我们的activity就要被销毁了。