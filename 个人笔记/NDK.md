# C语言入门

两个常用的库`#include<stdio.h>` `#include<stdlib.h>`

#### 1、C基本数据类型

- 数值型：`short`2字节、`int`4字节、`long`4字节、`float`4字节、`double`8字节。
- 字符型：`char`1字节
- 构造类型：数组、结构体`struct`、共用体（联合体）`union`、枚举`enum`
- 指针类型：类似Java中表示对象，`int* p` 、`void*`通用指针。
- 常量修饰符：`const`
#### 2、基本输入输出函数
- 输入函数
  ```c
  int  count;  char  name[4];
  //取出count ,name的内存地址，把用户输入的数据保存到内存地址上。
  //&表示取出地址  
  scanf(“数量%d名称%s”,&count,&name);
  ```
- 输出函数
  ```
  //输出函数
  int i = 1;
  printf(“%d\n%#\d”,i,&i);
  ```
- 占位符
  ```c
  %d  int 						
  %ld long  int
  %lld long  long  int
  %hd  断整形short int 
  %c  char
  %f  fload
  %lf  double
  %u  无符位
  %x  16进制
  %o  八进制
  %s  输出字符串
  ```
#### 3、数组
在C语言中，数组内存空间连续，数组的地址就是首个元素的地址。
```c
int c []= {1, 2 ,3 ,4 };
int*p = &c;  //int*p = &c[0];
printf("第0个元素:%d\n",*(p+0));//*（p+1表示指针位移一个单位。由指针类型决定。
printf("第1个元素:%d\n",*(p+1));
printf("第2个元素:%d\n",*(p+2));
printf("第3个元素:%d\n",*(p+3));
```
> C语言没有字符串。可以定义字符数组，但是必须指定长度。可以越界!!!
```c
char arr [9] = “中文2个字节”;  //自动转换
```
#### 4、指针
>指针的长度都是4个字节。64位编译器，指针长度为8个字节。即一个内存地址。
>32为系统，内存总线为32位，只能表示2^32个不同内存地址，即4GB。

指针变量的值指向了其它变量的地址。
```c
int i = 1;
int* p = &i;	//一级指针 ，将i的地址的值取出，赋给p
int** q = &p;   //二级指针，地址对应的值还是地址 
printf("%#x\n",p);
printf("%d\n",*p);    //*p  就等同与i 
printf("%d\n",**q);
```
- 函数调用

  - 值传递：不改变变量的值

  - 指针传递：（传递地址，相对于Java传递对象），直接改变变量的地址

    ```C
    void function(int** p){
      	int i = 3;
     		*p = &i ; //将i的地址，*p 。*p表示mianp的地址 
    	} 
     main() {
       	int* mainp;
       	function(&mainp); //将二级指针传递过去  int**  p = &mainp;
       	printf("%d",*mainp); 
     }
    ```


- 多级指针

  ```c
  int****  p;
  //*p的值是一个地址。**p的值是一个地址。***p的值是一个地址。****p才是数据。
  ```

#### 5、结构体（C语言的Java类）

```c
void function(){
  printf("function");
}
//定义结构体
struct student{
  int age;
  int height;
  char sex;
  //函数指针 
  void (*functionp)();
};
main() {
  //创建结构体类型，需要初始化结构体的变量和函数。 
  struct student stu ={20,180,'m',function};
  //输出长度为了运算方便，结构体长度自动补齐 
  printf("%d\n",sizeof(stu));
  //引用结构体变量的值
  printf("%d\n",stu.age);
  //调用结构体的指针函数 
  stu.functionp();
  //定义结构体指针，指向结构体变量 
  struct student* stup = &stu;
  (*stup).functionp();
  //->一级指针指向结构体的属性 
  stup->functionp(); 
  printf("%d\n",stup->age);
}
```

#### 6、联合体

```c
//联合体，能表示这几种变量。是一个新的类型。
//只能为其中一个值。新值会覆盖旧值。
//长度取决于最长的变量 
union{int i; short s ; char c} un;
un.i = 20;
un.s = 10;
un.c = 'a';
printf("%d",un.s); //值为97  看输出格式
```

#### 7、自定义类型`typedef`，C语言数据类型de别名表示

```c
//Java八大基本数据类型，在C语言中的表示
typedef unsigned char   jboolean;       /* unsigned 8 bits */
typedef signed char     jbyte;          /* signed 8 bits */
typedef unsigned short  jchar;          /* unsigned 16 bits */
typedef short            jshort;         /* signed 16 bits */
typedef int              jint;           /* signed 32 bits */
typedef long long        jlong;          /* signed 64 bits */
typedef float            jfloat;         /* 32-bit IEEE 754 */
typedef double           jdouble;        /* 64-bit IEEE 754 */

//Java对象，在C语言中的表示
typedef void*           jobject;   
typedef jobject         jclass;
typedef jobject         jstring;
typedef jobject         jarray;
typedef jarray          jobjectArray;
typedef jarray          jbooleanArray;
typedef jarray          jbyteArray;
typedef jarray          jcharArray;
typedef jarray          jshortArray;
typedef jarray          jintArray;
typedef jarray          jlongArray;
typedef jarray          jfloatArray;
typedef jarray          jdoubleArray;
typedef jobject         jthrowable;
typedef jobject         jweak;
```
#### 8、枚举

```c
//规定了取值范围
enum DAY {
	MON= 1, TUE, WED, THU, FRI, SAT, SUN
};
main(){	
	enum DAY  day = TUE; 
	printf("%d\n",day);   //输出2;定义了从1开始
}
```

#### 9、内存堆栈

- 栈：静态内存
  - 内存空间是连续的，后进先出
  - 自动分配的，大小固定
  - 由系统自动分配，效率更高
  - 系统自动释放内存
- 堆：动态内存
  - 需要程序狗手动申请，大小取决于系统虚拟内存`java new`、`C  malloc`
  - 程序狗手动释放`java 自动释放`、`C free函数`
  - 内存空间是单链表接口（查询效率低，增删效率高）
  - 空间不是连续的。
```c
//C语言申请堆内存的API
int* p  =  malloc(3*sizeof(int)); //申请12个字节的堆内存
int i ; 
for(i=1;i<=3;i++){
  scanf("%d\n",(p+i-1));
}
p = realloc(p,(3+5)*sizeof(int)); //重新申请新的堆内存32字节，并将p原来地址数据，保存。
for(i=1;i<=5;i++){
  scanf("%d\n",(p+3+i-1));
}
for(i=1;i<=8;i++){
  printf("%d\n",*(p+i-1));
}
```
# JNI入门
jni java本地开发接口，JNI是一种协议，这个协议用来沟通java代码个外部的本地c代码。
- java调用c/c++代码
- 外部的c/c++ 代码也可以调用java代码
#### 为什么要使用jni
- 1、JNI扩展了java 虚拟机的能力, 驱动开发  (wifi-hotspot) 
- 2、Native code效率高,数学运算,实时渲染的游戏上,音视频处理 (极品飞车,openGL,ffmpeg)
- 3、复用代码 (文件压缩,人脸识别…)
- 4、特殊的业务场景，物联网，智能家具。
- 5、安全策略，反编译。
#### 什么是交叉编译
> 在一个平台编译出另一个平台可以执行的 二进制程序。
- CPU平台：x86  arm 。不同CPU处理的指令集不同。
- 系统平台: Windows 、 linux 、Max OS
> 模拟另一个平台的特性去编写代码。`源代码->预编译->编译->链接->可执行程序`

编译工具：
- NDK： native development kits 。
- CDT（过时）： C/C++ developer tools    eclipse工具，类似ADT。
- cygwin：一个模拟器，可以在Windows平台下的Linux命令行。


## 编写JNI步骤