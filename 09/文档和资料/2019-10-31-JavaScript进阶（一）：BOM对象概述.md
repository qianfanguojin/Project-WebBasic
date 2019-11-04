---
title: JavaScript进阶（一）：BOM对象概述
date: 2019-10-31 19:40:13
tags:
	- JavaScript
categories:
	- JavaScript
description:
typora-root-url: ..
---

## 前言

通过前面的小案例，我们已经知道了将**JavaScript** **和DOM** 以及**事件**的结合，可以发挥出`JavaScript`独特的巨大能量，但其能量远远不止这些。

为了探究其更深的能量，从本篇开始，我们就开始系统的介绍何为DOM、BOM、以及事件。

那么首先，我们就从BOM开始。

<!--more-->

## 一、BOM概念

**BOM**，全名为 Browser Object Model ，翻译过来为 **浏览器对象模型**。

和前面我们介绍的DOM类似，DOM是将HTML文档中的各个元素封装成一个对象，而**BOM**则是将一个浏览器的各个组成部分封装成对象供调用使用。，

下面我们就来分析BOM其中包含哪几个对象以及其中的信息吧。

## 二、BOM的组成部分

首先我们看图：

<img src="/img/JavaScriptPro01/2.1.png"  />

> 图片若是看起来不够清晰，请拖动到新窗口打开查看

在这张图片中，我将BOM中各个对象的包含的大致范围和信息都清晰的注明了。

总的来说，BOM对象大致包含五个部分：

- Window对象：浏览器窗口对象
- Navigator：浏览器对象
- Screen：浏览器所处客户端的显示器屏幕对象
- History：浏览器当前窗口的访问历史记录对象
- Location：浏览器当前窗口的地址栏对象

在这五个部分中，Window对象尤为重要，根据图片的区域划分，我们可以很清楚的看出Window对象其中就已经包含了History、Location对象，还有一个非常重要的 **Document**对象，但由于其重要性太高，所以我们将其单独区分出来，称为 **DOM对象**。

而另外两个：Navigator、Screen 对象，我们使用到的次数较少，所以后面我们对于BOM中的内容主要就讲解 Window、History、Location 三个对象就可以了。

