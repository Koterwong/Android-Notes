### Realm使用入门教程

[官网详细教程](https://realm.io/docs/java/latest/#getting-started)，这里只做简单记录。使用Realm版本为1.1

##### 1、配置Grandle

在工程的build.grandle中添加如下代码

```java
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath "io.realm:realm-gradle-plugin:1.1.0"
    }
}
```

在具体的modle的build.grandle中配置

```
apply plugin: 'realm-android'
```

配置完成以后，就可以在项目中使用Realm了。

##### 2、创建Realm类对象，实现基本的CRUD

Realm类是实现对象持久化存储和事物管理的关键类，所有的RealmObject都强依赖与Realm类。创建Realm对象的方式有两种：

```java
Realm realm = Realm.getDefaultInstance();  //通过默认的方式获取Realm对象。
```

```Java
RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name("my.realm")
                .build();
Realm realm = Realm.getInstance(realmConfig);
```

第一种使用默认的方式创建Realm实例，第二种方式可以自定义自己的Realm相关设置，同时在Realm类的头注释中也有官方的推荐方式。

> 这里需要注意的是，Realm的实例不能在不同的线程间操作。确切的说，必须在每个要使用的线程上打开一个Realm实例。每个线程都会引用计算来自动缓存Realm实例，只要计数不为零，调用Realm.getInstance()方法将会返回缓存的Realm实例，这应该是一个轻量级的操作。  

基本CRUD

```Java
realm.executeTransaction(new Realm.Transaction() {
    @Override public void execute(Realm realm) {
        User user = realm.createObject(User.class);
        user.setAge(23);
        user.setName("wyk");
    }
});

final User user = realm.where(User.class).findFirst();
showStatus("insert ----->" + user.getName() + ":" + user.getAge());

//改
realm.executeTransaction(new Realm.Transaction() {
    @Override public void execute(Realm realm) {
        user.setName("koterwong");
        user.setAge(24);
        showStatus("------>" + user.getName() + ":" + user.getAge());
    }
});

realm.executeTransaction(new Realm.Transaction() {
    @Override public void execute(Realm realm) {
        realm.delete(User.class);
    }
});
final User user_02 = realm.where(User.class).findFirst();
showStatus("execute delete ----->" + user_02.getName() + ":" + user_02.getAge());
```

以上执行增删改查的方式使用事物块的方式，内部也是调用 realm.beginTransaction()开启事物，调用realm.commitTransaction()提交事物，并在发生错误时执行realm.cancelTransaction()方法，实现起来更加简洁。另外，以上代码执行在主线程，开启事物会相互阻塞其所在的线程，通过异步事物，可以将Realm会在后台线程，并在事物完成后将结果回传回调用线程。

异步事物的使用，需要注意的是，OnSuccess和OnError并不是必须的参数，看需要而定。

```java
realm.executeTransactionAsync(new Realm.Transaction() {
    @Override public void execute(Realm realm) {
         //do some operator
    }
}, new Realm.Transaction.OnSuccess() {
    @Override public void onSuccess() {
        //存储成功回调
    }
}, new Realm.Transaction.OnError() {
    @Override public void onError(Throwable error) {
        //存储失败回调
    }
});
```



