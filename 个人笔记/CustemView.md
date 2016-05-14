##自定义控件：

- 1.组合控件：将系统原生空间组合起来，加上动画效果，形成一种特殊的UI效果
- 2.自定义控件 
  - 自定义View
  - 自定义ViewGroup

- view或viewGroup的绘制流程
  - mearsure()->layout()->draw()

###自定义view和viewGroup控件步骤：

1. 测量：onMeasure 

   - 测量规则MeasureSpec：EXACTLY ,AT_MOST, UNSPECI	FIED
   - view：设置宽高`setMeasuredDimension(width, height);`
   - viewGroup：测量child的宽高`child.measure()`

2. 布局：onLayout。
   - 一般自定义viewGroup需要实现该方法
   - 目的：设置子View显示的位置。

3. 绘制：onDraw     
   - 控制view显示在屏幕上的样子。
   - 熟练使用Canvas.drawXXX()的方法。
   - 自定义viewgroup不需要重写

4. view的刷新: 

   - invalidate() -> draw() -> onDraw()


   - 子线成postInvlicate。

###旋转式菜单

	- 利用动画效果。ScaleAnimation

###大图轮播 ViewPager的使用

- viewpager 3.0新特性。低版本使用v4包
- viewpager 预加载机制，在内存中，保留三个缓存。不需要viewHolder
     	
  	- 会预先加载傍边两个view，如果有。

  - pagerAdapter需要重写的方法
    1. getCount()
       //判断滑动停止后显示的view是否使用缓存。
       //返回view==object。相同则使用
    2. isViewFromObject(View view, Object object)
       //初始化一个item。类似ListView的getView()	
    3. public Object instantiateItem(ViewGroup container, int position)
       //销毁一个pager。viewPager继承自viewgroup。container就是viewpager本身
       //position，销毁的位置。object销毁的对象。
    4. public void destroyItem(ViewGroup container, int position, Object object) 
         container.removeView((View) object);
###下拉选择菜单 popupWindow+ListView


- 假如listview的item中有Button，ImageButton,CheckBox等会强制获取焦点的view
  此时，listview的item无法获取焦点，从而无法被点击
- 解决方法：给item的根布局增加以下属性
  - android:descendantFocusability="blocksDescendants"
  - 设置之后，Button获取焦点，item中其他控件也可以获取焦点

###滑块菜单 

	- 自定义view实现onMeasure，ondraw
	- 处理onTouchEvent事件。

###下拉刷新：

- 自定义view继承ListView
  1. addHeaderView():添加头部headerView。
  2. 将headerView的paddingTop设置headerView高度的负值去隐藏它.
  3. 获取headerView的高度。
  4. 通过onTouchEvent()动态设置paddingTop

- getHeight()和getMeasuredHeight()的区别：

  - getMeasuredHeight():获取测量完的高度，只要在onMeasure方法执行完，就可以用它获取到宽高，在自定义控件内部多使用这个。
    - 使用view.measure(0,0)，可以主动通知系统去测量，然后就可以直接使用它获取宽高

  - getHeight()：必须在onLayout()方法执行完后，才能获得宽高。
    ​	 
    	headerView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
    		@Override
    		public void onGlobalLayout() {
    		headerView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
    		int headerViewHeight = headerView.getHeight();
    		//直接可以获取宽高
    		}
    	});
  - setSelection(position);将对应位置的item放置到屏幕顶端

##侧滑菜单SlidingMenu。自定义ViewGroup

1. 让viewGroup移动的三个方法：

   1. layout(l,t,r,b);
   2. offsetTopAndBottom(offset)和offsetLeftAndRight(offset);
   3. scrollTo()和scrollBy()方法;
      注意：滚动的是不是Viewgroup，而是屏幕本身。

2. 让view在一段时间内移动到某个位置(松开手指时，一定时间内慢慢滚动)

    1. 使用自定义动画(让view在一段时间内做某件事)
    2. 使用Scroller(模拟一个执行流程，)  

