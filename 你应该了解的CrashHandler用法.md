Android应用开发不可避免的会发生crash，无论我们的程序写的多么完美，总是无法避免crash的发生，可能是由于系统的bug，也可能是不充分的机型适配和糟糕的网络情况。当Crash发生的时候，系统会kill掉我们的程序，现象就是闪退或者提示用户程序已经停止运行，造成不好的用户体验。对于我们开发这来说，我们要尽可能的避免crash的发生，即使Crash发生我们也能够即时的获取crash信息，并修复我们程序的问题。

在Thread类中提供了这样一个方法`Thread.setDefaultUncaughtExceptionHandler();`，根据字面的上的意思我们可以了解到它的作用应该是设置默认的异常处理器。我们可以看一下这个方法默认实现。

```java
/**
 * Sets the default uncaught exception handler. This handler is invoked in
 * case any Thread dies due to an unhandled exception.
 *
 * @param handler
 *            The handler to set or null.
 */
public static void setDefaultUncaughtExceptionHandler(UncaughtExceptionHandler handler) {
    Thread.defaultUncaughtHandler = handler;
}
```

可以看到，该方法可以设置默认的异常信息处理器，当crash发生的时候系统会回调`UncaughtExceptionHandler`中的`uncaughtException`方法。那么我们就可以在程序崩溃的时候，收集崩溃日志，将崩溃日志写入到sd卡并且在适当的时候上传到服务器，这样我们开发人员就可以根据崩溃日志尽快修复bug。系统默认处理崩溃信息是很不友好的，我们可以在崩溃发生时，弹出对话框告诉用户程序发生了异常需要推出。甚至我们可以通过一些手段在程序推出的时候，马上重启我们的程序到值指定的页面。

这里提供了一个默认的异常处理方案，可以参考。

```java
public class CrashHandler implements Thread.UncaughtExceptionHandler {
  private final String TAG = this.getClass().getSimpleName();
  private static final boolean DEBUG = true;
  private static final String DATA_FORMAT = "yyyy-MM-dd_HH:mm";
  //可以设置自己的保存路径
  private static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath()
      + "/log";

  private String mPath = null;
  private Context mContext;
  private static final String FILE_NAME = "crash";
  private static final String FILE_NAME_SUFFIX = ".trace";

  private static CrashHandler sCrashHandler = new CrashHandler();
  private Thread.UncaughtExceptionHandler mDefaultCrashHandler;

  private CrashHandler() {}

  public CrashHandler get() {
    return sCrashHandler;
  }

  public void init(Context context) {
    this.init(context, PATH);
  }

  public void init(Context context, String filePath) {
    if (filePath == null) {
      throw new IllegalStateException("need to specify the exception information save the path");
    }
    mPath = filePath;
    mContext = context.getApplicationContext();
    mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
    Thread.setDefaultUncaughtExceptionHandler(sCrashHandler);
  }

  @Override public void uncaughtException(Thread thread, Throwable throwable) {
    throwable.printStackTrace();
    try {
      dumpExceptionToSDCard(throwable);
      uploadExceptionToService(throwable);
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (mDefaultCrashHandler != null) {
      mDefaultCrashHandler.uncaughtException(thread, throwable);
    } else {
      Process.killProcess(Process.myPid());
    }
  }

  private void dumpExceptionToSDCard(Throwable throwable) throws IOException {
    File dir = new File(mPath);
    if (!dir.exists()) {
      dir.mkdirs();
    }
    long current = SystemClock.currentThreadTimeMillis();
    String time = new SimpleDateFormat(DATA_FORMAT).format(new Date(current));
    File file = new File(mPath, FILE_NAME + time + FILE_NAME_SUFFIX);
    try {
      PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file)));
      printWriter.print(time);
      dumpPhoneInfo(printWriter);
      printWriter.println();
      printWriter.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void dumpPhoneInfo(PrintWriter printWriter) throws PackageManager.NameNotFoundException {
    PackageManager packageManager = mContext.getPackageManager();
    PackageInfo packageInfo = packageManager.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
    printWriter.println("AppVersion:" + packageInfo.versionCode);
    printWriter.println("OS Version:" + Build.VERSION.SDK_INT);
    printWriter.println("Vendor:" + Build.MANUFACTURER); //手机制造商
    printWriter.println("Model:" + Build.MODEL );  //手机型号
    printWriter.println("CPU ABI:" + Build.CPU_ABI);
  }

  private void uploadExceptionToService(Throwable throwable) {
    // if necessary ,upload the information to server
  }
}
```