# 没有布局

如果你只想把所有内容都塞进一栏里，那么不用设置任何布局也是OK的。然而，如果用户把浏览器窗口调整的很大，这时阅读网页会非常不适：读完每一行之后，你的视觉焦点要从右到左移动一大段距离。调整下浏览器窗口大小你就明白我的意思了！

在解决这个问题之前，我们需要了解一个很重要的属性： `display`

# "display"属性

`display` 是CSS中最重要的用于控制布局的属性。每个元素都有一个默认的 display 值，这与元素的类型有关。对于大多数元素它们的默认值通常是 `block` 或 `inline` 。一个 block 元素通常被叫做块级元素。一个 inline 元素通常被叫做行内元素。

## block

<div>`div` 是一个标准的块级元素。一个块级元素会新开始一行并且尽可能撑满容器。其他常用的块级元素包括 `p` 、 `form` 和HTML5中的新元素： `header` 、 `footer` 、 `section` 等等。</div>

## inline

`span` 是一个标准的行内元素。一个行内元素可以在段落中 <span> 像这样 </span> 包裹一些文字而不会打乱段落的布局。 `a` 元素是最常用的行内元素，它可以被用作链接。

## none

另一个常用的display值是 `none` 。一些特殊元素的默认 display 值是它，例如 `script` 。 `display:none` 通常被 JavaScript 用来在不删除元素的情况下隐藏或显示元素。

它和 `visibility` 属性不一样。把 `display` 设置成 `none` 不会保留元素本该显示的空间，但是 `visibility: hidden;` 还会保留。

## 其他 display 值

还有很多的更有意思的 display 值，例如 `list-item` 和 `table` 。[这里有一份详细的列表](https://developer.mozilla.org/en-US/docs/CSS/display)。之后我们会讨论下 `inline-block` 和 `flex` 。

## 额外加分点

就像我之前讨论过的，每个元素都有一个默认的 display 类型。不过你可以*随时随地*的重写它！虽然“人工制造”一个行内元素可能看起来很难以理解，不过你可以把有特定语义的元素改成行内元素。常见的例子是：把 `li` 元素修改成 inline，制作成水平菜单。

# margin: auto;

```
#main {
  width: 600px;
  margin: 0 auto; 
}
```

<div id="main">设置块级元素的 `width` 可以阻止它从左到右撑满容器。然后你就可以设置左右外边距为 `auto` 来使其水平居中。元素会占据你所指定的宽度，然后剩余的宽度会一分为二成为左右外边距。唯一的问题是，当浏览器窗口比元素的宽度还要窄时，浏览器会显示一个水平滚动条来容纳页面。让我们再来改进下这个方案...</div>

# max-width

```
#main {
  max-width: 600px;
  margin: 0 auto; 
}
```

<div id="main">在这种情况下使用 `max-width` 替代 `width` 可以使浏览器更好地处理小窗口的情况。这点在移动设备上显得尤为重要，调整下浏览器窗口大小检查下吧！顺便提下， [所有的主流浏览器](http://caniuse.com/#search=max-width)包括IE7+在内都支持 `max-width` ，所以放心大胆的用吧。

# 盒模型

在我们讨论宽度的时候，我们应该讲下与它相关的一个重点知识：*盒模型*。当你设置了元素的宽度，实际展现的元素却能够超出你的设置：因为元素的边框和内边距会撑开元素。看下面的例子，两个相同宽度的元素显示的实际宽度却不一样。

```
.simple {
  width: 500px;
  margin: 20px auto;
}

.fancy {
  width: 500px;
  margin: 20px auto;
  padding: 50px;
  border-width: 10px;
}
```

<div class="simple">我小一些...</div>

<div class="fancy">我比它大！</div>

以前有一个代代相传的解决方案是数学。CSS开发者需要用比他们实际想要的宽度小一点的宽度，需要减去内边距和边框的宽度。值得庆幸地是你不需要再这么做了... 

# box-sizing

经过了一代又一代人们意识到数学不好玩，所以他们新增了一个叫做 `box-sizing` 的CSS属性。当你设置一个元素为 `box-sizing: border-box;` 时，此元素的内边距和边框不再会增加它的宽度。这里有一个与前一页相同的例子，唯一的区别是两个元素都设置了 `box-sizing: border-box;` ：

```
.simple {
  width: 500px;
  margin: 20px auto;
  -webkit-box-sizing: border-box;
     -moz-box-sizing: border-box;
          box-sizing: border-box;
}

.fancy {
  width: 500px;
  margin: 20px auto;
  padding: 50px;
  border: solid blue 10px;
  -webkit-box-sizing: border-box;
     -moz-box-sizing: border-box;
          box-sizing: border-box;
}
```

<div class="simple">我们现在一样大小了！</div>

<div class="fancy">万岁！</div>

既然没有比这更好的方法，一些CSS开发者想要页面上所有的元素都有如此表现。所以开发者们把以下CSS代码放在他们页面上：

```
* {
  -webkit-box-sizing: border-box;
     -moz-box-sizing: border-box;
          box-sizing: border-box;
}
```

这样可以确保所有的元素都会用这种更直观的方式排版。

既然 `box-sizing` 是个很新的属性，目前你还应该像我之前在例子中那样使用 `-webkit-` 和`-moz-` 前缀。这可以启用特定浏览器实验中的特性。同时记住它是支持[IE8+](http://caniuse.com/#search=box-sizing)。

# position

为了制作更多复杂的布局，我们需要讨论下 `position` 属性。它有一大堆的值，名字还都特抽象，别提有多难记了。让我们先一个个的过一遍，不过你最好还是把这页放到书签里。

## static

```
.static {
  position: static;
}
```

<div class="static">`static` 是默认值。任意 `position: static;` 的元素不会被特殊的定位。一个 static 元素表示它*不会被“positioned”*，一个 position 属性被设置为其他值的元素表示它*会被“positioned”*。</div>

## relative

```
.relative1 {
  position: relative;
}
.relative2 {
  position: relative;
  top: -20px;
  left: 20px;
  background-color: white;
  width: 500px;
}
```

<div class="relative1">`relative` 表现的和 `static` 一样，除非你添加了一些额外的属性。</div>

<div class="relative2">在一个相对定位（position属性的值为relative）的元素上设置 `top`、 `right` 、 `bottom` 和 `left` 属性会使其偏离其正常位置。其他的元素则不会调整位置来弥补它偏离后剩下的空隙。</div>

## fixed

<div class="fixed">Hello！暂时不要太关注我哦。</div>

一个固定定位（position属性的值为fixed）元素会相对于视窗来定位，这意味着即便页面滚动，它还是会停留在相同的位置。和 `relative` 一样， `top` 、 `right` 、 `bottom` 和`left` 属性都可用。

我相信你已经注意到页面右下角的固定定位元素。你现在可以仔细看看它，这里有它所使用的CSS：

```
.fixed {
  position: fixed;
  bottom: 0;
  right: 0;
  width: 200px;
  background-color: white;
}
```

一个固定定位元素不会保留它原本在页面应有的空隙。

令人惊讶地是移动浏览器对 fixed 的支持很差。[这里有相应的解决方案](http://bradfrostweb.com/blog/mobile/fixed-position/).

## absolute

`absolute` 是最棘手的position值。 `absolute` 与 `fixed` 的表现类似，除了它不是相对于视窗而是相对于*最近的“positioned”祖先元素*。如果绝对定位（position属性的值为absolute）的元素没有“positioned”祖先元素，那么它是相对于文档的 body 元素，并且它会随着页面滚动而移动。记住一个“positioned”元素是指p osition 值不是 `static` 的元素。

这里有一个简单的例子：

```
.relative {
  position: relative;
  width: 600px;
  height: 400px;
}
.absolute {
  position: absolute;
  top: 120px;
  right: 0;
  width: 300px;
  height: 200px;
}
```

<div class="relative">这个元素是相对定位的。如果它是 `position: static;` ，那么它的绝对定位子元素会跳过它直接相对于body元素定位。<div class="absolute">这个元素是绝对定位的。它相对于它的父元素定位。</div></div>

这部分比较难理解，但它是创造优秀布局所必需的知识。下一页我们会使用 `position`做更具体的例子。

# float

另一个布局中常用的CSS属性是 `float` 。Float 可用于实现文字环绕图片，如下：

```
img {
  float: right;
  margin: 0 0 1em 1em;
}
```

![An Image](http://zh.learnlayout.com/images/ilta.png)Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus imperdiet, nulla et dictum interdum, nisi lorem egestas odio, vitae scelerisque enim ligula venenatis dolor. Maecenas nisl est, ultrices nec congue eget, auctor vitae massa. Fusce luctus vestibulum augue ut aliquet. Mauris ante ligula, facilisis sed ornare eu, lobortis in odio. Praesent convallis urna a lacus interdum ut hendrerit risus congue. Nunc sagittis dictum nisi, sed ullamcorper ipsum dignissim ac. In at libero sed nunc venenatis imperdiet sed ornare turpis. Donec vitae dui eget tellus gravida venenatis. Integer fringilla congue eros non fermentum. Sed dapibus pulvinar nibh tempor porta. Cras ac leo purus. Mauris quis diam velit.

# clear

`clear` 属性被用于控制浮动。比较下面两个例子：

```
<div class="box">...</div>
<section>...</section>
```

```
.box {
  float: left;
  width: 200px;
  height: 100px;
  margin: 1em;
}
```

<div class="box">我感觉好像我在漂浮！</div>

<section>在这个例子中， `section` 元素实际上是在 `div` 之后的（译注：DOM结构上）。然而 `div` 元素是浮动到左边的，于是 `section` 中的文字就围绕了 `div` ，并且 `section` 元素包围了整个元素。如果我们想让 `section` 显示在浮动元素之后呢？</section>

```
.box {
  float: left;
  width: 200px;
  height: 100px;
  margin: 1em;
}
.after-box {
  clear: left;
}
```

<div class="box">我感觉好像我在漂浮！</div>

<section class="after-box">使用 `clear` 我们就可以将这个段落移动到浮动元素 `div` 下面。你需要用 `left` 值才能清除元素的向左浮动。你还可以用 `right` 或 `both` 来清除向右浮动或同时清除向左向右浮动。

# 清除浮动（clearfix hack）

在使用浮动的时候经常会遇到一个古怪的事情：

```
img {
  float: right;
}
```

<div>![An Image](http://zh.learnlayout.com/images/ilta.png)不......这个图片比包含它的元素还高， 而且它是浮动的，于是它就溢出到了容器外面！

见证奇迹的时刻到了！有一种比较丑陋的方法可以解决这个问题，它叫做*清除浮动（clearfix hack）*.

让我们加入一些新的CSS样式：

```
.clearfix {
  overflow: auto;
}
```

现在再看看发生了什么：

<div class="clearfix">![An Image](http://zh.learnlayout.com/images/ilta.png)好多了！

这个可以在现代浏览器上工作。如果你想要支持IE6，你就需要再加入如下样式：

```
.clearfix {
  overflow: auto;
  zoom: 1;
}
```

有些独特的浏览器需要“额外的关照”。[清除浮动这谭水很深很深](http://stackoverflow.com/questions/211383/which-method-of-clearfix-is-best)，但是这个简单的解决方案已经可以在今天所有的主要浏览器上工作。

# 浮动布局例子

完全使用 `float` 来实现页面的布局是很常见的。这里有一个我之前用 `position` 实现的布局例子，这次我使用 `float` 实现了它。

```
nav {
  float: left;
  width: 200px;
}
section {
  margin-left: 200px;
}
```

<div class="clearfix"><nav>[Home](http://zh.learnlayout.com/float-layout.html)[Taco Menu](http://zh.learnlayout.com/float-layout.html)[Draft List](http://zh.learnlayout.com/float-layout.html)[Hours](http://zh.learnlayout.com/float-layout.html)[Directions](http://zh.learnlayout.com/float-layout.html)[Contact](http://zh.learnlayout.com/float-layout.html)</nav><section>这个例子和之前那个外观一模一样。请注意我们在容器上做了“清除浮动”。原本在这个例子中是不需要的，但是当 `nav` 比非浮动的内容还要高时就需要了。</section><section>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus imperdiet, nulla et dictum interdum, nisi lorem egestas odio, vitae scelerisque enim ligula venenatis dolor. Maecenas nisl est, ultrices nec congue eget, auctor vitae massa. Fusce luctus vestibulum augue ut aliquet. Mauris ante ligula, facilisis sed ornare eu, lobortis in odio. Praesent convallis urna a lacus interdum ut hendrerit risus congue. Nunc sagittis dictum nisi, sed ullamcorper ipsum dignissim ac. In at libero sed nunc venenatis imperdiet sed ornare turpis. Donec vitae dui eget tellus gravida venenatis. Integer fringilla congue eros non fermentum. Sed dapibus pulvinar nibh tempor porta. Cras ac leo purus. Mauris quis diam velit.</section>

# 百分比宽度

百分比是一种相对于包含块的计量单位。它对图片很有用：如下我们实现了图片宽度始终是容器宽度的50%。把页面缩小看下效果！

```
article img {
  float: right;
  width: 50%;
}
```

<article class="clearfix">![an image](http://zh.learnlayout.com/images/ilta.png)你甚至还能同时使用 `min-width` 和 `max-width` 来限制图片的最大或最小宽度！</article>

## 百分比宽度布局

你可以用百分比做布局，但是这需要更多的工作。在下面的例子中，当窗口宽度很窄时 `nav` 的内容会以一种不太友好的方式被包裹起来。总而言之，选一种最合适你的内容的方式。

```
nav {
  float: left;
  width: 25%;
}
section {
  margin-left: 25%;
}
```

<div class="container"><nav>[Home](http://zh.learnlayout.com/percent.html)[Taco Menu](http://zh.learnlayout.com/percent.html)[Draft List](http://zh.learnlayout.com/percent.html)[Hours](http://zh.learnlayout.com/percent.html)[Directions](http://zh.learnlayout.com/percent.html)[Contact](http://zh.learnlayout.com/percent.html)</nav><section>当布局很窄时， `nav` 就会被挤扁。更糟糕的是，你不能在 nav 上使用 `min-width` 来修复这个问题，因为右边的那列是不会遵守它的。</section><section>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus imperdiet, nulla et dictum interdum, nisi lorem egestas odio, vitae scelerisque enim ligula venenatis dolor. Maecenas nisl est, ultrices nec congue eget, auctor vitae massa. Fusce luctus vestibulum augue ut aliquet. Mauris ante ligula, facilisis sed ornare eu, lobortis in odio. Praesent convallis urna a lacus interdum ut hendrerit risus congue. Nunc sagittis dictum nisi, sed ullamcorper ipsum dignissim ac. In at libero sed nunc venenatis imperdiet sed ornare turpis. Donec vitae dui eget tellus gravida venenatis. Integer fringilla congue eros non fermentum. Sed dapibus pulvinar nibh tempor porta. Cras ac leo purus. Mauris quis diam velit.</section>

# 媒体查询

“响应式设计（Responsive Design）”是一种让网站针对不同的浏览器和设备“响应”不同显示效果的策略，这样可以让网站在任何情况下显示的很棒！

媒体查询是做此事所需的最强大的工具。让我们使用百分比宽度来布局，然后在浏览器变窄到无法容纳侧边栏中的菜单时，把布局显示成一列：

```
@media screen and (min-width:600px) {
  nav {
    float: left;
    width: 25%;
  }
  section {
    margin-left: 25%;
  }
}
@media screen and (max-width:599px) {
  nav li {
    display: inline;
  }
}
```

<div class="container"><nav>[Home](http://zh.learnlayout.com/percent.html)[Taco Menu](http://zh.learnlayout.com/percent.html)[Draft List](http://zh.learnlayout.com/percent.html)[Hours](http://zh.learnlayout.com/percent.html)[Directions](http://zh.learnlayout.com/percent.html)[Contact](http://zh.learnlayout.com/percent.html)</nav><section>当你调整浏览器窗口大小时，布局比以前更酷了！</section><section>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus imperdiet, nulla et dictum interdum, nisi lorem egestas odio, vitae scelerisque enim ligula venenatis dolor. Maecenas nisl est, ultrices nec congue eget, auctor vitae massa. Fusce luctus vestibulum augue ut aliquet. Mauris ante ligula, facilisis sed ornare eu, lobortis in odio. Praesent convallis urna a lacus interdum ut hendrerit risus congue. Nunc sagittis dictum nisi, sed ullamcorper ipsum dignissim ac. In at libero sed nunc venenatis imperdiet sed ornare turpis. Donec vitae dui eget tellus gravida venenatis. Integer fringilla congue eros non fermentum. Sed dapibus pulvinar nibh tempor porta. Cras ac leo purus. Mauris quis diam velit.</section>

现在我们的布局在移动浏览器上也显示的很棒。这里有一些[同样使用了媒体查询的著名站点](http://mediaqueri.es/)。在[MDN文档](https://developer.mozilla.org/en-US/docs/CSS/Media_queries)中你还可以学到更多有关媒体查询的知识。

## 额外加分点

使用 [meta viewport](http://dev.opera.com/articles/view/an-introduction-to-meta-viewport-and-viewport/) 之后可以让你的布局在移动浏览器上显示的更好。

# inline-block

你可以创建很多网格来铺满浏览器。在过去很长的一段时间内使用 `float` 是一种选择，但是使用 `inline-block` 会更简单。让我们看下使用这两种方法的例子：

## 困难的方式（使用浮动）

```
.box {
  float: left;
  width: 200px;
  height: 100px;
  margin: 1em;
}
.after-box {
  clear: left;
}
```

<div class="box">我在浮动！</div>

<div class="box">我在浮动！</div>

<div class="box">我在浮动！</div>

<div class="box">我在浮动！</div>

<div class="box">我在浮动！</div>

<div class="box">我在浮动！</div>

<div class="box">我在浮动！</div>

<div class="box">我在浮动！</div>

<div class="box">我在浮动！</div>

<div class="box">我在浮动！</div>

<div class="box">我在浮动！</div>

<div class="after-box">我在使用 clear，所以我不会浮动到上面那堆盒子的旁边。</div>

## 容易的方式（使用 inline-block）

你可以用 `display` 属性的值 `inline-block` 来实现相同效果。

```
.box2 {
  display: inline-block;
  width: 200px;
  height: 100px;
  margin: 1em;
}
```

<div class="box2">我是一个行内块！</div>

<div class="box2">我是一个行内块！</div>

<div class="box2">我是一个行内块！</div>

<div class="box2">我是一个行内块！</div>

<div class="box2">我是一个行内块！</div>

<div class="box2">我是一个行内块！</div>

<div class="box2">我是一个行内块！</div>

<div class="box2">我是一个行内块！</div>

<div class="box2">我是一个行内块！</div>

<div class="box2">我是一个行内块！</div>

<div>这次我可没有用 `clear` 。太棒了！</div>

你得做些额外工作来让[IE6和IE7支持](http://blog.mozilla.org/webdev/2009/02/20/cross-browser-inline-block/) `inline-block` 。有些时候人们谈到 `inline-block` 会触发叫做 `hasLayout` 的东西，你只需要知道那是用来支持旧浏览器的。如果你对此很感兴趣，可以在前面那个链接中找到更详细的信息。否则我们就继续下去吧。

# inline-block 布局

你可以使用 `inline-block` 来布局。有一些事情需要你牢记：

- `vertical-align` 属性会影响到 `inline-block` 元素，你可能会把它的值设置为 `top` 。
- 你需要设置每一列的宽度
- 如果HTML源代码中元素之间有空格，那么列与列之间会产生空隙

```
nav {
  display: inline-block;
  vertical-align: top;
  width: 25%;
}
.column {
  display: inline-block;
  vertical-align: top;
  width: 75%;
}
```

<div class="container"><nav>[Home](http://zh.learnlayout.com/inline-block-layout.html)[Taco Menu](http://zh.learnlayout.com/inline-block-layout.html)[Draft List](http://zh.learnlayout.com/inline-block-layout.html)[Hours](http://zh.learnlayout.com/inline-block-layout.html)[Directions](http://zh.learnlayout.com/inline-block-layout.html)[Contact](http://zh.learnlayout.com/inline-block-layout.html)</nav><div class="column"><section>Tada!</section><section>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus imperdiet, nulla et dictum interdum, nisi lorem egestas odio, vitae scelerisque enim ligula venenatis dolor. Maecenas nisl est, ultrices nec congue eget, auctor vitae massa. Fusce luctus vestibulum augue ut aliquet. Mauris ante ligula, facilisis sed ornare eu, lobortis in odio. Praesent convallis urna a lacus interdum ut hendrerit risus congue. Nunc sagittis dictum nisi, sed ullamcorper ipsum dignissim ac. In at libero sed nunc venenatis imperdiet sed ornare turpis. Donec vitae dui eget tellus gravida venenatis. Integer fringilla congue eros non fermentum. Sed dapibus pulvinar nibh tempor porta. Cras ac leo purus. Mauris quis diam velit.</section></div>

# column

这里有一系列新的CSS属性，可以帮助你很轻松的实现文字的多列布局。让我们瞧瞧：

```
.three-column {
  padding: 1em;
  -moz-column-count: 3;
  -moz-column-gap: 1em;
  -webkit-column-count: 3;
  -webkit-column-gap: 1em;
  column-count: 3;
  column-gap: 1em;
}
```

<div class="three-column">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus imperdiet, nulla et dictum interdum, nisi lorem egestas odio, vitae scelerisque enim ligula venenatis dolor. Maecenas nisl est, ultrices nec congue eget, auctor vitae massa. Fusce luctus vestibulum augue ut aliquet. Mauris ante ligula, facilisis sed ornare eu, lobortis in odio. Praesent convallis urna a lacus interdum ut hendrerit risus congue. Nunc sagittis dictum nisi, sed ullamcorper ipsum dignissim ac. In at libero sed nunc venenatis imperdiet sed ornare turpis. Donec vitae dui eget tellus gravida venenatis. Integer fringilla congue eros non fermentum. Sed dapibus pulvinar nibh tempor porta. Cras ac leo purus. Mauris quis diam velit.</div>

CSS columns是很新的标准，所以你需要使用前缀，并且它不被[IE9及以下和Opera Mini](http://caniuse.com/#search=column)支持。还有许多和 column 相关的属性，[点击这里了解更多](http://www.quirksmode.org/css/multicolumn.html)。否则让我们讨论下一个主题。

# flexbox

新的 `flexbox` 布局模式被用来重新定义CSS中的布局方式。很遗憾的是最近规范变动过多，导致各个浏览器对它的实现也有所不同。不过我仍旧想要分享一些例子，来让你知道即将发生的改变。这些例子目前只能在支持 flexbox 的 Chrome 浏览器中运行，基于[最新的标准](http://www.w3.org/TR/css3-flexbox/)。

网上有不少过时的 flexbox 资料。 如果你想要了解更多有关 flexbox 的内容，[从这里](http://css-tricks.com/old-flexbox-and-new-flexbox/)学习如何辨别一份资料是否过时。我已经写了一份[关于最新标准的详细文章](http://weblog.bocoup.com/dive-into-flexbox/)。

使用flexbox你还可以做的更多；这里只是一些让你了解概念的例子：

## 使用 Flexbox 的简单布局

```
.container {
  display: -webkit-flex;
  display: flex;
}
nav {
  width: 200px;
}
.flex-column {
  -webkit-flex: 1;
          flex: 1;
}
```

<div class="container"><nav>[Home](http://zh.learnlayout.com/flexbox.html)[Taco Menu](http://zh.learnlayout.com/flexbox.html)[Draft List](http://zh.learnlayout.com/flexbox.html)[Hours](http://zh.learnlayout.com/flexbox.html)[Directions](http://zh.learnlayout.com/flexbox.html)[Contact](http://zh.learnlayout.com/flexbox.html)</nav><div class="flex-column"><section>Flexbox好容易使用！</section><section>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus imperdiet, nulla et dictum interdum, nisi lorem egestas odio, vitae scelerisque enim ligula venenatis dolor. Maecenas nisl est, ultrices nec congue eget, auctor vitae massa. Fusce luctus vestibulum augue ut aliquet. Mauris ante ligula, facilisis sed ornare eu, lobortis in odio. Praesent convallis urna a lacus interdum ut hendrerit risus congue. Nunc sagittis dictum nisi, sed ullamcorper ipsum dignissim ac. In at libero sed nunc venenatis imperdiet sed ornare turpis. Donec vitae dui eget tellus gravida venenatis. Integer fringilla congue eros non fermentum. Sed dapibus pulvinar nibh tempor porta. Cras ac leo purus. Mauris quis diam velit.</section></div>

## 使用 Flexbox 的牛逼布局

```
.container {
  display: -webkit-flex;
  display: flex;
}
.initial {
  -webkit-flex: initial;
          flex: initial;
  width: 200px;
  min-width: 100px;
}
.none {
  -webkit-flex: none;
          flex: none;
  width: 200px;
}
.flex1 {
  -webkit-flex: 1;
          flex: 1;
}
.flex2 {
  -webkit-flex: 2;
          flex: 2;
}
```

<div class="initial">空间足够的时候，我的宽度是200px，如果空间不足，我会变窄到100px，但不会再窄了。</div>

<div class="none">无论窗口如何变化，我的宽度一直是200px。</div>

<div class="flex1">我会占满剩余宽度的1/3。</div>

<div class="flex2">我会占满剩余宽度的2/3。</div>

## 使用 Flexbox 的居中布局

```
.vertical-container {
  height: 300px;
  display: -webkit-flex;
  display:         flex;
  -webkit-align-items: center;
          align-items: center;
  -webkit-justify-content: center;
          justify-content: center;
}
```

<div class="vertical-container"><div>CSS里总算是有了一种简单的垂直居中布局的方法了！



