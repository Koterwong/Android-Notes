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

- Ogg = 带有 Theora 视频编码和 Vorbis 音频编码的 Ogg 文件
- MPEG4 = 带有 H.264 视频编码和 AAC 音频编码的 MPEG 4 文件
- WebM = 带有 VP8 视频编码和 Vorbis 音频编码的 WebM 文件

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

