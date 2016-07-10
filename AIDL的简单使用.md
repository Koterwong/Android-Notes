## AIDL(Android Interface definition language) 

在Android中每个进程都占用一个独立的虚拟机，两个应用程序是不能直接进行通讯的，Google给我们提供了进程间通讯的桥梁-----AIDL。

> AIDL设计原理：代理模式

#### Android中进程通讯的方式

- Intent
- BroadcastReceiver
- 文件
- AIDL  多个应用程序访问，操作多线程。
- Binder  多个应用程序访问，不操作多线程
- Messenger   单个引用程序访问，不操作多线程。


#### 使用AIDL 实现进程间通讯步骤

- 编写aidl文件
- 实现stub（继承binder并实现了aidl接口。内部proxy代理。）
- 共享aidl
- asInterface获取代理对象（aidl接口）。
- 调用aidl服务方法，（通过代理实现）。