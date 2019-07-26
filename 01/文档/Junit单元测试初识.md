## 什么是单元测试

在软件行业，测试方法大致分为两大类：

1. 黑盒测试。

   在黑盒测试中，它不考虑程序内部结构和内部特性，不需要写代码。例如我对该程序输入测试后，黑盒测试随后只检查程序执行之后的输出的结果是否正确，着眼考虑于程序的外部结构。

   

2. 白盒测试。

   相对于黑盒测试，其需要测试人员关注程序内部功能实现的流程，需要写代码。白盒测试在输入测试后，用户可以看到程序中的内部结构，逻辑，算法.... 等。

   

可以看到，白盒测试对于测试人员的要求更高，但是相应地，白盒测试能够发现程序的更多细致的问题，所以白盒测试对于开发人员来说是一项不可或缺的技能。



在Java 中，Junit单元测试框架就是一种白盒测试。

## 初步使用Junit

对于Junit测试来说，有以下几个步骤：

- 定义一个测试类
- 在测试类中定义一个可以独立运行的测试方法
- 给方法加`@Test`注解
- 导入Junit环境

下面我使用一个用例来具体解析如何使用Junit进行测试。



### 1. 新建一个测试项目

打开 idea ， 点击 File>New>Project，左边选择Java ，选择好 jdk 的路径 ，点击 Next ，再点击 Next，将项目名命名为`JunitTest`，然后点击 FINISH ，创建项目。

然后在`src` 目录下新建一个junit包。鼠标右键单击`src` 目录，然后点击New>Package，输入名字为junit，点击OK。

最后在junit包下新建我们想要测试的Calculator类。鼠标右键单击`junit` 目录，然后点击New>Java Class，输入名字为Calculator，点击OK。

此时我们的项目结构如下：

![](/Junit/01.png)

然后我们在`Calculator`类中定义两个方法用作被测试，代码如下：

```java
package junit;

/**
 * 计算器类
 */
public class Calculator {

    /**
     * 加法操作
     * @param a
     * @param b
     * @return
     */

    public int add (int a , int b) {
        return a + b;
    }

    /**
     * 减法操作
     * @param a
     * @param b
     * @return
     */
    public int substract (int a , int b) {
        return a - b;
    }

}

```

至此，项目基本建立完成。

### 2. 新建测试类

上面我们已经建好了项目且编写好了被测试类的方法，那我们要对Calculator中的方法进行测试的话应该需要编写一个测试类。

对于开发人员来说，将测试代码和源代码分开是一个好习惯。对于测试类，我们在`src` 同级目录下新建`test`文件夹，专门用来放测试代码，接着在IntelliJ IDEA里还要把这个test文件夹要设置成测试文件的根目录，右键选中
Mark Directory As - Test Sources Root。

![](/Junit/02.png)



建好目录之后，我们在同样在`test` 目录下新建一个与要测试的类相同的包`junit`。

同时在该包下新建类名为CalculatorTest的类，在编写代码的过程中，将测试类的包名和被测试的包名保持一致是一个好习惯。

通过测试类的名字我们又可以很清楚的了解到，这是用做Calculator类的测试类，所以对于测试类的命名，我们推荐使用`要测试的类名 + Test` 。

此时项目结构如下：

![](/Junit/03.png)

### 3. 在测试类中新建测试方法

我们打开CalculatorTest类，在里面输入以下内容：

```java
package jnnit;

import junit.Calculator;

public class CalculatorTest {


    /**
     * 测试add方法
     */
    public void testAdd(){
        //1.创建计算器对象
        Calculator ca = new Calculator();
        //调用add方法
        int result = ca.add(1,3);
        System.out.println(result);
    }
}

```

可以看到，我们定义了一个`testAdd`方法，用于对Calculator类中的add方法进行测试。

在这个方法中，我们首先是创建了一个计算器Calculator对象，然后调用它的add方法将1，3相加，最后输出相加的结果。

### 4. 添加注解独立运行

但是，这样的方法并不能直接运行。根据我们学过的知识，要使用`testAdd`方法，我们必须先new 一个对象出来，然后调用该方法，该方法才会被直接执行。

但是junit测试框架通过注解将该操作简化，我们只需在测试的方法上加上`@Test` 注解，就可以实现独立运行，下面是加上注解后的代码：

```java
package jnnit;

import junit.Calculator;
import org.junit.Test;

public class CalculatorTest {


    /**
     * 测试add方法
     */
    @Test
    public void testAdd(){
        //1.创建计算器对象
        Calculator ca = new Calculator();
        //调用add方法
        int result = ca.add(1,3);
        System.out.println(result);
    }
}

```

第一次使用的话，注解会报红，这是没有导入包的原因，我们点击`@Test`注解，按键盘的Alt+Enter 键：

![](/Junit/04.png)

选择第一个，然后idea就会自动加载该包。



加上注解后，我们可以看到在方法左边多了个绿色箭头，我们以前在使用`main`方法，左边也有同样的箭头，这代表该方法可以运行。

 ![](/Junit/05.png)



我们点击该箭头运行，出现如下结果：

 ![](/Junit/06.png)

可以看到，左边显示绿色提示，右边的控制台打印了我们输出的相加结果，测试成功。

当测试出现异常时，左边的提示会变成红色。

我们在测试时，都是通过是否出现提示红色来判断测试的程序段是否有问题，这就要求我们测试代码编写时要有一定的严谨性，下面我来介绍增加测试代码严谨性的方法。



## 使用断言

#### 1. 什么是断言？

断言就是对你的测试结果确定一个事先的肯定结果。在对这个测试结果做了断言之后，测试结果会与你事先希望的结果比较，如果一样，测试提示为绿色，结果不一样，测试提示为红色。

#### 2. 为什么使用断言？

在我们上面的测试中，我们调用`Calculator` 中的 add 方法，然后输出结果。

但是这样的测试存在很多弊端，也就是只通过结果无法判断程序的逻辑是否正确。

例如，我将`Calculator` 类中的add方法修改：

```java
  public int add (int a , int b) {
        return a - b;
    }
```

然后我们再次运行测试类的 testAdd 方法：

![](/Junit/07.png)

左边提示为绿色，没有发现问题。

我修改add方法的代码之后，其逻辑已经出现了问题，但是测试并没有出现问题，可以看出单纯通过打印输出结果无法判断程序是否正确。

#### 3. 开始使用断言

Junit 提供了一个类 Assert ，它有许多静态重载方法来定义断言：

![](/Junit/08.png)

使用断言，修改代码：

```java
  @Test
    public void testAdd(){
        //1.创建计算器对象
        Calculator ca = new Calculator();
        //2.调用add方法
        int result = ca.add(1,2);
//        System.out.println(result);
        //3.使用断言
        Assert.assertEquals(3,result);
    }
```

`assertEquals()`接收两个值，前者为我们期望的值也就是断言的值，后者时测试的真正结果值，此时我们再次运行测试方法：

![](/Junit/09.png)

可以看到，左边并没有变为绿色，这里没显示红色是我主题的原因。但是我们可以通过右边看到，上面提示Test failed ，也就是测试失败。

然后列出了原因 :

Expected ：3           期望为3
Actual：-1                实际为-1

此时我们将`Calculator` 中的 add 方法修改回正常：

```java
  public int add (int a , int b) {
        return a + b;
    }
```

运行测试方法，测试成功：

![](/Junit/10.png)



## 补充内容

使用`@Test`注解能够将方法设定为独立运行，但是每次我们运行测试方法时，都要在测试方法中new对象。

也就是说，类似于IO的操作，我们每一个测试方法都要先获得被测试类的对象才能调用它对应的方法，而这些操作基本在每个测试方法都要用到，为了减少程序代码的冗余，我们一般会将这些操作都放在一个函数里面，在类被使用时首先就执行加载数据，使用结束后释放数据。

而Junit刚好提供了这样的功能，通过`@Before`注解，指定该方法在所有测试方法执行之前执行。

通过`@After` 注解，指定该方法在所有测试方法之后执行。

我们来试验一下，修改测试类的代码如下：

```java
package jnnit;

import junit.Calculator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class CalculatorTest {
    /**
     * 在所有测试方法之前执行
     * 一般用于数据和资源的初始化
     */
    @Before
    public void beforeMethod(){
        System.out.println("Before");
    }

    /**
     * 在所有测试方法之后执行
     * 一般用于数据和资源的释放
     */
    @After
    public void afterMethod() {
        System.out.println("After");
    }


    /**
     * 测试add方法
     */
    @Test
    public void testAdd(){
        //1.创建计算器对象
        Calculator ca = new Calculator();
        //2.调用add方法
        int result = ca.add(1,2);
//        System.out.println(result);
        //3.使用断言
        Assert.assertEquals(3,result);
    }

}

```

然后我们运行测试方法：

![](/Junit/11.png)

可以看到打印的顺序和我们设想的一样。

JUnit4利用JDK5的新特性Annotation，使用注解来定义测试规则。
以下为几个常用的注解：

- @Test：把一个方法标记为测试方法
- @Before：每一个测试方法执行前自动调用一次
- @After：每一个测试方法执行完自动调用一次
- @BeforeClass：所有测试方法执行前执行一次，在测试类还没有实例化就已经被加载，所以用static修饰
- @AfterClass：所有测试方法执行完执行一次，在测试类还没有实例化就已经被加载，所以用static修饰
- @Ignore：暂不执行该测试方法

## 参考链接

https://blog.csdn.net/dreamweaver_zhou/article/details/79850202