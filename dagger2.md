#### 1、简单介绍

Dagger2是一款快速的依赖注入框架。

#### 2、相关概念

依赖注入：目标类中所依赖的类的初始化不是通过new 关键字创建出来，而是通过技术手段，将初始化好的类自动注入到目标类中。		

控制反转：用来削弱类之间的耦合关系，又称为依赖倒置原理，是面向对象的一种全新设计模式。可以理解为依赖注入的实现方式（个人理解）。

关于dagger2一些抽象概念的讲解，嗯，讲解的很好：

[Android：dagger2让你爱不释手-基础依赖注入框架篇](http://www.jianshu.com/p/cd2c1c9f68d4)

[Android：dagger2让你爱不释手-重点 概念讲解、融合篇](http://www.jianshu.com/p/1d42d2e6f4a5)

[Android：dagger2让你爱不释手-终结篇](http://www.jianshu.com/p/65737ac39c44)

这里小结一下：

- Inject：标注目标类的属性成员和被注入类的构造函数。在目标类中标注属性成员变量表示我们要这个类的实例；在标注被注入类的构造函数的时候，表示我们可以通过component将该类的实例注入到相关的目标类中。
- Module：可以理解为简单工厂，用于生成被注入类的实例。上面通过Inject标注构造的方式不需要使用Module去提供实例，但是第三方库需要注入的时候就必须使用Module实现。在Module中我们使用@Provides注解对module中提供实例的方法进行标注，Component需要那个实例就会查找Module中使用@Provides标注的方法。
- Component： 注射器，连接器。连接目标类和要被注入类的实例，同时也具有管理该component的module功能，Component中的modules注解可以把Module加入Component，modules可以加入多个Module。一般我们的应用程序要有一个全局的Component用来管理整个App都用到的类实例（这些全局的类实例一般都是单例的）。其次，对于每一个页面也会对应一个Component，这当然不是必须，有些页面依赖的类实例是一样的也可以公用一个Component。
- Provides：Module中创建依赖类实例的方法使用该注解标注。
- Qualifier：用于解决依赖注入的迷失。
- Scope：是一个需要我们自定义注解作用域，理解起来很困难，而且很多文章也讲的不是清楚。大部分文章总结Scope的作用是：可以通过自定义的Scope注解，来限定通过Module和Inject方法创建的类的生命周期和目标类的生命周期保持一致。其实Scope根本没有这些功能，它的真正作用是**管理Component之间的组织方式**。不管是依赖方式还是包含方式，都有必要用自定义的Scope注解来标记这些Component，这些注解最好不要一样了，不一样能更好的体现出Component的组织方式。还有编辑器会检查有依赖关系或包含关系的Component，若发现有Component没有自定义的Scope注解，则会报错；*另外Scope还能更好的管理Component和Module的匹配关系*；提高代码的可读性；后面两点在Singleton中有体现。
- Singleton：Singleton并没有创建单例的能力，它是Scope的一种实现。要想实现单例，我们需要在Module中定义创建实例的方法（Inject的方式不行），使用全局的AppComponent管理Module，保证AppComponent只初始化一次。既然Singleton不能创建单例，那么它的作用是什么呢？保证AppComponent和Module是匹配的，如果AppComponent的Scope个Module的Scope不一样，那么编译时就会报错。提高代码的可读性，让程序员明白Module中创建的类是单例的。

Component：Component是注入依赖实例的关键的关键，在目标类中使用Inject标注要注入的类的时候，在调用inject方法之后，Component会向自己管理的Module中查找用Provides标志的创建实例的方法，如果没有就会查找用Inject标记的相关构造函数。Module的优先级高于用Inject标注的构造函数。如果在Module找到相关实例，就会停止到Inject标注的构造函数中查找。

依赖注入迷失：Component在寻找目标类所依赖的实例时（在Module的Provides或者Inject标注的构造函数），这里假设在Inject标注中寻找，如果多个构造函数是用Inject进行标注，就会造成依赖注入迷失。这个时候就需要使用Qualifier（限定符）告诉Component使用那个构造函数进行实例化，通常我们是用自定义的Qualifier注解标注目标类的属性和Inject的够着函数（@Named注解就是Dagger2的默认实现方式）。这种实例化的方式相当与给使用Inject标志的构造函数加上一个ID。（使用方法后面介绍）。

**共享类实例，Component提供类的实例，如果Component想把其他Component注入到自己的目标类中，假设Activity的Component需要依赖到全局Component，那么这个时候就涉及共享类实例的问题了**

**Component之间的组织关系**

- dependencies ：一个Component可以依赖一个或者多个Conmponent。
- SubComponent： 一个Component可以包含一个或多个Component，被包含的Componnet还可以继续包含其他的Component。SubComponent就是被包含的体现。
- 继承：不是解决类共享实例的方式存在的，而是为了更好的管理、维护Component之间的关系，把Component一些共有的方法抽取到父类中，然后之类继承。

**一次依赖注入具体步骤如下：**

- 1、在Module中查找是否有创建该实例的方法。
- 2、Module中如果存在创建实例的方法，那么查看该方法是否存在参数。
  - 2.1、若存在参数，则按步骤1开始依次初始化每个参数。
  - 2.2、若不存在参数，则直接初始化该实例，整个依赖注入过程完成。
- 3、Module中不存在创建实例的方法，则查找Inject注解的构造函数。
  - 构造函数存在参数，从步骤1开始初始化每个参数。
  - 构造函数不存在参数，直接初始化该类实例，整个依赖注入过程完成。

#### 3、使用步骤

（1）配置grandle

在工程目录下的build.grandle添加。

```java
buildscript {
  repositories {
    mavenCentral()
  }
  dependencies {
    classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
  }
}	
```

在module的build.grandle中添加。

```JAVA
// Apply plugin
apply plugin: 'com.neenbedankt.android-apt'

// Add Dagger dependencies
dependencies {
  compile 'com.google.dagger:dagger:2.x'
  apt 'com.google.dagger:dagger-compiler:2.x'
}
```

#### 4、依赖注入

##### 4.1@Inject基本注入：

使用@Inject完成最基本的注入方式。

要注入的类：

```java
public class Book {
	//使用@Inject注解标注要注入类的构造函数。
    @Inject public Book() {
    }
}
```

目标类：

```Java
public class MainActivity extends AppCompatActivity {
	//在目标类中同样使用@Inject注入依赖的类。但是这时并不会赋值。
  	@Inject Book book;
}
```

使用Component（注射器、连接器）连接注入类和目标类。

```java
@Component
public interface AppComponent {
    void inject(MainActivity mainActivity);
}
```

完成以上步骤，需要我们手动make project生成相关类，然后进行注入。在目标类中代码如下：

```java
@Inject Book book;
@Override protected void onCreate(Bundle savedInstanceState) {
    ... ... 
	//注入依赖的类，这时候我们使用inject标签注入的类就初始化完成了。
    DaggerAppComponent.builder().build().inject(this);
}
```

Component连接了我们的目标类和注入类。上述注入方式的工作原理为：

- 在调用`inject(this)`方法的时候，component会查找目标类使用@Inject标注的属性。
- 查找属性使用@Inject标注的构造函数，并创建实例。
- 对属性进行赋值。

##### 4.2使用@Module的方式注入：

Module是一个静态工厂，用于生产目标类所依赖的实例。

```Java
@Module
public class AppModule {
    private Context context;
  	//通过Module的构造函数传参。
    public AppModule(Context context) {
        this.context = context;
    }
  	//Provides标记方法提供依赖类。@Singleton要
    @Singleton @Provides
    Book provideBook() {
        return new Book(context);
    }
}
```

Component

```Java
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
  	//直接返回要注入的实例，这样就可以在目标类中直接获取。也可以使用Void inject(Object obj)的方式。
  	//两种情况的适用场景不同。
    Book getBook();
  	//void inject(MyApp app); 这种情况使用于在一个类中进行注入，
}
```

目标类中，

```java
//获取Component对象。我们不使用
AppComponent daggerAppComponent = DaggerAppComponent
		.builder()
		.appModule(new AppModule(this))
		.build();
//同过Component获取实例。我们可以把component对象共享出去，这样在其他类中我们就可以获取book实例。
Book book = daggerAppComponent.getBook();
```

##### 4.3 使用Qualifier解决依赖注入迷失

Module：

```java
@Module
public class AppModule {
    public Context context;
    public AppModule(Context context) {
        this.context = context;
    }
  	//这里有多个提供提供Book的方法，
    @Provides @Named(value = "one")
    Book providerBookOne() {
        return new Book();
    }
    @Provides @Named(value = "two")
    Book providerBookTwo() {
        return new Book(context);
    }
}
```

@Component

```java
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(MyApp myApp);
}
```

@注入。

```java
//这里注入的时候，通过自定义的Qualifier注解告诉Component应该实例化那个实例。
@Named(value = "one")
@Inject Book one;
@Named(value = "two")
@Inject Book two;

@Override public void onCreate() {
    super.onCreate();
    DaggerAppComponent.builder()
            .build()
            .inject(this);
}
```

##### 4.4、@Scope 定义作用域

这里将Scope理解为用来管理Component实例和App或者Activity的生命周期一致就好。

```java
//自定义Scope，这里功能个Singleton类似
@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface PerApp {
}
```

Module

```java
@Module
public class AppModule {
    private final Context context;
    public AppModule(Context context) {
        this.context = context;
    }
  	//这里通过@PerApp标注，在Component中也必须有使用@PreApp与之对应。
    @Provides @PerApp Context providerContext() {
        return context;
    }
}
```

Component

```Java
//这里Component同样使用PerApp标志，不然编辑会检查错误。
@PerApp
@Component(modules = AppModule.class)
public interface AppComponent {
    Context getContext();
}
```

注入：

```Java
private AppComponent appComponent;
@Override public void onCreate() {
    super.onCreate();
    appComponent = DaggerAppComponent
            .builder()
            .appModule(new AppModule(this))
            .build();
    Context context = appComponent.getContext();
}
public AppComponent getAppComponent() {
    return appComponent;
}
```

注意点：

- 一个Module中只能存在一种Scope。
- Scope标注的Component和所依赖的Component的Scope不能一样。

##### 4.5、依赖Component

有时候，我们的Component可能还依赖另外一个Component所管理的Module产生的实例。例如下面的例子，展示了Activity的component依赖到全局的AppComponent，然后注入全局的AppComponent提供的实例。

@AppComponent:

```java
@PerApp
@Component(modules = AppModule.class)
public interface AppComponent {
    Context getContext();
    ToastUtils getToastUtils();  //提供全局的Toastutils
}
```

@AppModule：

```java
@Module
public class AppModule {
    private final Context context;
    public AppModule(Context context) {
        this.context = context;
    }
    @Provides @PerApp Context providerContext() {
        return context;
    }
  	//生产ToastUtils实例。
    @Provides ToastUtils providerToastUtils() {
        return new ToastUtils(context);
    }
}
```

@MainComponent：

可以看到，MainComponent依赖到了AppComponent。

```java
@PerActivity
@Component(dependencies = AppComponent.class ,modules = {MainModule.class,ActivityModule.class})
public interface MainComponent {
    void inject(MainActivity mainActivity);
}
```

@MainModule：

在MainModule这里提供了Book实例，我们要做的就是通过注入全局的Toast对象然后弹出Book的书名信息。

```java
@Module
public class MainModule {
    @Provides @PerActivity Book providesBook() {
        return new Book("android开发艺术探索");
    }
}
```

在MainActivity中注入ToastUtils实例和Book实例。

```java
@Inject ToastUtils toastUtils;
@Inject Book book;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    MainComponent mainComponent = DaggerMainComponent.builder()
            .appComponent(getAppComponent())  //注意
            .mainModule(new MainModule())
            .build();
    mainComponent.inject(this);
  
    Button button = new Button(this);
    button.setLayoutParams(new ViewGroup.LayoutParams(200, 200));
	//这里我们通过点击button弹出book.name的信息。
    button.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
            toastUtils.showToast("BookName：" + book.name );
        }
    });
    ((ViewGroup) findViewById(android.R.id.content)).addView(button);

}
```

