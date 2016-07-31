## Java面试题

##### 类与对象

[Java之美[从菜鸟到高手演变\]之类与对象(一)](http://blog.csdn.net/zhangerqing/article/details/8294039)
[Java之美[从菜鸟到高手演变\]之类与对象(二)](http://blog.csdn.net/zhangerqing/article/details/8298603)
[Java之美[从菜鸟到高手演变\]之类与对象(三)](http://blog.csdn.net/zhangerqing/article/details/8301934) 

博文的相关的知识点：
1.对象的创建过程，结合以下代码说明。

```Java
public class Person {
    public Person(int id) {
        System.out.println("person(" + id + ")");
    }
}

class Build {
    /*静态块*/
    static {
        System.out.println("this is static block!");
    }
    /*非静态块*/ {
        System.out.println("this is non-static block!");
    }
    Person p1 = new Person(1); 
    public Build() {
        System.out.println("this is build's block!");
        Person p2 = new Person(2);
    }
    Person p3 = new Person(3);
    public static void main(String[] args) {
        Build b = new Build();
    }
}  
```

输出结果：this is static block->this is not-static block->person(1)->person(3)->this is build block!->person(2)
创建对象的执行过程：1、先装载.class文件，创建Class对象，对静态数据进行初始化，并且值初始化一次。2、new Build()在堆内存上分配控件。3、执行非静态块。4、初始化变量。5、执行构造器。
总体执行顺序：静态块，静态属性->非静态块，属性->构造器。
##### java字符串的处理
[Java之美[从菜鸟到高手演变\]之字符串的处理](http://blog.csdn.net/zhangerqing/article/details/8093919)
##### 垃圾回收
[Java之美[从菜鸟到高手演变\]之JVM内存管理及垃圾回收](http://blog.csdn.net/zhangerqing/article/details/8214365)
##### 设计模式
[Java之美[从菜鸟到高手演变\]之设计模式](http://blog.csdn.net/zhangerqing/article/details/8194653)
[Java之美[从菜鸟到高手演变\]之设计模式二](http://blog.csdn.net/zhangerqing/article/details/8239539)                         
[Java之美[从菜鸟到高手演变\]之设计模式三](http://blog.csdn.net/zhangerqing/article/details/8243942)
[Java之美[从菜鸟到高手演变\]之设计模式四](http://blog.csdn.net/zhangerqing/article/details/8245537)
##### 多线程    
[Java之美[从菜鸟到高手演变\]之多线程简介](http://blog.csdn.net/zhangerqing/article/details/8271105)           
[Java之美[从菜鸟到高手演变\]之线程同步](http://blog.csdn.net/zhangerqing/article/details/8284609)
##### Java IO
[Java之美[从菜鸟到高手演变\]之JavaIO](http://blog.csdn.net/zhangerqing/article/details/8466532)
##### 智力题
[Java之美[从菜鸟到高手演变\]之智力题【史上最全】](http://blog.csdn.net/zhangerqing/article/details/8138296)     
##### 数据结构
[Java之美[从菜鸟到高手演变\]之数据结构基础、线性表、栈和队列、数组和字符串](http://blog.csdn.net/zhangerqing/article/details/8796518)
##### String、StringBuilder、StringBuffer、CharSequence 
1) CharSequence接口:是一个字符序列.String StringBuilder 和 StringBuffer都实现了它.
2) String类:对象具有不变性，每次对字符串操作都是生成新的对象，然后引用指向新的对象。
3) StringBuilder类;只可以在单线程的情况下进行修改(线程不安全).
4) StringBuffer类:可以在多线程的情况下进行改变(线程安全，内部加锁机制).
5)Stringbuilder比StringBuffer效率高,应该尽量使用StringBuilder.、

##### 抽象类与接口的区别？

- 抽象类
  抽象类体现了数据抽象的思想，它定义了一组抽象的方法，这些抽象方法由继承该类的子类去实现。它的出发点就是为了继承，否则它没有存在的任何意义。例如在Android中定义的BaseActivity、BaseToolBarActicity、BasePresenter（attachView(),deathView()）等。
  语法：1.使用abstract关键词修饰的类称之为抽象类，同时抽象方法中的定义也需要abstract修饰。2.抽象类中也可以没有抽象方法，比如HttpServlet方法。3.抽象类中可以有已经实现的方法，可以定义成员变量。
- 接口
  接口是用来建立类与类之间的协议，它所提供的只是一种形式，而没有具体的实现。同时实现该接口的实现类必须要实现该接口的所有方法，通过使用implements关键字。 接口是抽象类的延伸，java为了保证数据安全是不能多重继承的，也就是说继承只能存在一个父类，但是接口不同，一个类可以同时实现多个接口，不管这些接口之间有没有关系，所以接口弥补了抽象类不能多重继承的缺陷。

  语法：1.由interface关键词修饰的称之为接口；2.接口中可以定义成员变量，但是这些成员变量默认都是public static final的常量。3.接口中没有已经实现的方法，全部是抽象方法。4.一个类实现某一接口，必须实现接口中定义的所有方法。5.一个类可以实现多个接口。

##### Java中反射的作用是什么?什么时候会用到
JAVA反射机制是在#运行时#，对于任意一个类，都能够知道这个类的所有属性和方法；对于任意一个对象，都能够调用它的任意一个方法；这种动态获取的信息以及动态调用对象的方法的功能称为java语言的反射机制。Java反射机制主要提供了以下功能：
a)在运行时判断任意一个对象所属的类；
b)在运行时构造任意一个类的对象；
c)在运行时判断任意一个类所具有的成员变量和方法；
**d)在运行时调用任意一个对象的方法；生成动态代理。**

##### 写出一个死锁的程序

一个死锁程序，同步中嵌套了同步，当两个线程互相索要对方锁的时候就会造成死锁。

```Java
class Test implements Runnable {
    private boolean flag;
    Test(boolean flag) {
        this.flag = flag;
    }
    public void run() {
        if (flag) {
            while (true) {
                synchronized (MyLock.locka) {
                    synchronized (MyLock.lockb) {
                    }
                }
            }
        } else {
            while (true) {
                synchronized (MyLock.lockb) {
                    synchronized (MyLock.locka) {
                    }
                }
            }
        }
    }
}
class MyLock {
    static final Object locka = new Object();
    static final Object lockb = new Object();
}
```

##### 生产者消费者问题

```JAVA
/**
 * Java1.5新特性 lock对象替换synchronized。
 * condition对象替换object定义的控制器方法wait(),notify(),notifyAll();支持唤醒对象的操作。
 */
public class 生产者消费者2 {
	public static void main(String[] args) {
		Resouse_ r = new Resouse_();
		Producer pro = new Producer(r);
		Consumer con = new Consumer(r);
		Thread t1 = new Thread(pro);
		Thread t2 = new Thread(pro);
		Thread t3 = new Thread(con);
		Thread t4 = new Thread(con);
		t1.start();
		t2.start();
		t3.start();
		t4.start();
	}
}
// 操作的共享数据
class Resouse_ {
	private String name;
	private int id;
	private boolean flag = false; //标志位，是否生成或消费状态。
	private Lock lock = new ReentrantLock();
	private Condition conditionPro = lock.newCondition();
	private Condition conditionCus = lock.newCondition();

	public void set(String name) throws InterruptedException {
		lock.lock();
		try {
			// 使用while循环(不能使用if)，每次线程被唤醒都去判断这个标志。
			while (flag) {
				conditionPro.await();// t1,t2
			}
			this.name = name + "--" + id++;
			System.out.println(Thread.currentThread().getName() + "-生产者--"+ this.name);
			flag = true;
			conditionCus.signalAll();
		} finally {
			lock.unlock();// 释放锁的动作一定要执行。
		}
	}

	// t3 t4
	public void out() throws InterruptedException {
		lock.lock();
		try {
			while (!flag) {
				conditionCus.await();
			}
			System.out.println(Thread.currentThread().getName() + "-消费者----" + this.name);
			flag = false;
			conditionPro.signalAll();
		} finally {
			lock.unlock();
		}
	}
}

class Producer implements Runnable {
	private Resouse_ res;
	Producer(Resouse_ res) {
		this.res = res;
	}
	public void run() {
		while (true) {
			try {
				res.set("商品");
			} catch (InterruptedException e) {
			}
		}
	}
}

class Consumer implements Runnable {
	private Resouse_ res;
	Consumer(Resouse_ res) {
		this.res = res;
	}
	public void run() {
		while (true) {
			try {
				res.out();
			} catch (InterruptedException e) {
			}
		}
	}
}
```