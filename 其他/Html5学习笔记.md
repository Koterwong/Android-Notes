## HTML5 是如何起步的？

HTML5 是 W3C 与 WHATWG 合作的结果。

- W3C 指 World Wide Web Consortium，万维网联盟。
- WHATWG 指 Web Hypertext Application Technology Working Group。

WHATWG 致力于 web 表单和应用程序，而 W3C 专注于 XHTML 2.0。在 2006 年，双方决定进行合作，来创建一个新版本的 HTML。

为 HTML5 建立的一些规则：

- 新特性应该基于 HTML、CSS、DOM 以及 JavaScript。
- 减少对外部插件的需求（比如 Flash）
- 更优秀的错误处理
- 更多取代脚本的标记
- HTML5 应该独立于设备
- 开发进程应对公众透明

## 新特性

HTML5 中的一些有趣的新特性：

- 用于绘画的 canvas 元素
- 用于媒介回放的 video 和 audio 元素
- 对本地离线存储的更好的支持
- 新的特殊内容元素，比如 article、footer、header、nav、section
- 新的表单控件，比如 calendar、date、time、email、url、search

## Html视频

当前，video 元素支持三种视频格式：

- Ogg = 带有 Theora 视频编码和 Vorbis 音频编码的 **Ogg 文件**
- MPEG4 = 带有 H.264 视频编码和 AAC 音频编码的 **MPEG 4 文件**
- WebM = 带有 VP8 视频编码和 Vorbis 音频编码的 **WebM 文件**

使用：

```html
<video width="320" height="240" controls="controls" autoplay="autoplay">
  <source src="movie.ogg" type="video/ogg">
  <source src="movie.mp4" type="video/mp4">
Your browser does not support the video tag.
</video>
```

Video标签的属性:

| 属性                                       | 值        | 描述                                       |
| ---------------------------------------- | -------- | ---------------------------------------- |
| [autoplay](http://www.w3school.com.cn/tags/att_video_autoplay.asp) | autoplay | 如果出现该属性，则视频在就绪后马上播放。                     |
| [controls](http://www.w3school.com.cn/tags/att_video_controls.asp) | controls | 如果出现该属性，则向用户显示控件，比如播放按钮。                 |
| [height](http://www.w3school.com.cn/tags/att_video_height.asp) | *pixels* | 设置视频播放器的高度。                              |
| [loop](http://www.w3school.com.cn/tags/att_video_loop.asp) | loop     | 如果出现该属性，则当媒介文件完成播放后再次开始播放。               |
| [preload](http://www.w3school.com.cn/tags/att_video_preload.asp) | preload  | 如果出现该属性，则视频在页面加载时进行加载，并预备播放。如果使用 "autoplay"，则忽略该属性。 |
| [src](http://www.w3school.com.cn/tags/att_video_src.asp) | *url*    | 要播放的视频的 URL。                             |
| [width](http://www.w3school.com.cn/tags/att_video_width.asp) | *pixels* | 设置视频播放器的宽度。                              |

video方法、属性以及事件，下面列出了大多数浏览器支持的视频方法、属性和事件：

| 方法          | 属性          | 事件             |
| ----------- | ----------- | -------------- |
| play()      | currentSrc  | play           |
| pause()     | currentTime | pause          |
| load()      | videoWidth  | progress       |
| canPlayType | videoHeight | error          |
|             | duration    | timeupdate     |
|             | ended       | ended          |
|             | error       | abort          |
|             | paused      | empty          |
|             | muted       | emptied        |
|             | seeking     | waiting        |
|             | volume      | loadedmetadata |
|             | height      |                |
|             | width       |                |

实例

```Html
<!DOCTYPE html> 
<html> 
<body> 

<div style="text-align:center;">
  <button onclick="playPause()">播放/暂停</button> 
  <button onclick="makeBig()">大</button>
  <button onclick="makeNormal()">中</button>
  <button onclick="makeSmall()">小</button>
  <br /> 
  <video id="video1" width="420" style="margin-top:15px;">
    <source src="/example/html5/mov_bbb.mp4" type="video/mp4" />
    <source src="/example/html5/mov_bbb.ogg" type="video/ogg" />
    Your browser does not support HTML5 video.
  </video>
</div> 

<script type="text/javascript">
var myVideo=document.getElementById("video1");

function playPause()
{ 
if (myVideo.paused) 
  myVideo.play(); 
else 
  myVideo.pause(); 
} 

function makeBig()
{ 
myVideo.width=560; 
} 

function makeSmall()
{ 
myVideo.width=320; 
} 

function makeNormal()
{ 
myVideo.width=420; 
} 
</script> 

</body> 
</html>
```

## Html音频

HTML5 提供了播放音频的标准。audio 元素支持三种音频格式：

|            | IE 9 | Firefox 3.5 | Opera 10.5 | Chrome 3.0 | Safari 3.0 |
| ---------- | ---- | ----------- | ---------- | ---------- | ---------- |
| Ogg Vorbis |      | √           | √          | √          |            |
| MP3        | √    |             |            | √          | √          |
| Wav        |      | √           | √          |            | √          |

audio标签的属性：

| 属性                                       | 值        | 描述                                       |
| ---------------------------------------- | -------- | ---------------------------------------- |
| [autoplay](http://www.w3school.com.cn/tags/att_audio_autoplay.asp) | autoplay | 如果出现该属性，则音频在就绪后马上播放。                     |
| [controls](http://www.w3school.com.cn/tags/att_audio_controls.asp) | controls | 如果出现该属性，则向用户显示控件，比如播放按钮。                 |
| [loop](http://www.w3school.com.cn/tags/att_audio_loop.asp) | loop     | 如果出现该属性，则每当音频结束时重新开始播放。                  |
| [preload](http://www.w3school.com.cn/tags/att_audio_preload.asp) | preload  | 如果出现该属性，则音频在页面加载时进行加载，并预备播放。如果使用 "autoplay"，则忽略该属性。 |
| [src](http://www.w3school.com.cn/tags/att_audio_src.asp) | *url*    | 要播放的音频的 URL。                             |

例子：

```html
<audio controls="controls">
  <source src="song.ogg" type="audio/ogg">
  <source src="song.mp3" type="audio/mpeg">
Your browser does not support the audio tag.
</audio>
```

## HTML 5 拖放

html中拖放是标准的一部分，任何元素都能够拖放。下面是一个简单的例子，将img拖放到指定的矩形区域。

```html
<!DOCTYPE HTML>
<html>
<head>
<script type="text/javascript">
	function allowDrop(ev)
	{
		ev.preventDefault();
	}
     
	function drag(ev)
	{
		ev.dataTransfer.setData("Text",ev.target.id);  //指定要拖放的数据
	}

	function drop(ev)
	{
		ev.preventDefault();
		var data=ev.dataTransfer.getData("Text");
		ev.target.appendChild(document.getElementById(data));
	}
</script>
</head>
<body>
	<div id="div1" ondrop="drop(event)" ondragover="allowDrop(event)"> </div>
	
  	<img id="drag1" src="img_logo.gif" draggable="true"
         ondragstart="drag(event)" width="336" height="69" />
</body>
</html>
```

一个拖放事件大致可分为以下步骤：

- 设置元素可拖放，即上面img标签中`<img draggable="true" />`。 

- 明确要拖放元素的什么？在上面的例子中，ondragstart 属性调用了一个函数，drag(*event*)，它规定了被拖动的数据。dataTransfer.setData() 方法设置被拖数据的数据类型和值，上面，数据类型是 "Text"，值是可拖动元素的 id ("drag1")。

- 放到何处 - ondragover。ondragover 事件规定在何处放置被拖动的数据。默认地，无法将数据/元素放置到其他元素中。如果需要设置允许放置，我们必须阻止对元素的默认处理方式。这要通过调用 ondragover 事件的 *event*.preventDefault() 方法：

  ```
  event.preventDefault()
  ```

- 进行放置 - ondrop当放置被拖数据时，会发生 drop 事件。在上面的例子中，ondrop 属性调用了一个函数，drop(*event*)：

  ```html
  function drop(ev)
  {
  	ev.preventDefault();
  	var data=ev.dataTransfer.getData("Text");
  	ev.target.appendChild(document.getElementById(data));
  }
  ```

## Html 5 画布

HTML5 的 canvas 元素使用 JavaScript 在网页上绘制图像。Canvas画布是一个矩形区域，您可以控制其每一像素。canvas 拥有多种绘制路径、矩形、圆形、字符以及添加图像的方法。

（一）创建Canvas对象，规定元素的 id、宽度和高度：

```
<canvas id="myCanvas" width="200" height="100"></canvas>
```

（二）使用JavaScipt控制。Canvas本身并没有绘图能力，绘制工作在JavaScript内部完成。

```javascript
<script type="text/javascript">
	var c=document.getElementById("myCanvas");
	var cxt=c.getContext("2d");
	cxt.fillStyle="#FF0000";
	cxt.fillRect(0,0,150,75);
</script>
```

getContext("2d") 对象是内建的 HTML5 对象，拥有多种绘制路径、矩形、圆形、字符以及添加图像的方法。fillStyle 方法将其染成红色，fillRect 方法规定了形状、位置和尺寸。

（三）实例

实例 - 圆形，通过规定尺寸、颜色和位置，来绘制一个圆：

![](http://www.w3school.com.cn/i/ct_html5_canvas_circle.gif)

```Html
<script type="text/javascript">
	var c=document.getElementById("myCanvas");
	var cxt=c.getContext("2d");
	cxt.fillStyle="#FF0000";
	cxt.beginPath();
	cxt.arc(70,18,15,0,Math.PI*2,true);
	cxt.closePath();
	cxt.fill();
</script>

<canvas id="myCanvas" width="200" height="100" style="border:1px solid #c3c3c3;">
Your browser does not support the canvas element.
</canvas>
```

实例-渐变，使用您指定的颜色来绘制渐变背景：

![](http://www.w3school.com.cn/i/ct_html5_canvas_gradient.gif)

```html
<script type="text/javascript">
	var c=document.getElementById("myCanvas");
	var cxt=c.getContext("2d");
	var grd=cxt.createLinearGradient(0,0,175,50);
	grd.addColorStop(0,"#FF0000");
	grd.addColorStop(1,"#00FF00");
	cxt.fillStyle=grd;
	cxt.fillRect(0,0,175,50);
</script>

<canvas id="myCanvas" width="200" height="100" style="border:1px solid #c3c3c3;">
	Your browser does not support the canvas element.
</canvas>
```

实例 - 图像，把一幅图像放置到画布上：

![](http://www.w3school.com.cn/i/ct_html5_canvas_image.gif)

```html
<script type="text/javascript">
	var c=document.getElementById("myCanvas");
	var cxt=c.getContext("2d");
	var img=new Image()
	img.src="flower.png"
	cxt.drawImage(img,0,0);
</script>

<canvas id="myCanvas" width="200" height="100" style="border:1px solid #c3c3c3;">
	Your browser does not support the canvas element.
</canvas>
```

## Html 5 内联 SVG

什么是SVG？

- SVG 指可伸缩矢量图形 (Scalable Vector Graphics)
- SVG 用于定义用于网络的基于矢量的图形
- SVG 使用 XML 格式定义图形
- SVG 图像在放大或改变尺寸的情况下其图形质量不会有损失
- SVG 是万维网联盟的标准

SVG 的优势。与其他图像格式相比（比如 JPEG 和 GIF），使用 SVG 的优势在于：

- SVG 图像可通过文本编辑器来创建和修改
- SVG 图像可被搜索、索引、脚本化或压缩
- SVG 是可伸缩的
- SVG 图像可在任何的分辨率下被高质量地打印
- SVG 可在图像质量不下降的情况下被放大

把 SVG 直接嵌入 HTML 页面，实例：

```html
<!DOCTYPE html>
<html>
<body>

<svg xmlns="http://www.w3.org/2000/svg" version="1.1" height="190">
  <polygon points="100,10 40,180 190,60 10,60 160,180"
  style="fill:lime;stroke:purple;stroke-width:5;fill-rule:evenodd;" />
</svg>

</body>
</html>
```

如需学习更多有关 SVG 的知识，请阅读 [SVG 教程](http://www.w3school.com.cn/svg/index.asp)。

## HTML 5 Canvas vs. SVG

 **Canvas 和 SVG 都允许您在浏览器中创建图形，但是它们在根本上是不同的。**

**SVG**

SVG 是一种使用 XML 描述 2D 图形的语言。

SVG 基于 XML，这意味着 SVG DOM 中的每个元素都是可用的。您可以为某个元素附加 JavaScript 事件处理器。

在 SVG 中，每个被绘制的图形均被视为对象。如果 SVG 对象的属性发生变化，那么浏览器能够自动重现图形。

**Canvas**

Canvas 通过 JavaScript 来绘制 2D 图形。

Canvas 是逐像素进行渲染的。

在 canvas 中，一旦图形被绘制完成，它就不会继续得到浏览器的关注。如果其位置发生变化，那么整个场景也需要重新绘制，包括任何或许已被图形覆盖的对象。

**Canvas 与 SVG 的比较**

下表列出了 canvas 与 SVG 之间的一些不同之处。

Canvas

- 依赖分辨率
- 不支持事件处理器
- 弱的文本渲染能力
- 能够以 .png 或 .jpg 格式保存结果图像
- 最适合图像密集型的游戏，其中的许多对象会被频繁重绘

SVG

- 不依赖分辨率
- 支持事件处理器
- 最适合带有大型渲染区域的应用程序（比如谷歌地图）
- 复杂度高会减慢渲染速度（任何过度使用 DOM 的应用都不快）
- 不适合游戏应用

## HTML5 地理定位

暂时略过

## HTML 5 Web 存储

在客户端存储数据

HTML5 提供了两种在客户端存储数据的新方法：

- localStorage - 没有时间限制的数据存储
- sessionStorage - 针对一个 session 的数据存储

之前，这些都是由 cookie 完成的。但是 cookie 不适合大量数据的存储，因为它们由每个对服务器的请求来传递，这使得 cookie 速度很慢而且效率也不高。

Html5 存储数据特点：

- 在 HTML5 中，**数据不是由每个服务器请求传递的，而是只有在请求时使用数据**。它使在不影响网站性能的情况下存储大量数据成为可能。
- **对于不同的网站，数据存储于不同的区域，并且一个网站只能访问其自身的数据。**
- **HTML5 使用 JavaScript 来存储和访问数据。**

**（1）localStorage 方法存储数据**

localStorage 方法存储的数据没有时间限制。第二天、第二周或下一年之后，数据依然可用。

```html
下面的例子对用户访问页面的次数进行计数：
<script type="text/javascript">
	if (localStorage.pagecount)
  	{
 		localStorage.pagecount=Number(localStorage.pagecount) +1;
 	}
	else
  	{
  		localStorage.pagecount=1;
  	}
	document.write("Visits "+ localStorage.pagecount + " time(s).");
</script>
```

**（2）sessionStorage 方法**

sessionStorage 方法针对一个 session 进行数据存储。当用户关闭浏览器窗口后，数据会被删除。

```javascript
下面的例子对用户在当前 session 中访问页面的次数进行计数：

<script type="text/javascript">
	if (sessionStorage.pagecount)
  	{
  		sessionStorage.pagecount=Number(sessionStorage.pagecount) +1;
  	}
	else
  	{
  		sessionStorage.pagecount=1;
  	}
	document.write("Visits "+sessionStorage.pagecount+" time(s) this session.");
</script>
```

## HTML 5 应用程序缓存

什么是应用程序缓存（Application Cache）？HTML5 引入了应用程序缓存，这意味着 web 应用可进行缓存，并可在没有因特网连接时进行访问。**使用 HTML5，通过创建 cache manifest 文件，可以轻松地创建 web 应用的离线版本。**

应用程序缓存为应用带来三个优势：

- 离线浏览 - 用户可在应用离线时使用它们
- 速度 - 已缓存资源加载得更快
- 减少服务器负载 - 浏览器将只从服务器下载更新过或更改过的资源。

**（1）启用Cache Manifest**

如需启用应用程序缓存，请在文档的 `<html>` 标签中包含 manifest 属性：

```
<!DOCTYPE HTML>
	<html manifest="demo.appcache">
	...
</html>	
```

每个指定了 manifest 的页面在用户对其访问时都会被缓存。如果未指定 manifest 属性，则页面不会被缓存（除非在 manifest 文件中直接指定了该页面）。

manifest 文件的建议的文件扩展名是：".appcache"。

请注意，manifest 文件需要配置*正确的 MIME-type*，即 "text/cache-manifest"。必须在 web 服务器上进行配置。

**（2）配置Manifest 文件**

manifest 文件是简单的文本文件，它告知浏览器被缓存的内容（以及不缓存的内容）。

manifest 文件可分为三个部分：

- *CACHE MANIFEST* - 在此标题下列出的文件将在首次下载后进行缓存
- *NETWORK* - 在此标题下列出的文件需要与服务器的连接，且不会被缓存
- *FALLBACK* - 在此标题下列出的文件规定当页面无法访问时的回退页面（比如 404 页面）

（一）CACHE MANIFEST，第一行，CACHE MANIFEST，是必需的：

```html
CACHE MANIFEST
/theme.css
/logo.gif
/main.js
```

上面的 manifest 文件列出了三个资源：一个 CSS 文件，一个 GIF 图像，以及一个 JavaScript 文件。当 manifest 文件加载后，浏览器会从网站的根目录下载这三个文件。然后，无论用户何时与因特网断开连接，这些资源依然是可用的。

（二）NETWORK，下面的 NETWORK 小节规定文件 "login.asp" 永远不会被缓存，且离线时是不可用的：

```html
NETWORK:
login.asp
```

可以使用星号来指示所有其他资源/文件都需要因特网连接：

```html
NETWORK:
*
```

（三）FALLBACK，下面的 FALLBACK 小节规定如果无法建立因特网连接，则用 "offline.html" 替代 /html5/ 目录中的所有文件：

```
FALLBACK:
/html5/ /404.html
```

注释：第一个 URI 是资源，第二个是替补。

更新缓存

一旦应用被缓存，它就会保持缓存直到发生下列情况：

- 用户清空浏览器缓存
- manifest 文件被修改（参阅下面的提示）
- 由程序来更新应用缓存

**实例 - 完整的 Manifest 文件**

```
CACHE MANIFEST
# 2012-02-21 v1.0.0
/theme.css
/logo.gif
/main.js

NETWORK:
login.asp

FALLBACK:
/html5/ /404.html
```

重要的提示：以 "#" 开头的是注释行，但也可满足其他用途。应用的缓存会在其 manifest 文件更改时被更新。如果您编辑了一幅图片，或者修改了一个 JavaScript 函数，这些改变都不会被重新缓存。更新注释行中的日期和版本号是一种使浏览器重新缓存文件的办法。

> 关于应用程序缓存的注释:

请留心缓存的内容。

一旦文件被缓存，则浏览器会继续展示已缓存的版本，即使您修改了服务器上的文件。为了确保浏览器更新缓存，您需要更新 manifest 文件。

注释：浏览器对缓存数据的容量限制可能不太一样（某些浏览器设置的限制是每个站点 5MB）。

## Html5 Web Workers

什么是Web Workers?

web workers 是运行在后台的javascript，不会影响页面的性能。当在Html页面中执行脚本的时候，页面是不可响应状态，直到脚本已完成。而web workers 是运行在后台的javascript，不会影响页面的性能。您可以继续做任何愿意做的事情：点击、选取内容等等，而此时 web worker 在后台运行。

**（1）检测 Web Worker 支持** ，在创建 web worker 之前，请检测用户的浏览器是否支持它：

```javascript
if(typeof(Worker)!=="undefined")
{
  // Yes! Web worker support!
  // Some code.....
}
else
{
  // Sorry! No Web Worker support..
}
```

**（2）创建 web worker 文件**

现在，让我们在一个外部 JavaScript 中创建我们的 web worker。

在这里，我们创建了计数脚本。该脚本存储于 "demo_workers.js" 文件中：

```javascript
var i=0;
function timedCount()
{
	i=i+1;
	postMessage(i);
	setTimeout("timedCount()",500);
}
timedCount();
```

以上代码中重要的部分是 *postMessage()* 方法 - 它用于向 HTML 页面传回一段消息。**注释：web worker 通常不用于如此简单的脚本，而是用于更耗费 CPU 资源的任务。**

**（3）创建 Web Worker 对象**

我们已经有了 web worker 文件，现在我们需要从 HTML 页面调用它。

下面的代码检测是否存在 worker，如果不存在，- 它会创建一个新的 web worker 对象，然后运行 "demo_workers.js" 中的代码：

```javascript
if(typeof(w)=="undefined")
{
  	w=new Worker("demo_workers.js");
}
```

然后我们就可以从 web worker 发送和接收消息了。

向 web worker 添加一个 "onmessage" 事件监听器：

```javascript
w.onmessage=function(event){
	document.getElementById("result").innerHTML=event.data;
};
```

当 web worker 传递消息时，会执行事件监听器中的代码。event.data 中存有来自 event.data 的数据。

**终止 Web Worker**

当我们创建 web worker 对象后，它会继续监听消息（即使在外部脚本完成之后）直到其被终止为止。

如需终止 web worker，并释放浏览器/计算机资源，请使用 terminate() 方法：

```javascript
w.terminate();
```

完整的 Web Worker 实例代码

我们已经看到了 .js 文件中的 Worker 代码。下面是 HTML 页面的代码：

```javascript
<!DOCTYPE html>
<html>
<body>
	<p>Count numbers: <output id="result"></output></p>
	<button onclick="startWorker()">Start Worker</button>
	<button onclick="stopWorker()">Stop Worker</button>
	<br /><br />
<script>
	var w;
	function startWorker()
	{
		if(typeof(Worker)!=="undefined")
		{
  			if(typeof(w)=="undefined")
    		{
    			w=new Worker("demo_workers.js");
   			}
  			w.onmessage = function (event) {
   				document.getElementById("result").innerHTML=event.data;
  			};
		}
		else
		{
			document.getElementById("result").innerHTML="Sorry, your browser
 			does not support Web Workers...";
		}
	}
	function stopWorker()
	{
		w.terminate();
	}
</script>
</body>
</html>
```

**Web Workers 和 DOM**

由于 web worker 位于外部文件中，它们无法访问下例 JavaScript 对象：

- window 对象
- document 对象
- parent 对象

## Html 5 服务器发送事件

**HTML5 服务器发送事件（server-sent event）允许网页获得来自服务器的更新。**

**Server-Sent 事件 - 单向消息传递** ，Server-Sent 事件指的是网页自动获取来自服务器的更新。以前也可能做到这一点，前提是网页不得不询问是否有可用的更新。通过服务器发送事件，更新能够自动到达。

例子：Facebook/Twitter 更新、估价更新、新的博文、赛事结果等。

**（1）接收 Server-Sent 事件通知**

EventSource 对象用于接收服务器发送事件通知：下面是一个实例

```javascript
var source=new EventSource("demo_sse.php");
source.onmessage=function(event)
{
  document.getElementById("result").innerHTML+=event.data + "<br />";
};
```

例子解释：

- 创建一个新的 EventSource 对象，然后规定发送更新的页面的 URL（本例中是 "demo_sse.php"）
- 每接收到一次更新，就会发生 onmessage 事件
- 当 onmessage 事件发生时，把已接收的数据推入 id 为 "result" 的元素中

**（2）检测 Server-Sent 事件支持**

在上面的 TIY 实例中，我们编写了一段额外的代码来检测服务器发送事件的浏览器支持情况：

```javascript
if(typeof(EventSource)!=="undefined")
{
  // Yes! Server-sent events support!
  // Some code.....
}
else
{
  // Sorry! No server-sent events support..
}
```

**（3）服务器端代码实例**

为了让上面的例子可以运行，您还需要能够发送数据更新的服务器（比如 PHP 和 ASP）。

服务器端事件流的语法是非常简单的。把 "Content-Type" 报头设置为 "text/event-stream"。现在，您可以开始发送事件流了。

PHP 代码 (demo_sse.php)：

```PHP
<?php
	header('Content-Type: text/event-stream');
	header('Cache-Control: no-cache');
	$time = date('r');
	echo "data: The server time is: {$time}\n\n";
	flush();
?>
```

ASP 代码 (VB) (demo_sse.asp):

```ASP
<%
	Response.ContentType="text/event-stream"
	Response.Expires=-1
	Response.Write("data: " & now())
	Response.Flush()
%>
```

代码解释：

- 把报头 "Content-Type" 设置为 "text/event-stream"
- 规定不对页面进行缓存
- 输出发送日期（始终以 "data: " 开头）
- 向网页刷新输出数据

**（4）EventSource 对象**

在上面的例子中，我们使用 onmessage 事件来获取消息。不过还可以使用其他事件：

| 事件        | 描述           |
| --------- | ------------ |
| onopen    | 当通往服务器的连接被打开 |
| onmessage | 当接收到消息       |
| onerror   | 当错误发生        |

## HTML5 Input 表单

### HTML 5 新的Input类型

HTML5 拥有多个新的表单输入类型。这些新特性提供了更好的输入控制和验证。

下面将全面介绍这些新的输入类型：

- email
- url
- number
- range
- Date pickers (date, month, week, time, datetime, datetime-local)
- search
- color

**Input 类型 - email**

email 类型用于应该包含 e-mail 地址的输入域。在提交表单时，会自动验证 email 域的值。

```HTML
E-mail: <input type="email" name="user_email" />
```

**Input 类型 - url**

url 类型用于应该包含 URL 地址的输入域。在提交表单时，会自动验证 url 域的值。

```HTML
Homepage: <input type="url" name="user_url" />
```

**Input类型 - number**

number 类型用于应该包含数值的输入域。您还能够设定对所接受的数字的限定：

```HTML
Points: <input type="number" name="points" min="1" max="10" />
```

number类型的属性

| 属性    | 值        | 描述                                       |
| ----- | -------- | ---------------------------------------- |
| max   | *number* | 规定允许的最大值                                 |
| min   | *number* | 规定允许的最小值                                 |
| step  | *number* | 规定合法的数字间隔（如果 step="3"，则合法的数是 -3,0,3,6 等） |
| value | *number* | 规定默认值                                    |

**Input 类型 - range**

range 类型用于应该包含一定范围内数字值的输入域。range 类型显示为滑动条。

您还能够设定对所接受的数字的限定：

```HTML
<input type="range" name="points" min="1" max="10" />
```

**Input 类型 - Date Pickers（日期选择器）**

HTML5 拥有多个可供选取日期和时间的新输入类型：

- date - 选取日、月、年
- month - 选取月、年
- week - 选取周和年
- time - 选取时间（小时和分钟）
- datetime - 选取时间、日、月、年（UTC 时间）
- datetime-local - 选取时间、日、月、年（本地时间）

下面的例子允许您从日历中选取一个日期：

```HTML
Date: <input type="date" name="user_date" />
```

**Input 类型 - search**

search 类型用于搜索域，比如站点搜索或 Google 搜索。search 域显示为常规的文本域。

### HTML5 表单元素

HTML5 拥有若干涉及表单的元素和属性。

本章介绍以下新的表单元素：

- datalist
- keygen
- output

**（1）datalist 元素**

datalist 元素规定输入域的选项列表。列表是通过 datalist 内的 option 元素创建的。

**如需把 datalist 绑定到输入域，请用输入域的 list 属性引用 datalist 的 id：**

注意：option 元素永远都要设置 value 属性。

```HTML
Webpage: <input type="url" list="url_list" name="link" />
<datalist id="url_list">
	<option label="W3School" value="http://www.W3School.com.cn" />
	<option label="Google" value="http://www.google.com" />
	<option label="Microsoft" value="http://www.microsoft.com" />
</datalist>
```

**（2）keygen**

keygen 元素的作用是提供一种验证用户的可靠方法。

keygen 元素是密钥对生成器（key-pair generator）。当提交表单时，会生成两个键，一个是私钥，一个公钥。

私钥（private key）存储于客户端，公钥（public key）则被发送到服务器。公钥可用于之后验证用户的客户端证书（client certificate）。

目前，浏览器对此元素的糟糕的支持度不足以使其成为一种有用的安全标准。

```HTML
<form action="demo_form.asp" method="get">
	Username: <input type="text" name="usr_name" />
	Encryption: <keygen name="security" />
	<input type="submit" />
</form>
```

**（3）output 元素**

output 元素用于不同类型的输出，比如计算或脚本输出：

```HTML
<output id="result" onforminput="resCalc()"></output>
```

### HTML5 的新的表单属性

本章讲解涉及 <form> 和 <input> 元素的新属性。

**新的 form 属性：**

- autocomplete
- novalidate

**新的 input 属性：**

- autocomplete
- autofocus
- form
- form overrides (formaction, formenctype, formmethod, formnovalidate, formtarget)
- height 和 width
- list
- min, max 和 step
- multiple
- pattern (regexp)
- placeholder
- required

**autocomplete 属性**

autocomplete 属性规定 form 或 input 域应该拥有自动完成功能。

注释：autocomplete 适用于 `<form>` 标签，以及以下类型的 `<input>` 标签：text, search, url, telephone, email, password, datepickers, range 以及 color。

当用户在自动完成域中开始输入时，浏览器应该在该域中显示填写的选项：

```html
<form action="demo_form.asp" method="get" autocomplete="on">
	First name: <input type="text" name="fname" /><br />
	Last name: <input type="text" name="lname" /><br />
	E-mail: <input type="email" name="email" autocomplete="off" /><br />
	<input type="submit" />
</form>
```

**autofocus 属性**

autofocus 属性规定在页面加载时，域自动地获得焦点。

注释：autofocus 属性适用于所有 `<input>` 标签的类型。

```HTML
User name: <input type="text" name="user_name"  autofocus="autofocus" />
```

**form 属性**

form 属性规定输入域所属的一个或多个表单。

注释：form 属性适用于所有 `<input>` 标签的类型。**form 属性必须引用所属表单的 id，如需引用一个以上的表单，请使用空格分隔的列表。**

```html
<form action="demo_form.asp" method="get" id="user_form">
	First name:<input type="text" name="fname" />
	<input type="submit" />
</form>
Last name: <input type="text" name="lname" form="user_form" />
```

**表单重写属性**

表单重写属性（form override attributes）允许您重写 form 元素的某些属性设定。

表单重写属性有：

- formaction - 重写表单的 action 属性
- formenctype - 重写表单的 enctype 属性
- formmethod - 重写表单的 method 属性
- formnovalidate - 重写表单的 novalidate 属性
- formtarget - 重写表单的 target 属性

注释：表单重写属性适用于以下类型的 `<input>` 标签：submit 和 image。

```html
<form action="demo_form.asp" method="get" id="user_form">
	E-mail: <input type="email" name="userid" /><br />
	<input type="submit" value="Submit" /><br />
	<input type="submit" formaction="demo_admin.asp" value="Submit as admin" /><br />
	<input type="submit" formnovalidate="true" value="Submit without validation" /><br/>
</form>
```

注释：这些属性对于创建不同的提交按钮很有帮助。例如第二个提交按钮，就会提交到服务器的`demo_admin` 。

**height 和 width 属性**

height 和 width 属性规定用于 image 类型的 input 标签的图像高度和宽度。

注释：height 和 width 属性只适用于 image 类型的 `<input>` 标签。

```HTML
<input type="image" src="img_submit.gif" width="99" height="99" />
```

**list 属性**

list 属性规定输入域的 datalist。datalist 是输入域的选项列表。

注释：list 属性适用于以下类型的 `<input>` 标签：text, search, url, telephone, email, date pickers, number, range 以及 color。

```html
Webpage: <input type="url" list="url_list" name="link" />
<datalist id="url_list">
	<option label="W3Schools" value="http://www.w3school.com.cn" />
	<option label="Google" value="http://www.google.com" />
	<option label="Microsoft" value="http://www.microsoft.com" />
</datalist>
```

**min、max 和 step 属性**

min、max 和 step 属性用于为包含数字或日期的 input 类型规定限定（约束）。

max 属性规定输入域所允许的最大值。

min 属性规定输入域所允许的最小值。

step 属性为输入域规定合法的数字间隔（如果 step="3"，则合法的数是 -3,0,3,6 等）。

注释：min、max 和 step 属性适用于以下类型的 `<input>` 标签：date pickers、number 以及 range。

下面的例子显示一个数字域，该域接受介于 0 到 10 之间的值，且步进为 3（即合法的值为 0、3、6 和 9）：

```html
Points: <input type="number" name="points" min="0" max="10" step="3" />
```

**multiple 属性**

multiple 属性规定输入域中可选择多个值。

注释：multiple 属性适用于以下类型的 `<input>` 标签：email 和 file。

```html
Select images: <input type="file" name="img" multiple="multiple" />
```

**novalidate 属性**

novalidate 属性规定在提交表单时不应该验证 form 或 input 域。

注释：novalidate 属性适用于 `<form>` 以及以下类型的 `<input>` 标签：text, search, url, telephone, email, password, date pickers, range 以及 color.

```html
<form action="demo_form.asp" method="get" novalidate="true">
	E-mail: <input type="email" name="user_email" />
	<input type="submit" />
</form>
```

**pattern 属性**

pattern 属性规定用于验证 input 域的模式（pattern）。

模式（pattern） 是正则表达式。您可以在我们的 [JavaScript 教程](http://www.w3school.com.cn/js/index.asp)中学习到有关正则表达式的内容。

注释：pattern 属性适用于以下类型的 `<input>` 标签：text, search, url, telephone, email 以及 password。

下面的例子显示了一个只能包含三个字母的文本域（不含数字及特殊字符）：

```HTML
Country code: <input type="text" name="country_code"
pattern="[A-z]{3}" title="Three letter country code" />
```

**placeholder 属性**

placeholder 属性提供一种提示（hint），描述输入域所期待的值。

注释：placeholder 属性适用于以下类型的 <input> 标签：text, search, url, telephone, email 以及 password。

提示（hint）会在输入域为空时显示出现，会在输入域获得焦点时消失：

```HTML
<input type="search" name="user_search"  placeholder="Search W3School" />
```

**required 属性**

required 属性规定必须在提交之前填写输入域（不能为空）。

注释：required 属性适用于以下类型的 <input> 标签：text, search, url, telephone, email, password, date pickers, number, checkbox, radio 以及 file。

```HTML
Name: <input type="text" name="usr_name" required="required" />
```