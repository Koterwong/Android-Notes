首先分析execate()方法，execate()方法又会调用executeOnExecutor()方法。它们的源码如下。

```java
 @MainThread
 public final AsyncTask<Params, Progress, Result> execute(Params... params) {
     return executeOnExecutor(sDefaultExecutor, params);
 }
 
@MainThread
public final AsyncTask<Params, Progress, Result> executeOnExecutor(Executor exec,
        Params... params) {
    if (mStatus != Status.PENDING) {
        switch (mStatus) {
            case RUNNING:
                throw new IllegalStateException("Cannot execute task:"
                        + " the task is already running.");
            case FINISHED:
                throw new IllegalStateException("Cannot execute task:"
                        + " the task has already been executed "
                        + "(a task can be executed only once)");
        }
    }
    mStatus = Status.RUNNING;
  	//onPreExecute()方法最新执行！
    onPreExecute();
  	//mWorker记录传递参数。
    mWorker.mParams = params;
  	//串行的线程池开始执行。参数mFuture充当一个Runnable，它内部有mWorker。
  	//即mFuture可以理解为，封装了params的Runnble对象。
    exec.execute(mFuture);
    return this;
}
```

execute()传递给executeOnExecutor()一个sDefaultExecutor参数，它其实是一个串行的线程池，一个进程中的所有的AsyncTask全部在这个串行的线程中排队执行。

下面分析这个串行的线程池的执行过程，它在AsyncTask中的具体实现如下。

```Java
//串行的线程池。
public static final Executor SERIAL_EXECUTOR = new SerialExecutor();
private static volatile Executor sDefaultExecutor = SERIAL_EXECUTOR;

private static class SerialExecutor implements Executor {
    final ArrayDeque<Runnable> mTasks = new ArrayDeque<Runnable>();
    Runnable mActive;
    public synchronized void execute(final Runnable r) {
        mTasks.offer(new Runnable() {
            public void run() {
                try {
                  	//mFuture的run()方法执行，它会调用mWork的call方法，
                  	//call方法最终会在线程池中执行。
                    r.run();
                } finally {
     				//开始执行AsyncTask的任务。
                  	scheduleNext();
                }
            }
        });
      	//如果这个时候没有正在活动的AsyncTask任务，那么scheduleNext会执行下一个任务。
      	//直到所有任务执行完成。
        if (mActive == null) {
            scheduleNext();
        }
    }
    protected synchronized void scheduleNext() {
        if ((mActive = mTasks.poll()) != null) {
          	//执行任务的线程池。
            THREAD_POOL_EXECUTOR.execute(mActive);
        }
    }
}
```

mWork是AsyckTask中继承了Callable的抽象类WorkerRunnable的实例，内部用数据mParams数组记录了传递进来的参数。mFuture是系统为我们封装的带有Params的Runnable。上面说道，mFuture的run()方法执行，它会调用mWork的call方法，那么它最终会在线程池中执行。接下来看以下mWork的call方法，他在AsyncTask的构造方法中初始化。

```java
private final WorkerRunnable<Params, Result> mWorker;
private final FutureTask<Result> mFuture;
/**
 * Creates a new asynchronous task. This constructor must be invoked on the UI thread.
 */
public AsyncTask() {
    mWorker = new WorkerRunnable<Params, Result>() {
        public Result call() throws Exception {
          	//  mTaskInvoked设置为(true)，表示当前的任务已经执行。
            mTaskInvoked.set(true);
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            //noinspection unchecked
          	//call方法执行，doInBackground方法就会被调用。
            Result result = doInBackground(mParams);
            Binder.flushPendingCommands();
          	//将doInbackground的返回结构传递给postResult();
            return postResult(result);
        }
    };
    mFuture = new FutureTask<Result>(mWorker) {
        @Override
        protected void done() {
            try {
                postResultIfNotInvoked(get());
            } catch (InterruptedException e) {
                android.util.Log.w(LOG_TAG, e);
            } catch (ExecutionException e) {
                throw new RuntimeException("An error occurred while executing doInBackground()",
                        e.getCause());
            } catch (CancellationException e) {
                postResultIfNotInvoked(null);
            }
        }
    };
}
```

处理doInBackground返回的值postResult方法实现。

```java
private Result postResult(Result result) {
    @SuppressWarnings("unchecked")
    Message message = getHandler().obtainMessage(MESSAGE_POST_RESULT,
            new AsyncTaskResult<Result>(this, result));
    message.sendToTarget();
    return result;
}
```

这里处理就是通过sHandler发送一个MESSAGE_POST_RESULT的消息。sHandler的定义如下所示。

```java
private static class InternalHandler extends Handler {
    
  public InternalHandler() {
        super(Looper.getMainLooper());
    }
    @SuppressWarnings({"unchecked", "RawUseOfParameterizedType"})
    @Override
    public void handleMessage(Message msg) {
        AsyncTaskResult<?> result = (AsyncTaskResult<?>) msg.obj;
        switch (msg.what) {
            case MESSAGE_POST_RESULT:
                // There is only one result
                result.mTask.finish(result.mData[0]);
                break;
            case MESSAGE_POST_PROGRESS:
                result.mTask.onProgressUpdate(result.mData);
                break;
        }
    }
}
```

sHandler是一个静态的对象，所以必须在主线程中初始化，而在ActivityThread的main方法中已经做了系统已经为我们做了初始化。当sHandler接受到MESSAGE_POST_RESULT就会调用finish方法。

```java
private void finish(Result result) {
    if (isCancelled()) {
        onCancelled(result);
    } else {
        onPostExecute(result);
    }
    mStatus = Status.FINISHED;
}
```

finish方法中，如果AsyncTask取消了，那么就会回调onCancelled()方法，否则就会调用onPostExecute()方法，也就是这两个方法只会回调一个。

---

以上分析，AsyncTask的确是串行运行的(3.0以下为并行)，为了能在3.0以上使用并行，我们可以调用executeOnExecutor()。如下所示，让其在AsyncTask.THREAD_POOL_EXECUTOR线程运行。

```java
new MyAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
new MyAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
new MyAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
new MyAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
```

