#### 思考：为了Android设计只能通过UI线程更新UI？

最根本的原因时为了解决多线程并发。假设多个线程更新UI，很容易造成界面混乱，Android的UI控件不是线程安全的，如果对更新界面的操作进行加锁，又会造成性能下降，UI访问过于复杂。

如果在子线程中更新UI那么就会抛出异常，这点可以在ViewRootImpl中的chechThread()得道验证。

```Java
void checkThread(){
  	if(mThread != Thread.currentThread()){
  		throw new CalledFromWrongThreadException(Only the original ....);
	}
}
```

#### 主线程和子线程创建Handler对象的方式。

主线程相关的Handler

```Java
Handler mHandler = new Handler(){
  	handMessage(){}
};
```

子线程相关的Handler

```Java
new Thread(new Runnable() {  
  	@Override  
  	public void run() {
      	Looper.prepare();
    	mHandler = new Handler(){
		};
      	Looper.loop();
  	}  
}).start();  
```

#### 原理解析，为什么子线程和主线程创建Handler对象的方式不一样呢？

首先是Handler无参的构造方法

```Java
public Handler() {  
    if (FIND_POTENTIAL_LEAKS) {  
        final Class<? extends Handler> klass = getClass();  
        if ((klass.isAnonymousClass() || klass.isMemberClass() ||
             	klass.isLocalClass()) &&  
                (klass.getModifiers() & Modifier.STATIC) == 0) {  
            	Log.w(TAG, "The following Handler class should be static 
                      	or leaks might occur: " +  
                klass.getCanonicalName());  
        }  
    }  
  	//通过Looper.myLooper()方法取出Looper对象
    mLooper = Looper.myLooper();  
    if (mLooper == null) {  
        throw new RuntimeException(  
            "Can't create handler inside thread that has not called Looper.prepare()");  
    }  
    mQueue = mLooper.mQueue;  
    mCallback = null;  
}  
```

在创建Handler对象就会执行Looper.myLooper()方法取出mLooper对象。如果取出mLooper对象为空就会抛出“不能在线程中创建handler而没有调用Looper.prepare()方法”异常。这也印证了为什么不能在没有Looper对象的情况下创建Handler？

还是先来看一下Looper.myLooper()和Looper.prepare()的实现和作用。

Looper.myLooper()，调用ThreadLocal的get()方法，回去当前线程的Looper对象。

```Java
public static final Looper myLooper() {  
    return (Looper)sThreadLocal.get();  
} 
```

Looper.prepare()方法，创建looper对象，并设置给ThreadLocal。

```Java
public static final void prepare() {  
  	//如果已经存在Looper对象，抛出异常
    if (sThreadLocal.get() != null) {  
        throw new RuntimeException("Only one Looper may be created per thread");  
    }  
  	//设置looper对象。
    sThreadLocal.set(new Looper());  
}  
```

结论：由此可以看出，创建Handler对象之前，必须先创建Looper对象。

#### 那么主线程为什么可以直接创建Handelr对象？

这是因为在ActivityThread的main方法中，系统已经自动帮我们调用了Looper.prepare()方法。

```Java
public static void main(String[] args) {  
    SamplingProfilerIntegration.start();  
    CloseGuard.setEnabled(false);  
    Environment.initForCurrentUser();  
    EventLogger.setReporter(new EventLoggingReporter());  
    Process.setArgV0("<pre-initialized>");  
    //该放又会去调用Looper.prepare()方法。
  	Looper.prepareMainLooper();  
    ActivityThread thread = new ActivityThread();  
    thread.attach(false);  
    if (sMainThreadHandler == null) {  
        sMainThreadHandler = thread.getHandler();  
    }  
    AsyncTask.init();  
    if (false) {  
        Looper.myLooper().setMessageLogging(new 		
                     LogPrinter(Log.DEBUG, "ActivityThread"));  
    }  
    Looper.loop();  
    throw new RuntimeException(
      			"Main thread loop unexpectedly exited");  
} 
```

#### Handler对象发送消息（send和post（Runnable））

Handler提供了很多发送消息的方法，除了`sendMessageAtFrontOfQueue()`方法之外，其它发送消息的方法都会辗转调用到`sendMessageAtTime()`方法。

```Java
public boolean sendMessageAtTime(Message msg, long uptimeMillis)  {  
    boolean sent = false;  
  	//获取消息队列
    MessageQueue queue = mQueue;  
    if (queue != null) {  
        //记录handler
      	msg.target = this;  
      	//将Message消息对象将MessageQueue消息队列
        sent = queue.enqueueMessage(msg, uptimeMillis);  
    }  
    else {  
        RuntimeException e = new RuntimeException(  
            this + " sendMessageAtTime() called with no mQueue");  
        Log.w("Looper", e.getMessage(), e);  
    }  
    return sent;  
}  
```

MessageQueue即消息队列，这个类是在Looper的构造方法创建的，因此一个Looper也就对应一个MessageQueue对象，它的作用是将Message以单链表的形式排列，这里的`queue.enqueueMessage()`方法就是将消息加入消息队列。这里`msg.target = this`将handler对象赋给Message的target变量。

---

这里我们也看一下MessageQueue的enqueueMessage()方法的源码

```Java
final boolean enqueueMessage(Message msg, long when) {  
	...
    synchronized (this) { 
      	...
        //enpueueMessage其实就向单链表中插入操作。
        msg.when = when;  
        Message p = mMessages;  
        if (p == null || when == 0 || when < p.when) {  
            msg.next = p;  
            mMessages = msg;  
            this.notify();  
        } else {  
            Message prev = null;  
            while (p != null && p.when <= when) {  
                prev = p;  
                p = p.next;  
            }  
            msg.next = prev.next;  
            prev.next = msg;  
            this.notify();  
        }  
    }  
    return true;  
}  
```

Message对象加入MessageQueue后，就是Loop发挥作用的时候，loop方式是一个死循环，而MessageQueue的next()是一个阻塞式方法，没有消息就等待。

---

Looper.loop()：轮询MessageQueue，也是Looper中最重要的一个方法了。它会调用MessageQueue的next方法，没有就阻塞。

```Java
public static final void loop() {  
    Looper me = myLooper();  
    MessageQueue queue = me.mQueue;  
    while (true) {  
      	//取出消息，没有阻塞等待。
        Message msg = queue.next(); // might block  
        if (msg != null) {  
            if (msg.target == null) {  
                return;  
            }  
            if (me.mLogging!= null) me.mLogging.println(  
                    ">>>>> Dispatching to " + msg.target + " "  
                    + msg.callback + ": " + msg.what  
                    );  
          	//调用handler自己的dispatchMessage()方法
            msg.target.dispatchMessage(msg);  
            if (me.mLogging!= null) me.mLogging.println(  
                    "<<<<< Finished to    " + msg.target + " "  
                    + msg.callback);  
            msg.recycle();  
        }  
    }  
}  
```

如果loop轮询到消息对象，就会执行msg.target.dispatchMessage(msg)这行代码。msg.target也就是发送消息的Handle对象，然后调用Handelr的dispatchMessage方法，那么hander就会处理这条消息啦。但是这里需要注意的是，Handler的dispatchMessage方法是在创建Handler是所使用的Looper中执行的，这样就成功的将代码逻辑切换到指定的线程中了。

```Java
public void dispatchMessage(Message msg) {  
    if (msg.callback != null) {  
        handleCallback(msg);  
    } else {  
        if (mCallback != null) {  
            if (mCallback.handleMessage(msg)) {  
                return;  
            }  
        }  
        handleMessage(msg);  
    }  
} 
```
dispatchMessage就是Handler来处理这个消息了。

首先，它会判断msg的callback是否为空，如果不为空就会交给handleCallback处理。msg的Callback对象就是Handler的poat方法所传递出来的Runnable。handleCallback实现也很简单，如下所示。

```java
private static void handleCallback(Message msg){
  	message.callback.run();
}
```

其次，检查mCallback是否为空，不为空就调用mCallback的handerMessage方法来处理。Callback接口定义如下所示。

```java
public interface Callback{
  	public boolean handleMessage(Message msg);		
}
```

Callback接口的好处就是我们可以用来穿件一个handler而不用去派生它的子类。

最后，才会去hander自己的handleMessage方法。

---

以上就是Android消息处理机制的全过程啦。

