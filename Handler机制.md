### 为了Android设计只能通过UI线程更新UI

最根本愿意解决多线程并发。假设多个线程更新UI，很容易造成界面混乱，如果对更新界面的操作进行加锁，又会造成性能下降。

### 创建Handler对象

#### 主线程相关的Handler

```Java
Handler mHandler = new Handler(){
  	handMessage(){}
};
```

#### 子线程相关的Handler

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

#### 原理解析

首先是Handler无参的构造方法

```Java
public Handler() {  
    if (FIND_POTENTIAL_LEAKS) {  
        final Class<? extends Handler> klass = getClass();  
        if ((klass.isAnonymousClass() || klass.isMemberClass() ||
             	klass.isLocalClass()) &&  
                (klass.getModifiers() & Modifier.STATIC) == 0) {  
            	Log.w(TAG, "The following Handler class should be 							static or leaks might occur: " +  
                klass.getCanonicalName());  
        }  
    }  
  	//通过Looper.myLooper()方法取出Looper对象
    mLooper = Looper.myLooper();  
    if (mLooper == null) {  
        throw new RuntimeException(  
            "Can't create handler inside thread that has not called 					Looper.prepare()");  
    }  
    mQueue = mLooper.mQueue;  
    mCallback = null;  
}  
```

在创建Handler对象就会执行Looper.myLooper()方法取出mLooper对象。如果取出mLooper对象为空就会抛出“不能在线程中创建handler而没有调用Looper.prepare()方法”异常。那么为什么会出现这种异常呢？还是先来看一下Looper.myLooper()和Looper.prepare()的实现和作用。

Looper.myLooper()，获取Looper对象。

```Java
public static final Looper myLooper() {  
    return (Looper)sThreadLocal.get();  
} 
```

Looper.prepare()方法，创建looper对象

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

### Handler对象发送消息

Handler提供了很多发送消息的方法，除了`sendMessageAtFrontOfQueue()`方法之外，其它发送消息的方法都会辗转调用到`sendMessageAtTime()`方法。

```Java
public boolean sendMessageAtTime(Message msg, long uptimeMillis)  {  
    boolean sent = false;  
  	//获取消息队列
    MessageQueue queue = mQueue;  
    if (queue != null) {  
        //记录handler
      	msg.target = this;  
      	//消息入队
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

`snedMessageAtTime()`方法接受两个参数：arg0即消息对象，arg1发送时间，值为从开机到当前时间加上延时的毫秒数。`MessageQueue`即消息队列，这个类是在Looper的构造方法创建的，因此一个Looper也就对应一个MessageQueue对象，它的作用是将Message以队列的形式进行排列，这里的`queue.enqueueMessage()`方法就是将消息加入消息队列。这里`msg.target = this`将handler对象赋给message的target变量。

这里我们也看一下`queue.enqueueMessage()`的源码

```Java
final boolean enqueueMessage(Message msg, long when) {  
    if (msg.when != 0) {  
        throw new AndroidRuntimeException(msg + " This message is 
        		already in use.");  
    }  
    if (msg.target == null && !mQuitAllowed) {  
        throw new RuntimeException("Main thread not allowed to
        		quit");  
    }  
    synchronized (this) {  
        if (mQuiting) {  
            RuntimeException e = new RuntimeException(msg.target + 
            		" sending message to a Handler on a dead 
                    		thread");  
            Log.w("MessageQueue", e.getMessage(), e);  
            return false;  
        } else if (msg.target == null) {  
            mQuiting = true;  
        }  
        //
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

`Looper.loop()`

```Java
public static final void loop() {  
    Looper me = myLooper();  
    MessageQueue queue = me.mQueue;  
    while (true) {  
      	//取出消息。
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

`next()`方法取出消息如果当前MessageQueue中存在mMessages(即待处理消息)，就将这个消息出队，然后让下一条消息成为mMessages，否则就进入一个阻塞状态，一直等到有新的消息入队。

接下来就是`dispatchMessage`方法啦。

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