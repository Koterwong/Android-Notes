### Fragment你需要知道的知识点

> #### [官网介绍](https://developer.android.com/guide/components/fragments.html#Transactions)

fragment的出现给大屏幕设备，带来更加灵活的**UI**设计和动态的用户体验。Fragment定义自己的布局文件，是一个可重用的重要组件。

Fragment一般对应一个布局文件，并且在`onCreateView()`方法中将布局文件`inflate`转为成view对象，因此Fragment也可以理解一个View。可以灵活的添加和移除。

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

> 同样可以添加没有UI界面的Fragment，` add(Fragment, String) `。它并不与 Activity 布局中的视图关联，因此不会收到对 `onCreateView()` 的调用。因此，您不需要实现该方法。

#### 传值通讯

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

> ##### Acticity的返回栈又系统管理，而Fragment的返回栈又Activity管理

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

> 这里需要注意的是，addToBackStack()点击返回键返回到是未添加该Fragment的状态。这里说的是被替换的Fragment（上一个Fragment）的声明周期方法。

`onPause()->onStop()->onDestroyView()`

```Java
MainActivity.onUserInteraction / →☐
TestFragment.onPause / ☐→
TestFragment.onStop / ☐→
TestFragment.onDestroyView / ☐→
```

- 被remove或replace，没有执行addToBackStack()方法。

`onPause()->onStop()->onDestroyView()->onDestrop()->onDetch()`

```Java
MainActivity.onUserInteraction / →☐
TestFragment.onPause / ☐→
TestFragment.onStop / ☐→
TestFragment.onDestroyView / ☐→
TestFragment.onDestroy / ☐→
TestFragment.onDetach / ☐→
```

- 被remove或者replace返回到上一个Fragment状态。

`onCreateView()->onViewCreate()->onActivityCreated()–>onStart() ->onResume()`

```
TestFragment.onCreateView / ☐→
TestFragment.onViewCreated / ☐→
TestFragment.onActivityCreated / ☐→
TestFragment.onViewStateRestored / ☐→
TestFragment.onStart / ☐→
TestFragment.onResume / ☐→
```