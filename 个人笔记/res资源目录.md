##Android：res资源目录

- values 
	- strings:字符和字符数组资源。
	- ids：存放控件的id。预编译性能更高。
	- styles：Manifest主题和控件的样式。
	- colors：定义颜色资源。方便统一管理。
	- dimens：定义尺寸
- drawable：图像
	- 定义动画
		
		    <rotate xmlns:android="http://schemas.android.com/apk/res/android"
			    android:pivotX="50%"
			    android:pivotY="50%"
			    android:drawable="@mipmap/indicate_rotate1"
				android:fromDegrees="0"	
			    android:toDegrees="360">
			</rotate>

	- shape
	
			矩形（rectangle）、椭圆形(oval)、线性形状(line)、环形(ring)
			1. 在android:shape="ring"
		 	android:innerRadius	  尺寸，内环的半径。  
			android:thickness	 尺寸，环的厚度 
			android:useLevel	boolean值，如果当做是LevelListDrawable使用时值为true，否则为false.

			2. android:shape="rectangle"
			<corners	 圆角
			    android:radius="8dp"  
			    android:topLeftRadius="5dp"  
			    android:topRightRadius="15dp"  
			    android:bottomLeftRadius="20dp"  
			    android:bottomRightRadius="25dp"	
		    />  
		  	<gradient  渐变
			    android:startColor="#FFFF0000"  
			    android:endColor="#80FF00FF"  
			    android:angle="45"  渐变的角度，逆时针45°的倍数  
				android:type="linear" 渐变类型：linear 线性渐变，这是默认设置  
								              radial 放射性渐变，以开始色为中心。  
								              sweep 扫描线式的渐变。  
		    />   
			<solid   填充色
		    	android:color="#ffff9d77"  
		    />  
			<stroke    描边
			    android:width="2dp"  
			    android:color="#dcdcdc"	
		    />   
