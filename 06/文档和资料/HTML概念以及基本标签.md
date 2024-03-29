---
typora-root-url: ./
---

## 前言

最近开始从头学习一遍`Java Web`，在学习的过程中，我也把学习到的东西都记录下来，供以后查阅。

这篇主要记录关于HTML的概念和基本标签使用的方法。

<!--more-->

---

## 1. HTML基本信息

### 1.1 HTML  描述

HTML , 英文全称为HyperText Markup Language , 也就是超文本标记语言，是最基础的网页开发语言。

#### 超文本：

超文本是用 **链接** 的方法，将各种不同空间的文字信息组织在一起的网状文本。如何理解呢，比如我打开某个导航页：

![](/Web-Html01/01.png)

其中有各式各样的信息，我们可以通过点击上面的文本跳转到不同的页面，例如我点击左上角的 **百度** ，页面就会跳转到百度的页面，我点击 **新浪** ，页面就会跳转到新浪的页面，点击不同的超链接，可以跳转到不同的页面，在跳转到的页面我们又可以点击超链接跳转，如此层级往复，就形成了一个巨大的信息网。这就是所谓的超文本，通过链接跳转组成一个巨大的信息网。

![](/Web-Html01/01-1.png)

#### 标记语言：

标记语言，是一种将文本以及文本相关的其他信息结合起来，展现出关于文档结构和数据处理细节的电脑文字编码。

通过对信息进行标记，可以将信息以不同的形式显示出来，这就是标记语言的作用。比如我标记一样东西为文本，那我用`<font></font>`将内容标记为文本显示。要显示图片，我就使用`<img>`将内容标记为图片显示。

标记语言（HTML）严格上不是一门编程语言，因为其没有逻辑，内容是直接定义好结果的。就比如 1+ 1 在编程语言中可以很简单的计算为 2 ,而标记语言却没有这个概念，它无法实现逻辑计算功能。

### 1.2 HTML  规范

> 作为一门语言，与其他语言一样，HTML也有一套自己的规范，基本如下

1. 每个HTML文件都必须以 `<html>`开始 ，以 `</html> ` 结束。

2. 每个HTML文件名都是以`.html`结尾的。

3. `<head>` 标签负责在初始化页面的相关信息（如标题栏）。

4. `<body>` 标签负责显示页面内容，所有的页面显示的内容必须写在该标签下。

5. 一般来说，HTML 里的标签有开始就一定要结束，例如开始为`<head>` ，就一定要有`</head>` 来结束，也就是`<head></head>`。

   **注意：**

   > 某些标签，例如换行，是没有结束标签的，但是HTML也为这些标签规定了一个结束规范，将`/` 放在标签名后，表示在该标签结束，如换行标签`<br/>`

   

6. HTML的代码不区分大小写，也就是说，你写`<head>` 和 `<Head>` 都能表示它为标题标签，当然，Java是严格区分大小写的。

### 1.3 HTML 的操作思想。

网页中有很多数据，不同的数据可能需要不同的显示效果，这个时候需要使用标签把要操作的数据包起来（封装起来），通过秀爱标签的属性值实现标签内数据样式的变化。

一个标签相当于一个容器，想要修改容器内数据的样式，只需要改变容器的属性值，就可以

比如我们需要在网页上显示三个不同颜色的Hello Word，我们可以使用如下代码实现：

```html
<!--红色-->
<font color="red">Hello Word</font>
<!--绿色-->
<font color="green">Hello Word</font>
<!--蓝色-->
<font color="blue">Hello Word</font>
```

我们首先通过`<font>` 标签容器将他们包起来，然后修改标签的相关属性，就可以实现对标签内数据样式的改变。

## 2. HTML 基本标签

### 2.1 段落标签

语法格式：

```html
<p></p>
```

使用段落标签时，浏览器会自动在段落的前后加上一个空行，但是两个段落标签之间并不产生两个空行。

示例：

```html
  	<!-- 段落前后自动生成空行-->
		aaaaaa	
		<p>A</p>
    BBBBBBB
		<!--两个段落之间并不产生两个空行-->
    <p>B</p>
    <p>C</p>
```

效果：

![](/Web-Html01/p.png)



### 2.2 注释标签

实现代码：

```html
<!--你要注释的内容-->
```

使用注释后，你注释的内容不会被显示在页面上，但是这段内容依然会存在于源文件中。

### 2.3 标题标签、水平线标签和特殊字符

- 标题标签

  实现代码：

  ```html
  <h1></h1>
  <h2></h2>
  <h3></h3>
  <h4></h4>
  <h5></h5>
  <h6></h6>
  ```

  从h1到h6，文字大小依次变小，同时会自动换行。

- 水平线标签

  实现代码：

  ```html
  <hr/>
  ```

  相关属性：

  **size** ：线的粗细，取值1 - 7

  **color** ：线的颜色

- 特殊字符

  HTML 是一种标签语言，在HTML里，我们可以使用标签来对数据进行各种包装变换。

  但在某些情况下，其标签化管理会出现一些问题。

  例如我想显示`空格用<br/>` 这个字符串，使用文字标签来显示：

  ```html
  <font>空格用<br/><font/>
  ```

  可是当我们其运行到网页中时，奇怪的现象发生了，`<br/>` 这个字符串并没有显示在网页上。

  这是为什么呢，其实我们稍加理解以下就应该能搞懂，在html中，`<` 和 `>` 这两个符号将标签包在其中，浏览器一发现这两个符号，就会执行相关的操作，而不是将其作为字符显示。

  浏览器通过发现不同的标签来处理不同的数据，而标签其实也就是两个尖括号括子括在一起的内容，浏览器会扫描，发现是在`<>`之间的，就会将他们识别为标签。

  而上面的这段代码就是如此，当浏览器发现`<br/>` 时，会自动将其定义为换行符的标签，执行换行操作，而不是直接将其打印出来。

  而HTML的设计者们肯定不会允许这种bug的存在，于是，在HTML中引入了转义字符的概念，通过特殊的字符组成，来表示一个特殊的字符，例如：

  | 要表示的符号 | 对应的转义字符 |
  | :----------: | :------------: |
  |      <       |     `&lt;`     |
  |      >       |     `&gt;`     |
  |     空格     |    `&nbsp;`    |

  > 转义字符中的分号都为英文分号。

  那么，要表示刚才的字符串，真正的写法是：

  ```html
  <font>空格用&lt;br/&gt;</font>
  ```



### 2.4 列表标签

#### 2.4.1 有序列表

语法格式：

```html
<ol>
	<li></li> <!--列表项-->
</ol> <!--有序列表的范围-->
```

例如：

```html
<ol>
    <li>java</li>
    <li>html</li> 
    <li>css</li> 
</ol>
```

浏览器显示如下：

 ![](/Web-Html01/ol.png)



`<ol>`相关属性：**type** ：设置排序的方式，也就是序号的样式，取值可以为 1 、a 、i ，设置不同值，排序显示的序号不不同，默认为1。



#### 2.4.2 无序列表

语法格式：

```html
<ul>
        <li></li> <!--列表项-->
</ul>
```

例如：

```html
<ul>
  	<li>java</li>
    <li>html</li> 
  	<li>css</li> 
</ul>
```

 ![](/Web-Html01/ul.png)



`<ul>`相关属性 ：

**type** ：同样是设置序号的样式，有 空心圆circle、实心圆disc、实心方块square，默认为实心圆disc 。



#### 2.4.3 自定义列表

语法格式：

```html
<dl>
	<dt></dt> <!--自定义列表组-->
	<dd></dd> <!--自定义列表描述-->
</dl> <!--自定义列表的范围-->
```

例如：

```html
<dl>
    <dt>java</dt>
    <dd>html</dd>
    <dd>css</dd>
<dl>
```

浏览器显示如下：

![](/Web-Html01/dl.png)

### 2.4 图像标签< img>（重要）

如果说一个网站中只有文字，那么这个网站就会变得非常的单调，而在如今的互联网的网页中，图片在网页显示中占了极大的比重，在HTML中，图片的显示用`img` 标签控制：

```html
<img src="图片路径"/>
```

在`img` 标签中，`src`属性是必须赋值的，你必须指定图像标签所显示的图片所在位置，该路径可以为本地也可以是网络。

对于路径的写法：一般分为相对路径及绝对路径。相对路径以 ./ 开始，如：`./01.png` ，绝对路径以盘符开始，如：`C:/01.png` 。

除`src`属性外，`img` 标签还有一些常用属性：

**width** ：指定图片的宽度。

**height** ：指定图片的高度。



### 2.5 超链接标签< a>。

 语法方式：

```html
<a href="链接地址"></a>
```

如跳转到百度：

```html
		<!--href 属性指定资源位置 -->
    <a href="https://www.baidu.com">点我</a><br/>
```

相关属性：

**targe： **target指定跳转的方式 默认为 self ，如：


### 2.6 表格标签

`<table></table>` 用来定义一个表格。

`<tr></tr>`定义表格中的一行。

`<td></td> `定义表格中的一个单元格。

`<th></th>` 定义表头单元格。

`<caption>` 定义表格的标题。

定义一个基本的学生信息表：

```html
<!--定义一个简单的学生信息表-->
<table cellpadding="1">
  
    <tr>
        <td>学号</td>
        <td>姓名</td>
        <td>成绩</td>
    </tr>
    <tr>
        <td>1</td>
        <td>小明</td>
    	  <td>100</td>
    </tr>
    <tr>
        <td>2</td>
        <td>小李</td>
        <td>200</td>
    </tr>
    <tr>
        <td>3</td>
        <td>小华</td>
        <td>300</td>
    </tr>

</table>
```

显示效果如下：

![](/Web-Html01/02.png)

在表格中，也有一些语义化标签供我们使用：

`thead` : 表示表格的头部份。

`tbody` : 表示表格的内容体部分。

`tfoot`：表示表格的脚部分。

> 这些标签不附加任何样式，但是有严格的**位置控制**规定，且不受代码位置影响。thead中 显示在表格**最上方**，tbody中内容显示在 **thead 下面**，tfoot 中内容显示在 **tbody 下面**。

例如我修改上面的示例：

```html
<!--定义一个简单的学生信息表-->
<table border="1" cellspacing="0">
    <!--表格标题-->
    <caption>学生信息表</caption>

    <!--表示为结尾-->
    <tfoot>
    <tr>
        <td>学号</td>
        <td>姓名</td>
        <td>成绩</td>

    </tr>
    </tfoot>

    <!--表示为内容体-->
    <tbody>
    <tr>
        <td>1</td>
        <td>小明</td>
        <td>100</td>

    </tr>
    <tr>
        <td>2</td>
        <td>小李</td>
        <td>200</td>

    </tr>

    </tbody>

    <!--表示为标题-->
    <thead>

    <tr>
        <td>3</td>
        <td>小华</td>
        <td>300</td>

    </tr>
    </thead>
</table>
```

显示的效果为：

![](/Web-Html01/03-1.png)

表格的相关属性：

- table 标签的属性

  **border：**设置边框大小，默认为0不显示。

  **width：** 设置表格的宽度。

  **cellspacing：** 定义单元格之间的距离。

  **align：**设置表格在浏览器的位置

- tr 标签的属性

  **bgcolor：**设置该行的背景色。

  **align：**设置该行单元格内容的显示位置，如设置`align="center"` 该行单元格内容为居中显示。

- td 标签的属性

  **colspan**：单元格所占的列数。

  **rowspan：**单元格所占的行数。

### 2.7 语义化标签

语义化标签是指一些负责标识位置的标签，也不会产生实际的样式，只是为了给开发人员方便阅读代码所用而已。

HTML5以后引入了许多的语义化标签，大多是时候我们只要使用如下几种：

```html
<header>
    <!--表示这里的内容在页面头部显示-->
</header>

<nav>
    <!--表示这里的内容为导航栏-->
</nav>

<footer>

    <!--表示这里面的内容在页面底部显示-->

</footer>
```

语义化标签页可以理解为一种注释，只是负责说明而已，并不会对实际的数据进行样式修改。

### 2.8 块标签以及内联标签

在HTML中，标签总体可以分为两大类。

一类是块标签，如：`<h1>, <p>, <ul>, <table>，<div>`，块标签占用了全部宽度，在前后都是换行符，这些标签在浏览器显示时会开启一个新行来显示和结束内容，无论你是否在该标签使用前使用换行。

第二类是内联标签，如：`<b>, <td>, <a>, <img>`，内联标签只需要必要的宽度，不强制换行。这些标签在浏览器显示时通常不会开新行显示，除非前者标签已经占满一行的位置。

下面使用一个例子来简要说明：

```html
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>块标签和内联标签</title>
</head>
<body>
    <!-- 块标签区域 -->
    <h2>块标签示例</h2>
    <h6>标题一</h6>
    <div>没有在标题h6后加换行，块级元素div自动新开一行显示</div>
    <p>没有在div后加换行，块级元素p自动新开一行显示</p>
		

    <!-- 内联标签区域 -->
    <h2>内联标签示例</h2>
    <span>h2标题标签为块标签，内容结尾新开一行，这里的文本显示在标题下方。</span>
    <a href="#">内联标签a会和span标签在同一行</a>
    <span>内联标签会一直并列排放，直到充满浏览器再到下一行111111111111111
        111111111111111111111111111111111111111111111111111111111111</span><br/>
    <span>也可以使用换行来跳到下一行</span>

</body>
</html>
```

效果如下：

![](/Web-Html01/2.8-01.png)

<span style="color:blue">注意：</span>

- 块标签的新开一行其实就是换行，中间的空行都是外边距。
- 虽然块标签在前后都产生空行，但是块标签与块标签之间的空行不会叠加。
- 在大多数时候，我们都要记得 `div` 为块标签，`span`为内联标签，因为这两个标签在以后我们们编写网页时用的最多。

