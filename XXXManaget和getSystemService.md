###Context类提供的getSystemService()

### 获取电话相关。Telephoney ###

	TelephonyManager tm = (TelephonyManager) getSystemService
				(TELEPHONY_SERVICE);
	//需要read phone stage权限
	// 获取sim卡序列号
	String simSerialNumber = tm.getSimSerialNumber();

	//监听来电。系统会启动一个服务 去电系统才发广播(ip拨号器)
	tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
	//注册监听器。继承PhoneStateListener
	class MyListener extends PhoneStateListener 

### LBS，基于位置的服务 LocationManager ###		

	//粗糙和精准的网络定位。联网最好加上Internet权限
	android.permission.ACCESS_FINE_LOCATION
	android.permission.ACCESS_COARSE_LOCATION
	LocationManager locationManager = (LocationManager)
				getSystemService(Context.LOCATION_SERVICE);
	//设置定位标准/方式
	Criteria criteria = new Criteria();
	criteria.setCostAllowed(true); //设置是否允许产生费用
	criteria.setSpeedRequired(true);//设置是否对速度敏感
	criteria.setAltitudeRequired(true);//设置是否海拔敏感 
	criteria.setAccuracy(Criteria.ACCURACY_FINE); //设置准确的定位 
	//获取可用的定位。
	String provider = locationManager.getBestProvider(criteria, true);

### 手机震动Vibrator ### 
	
	需要权限 android.permission.VIBRATE

	Vibrator vibrator = (Vibrator) getSystemService	(VIBRATOR_SERVICE);
	// vibrator.vibrate(2000);震动两秒
	
	//开始震动，等待1秒->震动2秒->等待1秒->震动3秒。 -1表示循环一次
	//参数2大于0 表示从第几个位置开始循环
	vibrator.vibrate(new long[] { 1000, 2000, 1000, 3000 }, -1);

### 获得超级管理员 ###

	DevicePolicyManager  manager = (DevicePolicyManager) context.
			getSystemService(Context.DEVICE_POLICY_SERVICE);
	manager.wipeData(0);

					
###获取系统窗体服务  ###。

- Window层的添加，删除view。
- 在其他App上显示自己的浮窗。
- 并且可以相应点击事件
- WindowManager.LayoutParams
	
	### 运用Toast的原理：
	1. 获取windows的管理者，并设置 WindowManager.LayoutParams的参数。
	2. 需要一个View 。直接new 或者通过布局加载器，加载一个布局到view。
	3. 添加，更新，移除View	。
	4. 

		 	WindowManager windowManager = (WindowManager) getSystemService
					(WINDOW_SERVICE);

			
			// 将view添加在屏幕上(Window)
			windowManager.addView(view, params);
			// 从window中移除view
			windowManager.removeView(view);
### 进程管理器（任务管理器）ActivityManager   ###
	<uses-permission android:name="android.permission.GET_TASKS"/>																										
	am = (ActivityManager) context.getSystemService
			(Context.ACTIVITY_SERVICE);
	//获取正在运行进程
	getRunningAppprocesses()；
	//获取系统Ram. c思想
	getMemoryInfo(memory);
	//获取正在运行的服务，数量是100个
	am.getRunningServices(100)
	//清理后台进程。权限
	killBackgroundProcesses()

###  包管理器（程序管理器） ###
		
	context.getPackageManager()；
	//获取所有已安装的包信息
	packageManager.getInstalledPackages(0)

### 获取屏幕的宽高 ###
	
    getWindowManager().getDefaultDisplay().getWidth();

### 警报管理器 ###
	具有唤醒Cpu的功能，而Timer在Cpu休眠时不能唤醒Cpu执行定时任务。
	AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

### 音频管理器 ###

	AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);								

### 剪切板管理器 ###	

	ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);		
	ClipData clipData = ClipData.newPlainText("msg", info);
    manager.setPrimaryClip(clipData);																												
### 连接管理器 ###

	<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>					
	ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);	

### 输入法管理器 ###

	InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);																														
### 键盘管理器 ###

	KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);																																	
### 通知管理器 ###

	NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);																												
### wifi管理器 ###

	<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>																																	
	WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE); 																																	
### 传感器 ###
	
	SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);																																			
													
																																												
																							
