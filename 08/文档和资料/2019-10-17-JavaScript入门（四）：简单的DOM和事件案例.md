---
title: JavaScript入门（四）：简单的DOM和事件案例
date: 2019-10-17 16:33:18
tags:
	- JavaScript
categories:
	- JavaScript
description:
typora-root-url: ..\..
---

## 前言

经过了前面的学习，我们对`JavaScript`算是有了一个初步的了解。但是在真正的开发中，仅仅了解基础是不能帮助我们解决实际问题的。

今天我们就通过一个**点击按钮切换图片**的案例来引入 `JavaScript` 这门语言在实际应用中的用法。

<!--more-->

## 一、概念引入

在介绍之前，我们首先引入一些知识，我们可以先不必探究它们到底是什么，只要知道它可以实现这个功能即可。

###  1. DOM对象

DOM对象，全名为文档对象类型，是一种模型或者说一种标准，严格来说其不属于任何一门语言。

DOM对象为整个HTML文档建立一个模型（树结构），将HTML中的各个元素都看成一个对象，并定义一系列操作元素的方法，包括对元素的增、删、查、改。

在此案例中，我们需要使用到的是DOM中最重要的一个对象，该对象名为`document`。并使用该对象的一个重要方法：

```javascript
document.getElementById(id);
```

该方法的功能为通过元素id属性值获取元素，返回一个对象类型的元素。

### 2. 事件

简单代表来讲，事件就是一个动作，而HTML为这些动作指定了特定的对象，这些对象就叫**HTML事件**，例如：

- 点击动作 ： `onclick`
- 某个键盘被按下： `onkeydown`

和DOM对象一样，事件不属于任何一门语言，但其是与HTML密切相关的。而在JavaScript中，通过事件可以指定某个事件触发时执行相应的JavaScript代码。

例如，我要指定当为点击事件时执行某段 JavaScript：

```
onclick = "some JavaScript code";
```

事件可以作为元素的属性嵌入元素中，也就是指定该元素为触发对象，当在该元素中触发了指定的事件时，就可以执行该事件绑定在该元素上的`JavaScript`代码。

在此案例中，我们需要利用的事件为点击事件，对象名为：`onclick`

## 二、代码实现&效果展示

基本的代码实现如下所示，由于注释的已经很清楚了，我就不再细说了。

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
  
    <title>点击切换图片</title>
</head>
<body>
	<!--图片标签，用于显示图片-->
    <img id="img" width="400x" height="400px" src="./img/01.jpg">
</body>

<script>
    //获取id值为img的标签(元素)
    var eleImg = document.getElementById("img");
    //设定一个标志变量用来循环切换图片
    var flag = false;
    //监听此图片标签的onclick事件，当点击这个图片标签时，执行后面的JavaScript函数
    eleImg.onclick = function(){
        if(flag){ //flag为true ，此时显示的为第二张图片，点击切换到第一张
            eleImg.src = "./img/01.jpg";
            flag = false;
        }else{  //flag 为false，此时显示的是第一张图片，点击切换到第二张
            eleImg.src = "./img/02.jpg";
            flag = true;
        }
    }  
</script>
</html>
```

效果如下：

![](/img/JavaScript04/00.gif)

## 三、结语

本文章所有代码以及文件都已寄存在github上，地址： [点我跳转]( https://github.com/qianfanguojin/Project-WebBasic/tree/master/09/follow-SimpleDOM )

通过以上的例子，我们利用**DOM**和**事件**再结合`JavaScript`语句实现了一个简单的点击图片相互切换的效果。

这个例子在很多地方都能见到，比如输入验证码时看不清会提示你点击图片刷新验证码，实现原理都是一样的。

当然`JavaScript`能做到的肯定不止简单的切换图片，这些高级的功能在我们详细学习了事件以及**BOM、DOM**后，我们就会慢慢了解，而我们的入门阶段就到此结束了。

后面，我们正式开始进阶之路第一步：**BOM对象**。



