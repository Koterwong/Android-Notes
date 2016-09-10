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

另外`<input>`标签中另一个重要的属性就是name属性，它代表提交给后台数据的键信息。

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



