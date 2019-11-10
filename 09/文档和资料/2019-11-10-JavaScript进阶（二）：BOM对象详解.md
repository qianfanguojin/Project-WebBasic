---
title: JavaScript进阶（二）：BOM对象详解
date: 2019-11-10 19:05:10
tags:
	- JavaScript
categories:
	- JavaScript
description:
typora-root-url: ..\..
---

## 前言

上一篇文章中，我们介绍了BOM的来源以及其组成部分，为了加深我们对BOM的理解，今天我们就将BOM中的对象都分析一遍。

下面，我们就开始吧。

<!--more-->

从上一篇文章中，我们已经知道，BOM主要由以下五大对象组成：

- Window对象：浏览器窗口对象
- Navigator：浏览器对象
- Screen：浏览器所处客户端的显示器屏幕对象
- History：浏览器当前窗口的访问历史记录对象
- Location：浏览器当前窗口的地址栏对象

下面我们就将其中最重要的三大对象Window、History和Location对象进行分析。

## 一、Window对象

### 1、使用（调用）方式

不同于一般的对象，BOM中的Window对象不需要创建就可以直接使用，使用其中的方法直接调用即可，类似于我们已知的静态方法：

```javascript
window.方法名();
```

比如我们最常用的一个方法，弹出一个警告框 alert：

```javascript
window.alert("window弹出");
```

但由于在客户端 JavaScript 中，Window 对象是全局对象，所有的表达式都在当前的环境中计算。要引用当前窗口根本不需要特殊的语法，可以把那个窗口的属性作为全局变量来使用。

也就是说，使用window对象中的方法可以不用带对象名，直接写方法名即可：

```javascript
alert("no window弹出");
```

动图事例：

![](/img/JavaScriptPro02/1.1-01.gif)

---

### 2、Window方法

#### 2.1 三个带弹出框的方法

window对象中，大致有三个方法为带弹出框的，其方法名和大致用法为：

|  方法名   |                       用法                       |
| :-------: | :----------------------------------------------: |
|  alert()  |     显示带有一段消息和一个确认按钮的警告框。     |
| confirm() | 显示带有一段消息以及确认按钮和取消按钮的对话框。 |
| prompt()  |           显示可提示用户输入的对话框。           |

其中，alert 我们在上面以及前面多次提到了，就不在赘述了，下面我们主要看后面的这两个方法。



首先是 **confirm()** 方法，其负责显示一个带确认取消按钮的对话框，基本用法为：

```javascript
confirm("确定这样做吗");
```

演示：

![](/img/JavaScriptPro02/1.2-01.gif)

可以看到，**confirm** 的确实现了带确认取消的弹窗。

但是也可以看到，无论我点击 **confirm** 中的**确认**还是**取消**，好像都没有什么不同。事实上， **confirm** 方法会返回一个布尔值，当用户点击确定时返回**true**，点击取消时返回**false**。下面我们用一个例子演示：

```javascript
var flag = confirm("确定这样做吗？");
if(flag){
	alert("是的");
}else{
	alert("不");
}
```

演示：

![](/img/JavaScriptPro02/1.2-02.gif)



然后就是 **prompt()** 方法了，其负责显示一个具有输入框的弹出框，基本用法如下:

```javascript
promt("输入点东西吧");
```

同样的，prompt也具有返回值，返回的就是用户在输入框输入的信息。

> 注意，当点击输入框下方的取消时，无论你是否已经输入了信息，返回值都为 null

使用示例如下：

```javascript
var text = prompt("输入点东西吧");
alert(text);
```

演示效果：

![](/img/JavaScriptPro02/1.2-03.gif)

总的来说，这三个带弹出框的方法其实各有异同，他们三个使用的优先级为：

**confirm > alert > prompt**

更推荐大家使用 **confirm**，用户体验会更舒服。

#### 2.2 与打开和关闭有关的方法

在window对象中，跟打开和关闭有关系，且我们最常用的方法有以下两个：

| 方法名  |                      用法                      |
| :-----: | :--------------------------------------------: |
| open()  | 打开一个新的浏览器窗口或查找一个已命名的窗口。 |
| close() |                关闭浏览器窗口。                |

对于 **open()** 方法，其负责打开一个新的浏览器窗口，可以指定一个URL字符串在新窗口打开指定的网页地址。

其基本的用法如下：

```
//在新窗口打开百度
window.open("http://www.baidu.com");
```

> ps:  请不要混淆方法 Window.open() 与方法 Document.open()，这两者的功能完全不同。为了使您的代码清楚明白，请使用 Window.open()，而不要使用 open()。 

**open()** 方法返回一个window对象，该window对象为新打开的浏览器窗口。

其次是 **close()** 方法，其负责关闭指定浏览器窗口，使用时需要指定要关闭的window对象，若不指定，则默认关闭当前页面所处的窗口。

其基本用法如下：

```javascript
<body>
    <input type="button" id="btn" value="打开新窗口">
    <input type="button" id="btnc" value="关闭打开的窗口">
</body>
<script>
        //open方法演示
        var eleBtn = document.getElementById("btn");
        var newwindow;
        eleBtn.onclick = function(){
            newwindow = window.open("http://www.baidu.com");

        }
        //close用法
        var eleBtnc = document.getElementById("btnc");
        eleBtnc.onclick = function(){
            newwindow.close();
        }
</script>
```

为了更清楚的显示出 close 方法的用法，在这结合了 open() 方法的使用，close方法负责关闭open()方法所打开的窗口。

最终呈现效果：

![](/img/JavaScriptPro02/1.2-04.gif)

#### 2.3 与定时器有关的方法

> 定时器：类似闹钟，在一个规定的时间或者时间周期内执行

在window对象中，与定时器相关的方法共有四个，其方法名和大致描述如下：

|     方法名      |                        用法                        |
| :-------------: | :------------------------------------------------: |
|  setTimeout()   |       在指定的毫秒数后调用函数或计算表达式。       |
| clearTimeout()  |      取消由 setTimeout() 方法设置的 timeout。      |
|  setInterval()  | 按照指定的周期（以毫秒计）来调用函数或计算表达式。 |
| clearInterval() |       取消由 setInterval() 设置的 timeout。        |



`setTimeout()` 方法规定在指定的时间（毫秒）后执行指定的JavaScript代码，且仅**执行一次**。

语法：

```javascript
setTimeout(code,millisec);
//code 为指定要执行的JS代码
//millisec 为在执行代码前需要等待的时间（毫秒）
```

基本的用法为：

```javascript
//在3秒后弹出一个警告框
setTimeout("alert('lololol');",3000);
//在3秒后弹出一个confirm对话框
```



相反地，`clearTimeout()`则是取消由`setTimeout()`设定的延迟执行的操作。

在一段JavaScript代码中，可能会同时出现多个`setTimeout()`设定的定时器操作，和上文中的 `close()` 方法使用的情况类似，`clearTimeout()`在使用时必须通过 id值  指定某个特定的定时器。

而实际上， 每一个 `setTimeout()`方法都会返回一个独特的 id 值，这个id值指代了这个定时器。

所以，我们可以使用其返回的这个id值来取消指定的`setTimeout`定时器。

语法：

```javascript
clearTimeout(id);
//id值为特定的setTimeout()返回的id值
```

基本用法：

```javascript
//在3秒后弹出一个警告框
var id = setTimeout("alert('lololol');",3000);
//取消该弹出操作
clearTimeout(id);
```



假定这样一个情况：由于页面的数据需要实时性，所以每隔一段时间就需要刷新一次页面更新数据。

为了实现这个需求，我们需要一个每隔一段时间自动执行的代码。

而 `setInterval()` 方法的用法就是按照指定的周期（以毫秒计）来调用函数或计算表达式(循环调用)

语法 ：

```javascript
setInterval(code,millisec);
//code为函数每个周期所执行的JavaScript代码
//millisec 指定每隔多少时间（毫秒）执行函数中的方法
```

> 返回值：一个 id 值，标识一个独立的定时器，此定时器由setInterval()方法创建

基本用法：

```javascript
//每过2s向html文档中写入数字2
setInterval("document.write('2');",2000);
```



如 `setTimeout()` 设定的定时器可以被 `clearTimeout()`取消一样。`setInterval()`设定的循环定时器也可以被取消，对应的取消方法为 `clearInterval()`。

语法：

```javascript
clearInterval(id);
//id值为特定的setInterval()返回的id值
```

基本用法：

```javascript
//每过2s向html文档中写入数字2
var id = setInterval("document.write('2');",2000);
//过4s后取消循环写入数字2操作
setTimeout("clearInterval(id);",4000);
```



---

### 3、Window属性

从上篇文章中的图片中，我们可以清晰的看到，其实在BOM中，Window对象就已经包含了大部分的对象，而通过Window对象的属性，同样可以获取到其他的BOM对象的引用。

以下每个BOM对象通过Window对象对应的获取方式：

|  属性名   |            描述             | 通过window对象获取方法 |
| :-------: | :-------------------------: | :--------------------: |
|  history  |  对 History 对象的只读引用  |    `window.history`    |
| location  | 对 Location 对象的只读引用  |   `window.location`    |
| navigator | 对 Navigator 对象的只读引用 |   `window.navigator`   |
|  screen   |  对 Screen 对象的只读引用   |    `window.screen`     |

还有还有一个最重要的对象： **document 对象**，也是 Window对象的一部分。

也就是说，可以使用如下方法获取 document对象的引用：

```javascript
window.document;
```

在前面的点击切换图片的小案例中，我们使用了这个方法 ： `document.getElementByID()`。

其实也可以通过如下方式使用该方法：

```javascript
window.document.getElementById();
```

我们一般都会将前面的window省去，就像window中的各种方法一样，直接使用。

但我们还是要了解，其实 无论是BOM还是DOM，window对象都是其核心。

---

## 二、Location对象

### 1、使用（调用）方式

Location对象 代表浏览器窗口中的地址栏，和window对象一样，无需特殊的创建方式，通过对象名就可使用，并调用其中的方法：

```javascript
location.方法名()
```

而由于location对象属于window对象的一部分，所以也可以这样写：

```javascript
window.location.方法名();
```

### 2、Location方法

location对象中的方法及用法如下：

| 方法名    | 方法描述           |
| --------- | ------------------ |
| assign()  | 加载新的文档。     |
| reload()  | 重新加载当前文档。 |
| replace() | 文档替换当前文档。 |

由于这三个方法使用都比较简单，就不细分叙述了，基本使用如下代码所示：

```javascript
<body>
    
    <input type="button" value="assign" id="assign">
    <input type="button" value="reload" id="reload">
    <input type="button" value="replace" id="replace">

</body>

<script>
    /**
     * 加载新文档,参数为URL地址，会产生历史记录
     */
   
    document.getElementById("assign").onclick = function(){
        location.assign("http://www.baidu.com");
    } 
    /**
     * 重新加载文档，参数为一个布尔值，默认为false
     * 1.如该方法没有设定参数值或设定为false，那该方法会使用浏览器缓存来重新加载页面（浏览器默认刷新方式）
     * 2.如该方法参数值为true，那么该方法会无条件向服务器重新下载该文档并重新加载
     * 
     */ 
    document.getElementById("reload").onclick = function(){
        location.reload(true);
    }
    

    // /**
    //  * 替换当前文档，和 assign 方法类似 
    //  * 但是不会产生历史记录，也就是无法通过后退键返回上一个页面
    //  */
    document.getElementById("replace").onclick = function(){
        location.replace("http://www.baidu.com");
    }
    
</script>
```

效果演示：

![](/img/JavaScriptPro02/2.2-01.gif)

### 3、Location属性

location 的属性如下：

| 属性     | 描述                                          |
| :------- | :-------------------------------------------- |
| hash     | 设置或返回从井号 (#) 开始的 URL（锚）。       |
| host     | 设置或返回主机名和当前 URL 的端口号。         |
| hostname | 设置或返回当前 URL 的主机名。                 |
| href     | 设置或返回完整的 URL。                        |
| pathname | 设置或返回当前 URL 的路径部分。               |
| port     | 设置或返回当前 URL 的端口号。                 |
| protocol | 设置或返回当前 URL 的协议。                   |
| search   | 设置或返回从问号 (?) 开始的 URL（查询部分）。 |

各个属性的用法描述中已经很清楚了，我就不详细赘述了。

这里只提一个常用的属性 ：`href`，其功能为设置或返回完整的URL。

通过这个属性，我们可以获取浏览器地址栏的完整地址，或是设置地址为新地址，其用法如下：

```javascript
<body>
	<input type="button" value="href获取" id="href01">
    <input type="button" value="href设置" id="href02">
</body>
<script>
	/**
     * href 属性
     * 设置或返回完整URL
     * 当其设置一个新href时，其效果和assign()一样，加载新URL，并产生历史记录
     */
    document.getElementById("href01").onclick = function(){
        alert(location.href);
    }

    document.getElementById("href02").onclick = function(){
        location.href = "http://www.baidu.com";
    }
</script>
```

效果演示：

![](/img/JavaScriptPro02/2.3-01.gif)

## 三、History对象

> 特别注意：这里的History对象记录的历史记录只和当前窗口有关，并不是浏览器的全部历史记录。

### 1、使用（调用）方式

History 对象表示当前窗口的历史记录，同样通过对象名可以直接调用：

```javascript
history.方法名();
history.属性名
```

History对象也属于window对象的一部分，所以也可以：

```javascript
window.history.方法名();
window.history.属性名
```

### 2、History方法

在 history对象中，共有三个方法，它们分别为：

| 方法      | 描述                                        |
| :-------- | :------------------------------------------ |
| back()    | 加载当前窗口 history 列表中的前一个 URL。   |
| forward() | 加载当前窗口 history 列表中的下一个 URL。   |
| go()      | 加载当前窗口 history 列表中的某个具体页面。 |

为了完成这三个方法的演示，我们需要两个html文档，第一个文档`history00.html`核心内容为：

```javascript
<body>
    <a href="./history.html">去history对象页面</a>
</body>
```

第二个文档`history.html`核心内容为：

```javascript
<body>
    <a href="http://www.baidu.com">打开一个页面</a>
    <input type="button" value="goForward前进" onclick="goForward()">
    <input type="button" value="goBack后退" onclick="goBack()">
    <input type="button" value="go去后一个页面" onclick="go()">
</body>
<script>

    /**
     * forward()方法
     * 加载当前窗口历史列表的前一个页面
     * 类似于浏览器中的前进按钮
     * 
     */ 
    function goForward() {
        history.forward();
    }

    /**
     * back()方法
     * 加载当前窗口历史列表的后一个页面
     * 类似于浏览器中的后退按钮
    */
    function goBack() {
        history.back();
    }
    
    /**
     * go()方法
     * 加载当前窗口历史列表的一个具体页面
     * 参数可以为一个URL或者一个数字，数字代表要访问的URL在历史记录的相对位置
     */ 
    function go() {
        //-1表示向后一个页面
        history.go(-1);
    }
</script>
```

效果演示：

![](/img/JavaScriptPro02/3.2-01.gif)



### 3、History属性

history 对象只有一个属性：

| 属性   | 描述                              |
| :----- | :-------------------------------- |
| length | 返回浏览器历史列表中的 URL 数量。 |

用法：

```javascript
history.length;
```

例子，在上面第二个html文档中添加：

```javascript
	<body>
    <input type="button" value="获取历史记录数量" onclick="getLength()">
    </body>

    <script>
	/**
     *length属性
     * 显示当前窗口历史记录数量
     */ 
    function getLength() {
        alert(history.length);
    }
    <script>
```

演示：

![](/img/JavaScriptPro02/3.3-001.gif)

---

## 结语

> 本节中所有使用的演示代码及资料都可以在我的github上找到，直达链接 ： [点我跳转]( https://github.com/qianfanguojin/Project-WebBasic/tree/master/09/JavaScriptProDemo/follow-BOM )

本节，我们介绍了BOM对象中的各个对象以及各个对象中的相关方法和属性，我们算是对BOM有一个大概的认识了。

下节，我们开始DOM的学习，探究DOM这个强大的文档对象。

