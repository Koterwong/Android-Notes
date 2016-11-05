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

