---
typora-root-url: ./
---

## 前言

在CSS的使用中，**浮动 float** 是我们页面布局的一大利器，在笔者学习float的过程中，遇到了许多问题，但是并没有找到比较好的教程来解决这些问题。

这些天笔者通过自己的实验得到了许多结论，现在就分享给大家。



## 写在最前

1. 如果在查看文章时遇到任何错误的地方，还望指正。

2. 在查看本文之前，希望读者能够事先了解CSS中的块元素和内联元素的区别。

3. 块元素部分使用 div 为例，内联元素部分使用 span 为例。

   

好的，那我们文章开始。

## 一、float的使用

### 1. float 简单入门

在CSS中，指定一个元素变为浮动的CSS代码格式为：

```css
float: 浮动的方向;
```

下面是一个使用浮动实现文字环绕的简单例子：

```html
<body>
    <p>简单的示例</p> 
    <div style="float: left;width: 80px;height: 80px;background: red"></div>
    <div>围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字
        围绕的文字围绕的文字围绕的文字围绕的文字
        围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字
        围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字
        围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字
        围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字
        围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字
        围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字
        围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字
        围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字
        围绕的文字围绕的文字围绕的文字围绕的文字
        围绕的文字围绕的文字围绕的文字围绕的文字围绕的文字</div>
</body>
```

效果如图：

![](/web-css-float/1-01.png)

可以看到，我定义了一个div元素并指定它宽高以及背景色，同时定义另一个div元素负责显示文字。并对第一个div元素使用内联CSS样式将其设置为左浮动`float:left`，另一个div元素中的文字自动围绕在第一个div元素旁边。

### 2. 块级元素浮动解析

在CSS中，我们一般将元素按显示格式分为两大类，一类是块级元素，另一类是内联元素。

块级元素在浏览器中独占一行，自上而下排列。比如块级元素**div**，它们默认在浏览器的显示方式如图：

![](/web-css-float/2-01.png)

在标准状态下，块级元素在使用时会霸占一行的位置，即使它实际显示的内容并没有一行。如上图中`div01`后面明显有足够的位置放下`div02`，但是`div02`还是会新开一行来显示。

显然，为了更好的利用浏览器内容空间，我们不能让块级元素这么霸道，换言之如何实现在一行中显示多个块级元素就是首要解决的问题。

在标准状态下我们无法实现一行显示多个块，我们可以用到浮动来解决这个问题。

**浮动其实就是一种脱离标准流的方式，通过将元素设定为浮动，元素可以不受标准流的排版控制。**

对于块级元素，标准的排版形式就是每个块级元素独占一行。

文字解释可能不够清楚，我们通过实践来看看效果，将上图中的`div01`元素设为左浮动 `float:left`，此时页面显示如下：

![](/web-css-float/2-02.png)

可以明显看到，`div02`显示的高度变小了。这是为什么呢？难道浮动的元素高度都会消失吗？

为了确定效果，我将 `div01`中的红色背景去除，并将其边框设为红色以作区分，此时页面显示如下：

![](/web-css-float/2-03.png)

可以看到，红框内原来的`div01`元素的高度确实是被下面的`div02`元素覆盖了。

由此我们可以得出第一个结论：<span style="color:red">标准文档流中的某个元素在使用浮动之后，该元素的高度会在标准文档流中塌陷，致使其余在标准文档流中的元素会自动向上移动覆盖该元素的高度。</span>

那么在多个元素都浮动时，又会发生什么呢？我们将上图样式恢复到默认状态，并设置 `div02，div03`都为左浮动`float:left`，此时页面显示如下：

![](/web-css-float/2-04.png)

可以看到，所有浮动的元素都向左上靠拢，并依次从左到右排列，而`div04`由于没有设置为浮动，元素区域上移。

那么是都往左边靠拢吗？我们试试将浮动方向设为向右。将 `div02，div03`设为右浮动`float:right`，此时页面显示如下：

![](/web-css-float/2-05.png)

可以看到，所有浮动的元素都向右上靠拢，并依次从右到左排列，而`div04`由于没有设置为浮动，元素区域上移。

由此，我们可以得出第二个结论：

某个M元素在被设置为浮动之后，会自动向其浮动的方向往上移动。

- <span style="color:red">如果M元素的上一个元素也是浮动的，则该元素会紧随在上一个元素行的后边，直到该元素行占满再到下一行。如上图中的div02 和 div03</span>
- <span style="color:red">如果M元素的上一个元素为标准文档流中的元素，则M元素会下放到上一个元素的下行。如上图中 div01  和 div02 的位置关系</span>

> 注意，在上面的解释中提到了**后边**，这里特别说明下，这里的后边指的是一个顺序，比如左浮动时浮动元素都往左上角跑，那么谁先到左上（HTML代码中谁先声明）谁就在前面，在此示例中div02声明浮动在前，所以它在前，div03 在后。同理可得右浮动时也是一样的道理。



### 3. 浮动的清除clear

首先我们使用一张上面的示例图：

![](/web-css-float/2-04.png)

在这个示例中，我们将`div02，div03`都设为左浮动，此时它们向左上靠拢。但是由于浮动会导致元素的高度塌陷，造成了非浮动元素`div04`的部分内容被掩盖在浮动的 `div02`下方。

现在我有一个需求，我想让`div04` 显示出全部的内容，而不是躲在`div02`的下方。为了解决这个需求，我们需要引入`clear`属性。

clear 属性是CSS中一个非常重要的属性，它的功能是：**清除元素周围指定方向上的float浮动元素**，可以使 float 变得不那么**流氓**。

clear属性取值介绍：

- none  :  默认值。允许两边都可以有浮动对象
- left   :  不允许左边有浮动对象
- right  :  不允许右边有浮动对象
- both  :  不允许有浮动对象

在示例中，由于`div04`周围只有左浮动元素 `div02,div03`。 我们就清除左边的浮动元素，在`div04`设置`clear:left`属性，显示效果如下：

  ![](/web-css-float/3-01.png)

效果很明显，在添加`clear:left`属性后，`div04`元素周围所有的左浮动元素（div02，div03）被清除，`div04`元素自动下移到最下方，也就是去除了float流氓点之中的高度塌陷问题。

是不是使用起来很简单？但是很多人在理解clear属性时都容易犯一个小错误，我们使用一个简单的例子来解释这个问题。

假设我们有两个设定为右浮动(`float:right`)的元素`div001，div002`，由于它们浮动的特性，默认情况下它们会自动向浏览器右上角靠拢，如图：

![](/web-css-float/3-02.png)

此时我想让`div002`元素脱离float的流氓设定，不向右上靠拢，而是在`div001`的下方，我们很容易就想到使用clear清除浮动，既然是要`div002`往下，且`div002`在`div001`的左边，那么我们在`div001`中声明清除左边的`div002`浮动元素是不是 `div002`就自动下移了呢？。

我们在`div001`中声明`clear:left`，查看效果：

![](/web-css-float/3-03.png)

你会发现，一点用都没有，`div002`并没有如愿下移。为什么呢？请大家牢记下面这句话

**clear属性永远只会作用于元素本身，不能影响其他的元素样式显示！！！**

**clear属性永远只会作用于元素本身，不能影响其他的元素样式显示！！！**

**clear属性永远只会作用于元素本身，不能影响其他的元素样式显示！！！**

首先我们看上面的这张例图：

![](/web-css-float/3-01.png)

我们来分析一下`div04`的下移效果是如何生成的：

1. `div04`在设定了属性`clear:left`之后，`div04`搜索周围设定了`float:left`的元素，发现`div02,div03`符合要求。
2. 然后，`div04`元素将符合条件的元素`div02,div03`执行清除浮动的操作，但是注意：<span style="color:red">这里的**清除**指的是从`div04`元素出发的清除，将浮动对`div04`影响清除。在某个元素设为浮动之后，元素的高度会塌陷，`div04`受其影响，位置上移。在执行清除之后，`div04`所受的影响去除，自动新开一行显示</span>



总结：

- <span style="color:red">clear属性永远只会作用于元素本身，不能影响其他的元素样式显示。</span>
- <span style="color:red">clear属性中的方向指的是要清除的对应浮动元素的方向</span>



### 4. 浮动与文本与内联元素

在上面的内容中，我们说到，浮动会使元素的高度塌陷，块级元素会受其影响位置上移，那么如果是文本或者内联元素呢？

我们使用一个例子来试验一下，设置一个块级元素div浮动，一个span内联元素不浮动，查看效果：

![](/web-css-float/4-01.png)

可以看到，内联元素将浮动的元素也看做显示内容的约束部分，并没有向块级元素一样陷入浮动元素的下方。

那么文本内容呢？我们使用两个div元素来试验，将**div2背景色设置为红色**。将div1设为浮动，不设背景色，并将其高度和宽度设置的大一点。查看效果：

![](/web-css-float/4-02.png)

可以看到，div红色内容全部陷入在浮动的div1中，但是div2文字并没有陷入浮动的div1中，是文字内容不受高度塌陷影响吗？我们将div1的高度设的再大一点，查看效果：

![](/web-css-float/4-03.png)

如我们猜想，文字依然被挤压到下方。文字也会将浮动元素看作显示内容的约束部分。





## 二、总结

- 对一个元素设置浮动通过float属性控制，属性值元素浮动的方向

- 多个浮动元素会按照再HTML中声明的顺序依次向浮动的方向排列

- clear属性负责将元素周围的浮动元素对其的影响效果清除，但是仅作用于自身，并不会将浮动元素的样式清除。

- 文本与内联元素不会受浮动元素的影响。

  最后再给出一个我觉得不错的回答，是关于文档流和文本流的：

  > 文档流是相对于盒子模型讲的
  > 文本流是相对于文子段落讲的
  > 元素浮动之后，会让它跳出文档流，也就是说当它后面还有元素时，其他元素会无视它所占据了的区域，直接在它身下布局。但是文字却会认同浮动元素所占据的区域，围绕它布局，也就是没有拖出文本流。
  > 但是绝对定位后，不仅元素盒子会拖出文档流，文字也会出文本流。那么后面元素的文本就不会在认同它的区域位置，会直接在它后面布局，不会在环绕。
  > 当然你可以使用 index-z 来让底部的元素到上面来，类似于一个图层的概念
  > 作者：张木大原链接：https://www.zhihu.com/question/21911352/answer/102028085来源：知乎著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。



## 参考文章：

[经验分享：CSS浮动(float,clear)通俗讲解](https://www.cnblogs.com/iyangyuan/archive/2013/03/27/2983813.html)