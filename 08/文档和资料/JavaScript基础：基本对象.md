## 前言

在JavaScript中，提供了许多的内置对象来方便我们更高效的编写代码，今天我们就来介绍一下它们的使用方法。

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
   
   想了解Date中的更多方法的用法，请移步[W3C-Date 对象方法](https://www.w3school.com.cn/jsref/jsref_obj_date.asp)

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

   使用方法：

   ```javascript
   Math.random();
   ```

   功能：返回一个 0 ~ 1 之间的随机数，含0不含1。

   生成并输出一个 0 ~ 10  之间的随机数：

   ```javascript
   document.write(10*Math.random() + "<br/>");
   ```

2. ceil()。

   功能：向上取整计算，返回的是大于或等于函数参数，并且与之最接近的整数。

   使用方法：

   ```javascript
   Math.ceil(x);//x为一个数值
   ```

   获取大于 3.3 且与之最接近的整数：

   ```javascript
   document.write(Math.ceil(3.3) + "<br/>");
   
   /*
   输出结果：
   4
   */
   ```

3. floor()。

   功能：向下取整运算，返回的是小于或等于函数参数，并且与之最接近的整数。

   使用方法：

   ```javascript
   Math.floor(x)；//x为一个数值
   ```

   获取小于 5.3 且与之最接近的整数：

   ```javascript
   document.write(Math.floor(5.3) + "<br/>");
   
   /*
   输出结果：
   5
   */
   ```

4. round()。

   功能：四舍五入取整运算。返回的是函数参数经过四舍五入运算后的整数值。

   使用方法：

   ```javascript
   Math.round(x);//x为一个数值
   ```

   获取小于 5.3 且与之最接近的整数：

   ```javascript
    document.write(Math.round(7.3) + "<br/>");
   
   /*
   输出结果：
   7
   */
   ```

   想了解Math中的更多方法的用法，请移步[W3C-Math 对象方法](https://www.w3school.com.cn/jsref/jsref_obj_math.asp)

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

   使用方法：

   ```javascript
   正则表达式对象.test(String);//String 为需要验证的字符串
   ```

   

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

   使用方法：

   ```javascript
   正则表达式对象.exec(String);//String 为待被寻找的字符串
   ```
   
   

   寻找字符串中是否有`app`字符串：

   ```javascript
   var str = "apple";
   var reg = /app/;
   reg.exec(str);
   ```
   
   结果：
   
   由于字符串中存在`app`字符串，返回的结果为字符串`app`

想了解JavaScript中RegExp正则表达式中的更多方法的用法，请移步[W3C-RegExp 对象方法](https://www.w3school.com.cn/jsref/jsref_obj_regexp.asp)

---

### 5. Global 全局对象

全局对象是预定义的对象，作为 JavaScript 的全局函数和全局属性的占位符。通过使用全局对象，可以访问所有其他所有预定义的对象、函数和属性。全局对象不是任何对象的属性，所以它没有名称。

在JavaScript中，Global全局对象其实就是一些方法和属性的集合，这些方法和属性的调用只需要直接书写名字即可。

#### 5.1 全局对象中的一些方法

##### 5.1.1 编解码

在浏览器的数据传输中，为了让数据在传输的时候更稳定和高效，数据在传输时会被编码，接收时会被解码，在默认情况下，数据提交时在服务器上编码。但是有时候我们会需要自己手动编码一次。

1. encodeURI()。

   功能：将一段字符串编码为UTF-8格式，编码的字符一定，并返回编码后的字符串。

   使用方法：

   ```javascript
   encodeURI(String);//String 为待编码的字符串
   ```

   

   示例：

   ```javascript
   <script>
           var code_eu1 = "千帆过烬";
           document.write(encodeURI(code_eu1) + "<br/>");
           //编码部分重要的字符，有些字符不被编码，如 "?"" 和 ":"" 
           var code_eu2 = "http://baidu.com/wd?千帆过烬";
           document.write(encodeURI(code_eu2) + "<br/>");
   </script>
   
   
   /*
   输出结果：
   %E5%8D%83%E5%B8%86%E8%BF%87%E7%83%AC
   http://baidu.com/wd?%E5%8D%83%E5%B8%86%E8%BF%87%E7%83%AC
   */
   ```

   如注释中所提到的，encodeURI() 可以实现对字符串的编码，编码的字符有限制，如链接中的`：` 和 `？`都没有被编码。这也是经常使用的编码方式，可以让我们清楚的看出地址信息。

2. decodeURI()。

   功能：相反的，decodeURI() 就是用来解码被 encodeURI() 编码后的字符串

   使用方法：

   ```javascript
   decodeURI(String);//String 为待解码的字符串
   ```
   
   
   
   示例：
   
   ```javascript
   <script>
   				var code_du1 = decodeURI("%E5%8D%83%E5%B8%86%E8%BF%87%E7%83%AC");
           document.write(code_du1 + "<br/>");
           //解码时也不对 "?" 和 ":" 进行操作
           var code_du2 = decodeURI("http://baidu.com/wd?%E5%8D%83%E5%B8%86%E8%BF%87%E7%83%AC");
           document.write(code_du2 + "<br/>");
</script>
   
   /*
   输出结果：
   千帆过烬
   http://baidu.com/wd?千帆过烬
   */
   ```
   

##### 5.1.2 编解码更多字符

在上面的encodeURI()编解码方法之中，`?` 和 `:` 字符是不被编码的，为了编解码这些URI之间的分隔符，引入了以下两个方法：

1. encodeURIComponent()。
2. decodeURIComponent()。

这两个方法的用法和上面的方法一样，只是编解码的字符多一点而已，不过要记得使用什么方法编码，就要使用对应的方法解码，避免出现乱码的情况：

看例子：

```javascript
<script>
				//1.encodeURIComponent 编码
        var code_euc1 = encodeURIComponent("千帆过烬");
        document.write(code_euc1 + "<br/>");
        //编码更多字符，编码URI组件，将URI之间的分隔符如 "?"" 和 ":"" 编码
        var code_euc2 = encodeURIComponent("http://baidu.com/wd?千帆过烬");
        document.write(code_euc2 + "<br/>");

        //2. encodeURIComponent 解码
        var code_duc1 = decodeURIComponent(code_euc1);
        document.write(code_duc1 + "<br/>");
        //解码对 "?" 和 ":" 也进行操作
        var code_duc2 = decodeURIComponent(code_euc2);
        document.write(code_duc2 + "<br/>");
</script>

/*
输出结果：
%E5%8D%83%E5%B8%86%E8%BF%87%E7%83%AC
http%3A%2F%2Fbaidu.com%2Fwd%3F%E5%8D%83%E5%B8%86%E8%BF%87%E7%83%AC
千帆过烬
http://baidu.com/wd?千帆过烬
*/
```

具体的描述注释以及代码中已经说明了，我就不再赘述了。



##### 5.1.3 字符串转数字

1. parseInt() 

   功能：解析一个字符串的数字内容，返回该数字内容。

   使用方法：

   ```javascript
   parseInt(String,radix);
   ```

   | 参数   | 描述                                                         |
   | :----- | :----------------------------------------------------------- |
   | string | 必需。要被解析的字符串。                                     |
   | radix  | 可选。表示要解析的数字的基数。该值介于 2 ~ 36 之间。如果省略该参数或其值为 0，则数字将以 10 为基础来解析。如果它以 “0x” 或 “0X” 开头，将以 16 为基数。如果该参数小于 2 或者大于 36，则 parseInt() 将返回 NaN。 |

   说明：parseInt()方法会从左到右解析字符串，如果字符串第一个为数字，则返回数字内容，如 `parse(123abc)`返回`123` ，如果字符串第一个不为数字，则返回NaN，如`parseInt(abc123)` 返回NaN。

##### 5.1.4 判定一个值是否为NaN

1. isNaN()

   功能：判断一个值的内容是否为NaN。

   使用方法：

   ```javascript
   isNaN(x);//x为待检测的值
   ```

   说明：NaN在与任意值（包括其自身）进行`==` 比较时，都只会返回false，所以我们不能通过`x==NaN`判断一个值是否为NaN，于是引入了 isNaN() 方法来对数值是否为NaN进行判断。

##### 5.1.5  计算某个字符串，并执行其中的的 JavaScript 代码。

1. exval()

   功能：计算某个字符串，并执行其中的的 JavaScript 代码（用的较少）。

   使用方法：

   ```javascript
   eval(string);//string为含有要计算或执行的JavaScript语句字符串
   ```

   示例：

   ```javascript
   				eval("x=10;y=20;document.write(x*y + '<br/>')"); 
           document.write(eval("2+2"));    
   
   /*
   输出结果：
   200
   4
   */
   ```



想了解JavaScript中Global全局对象中的更多方法的用法，请移步[W3C-Global](https://www.w3school.com.cn/jsref/jsref_obj_global.asp) 。

   

---

### 6. Function函数对象

Function对象在JavaScript中用于描述一个函数（方法）。

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

1. JavaScript中函数定义时参数不使用 var 关键字，返回值也不需要声明。

   在Java中，我们定义一个方式时如果需要参数，那么在定义参数时必须指定数据类型，而且必须指定函数的返回值，如：

   ```java
   public void toLearn(String name, String age){//参数必须使用数据类型关键字定义
   			//方法体
   }
   ```

   但是在JavaScript中，由于其弱类型语言的特性，在定义函数参数时，可以不使用 var 关键字修饰，如果你加了var 还会报错，函数返回值也不用声明：

   ```javascript
   //不使用var,只使用参数名即可，函数也不必声明返回值，直接使用return返回需要返回的值即可
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
   //定义两个都为fun_same的函数，后者的函数内容会覆盖前者
   <script>
   				var fun_same = function(){
               document.write("same_front" + "<br/>");
           }
   
           var fun_same = function(){
               document.write("same_back" + "<br/>");
           }
           //调用函数
           fun_same(); 
   </script>
   
   /*
   输出same_back
   */
   
   ```

3. **在JavaScript中，函数的调用只与方法的名称有关，和参数列表无关(重要)**

   在Java等语言中，方法（函数）在调用时如果需要传入参数，那么就必须将所有参数都传入，使用Java定义一个方法：
   
   ```java
   private int add(int a, int b){
           return a + b;
   }
   ```
   
   在调用时必须将需要的两个参数传入：
   
   ```java
   //必须传入参数
   add(1,2);
   //不传入则报错找不到方法
   add();
   ```
   
   当然有人可能想起来了，在Java中提供了函数重载的功能，可以定义一个相同函数名，不同参数列表的函数来实现同一函数多种功能。但是这也说明了在Java等语言中，确定调用的方法（函数）是通过函数名 + 参数列表来实现的。
   
   
   
   不同于Java，在JavaScript中，函数的调用只与函数名有关，函数在定义时规定的参数列表只是函数的期待值而已。
   
   也就是说，JavaScript的函数在调用时不用遵循该函数定义时规定的参数列表，可以不传参数，也可以传超出个数的参数，都不会出错。
   
   请看例子：
   
   ```javascript
   <script>
   				//3. 在JavaScript中，函数的调用只与方法的名称有关，和参数列表无关(重要)
           var fun_agru1 = function(a,b,c){
   
               document.write(a +"**" + b + "**" + c + "<br/>");
           }
           fun_agru1(1,2,3);//将全部参数传入
           fun_agru1(1);//只传入两个参数
           fun_agru1();//不传任何参数
           fun_agru1(1,2,3,4);//传入超出函数需要的参数个数
   </script>
   
   /*
   输出结果：
   1**2**3
   1**undefined**undefined
   undefined**undefined**undefined
   1**2**3
   */
   ```
   
   在这个例子中，我定义了一个函数，功能为打印传入的参数值，从输出结果中可以看到，四种不同参数个数的调用方式都成功打印了结果。
   
   当传入的参数足够时，参数值全部被打印，多余的参数未使用但是不会影响函数执行。
   
   当参数不够时，函数在执行时将未赋值的参数视为未定义的，而在JavaScript中未定义则代表值为`undefined`，所以第二、第三个调用未接收到参数值的参数都打印了`undefined`。
   
   
   
   4.**在函数声明中有一个隐藏的内置对象arguments（数组），封装了所有传入的实际参数（重要）**
   
   在第三个特点的介绍中，我们介绍了JavaScript中的函数调用时能够接收的实参是不受限制的，当参数个数不够时，未传入实参的形参值为`undefined`。那么当传入的实参超过需求时，多余的参数又到哪里去了呢？
   
   在JavaScript的函数中，有一个arguments对象，其本质是一个数组，包含了所有该函数接收到的实参。所有的参数按照调用函数时定义的次序存储在argments数组中，上面多余的参数就在这个数组里。
   
   请看例子：
   
   ```javascript
           var fun_agru1 = function(a,b,c){
               document.write(arguments.length + "<br/>");         
               document.write(arguments[0] + "**"); 
               document.write(arguments[1] + "**"); 
               document.write(arguments[2] + "**"); 
               document.write(arguments[3]);
               document.write("<br/>");
           }
           fun_agru1(1,2,3,4);//传入超出函数需要的参数个数
   				
   
   
   /*
   输出结果：
   4
   1**2**3**4
   */
   ```
   
   从打印的结果中可以看到，arguments对象里面存储了所有的该函数传入的实参，而且按照次序存储在数组中。
   
   其中 length 属性为arguments数组的长度，也就是接收到参数的个数。
   
   arguments[0] 对应 第一个参数 1，argument[1]对应第二个参数 2 ......等等。
   
   
   
   

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

 

 