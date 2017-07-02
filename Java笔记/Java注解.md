## 全面解析Java注解（Annotation）

##### 概念

Java提供了一种原程序中的元素， 关联任何信息和任何数据的途径和方法。

为什么要学习注解，学习注解的好处，学习注解能做什么？

- 1、能够读懂别人写的代码，特别是框架相关的代码
- 2、让编程更加简洁，代码更加清晰
- 3、 让别人高看一眼。
- 4、自定义注解

##### java中的常见注解

- `Override：`复写了父类的方法
- `Deprecated：`表示该方法已经过时
- `@SuppressWarnings("deprecation")`忽略了“deprecation”警告

##### 第三方注解

Spring注解，Mybatis注解

##### 注解分类

按照运行机制分

- 源码注解：注解只在源码中存在，编译成.class文件就不存在了。
- 编译时注解：注解在源码和class文件都存在。
- 运行时注解：在运行阶段还起作用，甚至会影响运行逻辑的注解；

按照来源分：jdk自带注解，第三方注解，自定义注解。

##### 自定义注解

```Java
/*
 * 声明注解。
 * 关键字说明：
 * 1、@Target:作用域（
 * 				constructor(构造方法声明)
 * 				field（字段声明）
 * 				local_variable（局部变量声明），
 * 				method（方法声明）
 * 				package（包声明）
 * 				parameter（参数声明）
 * 				type（类，接口声明）
 * 2、@Retention:生命周期
 * 				source：只在源码显示，编译时会丢弃.
 * 				class：编译时会记录到class中，运行时忽略。
 * 				runtime：运行时存在，可以通过反射读取
 * 3、Inherited ---继承注解
 *				只能继承类，不能继承接口，也不能继承父类方法的注解。	
 * 4、Documented---生成Javadoc时，包含次注解。
 */

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.SOURCE)
@Inherited
@Documented
public @interface Description {

	//成员类型基本数据类型，String，annotation，Enumeration
	//成员名只有一个时，必须是value(),在使用时可以忽略成员名和=
	//注解可以没有成员，没有成员的注解是表示注解  
	 String name(); 
	 String age();
}
```

##### 获取定义的注解。

概念：通过反射获取类，函数或成员上的**运行时**注解信息，从而实现动态控制程序运行的逻辑。

```Java
public static void main(String[] args) {
	//1、使用类加载器加载类
	try {
		Class clazz = Class.forName("annotation.PersonImpl01");
		//2、判断是否存在注解
		boolean isExits = clazz.isAnnotationPresent(Description.class);
		if (isExits) {
			//3、解析注解
			Description description = (Description) 
					clazz.getAnnotation(Description.class);
			System.out.println(
					"name="+description.name()+"" +
					",age="+description.age());
		}
		//a、获取方法上的注解
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			boolean idExits = method.
					isAnnotationPresent(Description.class);
			if (idExits) {
				Description description = method.
						getAnnotation(Description.class);
				System.out.println(
						"name="+description.name()+
						",age="+description.age());
			}
		}
		
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}
}
```

