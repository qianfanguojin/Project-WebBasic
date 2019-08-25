## 前言

## 基本对象介绍

### 1. Array数组对象

Array 对象用于在单个的变量中存储多个值，也称数组。

#### 1.1 创建方式&访问元素方式

**在JavaScript中，创建一个个数组主要有四种方式**

1. 使用new 关键字创建一个Array对象创建一个空的数组

   ```javascript
   var arr = new Array();
   ```

2. 使用 new 关键字创建一个Array对象来创建一个数组，同时传入元素列表。

   ```javascript
   var arr1 = new Array(2,0,8);
   ```

3. 用 new 关键字一个Array对象来创建数组，传入默认长度

   ```javascript
   var arr2 = new Array(5);
   ```

4. 直接定义元素列表创建数组。（**推荐使用**）

   ```javascript
   var arr3 = [9,5,0];
   ```

**访问数组元素的方式也和其他语言没有什么区别，使用 `数组名[下标]`访问，当然下标也是从0开始的：**

```javascript
				var arr4 = new Array(10,20,30,40);
        document.write(arr4[0] + ",");
        document.write(arr4[1] + ",");
        document.write(arr4[2] + ",");
        document.write(arr4[3] + ",");
```

输出：

```javascript
10,20,30,40
```



#### 1.2  数组的特性

1. 在JavaScript中，数组Array中的数据类型是可变的。

   在Java中，数组在创建之时必须指定存储的数据类型，指定后该数组只能存储该类型的数据，如定义一个存储整型数据且大小为10的数组：

   ```java
   int[] arr = new int[10];//该数组只能存储int类型数据
   ```

   将JavaScript中的数组中每个元素都可以看作一个变量，由于JavaScript中的变量可以随意变换数据类型，所以JavaScript中的数组内的数据类型不局限于一种，且是可变的。

   您可以在数组保存对象。可以在数组中保存函数。你甚至可以在数组中保存数组

   定义一个包含number，string，boolean 类型的数组：

   ```javascript
   var arr5 = new Array(10,"10",true);
   ```

2. 在JavaScript中，数组Array的长度是可变的。

   在Java，Java 中数组的大小是固定的，如果访问数组元素时下标超过范围会报异常。

   而在JavaScript中，数组的大小是可以变化的，当您访问数组元素时下标超过范围不会报异常。

   下面是一个简单的例子：

   ```javascript
   			 /*
           04.数组长度可变
           */
           //定义两个元素的数组
           var arr6 = new Array(66,99);
           document.write(arr6[0] + ",");
           document.write(arr6[1] + ",");
           //可以直接访问第12个元素，但是由于该元素未指定值，所以打印为 undefined
           document.write(arr6[11] + ","); 
           //未赋值时长度不变,不包括该元素，输出2
           document.write(arr6.length + ","); 
           
           //指定值后打印真实值
           arr6[11] = 999;
           document.write(arr6[11] + ",");
           //此时的数组长度length也变为12
           document.write(arr6.length); 
   ```

   输出结果：

   ```javascript
   66,99,undefined,2,999,12
   ```

   **注意：**在直接访问超过范围的数组元素时，如该元素未赋值，此时数组的长度不变。

#### 1.3 数组的属性

1. length

   功能：获取数组的长度。

   使用方法：

   ```javascript
   数组名.length
   ```

   给数组添加新元素时，如果必要，将更新 length 的值。

   设置 length 属性可改变数组的大小。如果设置的值比其当前值小，数组将被截断，其尾部的元素将丢失。如果设置的值比它的当前值大，数组将增大，新的元素被添加到数组的尾部，它们的值为 undefined。

#### 1.4 数组的常用方法

1. join()方法

   功能：把数组的所有元素放入一个字符串。元素通过指定的分隔符进行分隔，并返回该字符串。

   使用方法：

   ```javascript
   数组名.join(参数separator);
   ```

   | 参数      | 描述                                                         |
   | :-------- | :----------------------------------------------------------- |
   | separator | 可选。指定要使用的分隔符。如果省略该参数，则使用逗号作为分隔符。 |

   示例：

   ```javascript
           //join()方法，把数组的所有元素放入一个字符串。元素通过指定的分隔符进行分隔，返回该字符串。
           var arr7 = [2,4,6,8,10];
           //不指定参数，默认分隔符为","
           document.write(arr7.join() + "<br/>");
           //可以指定多种类型的参数作为分隔符
           document.write(arr7.join(0) + "<br/>");
           document.write(arr7.join(">") + "<br/>");
           document.write(arr7.join(null) + "<br/>");
   ```

   输出结果：

   ```javascript
   2,4,6,8,10
   2040608010
   2>4>6>8>10
   2null4null6null8null10
   ```

2. push()方法

   

   功能：向数组的末尾添加一个或更多元素，并返回新的长度。

   使用方法：

   ```javascript
   数组名.push(参数element1,参数element2.....);
   ```

   示例：

   ```javascript
   				//2.push()方法，向数组的末尾添加一个或多个元素，并返回新的长度。
           var arr7 = ["p","u","s","h"];
           document.write(arr7 + "<br/>");
           //可同时添加多个元素,返回添加元素后数组的长度
           document.write(arr7.push("p","o","p") + "<br/>");
           document.write(arr7);
   ```

   输出结果：

   ```javascript
   p,u,s,h
   7
   p,u,s,h,p,o,p
   ```

   想了解Array中的更多方法的用法，请移步 [W3C-Array 对象方法](https://www.w3school.com.cn/jsref/jsref_obj_array.asp)

#### 1.5 Array 数组对象总结

通过上面的分析，我们大致可以看出JavaScript中Array数组的一些特点。

- 可变大小，可存储多种元素类型。
- 拥有许多包装函数供使用。
- 和Java中的ArrayList很类似。

---

### 2. Data日期对象

Date 对象用于处理日期和时间。

#### 2.1 创建方式

使用new 关键字创建Date对象

   ```javascript
var date = new Date();
   ```

#### 2.2 相关方法

1. toLocaleString()

   功能：返回当前date对象对应的时间本地字符串格式。

   使用方法：

   ```
   Date对象.toLocalString()
   ```

   示例：

   ```javascript
   				//toLocaleString()方法，返回当前date对象对应的时间本地字符串格式
           var date1 = new Date();
           document.write(date1 + "<br/>");
           document.write(date1.toLocaleString());
   ```

   输出结果：

   ```javascript
   Fri Aug 23 2019 16:32:04 GMT+0800 (中国标准时间)
   2019/8/23 下午4:32:04
   ```

2. getTime()

   功能：返回当前时间距离1970年1月1日0点的毫秒数

   使用方法：

   ```javascript
   Date对象.toLocalString()
   ```

   示例：

   ```javascript
   				//2.getTime()方法，返回当前时间距离1970年1月1日0点的毫秒数。
           var date2 = new Date();
           document.write(date2.getTime() + "<br/>");
   ```

   输出结果：

   ```javascript
   1566549929455
   ```

---

### 3. Math数学对象

Math 对象用于执行数学任务。

#### 3.1 创建方式

和Java一样，JavaScript中的 Math对象使用不需要创建对象实例。当我们需要使用它的方法时，直接使用`Math.方法名`调用即可使用。

如调用一个生成随机数的方法：

```javascript
Math.random();
```

#### 3.2 属性

通过使用`Math.属性名`可以获取Math对象的相关属性

在Math对象中，拥有的属性列表如下：

| 属性      | 描述                                              |
| :-------- | :------------------------------------------------ |
| E         | 返回算术常量 e，即自然对数的底数（约等于2.718）。 |
| `LN2`     | 返回 2 的自然对数（约等于0.693）。                |
| `LN10`    | 返回 10 的自然对数（约等于2.302）。               |
| `LOG2E`   | 返回以 2 为底的 e 的对数（约等于 1.414）。        |
| `LOG10E`  | 返回以 10 为底的 e 的对数（约等于0.434）。        |
| `PI`      | 返回圆周率（约等于3.14159）。                     |
| `SQRT1_2` | 返回返回 2 的平方根的倒数（约等于 0.707）。       |
| `SQRT2`   | 返回 2 的平方根（约等于 1.414）。                 |

表格上对于功能的描述已经很清楚了，我就不再细细阐述了。

#### 3.3 常用工具方法

1. random()。

   功能：返回一个 0 ~ 1 之间的随机数，含0不含1。

   生成并输出一个 0 ~ 10  之间的随机数：

   ```javascript
   document.write(10*Math.random() + "<br/>");
   ```

2. ceil()。

   功能：向上取整计算，返回的是大于或等于函数参数，并且与之最接近的整数。

   获取大于 3.3 且与之最接近的整数：

   ```javascript
   document.write(Math.ceil(3.3) + "<br/>");
   ```

3. floor()。

   功能：向下取整运算，返回的是小于或等于函数参数，并且与之最接近的整数。

   获取小于 5.3 且与之最接近的整数：

   ```javascript
   document.write(Math.floor(5.3) + "<br/>");
   ```

4. round()。

   功能：向下取整运算，返回的是小于或等于函数参数，并且与之最接近的整数。

   获取小于 5.3 且与之最接近的整数：

   ```javascript
    document.write(Math.round(7.3) + "<br/>");
   ```

上面的函数实例输出结果汇总：

```javascript
4
5
7
```

#### 3.4 Math对象小练习

接触了Math对象的取整方法，我们做个小练习实现1-100（不包括100）内的随机整数。

下面是我的分析步骤加代码：

```javascript
				/*
        03.小测验，输出1-100（不包括100）内的随机整数
        */
        //1.使用random()方法获取 0 ~ 99.9999... 的随机数
        var num = 100*Math.random();
        //2.使用ceil()方法向上取整，去除小数，得到0 ~ 99（包括） 的整数。
        num = Math.ceil(num);
        //3.对结果加 1 ，得到 1 ~ 99（包括99） 
        num += 1;
        document.write(num);
```

---

### 4.RegExp正则表达式对象

#### 4.1 什么是正则表达式

正则表达式是一种定义字符串组成规则的一种表达式，通过正则表达式，我们可以约束字符串的组成规则，也可以从字符串中按照特定的规则取出数据。

#### 4.2 正则表达式字符

##### 4.2.1 开始结束符号

**^：**负责规定匹配的字符串必须以该字符后的字符串内容为开始。

如图：

![4.2.1-01](./JavaScript03/4.2.1-01.png)

可以看到，即使是字符串中间仅有一个空格，也不匹配。

**$：** $ 字符负责规定匹配的字符串必须以该字符前的字符串内容为结束。

如图：

![4.2.1-01](./JavaScript03/4.2.1-02.png)

可以看到，我在字符串末尾多加一个字符A，该字符串就不能匹配，当然空格也一样不行，因为空格也是一个字符。

**当^ ,$ 结合时：**规定匹配的字符串必须是 ^ 和 $ 之间的内容。

如图：

![4.2.1-01](./JavaScript03/4.2.1-03.png)



---

##### 4.2.2 单个字符

使用`[]`代表匹配单个字符，可以在其中使用 `-` 匹配范围，如：

- 匹配字母`a`字符：[a]
- 匹配一个小写字母字符：[a-z]
- 匹配一个大写字母字符：[A-Z]
- 匹配一个数字字符字符：[0-9]
- 匹配一个小写字母或者大写字母或者数字或者下划线：[0-9A-Za-z_]

很明显，当匹配字符的类型多了，这样的写法很复杂。在正则表达式中，规定了一些特殊符号代表特殊含义的单个字符：

**\d：**单个数字字符[0-9]

**\w：**单个单词字符[a-zA-Z0-9_]

---

##### 4.2.3 量词符号

**?：**表示出现0次或1次

***：**表示出现0次或多次

**+：**表示出现1次或多次

**{m,n}：**表示 m<=出现的次数<=n

- m如果缺省：{,n}表示最多n次
- n如果缺省：{m,}表示最少m次

#### 4.3 JavaScript中的正则表达式

##### 4.3.1 创建方式

在JavaScript 中，有两种方式创建一个正则表达式：

1. 使用new关键字新建RegExp对象创建（不推荐）

   ```javascript
   var reg = new RegExp("正则表达式内容");
   ```

2. 使用`\`符号包含字符串创建

   ```javascript
   var reg = /正则表达式内容/;
   ```

##### 4.3.2 相关方法

1. test()。

   功能：验证指定的字符串是否符合正则定义的规范，返回一个boolean值。

   匹配字符串中是否符合正则表达式`^abc`

   ```javascript
   var str = "abcdefg";
   var reg = /^abc/;
   reg.test(str);
   ```

   结果：

   由于字符串开头为abc，返回的结果为 `true`

   

   

2. exec()。

   功能：寻找指定的字符串是否存在符合正则定义的规范的文本，返回寻找到的文本。

   寻找字符串中是否有`app`字符串：

   ```javascript
   var str = "apple";
   var reg = /app/;
   reg.exec(str);
   ```

   结果：

   由于字符串中存在`app`字符串，返回的结果为字符串`app`

---

### 5. Global 全局对象

全局对象是预定义的对象，作为 JavaScript 的全局函数和全局属性的占位符。通过使用全局对象，可以访问所有其他所有预定义的对象、函数和属性。全局对象不是任何对象的属性，所以它没有名称。

在JavaScript中，Global全局对象其实就是一些方法和属性的集合，这些方法和属性的调用只需要直接书写名字即可。

#### 5.1 全局对象中的一些方法

---

### 6. Function函数对象

Function对象用于描述一个函数（方法）

在Java中，方法（函数）存在于类中，创建对象后是对象的一部分。而在JavaScript中，函数就是一个对象。

#### 6.1 函数的创建方式&调用

在JavaScript中，创建一个函数一共有三种方式：

1. new 一个Function对象，传入形式参数列表和函数体(不推荐)

   ```javascript
   var fun_new = new Function("a","b","alert(a)");
   ```

2. function 函数名称(形式参数列表){函数体}

   ```javascript
   function fun_name(a,b){
       alert(a);
   }
   ```

3. var 函数名 = function(){函数体}

   ```javascript
   var fun_name2 = function (a, b) {
   		alert(a);
   }
   ```

要使用某个函数其实也很简单，和其他语言没有多大区别，使用 `方法名(参数列表)`调用即可。

如：

```javascript
//定义一个加法的方法
var fun = function(x,y){
		return x + y;
}
//调用方法
var result = fun(1,3);


/*
结果为4
*/
```



#### 6.2 函数的特点

1. JavaScript中函数定义时参数不使用 var 关键字。

   在Java中，我们定义一个方式时如果需要参数，那么在定义参数时必须指定数据类型，如：

   ```java
   public void toLearn(String name, String age){//参数必须使用关键字定义
   			//方法体
   }
   ```

   但是在JavaScript中，由于其弱类型语言的特性，在定义函数时，可以不必使用 var 关键字修饰，如果你加了var 还会报错：

   ```javascript
   //不使用var
   var fun_novar = function(a,b){
   		//函数体
   }
   //使用var，会报错
   var fun_var = function(var a,var b){
   		//函数体
   }
   ```

2. JavaScript的函数是一个对象，对象又是一种变量，和变量的定义特点一样，如果定义了多个相同名称的函数，后面的函数会覆盖前面的函数定义，只会存在**一个**真正可用函数实例。

   ```javascript
   <script>
   				var fun_same = function(){
               document.write("same_front" + "<br/>");
           }
   
           var fun_same = function(){
               document.write("same_back" + "<br/>");
           }
           fun_same(); 
   </script>
   
   /*
   输出same_back
   */
   
   ```

3. **在JavaScript中，函数的调用只与方法的名称有关，和参数列表无关(重要)**

   在Java等语言中，方法（函数）

#### 6.3 函数的属性

length：代表形参的个数

示例：

```javascript
<script>
var fun_attr = function(a,b,c);
var len = fun_attr.length;
document.write(len + "<br/>");
</script>
/*
输出结果为3
*/
```

从这里我们更可以看出JavaScript中的函数是对象了，因为它有自己的属性。

 

 