[TOC]

### Html标签

#### 1. 排版标签

```html
<br/> 换行标签。
<hr/> 水平线。
<p align = "center" >  段落，段落的开始和结束要流出一行。
<div> 在浏览器中声明一块区域，区域中可以包含文字。
<span> 块级元素，不会换行。
&nbsp;	空格
```

#### 2. 字体标签

```html
<font
     color = "red"
     size = "12"
     face = "宋体"
     >字体标签</font>
<hx> 标题标签
<b></b>  加粗
<i></i>  倾斜
>特殊符号
&al   < 
&gl   > 
&amp  &
<marpuee>滚动标签</marpuee>   滚动
```

#### 3. 列表标签

```html
<!-- 有序列表 -->
<ol  type="a" start = "3" >
	<li>条目1</li>
  	<li>条目2</li>
</ol>
<!-- 无序列表 -->
<ul type = "square">
  <li>条目1</li>
  <li>条目2</li>
</ul>
<!--数据格式化列表-->
<dl>
  <dt>上传数据</dt>
  <dd>下层数据</dd>
  <dd>下层数据</dd>
</dl>
```

#### 4. 图片标签

```html
<img src="../imgs/girl4.jpg" width="60%" height="70%" alt="图片文字说明">
```

#### 5. 超链接标签

```html
<!--连接资源-->
<a href = "链接的路径"  target = "_blank"></a> 
<!--定位资源-->
<a href="#top">返回顶部位置</a>
<a name="top" >顶部位置（定义锚点）</a>
```

#### 6. 表格标签

```html
<table border = "1" width = "50%" bgcolor="gray" bordercolor="red" >
	<caption>用户列表</caption>
	<tr align = "center">
		<th>数据</th>
		<th>数据</th>
	</tr>
	<tr>
		<td>数据</td>
		<td>数据</td>
	</tr>
</table>
<!--合并单元格-->
	<td colspan = "4"></td>  列合并
	<td rowspan = "4"></td>  行合并
```

#### 7. 表单标签

from表单用户收集用户的输入。from表单元素包括不同type类型的`<input>`输入类型元素、`<select>`下拉选择框元素、`textarea`元素和一些其他元素。

##### from表单的属性

| accept-charset | 规定在被提交表单中使用的字符集              |
| -------------- | :--------------------------- |
| **action**     | 规定向何处提交表单的地址                 |
| autocomplete   | 规定浏览器应该自动完成表单（默认开启）          |
| enctype        | 规定被提交数据的编码                   |
| **method**     | 规定提交数据所使用的HTTP方法             |
| **name**       | 规定表单的key                     |
| novalidate     | 规定浏览器不验证表单                   |
| target         | 提交action属于的目标地址打开方式（默认_self） |

##### input的type属性值的含义：

| text     | 文本输入                           |
| :------- | ------------------------------ |
| password | 密码输入                           |
| submit   | 定义表单表单提交至表单处理程序的按钮，action属性指定。 |
| radio    | 定义单选按钮，name属性相同视为同一组单选。        |
| button   | 按钮，一般和js定义的事件配合使用。             |
| reset    | 重置                             |
| checkbox | 复选框                            |

另外`<input>`标签中另一个重要的属性就是name属性，它代表提交给后台数据的键信息，value代表值属性。

## Css：cascading style sheet

> css用于设置网页的显示效果，将网页的显示和内容分离，也是一种`解耦`的思想，html只需要把显示内容封装起来，不使用属性，用css来控制内容的显示效果。在我们需要更换网页的显示效果的时候，只需要更改css样式而不需要我们更改html代码。

##### css和html结合的三种方式

- 使用标签自带的style属性，`style = "css代码"`。
- 使用style标签，`<style type = "test/css"> css代码 </style>`，这个标签需要放到`<head></head>`中间。
- 引入外部文件的方式。
  - 通过`@import url ("css文件地址")`，需要注意，这种方式引入方式代码必须放到`<style>`中间。
  - 通过`<link rel="stylesheet" type="text/css" href="css文件地址">`方式引入，这种方式不需要放到`<style>`中间，放到`<head></head>`中间即可。

##### css选择器

这个先说一下css选择器的优先级：由上到下，由外到内。优先级由低到高。**标签名选择器 < 类选择器 < ID选择器 < style属性。**

- 基本选择器

  - 标签选择器，css代码直接作用到标签上`div{ } span{ }`。**给相同的标签设置相同的样式。**
  - 类选择器：在HTML标签上提供了`class`用于引入css属性。class选择器的基本写法`.class的名称{ css代码 }`，在html中通过`<div class = "class的名称">`的方式引入。**这种方式可以给不同的标签设置相同的样式。**
  - ID选择器：在HTML标签上提供了`id`属性用于引入css属性。ID选择器的基本写法`#id的名称{ css代码 }`，在HTML中通过`<div class = "id的名称">`的方式引入。**这种方式一般与JS搭配使用较多**

- 拓展选择器

  - 关联选择器：写法`div  font{ css代码 }` ，这个css样式在作用在嵌套在**嵌套在div标签内font标签。** 

  - 组合选择器：写法`.hehe,#haha{ css代码 }` ，这个写法一般用在多个**选择器具有相同的样式代码**。

  - 伪元素选择器：根据HTML标签的不同的状态显示不同的代码片段，其实就是为标签选择器加入不同的状态响应。一般写法：

    ```css
    input:FOCUS {  /* 在输入框获取焦点的时候显示css属性 */
    	background-color: red;  
    }
    a:LINK {  /* 未访问 */
    	text-decoration: none;
    	color: blue;
    }
    a:VISITED {  /* 访问完成 */
    	color: yellow;
    	font-size: 20px;
    }
    a:HOVER {  /* 悬停 */
    	color: red;
    	font-size: 25px;
    }
    a:ACTIVE {  /* 点击状态 */
    	color: green;
    	font-size: 35px;
    }
    ```

- 属性选择器，对带有指定属性的标签设置样式。属性选择器在为不带有 class 或 id 的表单设置样式时特别有用：

  ```css
  input[type="text"]
  {
    width:150px;
    display:block;
    margin-bottom:10px;
    background-color:yellow;
    font-family: Verdana, Arial;
  }
  ```

##### css盒子模型

在进行布局前需要把数据封装到一块一块的区域内，这个区域的专业术语叫盒子。关于和盒子的几个属性：边框（border）、内边距（padding）、外边距（margin）。

- 使用css + div 布局

```css
<style type="text/css">
    div {
        width: 200px;
        height: 100px;
    }
    #div1 {
        background-color: red;
        position: absolute;
        top: 100px;
        left: 50px;
    }
    #div2 { background-color: green; }
</style>
	
/* html布局*/
<div id="div1">div区域1</div>
<div id="div2">div区域2</div>
```

## JavaScript

javascript是基于**对象和事件驱动**的脚本语言，作用在客户端的浏览器上。具有交互性、安全性（不可以访问本地的磁盘）、跨平台性（通过浏览器解析）。

JavaScript的语言组成

- ECMAScript：javascript语言底层标准（语法，变量）。
- BOM ：Browser Object Model 浏览器对象模型。
- DOM ：Document Object Model 文档对象模型。

JavaScript和Html代码的结合方式：

- 内部JS程序，放在HTML代码中：`<script type="text/javascript"> JS代码 </script>`。JS代码可以放到HTML代码的任意位置，但是推荐放到body的最下面。
- 外部JS程序，在HTML中引入单独的JS程序：`<script src = "test.js"></script>`。需要注意的是，如果引入外部的JS代码，那么在`<script>`标签的内部就不能再编写js代码，因为引入外部的js代码会使内部代码失效。

##### JavaScript的数据类型

JavaScript和java一眼存在两种数据类型：基本数据类型和引用数据类型。

- 基本数据类型是存储在栈（Stack）中的简单数据。包括：Num 、String 、Boolean、null、Undefined。（区别Java，String在JS中属于基本数据类型）。
- 引用数据类型是存储在堆（heap）中的对象。所有的引用类型都是Object。
- Js是一种弱引用类型的语言，课通过`typeOf（）`方法查看变量的类型。

##### JavaScript的运算符

Js和运算符和java区别不大，这里只把和java有差别的地方显示出来。

- Js中小数和整数都是数字类型，在除法运算中没有整数会出现小数结果。
- 字符串与数字相加 + ，是字符串连接。**如果相减，字符串直接转化成数字再相减。**
- Boolean类型可以进行运算，**false就是0或者null，非0非空是true，默认用1显示。**
- js中 == 比较的是两个变量的值是否相等。比较的时候两个变量会转化成一致的变量进行运行。如果我们要比较变量的类型和值相等，就需要使用`===` 。

##### JavaScript的语句、数组和函数

**语句** ：Js中语句和java几乎没有区别，这里也不再记录了。

**数组** ：在JS中声明数组的方式有三种：`1. var arr = [1,2,3]` 声明一个包含三个元素的数组。`2. var arr= new Array(5) ` 声明一个长度是5的数组。 `3. var arr = new Array(1,2,3)`声明一个长度为三，包含1、2、3元素的数组。**在Js中数组的长度是可变的，可以存放不同类型的数据。**

**函数** :  在JS中声明一个函数使用`function`关键字。js的函数具有以下特点

- 定义参数列表时，不必使用var关键字。
- JavaScript中函数不存在重载形式。
- 在每一个JS的函数总都存在一个arguments数组，用于存放函数参数列表。
- 如果调用方法是，忘记了添加`()` ，那么函数表示函数对象的引用，打印出来的话显示的内容的是函数体的内容。

**动态函数 ：** 动态函数是通过js的内置对象Function，通过new Function(参数1，参数2)来创建动态函数。

```javascript
var param1 = "x,y";
var param2 = "return x + y";
//声明动态函数
var add = new Function(param1,param2);
var sum = add(4,5);  
```

**匿名函数：** 匿名函数就是没有名称的函数，通常是函数的简写形式。

```javascript
var getSum = function (){
	return 100;
};
alert(getSum());
```

#### JavaScript内置对像

**String对像**

在js中String对象相关的方法分为两类：一种是与Html相关的方法，另一种是和java中String对象相似的方法。和java对象相识的就不列出啦。下面是和html相关的方法。

```javascript
var str = "abc";
println(str.bold());       				//粗体
println(str.fontsize(3));				//字体的大小 1-7
println(str.fontcolor("red"));			//字体的颜色
println(str.italics());          		//斜体
println(str.link("www.google.com"));    //链接到网站
println(str + '3'.sub());     			//显示下标
println(str + "2".sup());				//显示上标
```

**Array对象**

创建数组的方法上面已经提到过了，这里对js中数组对象的方法做一下说明。

```javascript
concat(元素，数组);		//返回新的数组
join(s)					//通过s标识，进行分隔，返回字符串
pop()					//删除末尾的元素，返回最后一个元素
push()					//向末尾添加元素，返回新数组的长度
sort()					//排序的方法
```

**Date对象**

```javascript
var date = new Date();

date.toLocaleString()	    			//转换本地的日期格式
date.toLocaleDateString()	         	//只包含日期
date.toLocaleTimeString()				//只包含时间

date.getDate();							//返回一个月中的某一天（1-31）
date.getDay();							//返回一周的某一天（0-6）
date.getMouth();						//返回月份（0-11）
date.getFullYear();						//返回年份
date.getTime();							//返回毫秒数
```

**Math对象**

```javascript
//Math对象包含的对象全是静态方法。
Math.ceil(x)		//上舍入
Math.floor(x)		//下舍入
Math.round(x)		//四舍五入
Math.pow(x,y)		//x的y次幂
Math.random()		//0-1的随机数
```

**RegExp正则表达式对象**

```javascript
//创建RegExp对象
var reg = /[1-4]{3}/    				// "//"中间的内容代表正则表达式的内容
var reg = /^[1-4]{3}$/  				// "/^ &/"中间的内容代表正则表达式内容
var reg = new RegExp("[1-4]{3}") 		//这种创建正则表达式的方法不常用
//匹配的两个方法

```

**Js全局函数Global**

全局函数本身存在内存中，直接可以哪来使用，通过Global对象进行管理。

```javascript
eval("alter('he')")   			//直接解析js代码执行。
isNaN()							//判断是否是非数字值
parseInt()						//解析字符串，返回整数
encodeURI()						//对内容进行编码，表单输入内容自动帮我们进行编码上传。
decodeURI()						//解析解码
encodeURIComponent()			//编解码URI组件
decodeURIComponent()
```

#### Bom

Bom，Browser Object Model，浏览器对象模型，提供了一系列操作浏览器对象的API。主要包括以下对象：

- Window
- Navigator
- Screen
- History
- Location

Window对象，表示在浏览器中打开的窗口，一般情况下一个窗口下有一个Window对象（iFrame标签会为没框架创建一个Window）。window对象可以操作当前的窗口，例如获取窗口的位置、确认窗口大小、弹出对话框(window.alter())。比如打开窗口`window.open`和关闭当前窗口`window.close()` 。

Navigator浏览器版本对象，可以通过navigator.uesrAgent回去浏览器相关信息。

Screen和屏幕相关对象。

History浏览器历史对象，back()方法返回上一个页面，forward()去下一个页面，go()去指定的页面，0代表当前页面。

Location和浏览器地址相关的对象，location.href  = "http://www.baidu.com"，让浏览器跳转到当前地址。

#### Dom

Dom，Document Object Model，浏览器对象模型，提供了一系列操作html文档的对象。详细的Dom相关api可以参考[W3SchoolDom](http://www.w3school.com.cn/jsref/dom_obj_document.asp) 。



# 附录：Html标签名次解析

| **<!-->**        | **/**                      | **注释**                     |
| ---------------- | -------------------------- | -------------------------- |
| **<!DOCTYPE>**   | **document type**          | **文档类型**                   |
| **<a>**          | **anchor**                 | **超链接**                    |
| **<abbr>**       | **abbreviation**           | **缩写词**                    |
| **<acronym>**    | **acronym**                | **缩写词**                    |
| **<address>**    | **address**                | **联系信息**                   |
| **<applet>**     | **applet**                 | **/**                      |
| **<area>**       | **area**                   | **定义图像映射中的区域**             |
| **<article>**    | **article**                | **文章**                     |
| **<aside>**      | **aside**                  | **旁注**                     |
| **<audio>**      | **audio**                  | **音频**                     |
| **<b>**          | **bold**                   | **加粗**                     |
| **<base>**       | **base**                   | **指定链接的默认(基准)地址或默认(基准)目标** |
| **<basefont>**   | **base font**              | **指定默认(基准)字体**             |
| **<bdi>**        | **BiDi-Isolate**           | **双向隔离文本的方向**              |
| **<bdo>**        | **BiDirectional Override** | **双向覆盖文本的方向**              |
| **<big>**        | **big**                    | **大号文本**                   |
| **<blockquote>** | **block quote**            | **块引用**                    |
| **<body>**       | **body**                   | **文档主体**                   |
| **<br>**         | **break**                  | **换行**                     |
| **<button>**     | **button**                 | **按钮**                     |
| **<canvas>**     | **canvas**                 | **画布**                     |
| **<caption>**    | **caption**                | **表格标题**                   |
| **<center>**     | **center**                 | **居中文本**                   |
| **<cite>**       | **cite**                   | **引用**                     |
| **<code>**       | **code**                   | **代码文本**                   |
| **<col>**        | **column**                 | **表格列**                    |
| **<colgroup>**   | **column group**           | **表格列组**                   |
| **<command>**    | **command**                | **命令**                     |
| **<datalist>**   | **data list**              | **数据列表**                   |
| **<dd>**         | **Definition Description** | **定义描述**                   |
| **<del>**        | **delete**                 | **删除**                     |
| **<details>**    | **details**                | **描述文档或文档某个部分的细节**         |
| **<dfn>**        | **definition**             | **定义**                     |
| **<dialog>**     | **dialog**                 | **对话框**                    |
| **<dir>**        | **direction**              | **方向**                     |
| **<div>**        | **division**               | **部分**                     |
| **<dl>**         | **Definition List**        | **定义列表**                   |
| **<dt>**         | **Definition Term**        | **定义条目**                   |
| **<em>**         | **emphasize**              | **强调**                     |
| **<embed>**      | **embed**                  | **嵌入**                     |
| **<fieldset>**   | **field set**              | **表单字段集**                  |
| **<figcaption>** | **figure caption**         | **figure的标题**              |
| **<figure>**     | **figure**                 | **流内容(图片、图表等)**            |
| **<font>**       | **font**                   | **字体**                     |
| **<footer>**     | **footer**                 | **底部**                     |
| **<form>**       | **form**                   | **表单**                     |
| **<frame>**      | **frame**                  | **框架**                     |
| **<frameset>**   | **frame set**              | **框架集**                    |
| **<h1-h6>**      | **headline**               | **标题**                     |
| **<head>**       | **head**                   | **头标签**                    |
| **<header>**     | **header**                 | **头部**                     |
| **<hr>**         | **Horizontal Rule**        | **水平线**                    |
| **<html>**       | **html**                   | **html**                   |
| **<i>**          | **italic**                 | **斜体**                     |
| **<iframe>**     | **inline frame**           | **内联框架**                   |
| **<img>**        | **image**                  | **图片**                     |
| **<input>**      | **input**                  | **输入**                     |
| **<ins>**        | **insert**                 | **插入**                     |
| **<kbd>**        | **keyboard**               | **键盘文本**                   |
| **<keygen>**     | **keygen**                 | **表单的密钥对生成器字段**            |
| **<label>**      | **label**                  | **标注**                     |
| **<legend>**     | **legend**                 | **说明**                     |
| **<li>**         | **List Item**              | **列表项**                    |
| **<link>**       | **link**                   | **链接**                     |
| **<map>**        | **map**                    | **图像映射**                   |
| **<mark>**       | **mark**                   | **记号文本**                   |
| **<menu>**       | **menu**                   | **菜单列表**                   |
| **<meta>**       | **meta**                   | **元信息**                    |
| **<meter>**      | **meter**                  | **度量衡**                    |
| **<nav>**        | **navigation**             | **导航**                     |
| **<noframes>**   | **no frames**              | **不支持框架的用户的替代内容**          |
| **<noscript>**   | **no script**              | **不支持客户端脚本的用户的替代内容**       |
| **<object>**     | **object**                 | **嵌入对象**                   |
| **<ol>**         | **Ordered List**           | **有序列表**                   |
| **<optgroup>**   | **options group**          | **选项组**                    |
| **<option>**     | **option**                 | **选项**                     |
| **<output>**     | **output**                 | **输出**                     |
| **<p>**          | **paragraph**              | **段落**                     |
| **<param>**      | **parameter**              | **参数**                     |
| **<pre>**        | **preformatted**           | **预格式化文本**                 |
| **<progress>**   | **progress**               | **进度**                     |
| **<q>**          | **quote**                  | **引用**                     |
| **<rp>**         | **ruby parenthesis**       | **不支持ruby元素时显示的内容**        |
| **<rt>**         | **ruby text**              | **ruby文本**                 |
| **<ruby>**       | **ruby**                   | **旁注标记(注音标示)**             |
| **<s>**          | **strike**                 | **删除线文本**                  |
| **<samp>**       | **sample**                 | **样本文本**                   |
| **<script>**     | **script**                 | **脚本**                     |
| **<section>**    | **section**                | **部分**                     |
| **<select>**     | **select**                 | **下拉列表**                   |
| **<small>**      | **small**                  | **小号文本**                   |
| **<source>**     | **source**                 | **媒介资源**                   |
| **<span>**       | **span**                   | **用来组合文档中的行内元素**           |
| **<strike>**     | **strike**                 | **删除线文本**                  |
| **<strong>**     | **strong**                 | **强调文本**                   |
| **<style>**      | **style**                  | **样式**                     |
| **<sub>**        | **subscript**              | **下标**                     |
| **<summary>**    | **summary**                | **details元素的标题**           |
| **<sup>**        | **superscript**            | **上标**                     |
| **<table>**      | **table**                  | **表格**                     |
| **<tbody>**      | **table body**             | **表格主体**                   |
| **<td>**         | **table data**             | **表格数据单元格**                |
| **<textarea>**   | **textarea**               | **文本框**                    |
| **<tfoot>**      | **table footer**           | **表格底部**                   |
| **<th>**         | **table headline**         | **表格标题**                   |
| **<thead>**      | **table head**             | **表格头标签**                  |
| **<time>**       | **time**                   | **时间**                     |
| **<title>**      | **title**                  | **标题**                     |
| **<tr>**         | **table row**              | **表格行**                    |
| **<track>**      | **track**                  | **媒体播放器中的文本轨道**            |
| **<tt>**         | **typewrite text**         | **打字机文本**                  |
| **<u>**          | **underline**              | **下划线**                    |
| **<ul>**         | **Unordered List**         | **无序列表**                   |
| **<var>**        | **variable**               | **变量**                     |
| **<video>**      | **video**                  | **视频**                     |
| **<wbr>**        | **Word BReak**             | **单词换行**                   |



