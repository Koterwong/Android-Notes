#GSON
----------------------------------------------
* [JSON 介绍](#json_intro)

* [Gson 下载](#gson_download)

* [Gson 解析 和 格式化](#gson)

  * [Gson 格式化](#gson_format)

  * [Gson 解析](#gson_parse)



##<a name="json_intro"></a> Json 介绍


Json 全称 JavaScript Object Natation ，用来描述数据结构，它是基于纯文本的数据格式，是一种轻量级的数据交换格式。广泛应用于 服务端 与 客户端 的数据交互。
​	
* __格式__

  Json 以 key－value的形式存储数据;

  Key的取值 为 `String` 类型;

  Value的取值 为 `String`,`boolean`,`Number`,`数组`,`Object`,`null`;

  Json 串 以 `{` 开始, 以 `}` 结尾;

  Json 串中 `数组` 是 以 `[` 开始, 以 `]` 结尾;

  Json 串中 `Object` 是 以 `{` 开始, 以 `}` 结尾;

* __案例__

__基本类型__ :

```
	{
  	    "name": "张三",
  	    "age": 18,
  	    "sex": true
  	}
```

__数组类型__ :

		[               
				{
		            "name": "张三",
		            "age": 18,
		            "sex": true
		        },
		        {
		            "name": "李四",
		            "age": 19,
		            "sex": false
		        }
		]

__对象嵌套__ :

		{
		    "name": "teacher",
		    "computer": {
		        "CPU": "intel7",
		        "disk": "512G"
		    },
		    "students": [
		        {
		            "name": "张三",
		            "age": 18,
		            "sex": true
		        },
		        {
		            "name": "李四",
		            "age": 19,
		            "sex": false
		        }
		    ]
		}


* __树状结构__

  json 字符串相当于一个倒挂的树, key 相当于 树的节点.

  ![json 树结构](./json_tree.png)


##<a name="gson_download"></a> Gson 下载

* 网址 <http://mvnrepository.com/artifact/com.google.code.gson/gson>
* 选择版本，进入下载jar.



## <a name="gson"></a> Gson 格式化 和 解析

### <a name="gson_format"></a> Gson 格式化
-----------------------------------------------
__将 java 对象 格式化为 Json 字符串.__

* 实现步骤 : 

  1. 获得需要的对象:

     	Student stu = new Student();
     	stu.setName("张三");
     	stu.setAge(18);
     	stu.setSex(true);

  2. 格式化

     	Gson gson = new Gson();
     	//将 对象 转化成 json 字符串
     	String json = gson.toJson(stu)




### <a name="gson_parse"></a> Gson 解析
-----------------------------------------------
__将 Json 字符串 解析 成 java 对象.__

* __Gson 的 节点对象:__

  `JsonElement` : 所有的节点 都是 JsonElement 对象.

  `JsonPrimitive` : 基本的 数据类型的 节点 对象， JsonElement 的子类.

  `JsonNull` : 代表 空节点 对象，即 有 key，value 为空，JsonElement 的子类.

  `JsonObject` : 对象 数据类型的 节点 对象, JsonElement 的 子类.

  `JsonArray` : 数组 数据类型的 节点 对象, JsonElement 的 子类.

* __JsonElement的取值__:

  1. `JsonPrimitive` : value 的 取值对应 java	

     ```
     int,double,float,long,short,boolean,char,byte,String,BigDecimal,BigInteger,Number
     ```
  2. `JsonObject` : value 的 取值对应 java 的 Object 对象.

  3. `JsonArray` : value 的 取值对应 java 的 List 及其子类对象.

* __Json的解析成 java 对象__

  json：

  	{'name':'张三','age':18,'sex':true}	

  代码：

  	Gson gson = new Gson();
  	// 将json 转化成 java 对象
  	Student stu = gson.fromJson(json, Student.class);

  * __Json 解析 成 List__	

  json:
  ​	
  	[{'name':'小1','age':18,'sex':true},{'name':'小2','age':19,'sex':false},{'name':'小3','age':20,'sex':true},{'name':'小4','age':21,'sex':false},{'name':'小5','age':22,'sex':true}]

  代码：

  	Gson gson = new Gson();
  	// 将 json 转化 成 List泛型
  	List<Student> stus = gson.fromJson(json, new TypeToken<List<Student>>() {}.getType());


* __Json 解析 成 map__

  json:
  ​	
  	{'小3':{'name':'小3','age':20,'sex':true},'小4':{'name':'小4','age':21,'sex':false},'小5':{'name':'小5','age':22,'sex':true},'小1':{'name':'小1','age':18,'sex':true},'小2':{'name':'小2','age':19,'sex':false}}

  代码：

  	Gson gson = new Gson();
  	// 将 json 转化 成 Map泛型
  	Map<String,Student> map = gson.fromJson(json, new TypeToken<Map<String,Student>>() {}.getType());


* __Json 节点 的解析__

  json:

  	{'flag':true,'data':{'name':'张三','age':18,'sex':true}}	

  步骤 :

  1. 获得 解析者

     	JsonParser parser = new JsonParser();

  2. 获得 根节点元素

     	JsonElement element = parser.parse(json);​

  3. 根据 文档判断根节点属于 什么类型的 Gson节点对象


     	// 假如文档 显示 根节点 为对象类型
     	// 获得 根节点 的实际 节点类型
     	JsonObject root = element.getAsJsonObject();


  1. 取得 节点 下 的某个节点的 value

     	// 获得 flag 节点的值, flag 节点为基本数据节点
     	JsonPrimitive flagJson = root.getAsJsonPrimitive("flag");
     	// 基本节点取值
     	boolean flag = flagJson.getAsBoolean();
     	
     	// 获得 data 节点的值，data 节点为Object数据节点
     	JsonObject dataJson = root.getAsJsonObject("data");
     	// 将节点上的数据转换为对象
     	Student stu = new Gson().fromJson(dataJson,Student.class);




