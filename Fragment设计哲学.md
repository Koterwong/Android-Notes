### Fragment你需要知道的知识点

> #### [官网介绍](https://developer.android.com/guide/components/fragments.html#Transactions)

Fragment译为“碎片”，是一个可重用的重要组件，一般对应一个布局文件，并且在`onCreateView()`方法中将布局文件`inflate`转为成view对象，因此Fragment也可以理解一个View，可以灵活的添加和移除。

#### 静态添加Fragment。

```Java
<!--指定id/tag(不指定系统默认使用布局id)。 name或class-->
<fragment
	android:id="@+id/fragment1"
	class="com.koterwong.fragmenttest.Fragment_01"
	android:layout_width="match_parent"
	android:layout_height="match_parent"
    tools:layout =""
</fragment>
```

> 静态加载会执行onInflate()方法，不过这个方法已经废弃。

#### 动态添加Fragment

Fragment在Acticity的添加和移除遵循数据库的事务特性。

```Java
Fragment fragment = new TestFragment();
getFragmentManager().beginTransaction()
  				.replace(R.id.add_fragment,fragment)
                .addToBackStack(null)
                .commit();
```

#### 与Activity通信

```Java
//获取Fragment对象
getFragmentManager().findFragmentById(R.id.rightfragment);
//这个tag需要在添加的时候指定。
getFragmentManager().findFragmentByTag(tag);
//获取Acticity对象
MainActivity activity = (MainActivity) getActivity();
View listView = activity.findViewById(R.id.list_view);
```

#### 生命周期

> ##### Acticity的返回栈（任务栈）由系统管理，而Fragment的返回栈有Activity管理

- 第一次被添加到Acticity的声明周期	

`onAttach()->onCreate()->onCreateView()->onActivityCreated()->onStart()->onResume();`

```Java
MainActivity.onCreate / ☐→
MainActivity.onContentChanged / ☐→
TestFragment.onAttach / ☐→
MainActivity.onAttachFragment / ☐→
TestFragment.onCreate / ☐→
TestFragment.onCreateView / ☐→
TestFragment.onViewCreated / ☐→
TestFragment.onActivityCreated / ☐→
TestFragment.onViewStateRestored / ☐→
MainActivity.onStart / ☐→
TestFragment.onStart / ☐→
MainActivity.onPostCreate / ☐→
MainActivity.onResume / ☐→
TestFragment.onResume / ☐→
MainActivity.onPostResume / ☐→
MainActivity.onAttachedToWindow / ☐→
MainActivity.onWindowFocusChanged / ☐→
```

- 被remove或replace，并且执行了addToBackStack()方法。

```Java
MainActivity.onUserInteraction / →☐
TestFragment.onPause / ☐→
TestFragment.onStop / ☐→
TestFragment.onDestroyView / ☐→
//点击back键返回。
TestFragment.onCreateView / ☐→
TestFragment.onActivityCreated / ☐→
TestFragment.onResume / ☐→
```

- 被remove或replace，没有执行addToBackStack()方法。

```Java
MainActivity.onUserInteraction / →☐
TestFragment.onPause / ☐→
TestFragment.onStop / ☐→
TestFragment.onDestroyView / ☐→
TestFragment.onDestroy / ☐→
TestFragment.onDetach / ☐→
```

- Fragment搭配ViewPager使用。

由于ViewPager的预加载特性，ViewPager左右的Fragment会预加载，并且生命周期从onAttach - > onResume 偷偷跑一遍。当某一个Fragment不在当前显示的ViewPager的左面或者右面的时候，这个Fragment就会进入onDestroyView状态，当该Fragment重新进入ViewPager预加载页面的时候，又会从新执行onCreateView - > onResume方法，以上就是Fragment和ViewPager基本的生命周期方法。



