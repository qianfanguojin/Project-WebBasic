## 1. 什么是注解

### 1.1 基本介绍

注解（Annotation），也叫元数据。一种代码级别的说明。它是 **JDK1.5** 及以后版本引入的一个特性，与类、接口、枚举是在同一个层次。它可以声明在包、类、字段、方法、局部变量、方法参数等的前面，用来对这些元素进行说明。

举个例子，注释我们都知道，注释是对程序的一些说明，是为了让程序员看得懂程序而编写的。

而注解也是对程序的说明，不过是给计算机看的，计算机通过注解得到信息，可以把注解理解为标签，给类、方法、成员变量贴上标签，对他们进行一些说明。

在使用 Junit 单元测试时，我们在每一个单元测试的方法上都要加上一个`@Test` 注解，这个注解就是告诉测试程序这个方法是测试方法，是可以用作单元测试的，可以直接运行：

```
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
```



### 1.2 注解的基本结构

接下来我们从源码找寻注解的结构。

首先我们查看 SuppressWarnings 注解内部内容：

```java
@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE})
@Retention(RetentionPolicy.SOURCE)
public @interface SuppressWarnings {

    String[] value();
}
```

可以看到，里面的结构为两个注解 + 一个用@interface声明的名称，该名称`{}`范围中还有一个类似方法定义的`String[] value()`。

#### 1.2.1 注解定义分析

首先我们看`@interface` ，它其实是一种标识符，标识声明为一个注解，就如同`class`标识为声明为类一样。

那么 @interface 内部到底是如何实现注解定义的呢，我们可以通过反编译查看其中的内容，首先我们在任意目录下新建一个 MyAnno.java 文件，然后打开命令行窗口并定位到该目录，执行以下命令：

```java
javac MyAnno.java //编译形成class文件
javap MyAnno.class //反编译
```

可以看到在执行第二条命令的下面出现以下代码信息：

```java
Compiled from "MyAnno.java"
public interface MyAnno extends java.lang.annotation.Annotation {
}
```

看到这里我们就很熟悉了，这不就是声明了一个名字为 MyAnno 的接口然后继承了 `java.lang.annotation.Annotation` 吗，那么 `java.lang.annotation.Annotation` 是啥，我们打开API文档，搜索 Annotation ，可以看到这么一段内容：

> - public interface Annotation
>
>   所有注解类型扩展的公共接口。  注意，手动扩展这个接口不限定注解类型。 还要注意，此接口本身并不定义注解类型。  有关注解类型的更多信息，请参见The Java™ Language Specification的第9.6 节  。 [`AnnotatedElement`](../../../java/lang/reflect/AnnotatedElement.html)接口讨论了将注解类型从不可重复性转变为可重复时的兼容性问题。  

到这基本就清楚了，@interface 用来定义一个注解，而且注解底层本质是一个接口，所有注解默认继承了 `java.lang.annotation.Annotation`接口  当然要注意，**注解不支持继承**，不可以使用 extends 关键字继承自 @interface 。

#### 1.2.2 元注解

我们再观察上面两个注解 @Target 和 @Retention 。

`@Target`，`@Retention`两个注解其实是由 Java 提供的 **元注解** ，所谓元注解就是注解的注解，可能有点懵，我这样解释一下：

比如在类上面使用注解是表述类或描述类的一些信息，而**元注解在注解中的使用就是用来表述或定义注解的一些信息**。下面我们分别对这两个元注解进行分析。

- **@Target注解**

  查看 @Target 注解的内容如下：

  ```java
  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.ANNOTATION_TYPE)
  public @interface Target {
      /**
       * Returns an array of the kinds of elements an annotation type
       * can be applied to.
       * @return an array of the kinds of elements an annotation type
       * can be applied to
       */
      //返回注释类型的元素种类的数组
      ElementType[] value();
  }
  ```
  
  可以看到注解内容中有一个 `ElementType[] value()` 。
  
  ElementType 为枚举类型，其作用是帮助我们在定义 Target 注解时指定传入的值，其中定义的内容如下：
  
  ```java
   
     * @author  Joshua Bloch 
     * @since 1.5 // 1.5版本之后可用
     * @jls 9.6.4.1 @Target 
     * @jls 4.1 The Kinds of Types and Values 
     */
    public enum ElementType {
        /** Class, interface (including annotation type), or enum declaration */
        //表明该注解可以用于类、接口、（包括注解类型）或枚举类型之上
        TYPE,
    
        /** Field declaration (includes enum constants) */
        //表明该注解可用于字段，包括enum常量之上
        FIELD,
    
        /** Method declaration */
        //表明该注解可用于方法之上
        METHOD,
    
        /** Formal parameter declaration */
        //表明该注解可用于方法参数之上
        PARAMETER,
    
        /** Constructor declaration */
        //表明可用于构造函数之上
        CONSTRUCTOR,
    
        /** Local variable declaration */
        //表明该注解可用于局部变量之上
        LOCAL_VARIABLE,
    
        /** Annotation type declaration */
        //表明该注解可用于注解之上
        ANNOTATION_TYPE,
    
        /** Package declaration */
        //表明该注解可以用于包之上
        PACKAGE,
    
        /**
         * Type parameter declaration
         *
         * @since 1.8 从java 1.8 开始可用
         */
         //表明该注解可用于类型参数
        TYPE_PARAMETER,
    
        /**
         * Use of a type
         *
         * @since 1.8 从java 1.8 开始可用
         */
         //表明该注解可用于类型使用
        TYPE_USE
    }
  ```
  
   解释：
  
    Target 是目标的意思，@Target 就是约束注解可以使用的范围，例如我想约束注解只可作用于**方法**上，那么我就需要在注解的定义上声明如下：
  
  ```java
    //注解作用于方法上
    @Target(ElementType.METHOD)
    public @interface AnnoBasic {
    }
  ```
  
    Target 中的成员变量`ElementType[] value()`是一个数组，也就是意味着可以同时出传入多个类型相同但数值不同的值。
  
    例如我想使注解作用于类、方法、成员变量上，就可以这样定义：
  
  ```java
    //注解作用于类、方法、成员变量上。
    @Target({ElementType.TYPE,ElementType.METHOD,ElementType.FIELD}) 
    public @interface AnnoBasic {
    }
  ```
  
    不过在这里要注意使用大括号`{}`将这些属性括起来表示为数组。
  
    **还有一点**，当注解上没有使用 @Target 注解约束时，表示该注解可以作用于任何元素之上。

- **@Retention注解**

  查看@Retention注解的内容：

  ```java
  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.ANNOTATION_TYPE)
  public @interface Retention {
      /**
       * Returns the retention policy.
       * @return the retention policy
       */
      //返回保留的策略
      RetentionPolicy value();
  }
  
  ```

  类似 @Target 注解，这里也有一个成员属性值 value（），类型为`RetentionPolicy`。RetentionPolicy也是枚举类型，其中封装的值帮助我们定义@Retention注解指定传入的值，其中内容如下：

  ```java
  public enum RetentionPolicy {
      /**
       * Annotations are to be discarded by the compiler.
       */
      //表明注解被编译器丢弃（该类型的注解信息只会保留在源码里）
      //源码经过编译后，注解信息就会被丢弃，不会保留在class文件中
      SOURCE,
  
      /**
       * Annotations are to be recorded in the class file by the compiler
       * but need not be retained by the VM at run time.  This is the default
       * behavior.
       */
      //表明注解会被记录在class文件中，但VM（虚拟机）在运行时不会被保留该注解，该内容不会被加载到虚拟机中。
      //在不手动指定注解的生命周期时，默认注解声明周期都是该值
      CLASS,
  
      /**
       * Annotations are to be recorded in the class file by the compiler and
       * retained by the VM at run time, so they may be read reflectively.
       *
       * @see java.lang.reflect.AnnotatedElement
       */
     	//表明该注解会被记录在class文件中而且在VM（虚拟机）运行时会被保留
      //虚拟机会加载class文件中的该注解的内容，可以使用反射机制读取该注解的内容
     
      RUNTIME
  }
  ```

  解释：

  具体每个属性的信息可以查看上面的代码注释，说明的很清楚，我再说几点。

  第一，类似@Override、@SuppressWarning这些对代码编写规范的提示类注解，他们的作用周期都是 SOURCE 也就是源码阶段。

  第二，在目前的主流框架中，将注解定义为 RUNTIME 运行时时用的比较多的，如SpringMvc中的@Controller、@Autowired、@RequestMapping等。定义在运行时，我们可以通过反射获取注解的内容，可以简化代码。

- **@Inherited注解**

  @Inherited 负责指定注解可以被继承，但不是真正意义上的继承，**注解本质上是不可以继承的**，我们无法使用`extends`标识符继承自 @interface 。这里的继承指得是可以让子类通过反射使用Class对象中的getAnnotations() 获取父类被@Inherited修饰的注解，具体请看以下示例：

  ```java
  
  /**
   * @author qianfanguojin
   *  /@Inherited表明注解可继承的示例
   *
   */
  
  @Inherited
  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  @interface Anno1{
      String value() default "Anno1被使用";
      //定义@Inherited的注解
  }
  
  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  @interface Anno2{
      String value() default "Anno2被使用";
      //没有定义@Inherited的注解
  }
  
  
  @Anno1
  @Anno2
  class A{
  
  }
  
  class B extends A{
      public static void main(String[] args) throws ClassNotFoundException {
          //获得子类B的Class对象
          Class cls = Class.forName("cn.annotation.B");
  		//获得子类B上面定义的所有注解（注意该注解是要定义在类的声明上面的）
          Annotation[] ano = cls.getAnnotations();
          for(Annotation a : ano) {
              //获得的注解类型如果来自Anno1注解，输出该注解中value的值
              if(a instanceof Anno1) {
                  System.out.println(((Anno1)a).value());
              }
              //获得的注解类型如果来自Anno2注解，输出该注解中value的值
              if (a instanceof Anno2) {
                  System.out.println(((Anno2)a).value());
              }
          }
      }
  }
  ```

  输出结果：

  ```java
  Anno1被使用
  ```

- **@Documented**

  说明该注解将被包含在javadoc中。和注释一样，注解虽然会在源码中显示，但是 javadoc 命令生成的文档默认会隐藏注解内容，如果我们想让某个注解在 javadoc 中可显示，在注解中定义即可，如：

  ```
  /**
   * 定义注解可以在java文档中出现
   */
  @Documented
  @interface DO{
  
  }
  ```


#### 1.2.3 注解元素（属性）

##### p1. 基本定义和使用

在上面 @SuppressWarning 注解的定义内容中，有一个`String[] value()`。

这其实是注解的属性，或者说是元素。在前面的分析中，我们知道，注解的本质是一个接口，在接口中，我们可以定义抽象方法，在注解中我们同样可以，只不过在注解中，抽象方法被用来表示注解的元素。

于是在注解中注解元素的定义以 **无形参的方法** 形式声明，方法名定义了该元素的名字，返回值定义了该元素的类型。

在大多数自定义注解中，一般都会声明一些元素以标识某些值，方便程序使用，比如我们上面使用的 Target 注解，它在注解定义中定义了一个成员属性，那么我们就可以在声明注解的时候传入相关值来实现不同的效果：

```java
@Target(ElementType.METHOD) //注解应用于方法上
```

在注解定义中，支持如下的数据类型：

- 所有的基本类型（int,float,boolean,byte,double,char,long,short）
- String 字符串
- Class 类对象
- enum 枚举
- Annotation 注解
- 上述类型的数组

除以上的类型之外，在注解中不可使用其他类型或是说基础类型的包装类型如 `Integer`，不然编译器会报错。

还有一点就是和定义成员变量一样，注解元素也支持定义默认值，在某个元素定义了默认值后，我们在使用注解时可以不指定该元素的值。定义默认值方法是在定义的注解元素名后用 `defult` 指定。

使用示例如下：

```java
package cn.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

//注解作用于类、方法、成员变量上。
@Target({ElementType.TYPE,ElementType.METHOD,ElementType.FIELD})
public @interface AnnoBasic {
}


//注解作用于方法上
//@Target(ElementType.METHOD)
//public @interface AnnoBasic {
//}


/**
 * @author qianfanhuojin
 * 对注解中可使用的元素类型示例
 */
@interface AnnoElement{
    //基础类型 ，设定默认值都为0；
    int a() default 0;
    float b() default 0;
    long c() default 0;
    double d() default 0;

    //String 类型，默认值为空字符串
    String e() default "";

    //Class类型,默认值为void.class
    Class f() default void.class;

    //枚举类型,这里使用ElementType枚举类做示例,默认值为Element.TYPE
    ElementType g() default ElementType.TYPE;


    //注解类型，也就是可以使用嵌套注解，使用上面的注解AnnoBasic,默认值为@AnnoBasic
    AnnoBasic h() default @AnnoBasic;

    //数组
    int[] i() default 0;

}
```

注意两点：

1. 在定义默认值时要注意，编译器对注解属性的默认值使用规定非常严格。

   首先是任何一个元素的默认值都不能设定为 `null`，在使用注解时也不能将元素的值指定为null ；其次是任一元素不能有不确定的值，要么在注解的定义中声明元素的默认值，不然就必须要在代码中使用注解时指定该元素的值。对于有些情况我们想让其表示为空时，只能用空自字符串或者是0等其他参数代替。

2. 使用数组作为注解元素时，在使用该注解需要指定多个值时，需要用`{}`将数值包含起来，如：

   ```java
   @Target({ElementType.TYPE,ElementType.METHOD,ElementType.FIELD})
   ```

   

##### p2. 使用快捷方式指定注解元素的值

在使用注解时，我们要指定注解元素的值，一般来说都是使用 `key=value` 的方式，比如：

```java
@AnnoElement(a=1,b=2)
```

但有的时候我们并不需要指定所有注解元素的值，或者说只有一个注解元素的值需要指定，在注解中提供了一个元素名为 `value()`的元素，只要在注解的定义中定义了名为 `value`的元素，我们在使用注解指定该值时就可以直接在括号中填入该值，最明显的一个案例就是 @Target 注解的内容：

```java
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Target {
    /**
     * Returns an array of the kinds of elements an annotation type
     * can be applied to.
     * @return an array of the kinds of elements an annotation type
     * can be applied to
     */
    //返回注释类型的元素种类的数组
    ElementType[] value();
}
```

定义了元素名为 value() 后，我们在使用时就可以直接填入该值的内容，如：

```java
@Target({ElementType.TYPE,ElementType.METHOD,ElementType.FIELD})
```



---



## 2. 注解的基本使用

在 Java 中，注解的语法格式为： `@+注解名`，如`@Override`。

一般来说，注解的使用主要分为以下三类：

- 编写文档。在代码的文档注释里使用 Java 提供的注解，然后使用 javadoc 命令生成doc文档。
- 编译检查。通过代码里标识的注解能够让编译器实现编译检查，如 【Override】命令检查是否为继承重写的方法。
- 代码分析。通过代码里标识的注解对代码进行分析，处理。



在现代的主流框架中，如Spring ，大量使用了注解来简化代码，那么现在我们就来分析注解具体的一些我们应该学习的地方。


### 2.1 使用注解编写文档

使用 Java 时，我们经常会遇到自己不认识的类或者是某个方法我们不知道具体是做什么用的，此时我们都知道可以翻阅 java 的 api 文档查看，如 java8 的文档：

![](/JavaAnnotation/01.png)

那么这些文档怎么生成的呢？

其实这些都是使用 javadoc 命令通过解析代码中的文档注释以及相关注解自动生成的网页。

现在我们用一个简单的例子测试以下：

```java
/**
 * 注解生成 Javadoc
 * @author 千帆过烬
 * @since 1.5
 * @version 1.0
 *
 */

public class AnnoDocDemo {

    /**
     * 计算两数的和
     * @param a 整数
     * @param b	整数
     * @return 两数的和
     */
    public int add (int a , int b) {
        return a + b;
    }

}
```

然后在 AnnoDocDemo.java 文件所在目录打开命令行窗口，执行：

```java
javadoc AnnoDocDemo.java
```

此时在目录下会生成许多网页相关的文件：

![](/JavaAnnotation/02.png)

然后我们打开 index.html 可以看到：

![](/JavaAnnotation/03.png)

生成了跟我们的 api 文档一样的样子，而且刚才我们定义的注解都被解析成文档的格式

如`@since` 表示从何版本开始，方法中的`@return` 表示对返回值的解释。

当然在文档注释中可以使用的注解不止这些，通过使用这些注解可以帮助 javadoc命令 在生成文档的时候根据注解内容来生成不同的文档内容。也就是说，注解能让 java 程序通过注解得到程序想要的信息。



###  2.2 JDK 内置的一些注解

在 JDK 中预置的三个常用注解为：

1. `@Override` ：检测该被注解标注的方法是否是继承自父类（接口）的。

   源码为：

   ```java
   @Target(ElementType.METHOD)
   @Retention(RetentionPolicy.SOURCE)
   public @interface Override {
   }
   ```

   

2. `@Deprecated`：被该注解标注的内容，表示已经过时。

   源码为：

   ```java
   @Documented
   @Retention(RetentionPolicy.RUNTIME)
   @Target(value={CONSTRUCTOR, FIELD, LOCAL_VARIABLE, METHOD, PACKAGE, PARAMETER, TYPE})
   public @interface Deprecated {
   }
   ```

   

3. `@SuppressWarning`：有选择的关闭编译器对类、方法、成员变量、变量初始警告。

   源码为：

   ```java
   @Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE})
   @Retention(RetentionPolicy.SOURCE)
   public @interface SuppressWarnings {
       String[] value();
   }
   ```

   内部的 String 数组接收的值如下：

   ```
   deprecation：使用了不赞成使用的类或方法时的警告；
   unchecked：执行了未检查的转换时的警告，例如当使用集合时没有用泛型 (Generics) 来指定集合保存的类型; 
   fallthrough：当 Switch 程序块直接通往下一种情况而没有 Break 时的警告;
   path：在类路径、源文件路径等中有不存在的路径时的警告; 
   serial：当在可序列化的类上缺少 serialVersionUID 定义时的警告; 
   finally：任何 finally 子句不能正常完成时的警告; 
   all：关于以上所有情况的警告。
   ```

   

使用介绍如下：

#### 2.2.1 @Override

首先是`@Override`注解，添加如下代码

```java
/**
 * 1. `@Override` ：检测该被注解标注的方法是否是继承自父类（接口）的。
 * 2. `@Deprecated`：被该注解标注的内容，表示已经过时。
 * 3. `@SuppressWarning`：压制（忽略）警告。
 */
public class AnnoJdk {

    /**
     * /@Override注解是对代码的一种严谨性检查
     * 在方法上声明该注解能够确保方法是重写自父类的
     */

    //声明了Override注解后，该方法必须在父类中存在
    //所有的类都继承了Object类，toString方法在Object类中，这里不报错
    @Override
    public String toString() {
        return super.toString();
    }

    //这里会报错
    //声明了@Oerride注解后，该方法必须在父类能够找到，toString1方法在父类中不存在
    @Override
    public String toString1(){

    }


    
}
```

![](/JavaAnnotation/04.png)

可以看到，编辑器报错，并提示 `Method dose not override method from its superclass` 也就是该方法不是重写父类的，声明`@Override注解`会报错。



#### 2.2.2 @Deprecated

其次是`@Deprecated`注解，在刚才的文件中添加代码：

```java
/**
     * /@Deprecated注解对方法进行过时标记
     * 被该注解标识的方法在使用时会提示过时，在ide中该方法被画了一条线
     */
    @Deprecated
    public void add(){
        //旧的方法
    }

    public void addnew(){
        //新的方法
    }
```

这时我们如果调用 `add（）` 方法时，会出现：

![](/JavaAnnotation/05.png)

提示我们方法过时，在 Java 的发展中，强大的类库为我们提供了许多的方便，不过有些方法或类久了之后在程序中使用已经不再推荐，如 Java 中的 Data 类z中的许多方法：

![](/JavaAnnotation/06.png)

A [link](#this-is-a-title) to jump towards target header



#### 2.2.3 @SuppressWarning

最后是`@SuppressWarning`注解，我们在编辑器编写代码时候，如：

![](/JavaAnnotation/07.png)

这里每一小点点都是编译器帮我们检查出来的一些可能的问题，编辑器会以一些方式来提醒用户，但是有得时候警告可能很多，而且我们自己其实知道代码并没有什么问题，我们可以使用`@SuppressWarning`注解来压制警告。

`@SuppressWarning`压制警告的范围和类型由自己决定，比如我想压制整个类的所有警告类型，我在类名上声明注解为:

```java
@SuppressWarnings("all")
```

在类上声明，该注解会作用于整个类的区域，后面的字符串指定你要压制的类型，一般我们使用`al`即全部类型的警告，其余的属性请翻阅上面的 [JDK 内置的一些注解](#2.2-JDK-内置的一些注解) （按住Ctrl + 鼠标左键跳转）。



----



## 3. 注解进阶使用

### 3.1 获取使用注解对象

在使用自定注解之前，我们先来了解一下如何获取注解中的内容。

根据前面的知识，我们知道注解声明的位置是在类、方法、成员变量....等等之上，如果接触过反射的话，我们知道在程序的运行过程中我们可以通过反射机制获取类中的构造方法、成员方法、成员变量等，而注解也可以看作是类、方法、成员变量中的一部分。

为了在运行时准确的获取到注解的相关信息，Java 在java.lang.reflect 反射包下提供了AnnotatedElement 接口，反射我们使用的基本类如Method类、Constructor类、Field类、Class类和Package类都实现了该接口。

该接口中的方法及功能如下：

|             方法返回值             |                            方法名                            |                           方法功能                           |
| :--------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
|     `<T extends Annotation> T`     |          `getAnnotation(Class<T> annotationClass)`           | 返回该元素的指定注解类型的注解对象（包括继承自父类的），如找不到该注解类型，则返回 null。 |
|           `Annotation[]`           |                      `getAnnotations()`                      |        返回该元素上所有存在的注解，包括继承至父类的。        |
| `default <T extends Annotation> T` |     `getDeclaredAnnotation( Class <T> annotationClass )`     | 返回该元素的指定注解类型的注解对象，如找不到该注解类型，则返回 null，忽略继承自父类的注解。 |
|           `Annotation[]`           |                  `getDeclaredAnnotations()`                  |      返回该元素上所有存在的注解，忽略继承至父类的注解。      |
|         `default boolean`          | `isAnnotationPresent( Class <?  extends Annotation> annotationClass )` | 如果此元素上*存在*指定类型的注解(包括继承自父类的)，则返回true，否则返回false。  该方法主要用于方便访问标记注解。 |

一个简单的使用案例如下：

```java
package cn.annotation;

import java.lang.annotation.*;
import java.util.Arrays;

import static java.lang.annotation.ElementType.METHOD;

/**
 * @author qianfanguojin
 * 注解的简单示例
 */

@AH1
@AH2
class AnnoGetH{
}

@A1
@A2
public class AnnoGet extends AnnoGetH{
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException {
        //获取AnnoGet类的Class对象
        Class<?> aClass = Class.forName("cn.annotation.AnnoGet");
        //由于我注解都是定义在类上面的
        //下面的注解都要用Class类对象来获取
        //相应的如果定义在方法上则要用方法Method对象获取
        
        System.out.println("----------getAnnotation 获取该元素指定注解类型的注解对象,包含继承的----------");
        A1 a1 = aClass.getAnnotation(A1.class);
         //获取继承自AnnoGetH类的注解
        AH1 ah1 = aClass.getAnnotation(AH1.class);
        System.out.println(a1.toString());
        System.out.println(ah1.toString());

        System.out.println("----------getAnnotations 获取该元素所有的注解对象，包含继承----------");
        Annotation[] annotations = aClass.getAnnotations();
        System.out.println(Arrays.toString(annotations));

        System.out.println("----------getDeclearedAnnotation 获取该元素指定注解类型的注解对象，忽略继承的----------");
        A2 a2 = aClass.getDeclaredAnnotation(A2.class);

        //此方法不能获取到继承的注解，否则会抛出异常
        System.out.println(a2.toString());

        System.out.println("----------getDeclearedAnnotations 获取该元素所有的注解对象，忽略继承的----------");
        Annotation[] annotations1 = aClass.getDeclaredAnnotations();
        System.out.println(Arrays.toString(annotations1));



        System.out.println("----------isAnnotationPresent 判断该元素是否存在指定的注解对象，包括继承的----------");

            //依次判断该元素是否有相应的注解
            if (aClass.isAnnotationPresent(A1.class)) {
                System.out.println("A1 true");
            }

            if (aClass.isAnnotationPresent(A2.class)) {
                System.out.println("A2 true");
            }

            if (aClass.isAnnotationPresent(AH1.class)) {
                System.out.println("AH1 true");

            }

            if (aClass.isAnnotationPresent(AH2.class)) {
                System.out.println("AH2 true");

            }


    }

}

/**
 * AnnoH类的注解,可继承
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, METHOD})
@interface AH1{

}

/**
 * AnnoH类的注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, METHOD})
@interface AH2{

}

/**
 * Anno类的注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, METHOD})
@interface A1{
}

/**
 * Anno类的注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, METHOD})
@interface A2{

}
```

输出的结果为：

```
----------getAnnotation 获取该元素指定注解类型的注解对象,包含继承的----------
@cn.annotation.A1()
@cn.annotation.AH1()
----------getAnnotations 获取该元素所有的注解对象，包含继承----------
[@cn.annotation.AH1(), @cn.annotation.A1(), @cn.annotation.A2()]
----------getDeclearedAnnotation 获取该元素指定注解类型的注解对象，忽略继承的----------
@cn.annotation.A2()
----------getDeclearedAnnotations 获取该元素所有的注解对象，忽略继承的----------
[@cn.annotation.A1(), @cn.annotation.A2()]
----------isAnnotationPresent 判断该元素是否存在指定的注解对象，包括继承的----------
A1 true
A2 true
AH1 true
```

要注意一点，虽然getAnnotations 和getAnnotation 都能获取到从父类继承的注解，但是父类的注解必须被声明 @inherited 注解，子类才能获取到该父类的注解。



### 3.2 自定义注解实现对方法的测试

了解完注解的基础知识以及如何使用反射获取注解之后，我们通过一个例子自定义注解解决我们的问题。

假设小明写了一个类 Calculator  ，他自信满满的对我说：

小明：“像我这么优秀的人，我写的代码肯定是没有问题的”

我：“自信是好的，但是还是不要轻心，还是测试比较一下比较好的”

小明：“那你想怎么测试，一个个调用很麻烦的，还要一个个分析”

我：“不用，我只要使用注解@QTest就好了，你看我表现吧”



小明编写的类 **Calculator** ：

```java
/**
 * @author qianfanguojin
 * 计算器类
 */
public class Calculator {

    //减法
    @AnnoCheck
    public void add () {
        System.out.println("1 + 0 =" + (1 + 0));
    }

    //减法
    @AnnoCheck
    public void sub() {
        System.out.println("1 - 0 =" + (1 - 0));
    }

    //乘法
    @AnnoCheck
    public void mul() {
        System.out.println("1 * 0 =" + (1 * 0));
    }
    //除法
    @AnnoCheck
    public void div() {
        System.out.println("1 / 0 =" + (1 / 0));
    }

    public void showSuccess() {
        System.out.println("啊哈哈，没有BUG");
    }

}
```

测试使用的注解 **AnnoCheck**：

```java
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AnnoCheck {
}

```

最后，我使用自己的测试类 CalculatorCheck 就可以测试 Calculator 类中的方法了：

```java
/**
 * @author qianfanguojin
 * 测试类，对方法测试并生成测试信息
 */
public class CalculatorCheck {

    public static void main(String[] args) throws Exception{
        //获取Calculator类的Class对象
        Class cls = Class.forName("cn.annotation.Calculator");
        //创建一个类实例用于运行方法
        Calculator ca =  new Calculator();
        //计算运行错误的次数
        int num = 0 ;
        //打印结果的字符串
        StringBuilder strb = new StringBuilder();
        //获取该类中的所有方法
        Method[] methods = cls.getMethods();
        for(Method m: methods) {
            //判断该方法上是否有注解，有则是要测试的方法
            if (m.isAnnotationPresent(AnnoCheck.class)) {
                //测试的方法可能会有错误，我们应该捕获异常
                try {
                    m.invoke(ca);
                    strb.append("方法: " + m.getName() + " 运行成功\n");
                }catch (Exception e){
                    num++;
                    strb.append("方法: " + m.getName() + " 运行出现异常\n");
                    strb.append("异常名为：" + e.getCause().getClass().getSimpleName() + "\n");
                    strb.append("异常的原因：" + e.getCause().getMessage() + "\n");

                }

            }
        }
        strb.append("--------------------\n");
        strb.append("发现" + num + "个错误！");
        System.out.println(strb.toString());
    }
}
```

输出的结果为：

```java
1 + 0 =1
1 - 0 =1
1 * 0 =0
方法: add 运行成功
方法: sub 运行成功
方法: mul 运行成功
方法: div 运行出现异常
异常名为：ArithmeticException
异常的原因：/ by zero
--------------------
发现1个错误！
```

可以看到，提示div 方法出现了异常，异常名为 ArithmeticException ，异常原因为除 0 操作。

小明的代码是有问题的，而我也通过注解完成了我的目的。

利用注解 + 反射的结合能帮我们解决平时看起来很复杂的问题，在目前的应用中，注解在各种框架中大放光彩，比如：

- JUnit 测试框架

- Android 的 ButterKnife

- Spring 

  ..... 



## 4. jdk 8 对注解功能的增强

### 4.1 新增元注解@Repeatable

@Repeatable 注解在Java 8 中加入，它表示的注解可以在同一个位置重复使用。比如：

```
@Role("USER")
@Role("ADMIN")
public class Person{
}
```

我们可以这样理解，一个人可以有多种角色，它是用户，又是管理员。在 java8 之前，如果我们要实现一个注解表示多个相同类型但不同的值，我们必须要在注解内部声明数组，比如@Target注解：

```java
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Target {
    /**
     * Returns an array of the kinds of elements an annotation type
     * can be applied to.
     * @return an array of the kinds of elements an annotation type
     * can be applied to
     */
    //返回注释类型的元素种类的数组
    ElementType[] value();
}
```

然后我们在使用时只能声明一次注解，然后指定多个值形成数组:

```java
@Target({ElementType.TYPE,ElementType.METHOD,ElementType.FIELD})
```

而Java8又提供了另一种解决方式：

首先定义可以重复使用的注解：

```java
/**
 * Role注解，允许重复使用
 */
@Repeatable(Roles.class) //指定注解要放置的容器
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface Role{
    String value();
}
```

然后我们可以在同一个地方使用注解了，但是每个Role注解里面的身份信息不一样，我们应该需要一个容器保存该注解不同的形式，于是我们定义容器：

```java
/**
 * Role注解的容器，接收相同的Role注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface Roles{
    Role[] value();
}
```

在这里有一个Role元素数组负责保留声明的多个 Role 注解。

为了处理这种重复注解，java8在 AnnotatedElement 接口中增加了两个方法，用法如下：

|             方法返回值              |                          方法名                          |                           方法功能                           |
| :---------------------------------: | :------------------------------------------------------: | :----------------------------------------------------------: |
| `default <T extends Annotation>T[]` |    `getAnnotationsByType( Class<T> annotationClass)`     | 返回该元素上指定注解类型，重复使用的注解，包括继承自父类的。 |
| `default <T extends Annotation>T[]` | `getDeclaredAnnotationsByType(Class<T> annotationClass)` | 返回该元素上指定注解类型，重复使用的注解，不包括继承自父类的。 |

对于 getAnnotationsByType 首先该方法会调用getDeclaredAnnotationsByType 寻找当前元素中是否有指定注解类型重复声明的注解，如有则返回当前元素定义的所有注解。

如找不到，并且注解是声明在一个类上，而且注解是可继承的(使用了@Inherited)，则就去父类寻找是否有指定注解类型重复声明的注解。

两者只能取一，意思是如果你开始获取到子类有指定注解类型的重复注解，那么就不会再去寻找父类是否有该指定类型的重复注解，子类找不到再去寻找父类。

简单的使用示例如下：

```java
@Role("USER")
@Role("ADMIN")
public class Person extends Child{

    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> cls = Class.forName("cn.annotation.Person");
        Role[] annotationsByType = cls.getAnnotationsByType(Role.class);
        for (Role r: annotationsByType){
            System.out.println(r.toString());
        }
    }
}

@Role("MAN")
@Role("Child")
class Child{

}

/**
 * Role注解，允许重复使用
 */
@Inherited //Roles 和 Role 两个注解都要定义为可继承，否则子类无法获取父类的重复注解
@Repeatable(Roles.class) //声明该注解可重复使用，并指定该注解要放置的容器
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface Role{
    String value();
}


/**
 * Role注解的容器，接收相同的Role注解
 */
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface Roles{
    Role[] value();
}
```

输出结果：

```java
@cn.annotation.Role(value=USER)
@cn.annotation.Role(value=ADMIN)
```

根据上面的说明，子类父类只能选一，我要获取父类的则必须把子类同名的重复注解注释：

```java
//@Role("USER")
//@Role("ADMIN")
```

此时我们再运行，结果为：

```java
@cn.annotation.Role(value=MAN)
@cn.annotation.Role(value=Child)
```

成功获取到父类的重复注解。



好了，本篇文章结束，谢谢大家阅读。

## 5. 参考链接

1. [深入理解Java注解类型(@Annotation)](https://blog.csdn.net/javazejian/article/details/71860633)
2. [秒懂，Java 注解 （Annotation）你可以这样学](https://blog.csdn.net/briblue/article/details/73824058)