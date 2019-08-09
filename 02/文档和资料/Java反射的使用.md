## 1. 反射的出现

将类的各个组成部分封装成其他对象，这就是java的反射机制，通过反射，我们可以得到每个类的各个组成部分。

由于反射的这些优秀特性，现代的主流Web框架 Spring / MyBatis 等都使用了反射，可以说，反射是框架的灵魂。

---

### 1.1 Java代码经历的三个过程

这里可能听的还是很迷糊，反射是什么？作用在什么地方？要搞清楚这个问题，我们首先看一张图：

![](/JavaReflect/01.png)

这张图简略的描述了一个java 程序从编写完成到执行经历的过程。

我们首先看**第一阶段**，我们新建了一个Person.java 文件 ，在里面有一个公有的Person类，类里面有成员变量，构造方法，成员方法。

编写好代码之后，我们要运行的话，首先需要编译，我们使用java自带的`Javac`命令编译后，此时在我们的目录下会生成一个Java字节码文件 `Person.class`。

在`Person.class` 中，主要有三部分内容，一部分是我们所有的成员变量，一部分是我们的构造方法，一部分是我们的成员方法，当然其中不止这些内容，还有类名等等其他信息，在这里我们就不细说了。

Java代码编译完了之后，我们怎么使用呢。我们都知道，要使用一个类首先应该先创建一个对象，也就是使用`new` 关键字创建对象，而此时创建对象这个阶段我们称之为`RunTime` 运行时阶段，也就是上图的**第三阶段**。

但是我们需要考虑一些问题，我们编译好的Java字节码文件开始是在硬盘中的，而程序都是在内存中运行的，在使用之前我们应将其加载的内存中去，如何将字节码加载到内存中呢？在Java中负责加载字节码到内存中的功能的工具Java官方称为 **类加载器（ClassLoader）** ，它负责将类对应的字节码文件加载到内存中，此时为上图的 **第二阶段**。

加载到内存中时，我们肯定要想个方式来表示字节码文件吧，不然我们`new Person` 时如何找到我们的加载的类的信息呢，那么在内存中，Java如何描述字节码文件中的信息呢？在Java中，万物皆对象。在内存中，也有一个对象负责存储描述字节码文件中的信息，该对象为`Class` 类的对象，在Java的api中，我们也可以找到这个类：

![](/JavaReflect/02.png)

在被加载类的 Class 对象中，包含了这个类所有共同的类型信息。什么是共同的类型信息呢？就比如每个类的成员变量，构造方法，成员方法，类名....等等。
这里我们也要注意一个点，一个class字节码文件对应一个Class对象，也就是说，无论你创建了多少个实例对象，在JVM或者说内存中只会存在一个Class对象，其负责描述类的共同类型信息。具体对应关系看下图：

![](/JavaReflect/03.png)

根据前面的分析，我们已经知道了Java使用`Class` 对象来描述类的相关信息，而我们都知道，无论是什么什么类，里面基本上都有三类信息：成员变量，构造方法，成员方法，他们都有自己独特的属性和功能，比如成员变量我们可以获取它的值或是设置它的值， 一个类中也不一定只有一个成员变量。

于是在`Class`对象中，每一个成员变量都被封装成一个`Field`对象，而多个成员变量就形成了`Field []`对象数组。构造方法，成员方法也是如此，在Class类中，他们分别为：

- 成员变量 Field  
- 构造方法 Constructor
- 成员方法 Method

第二阶段加载类的步骤执行完后，JVM再根据`Class`对象中的信息实例对象，至此，对象才被真正创建。

### 1.2 反射的思想

根据我们上面的分析，类在被使用前，应先被类加载器加载到内存中以`Class`对象的方式存在，`Class` 对象将类的成员变量，构造函数，成员方法封装成不同的对象。

那么我们可不可以这样想，当一个Person类的对象在使用时，由于其共有的`Class` 对象中将该对象的成员变量，构造函数，成员方法等封装成了对象，我们是不是可以通过操作这些封装好的对象来操控Person对象中的相关信息？

答案时肯定的，这也就是反射的实现思想，在程序的运行过程中，能够知道程序的所有属性和方法，也能够随时调用改变其中的属性和方法，这就是<span style="color:red">反射机制</span>>

使用反射的优点如下：

- 可以在程序的运行过程中去操作这些对象
- 可以解耦，提高程序的阿可扩展性

---



> 知道了反射的概念之后，我们使用一些案例来分析反射的基本使用



## 2. 反射的基本使用

### 2.1 获取Class对象

通过上面的分析，我们知道了反射可以操控类的`Class` 对象，那么在使用反射之前，我们应该先获得类的`Class` 对象。

我们可以使用如下三种方式获得`Class` 对象，对应Java代码经历的三个阶段。

1. 类还未被加载进内存，也就是源代码阶段使用

   <span style="color:blue">Class.forName("全类名") ：将字节码文件加载进内存，返回Class对象。</span>

2. 类已经被加载进内存了，但是实例对象还没创建时使用

   <span style="color:blue">类名.class：通过类名的属性class获取</span>

3. 类已经被实例化，也就是已经有了实例对象时使用

   <span style="color:blue">对象名.getClass()：利用Object类中定义的getClass()方法</span>

文字描述可能不够透彻，我们使用代码来验证以下我们的结果。

我们打开idea ，新建一个java项目，名字为ReflectDemo，然后新建一个实体类Person，一个ReflectDemo1，此时项目结构如下：

![](/JavaReflect/04.png)

然后我们在Person类中添加如下代码：

```java
public class Person {
    private int age;
    private String name;

    public Person () {
        
    }

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}

```



相应的ReflectDemo1的代码如下：

```java
public class ReflectDemo1 {

    /**
     * 三种不同的获取Class对象的方式：
     * 1、Class.forName("全类名") ：将字节码文件加载进内存，返回Class对象。
     * 2、类名.class：通过类名的属性class获取
     * 3、对象名.getClass()：利用Object类中定义的getClass()方法
     */

    public static void main(String[] args) throws ClassNotFoundException {
        //1.Class.forName("类名")
        //注意这里的字符串是全类名，也就是包名+类名，我这直接写类名的原因是我没有定义包。
        Class cla1 = Class.forName("Person");
        System.out.println(cla1);

        //2.类名.class
        Class cla2 = Person.class;
        System.out.println(cla2);

        //3.对象名.getClass()
        Person p = new Person();
        Class cla3 = p.getClass();
        System.out.println(cla3);
    }
}

```

运行结果：

![](/JavaReflect/05.png)

可以看到，输出的结果是一样的，那么是不是他们都是同一个Class对象吗？

我也不确定，单纯靠输出不能完全判定，我们还需要通过一些验证代码来确定。

根据我们以前学过的知识，判断两个对象是否相等，也就是引用指向的内存空间是否相同，我们可以使用 `==`运算符。

那么我们添加代码如下：

```java
		//判断三个对象是否相等
        System.out.println(cla1 == cla2);
        System.out.println(cla1 == cla3);
        System.out.println(cla2 == cla3);
```

然后运行：

![](/JavaReflect/06.png)

结果都为true，他们的对象都是一样的。这说明了什么？

我们整理一下思路

1. 首先`cla1`对象我们通过`Class.forName("Person")` 获得，而此时类还为被加载。
2. `cla2` 对象我们通过`Person.class` 获得，此时类已经加载但是没有实例化。
3. `cla3`对象我们通过`p.getClass()`获得，此时类已经实例化。

通过对比，三个对象都是同一个对象，也就是说，对于同一个类，它在依次运行时只会被加载一次，且只存在一个`Class` 对象。



### 2.2 使用Class对象

关于Class对象的使用，主要分为四类：

1. 成员变量Field对象的使用
2. 构造方法Constructor对象的使用
3. 成员方法Method对象的使用
4. 对类名的获取

下面我们来分析他们的具体用法。

#### 2.2.1 使用Field类操作成员变量

Class类中有关Field类的基本操作如下：

| 方法返回值 |              方法名              |                           方法功能                           |
| :--------: | :------------------------------: | :----------------------------------------------------------: |
|  `Field`   |     `getField(String name)`      | 获取指定name名称、具有public修饰符的变量（字段），包括继承字段。 |
| `Field[]`  |          `getFields()`           |     获取所有具有public修饰符的变量（字段），包括继承字段     |
|  `Field`   | `getDeclaredField(String name )` | 获取指定name名称的变量（字段），且不考虑修饰符（包括private修饰的），不包括继承字段。 |
| `Field[]`  |      `getDeclaredFields()`       | 获取类中所有变量（字段），不考虑修饰符（包括private修饰的），不包括继承字段 |

Field类中常用的方法：

| 方法返回值 |              方法名               |                           方法功能                           |
| :--------: | :-------------------------------: | :----------------------------------------------------------: |
|  `Object`  |         `get(Object obj)`         |         返回指定对象上此 Field 对象所表示的字段的值          |
|   `void`   | `set(Object obj ,  Object value)` | 将指定对象(obj)变量上此 Field 对象表示的字段设置为指定的新值(value) |
|   `void`   |   `setAccessible(boolean flag)`   | 将此对象的 accessible 标志设置为指定的布尔值，accessible 属性默认为false，设置为true后我们称为《暴力反射》 |

首先在`Person`类修改代码如下：

```java
public class Person {
    private int age;
    private String name;

    /**
     * 用作测试Filed类的使用
     */
    public int height = 180;
    protected String sex = "male";
    public int weight = 120;
    String hobby = "篮球";

    

    public Person () {

    }

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", height=" + height +
                ", sex='" + sex + '\'' +
                ", hobby='" + hobby + '\'' +
                '}';
    }
}

```



然后新建一个ReflecField文件，添加如下代码：

```java
import java.lang.reflect.Field;

public class ReflectField {


    /**
     * Class对象中关于Field的操作
     *
     */
    public static void main(String[] args) throws Exception {
        //获取Person类的class对象
        Class pes = Class.forName("Person");
        //创建一个Person对象
        Person p = new Person();

        System.out.println("**********输出结果**********");
       
        //getField(String name )的使用
        System.out.println("----------getField(String name )的使用----------");
        //注意这里name对应的字段一定要是public修饰的，且该字段存在
        //如不满足上述条件，会抛出 NoSuchFieldException 异常
        try{
            Field age = pes.getField("age");
            System.out.println(age);//age为private修饰，这里不会输出任何值，且抛出NoSuchFieldException异常
        }catch (NoSuchFieldException nfe){
            nfe.printStackTrace();
        }
        Field height = pes.getField("height");
        System.out.println(height);



        System.out.println("----------getFields()的使用----------");
        //获取所有具有public修饰符的变量（字段），包括继承字段
        Field[] fields = pes.getFields();
        for(Field f: fields){
            System.out.println(f);
        }


        System.out.println("----------getDeclaredField(String name )的使用----------");
        //获取指定name名称的变量（字段），且不考虑修饰符（包括private修饰的），不包括继承字段。
        Field age1 = pes.getDeclaredField("age"); //age为private修饰
        Field name1 = pes.getDeclaredField("name");//name字段为private修饰
        Field sex = pes.getDeclaredField("sex");  //sex为protected修饰
        System.out.println(sex);
        System.out.println(age1);


        System.out.println("----------getDeclaredFields()的使用----------");
        //获取类中所有变量（字段），不考虑修饰符（包括private修饰的），不包括继承字段
        Field[] fields1 = pes.getDeclaredFields();
        for (Field f : fields1){
            System.out.println(f);
        }

       
        System.out.println("----------获取变量的值----------");
        //返回指定对象上Field表示的值
        //会对你访问的字段进行权限修饰符检查。也就是private的不可访问
        //如想访问私有，必须使用setAccessible(true)《暴力反射》来忽略权限修饰符的检查
        //否则抛出IllegalAccessException异常
        System.out.println(height.get(p));
        System.out.println(sex.get(p));
        try{
            //age1为获取到的私有对象
            System.out.println(age1.get(p));
        }catch (IllegalAccessException iae){
            iae.printStackTrace();
        }
        //暴力反射
        age1.setAccessible(true);
        System.out.println(age1.get(p));//输出为0，这是因为int类型默认值为0


        System.out.println("----------设置变量的值----------");
        //将指定对象变量上此 Field 对象表示的字段设置为指定的新值。
        //会对你访问的字段进行权限修饰符检查。也就是private的不可访问
        //如想访问私有，必须使用setAccessible(true)《暴力反射》来忽略权限修饰符的检查
        //否则抛出IllegalAccessException异常
        System.out.println("修改所有属性值：" + p.toString());
        height.set(p,170);
        try{
            //name1为获取到的私有对象
            name1.set(p,"小千");

        }catch (IllegalAccessException iae){
            iae.printStackTrace();
        }
        //查看name字段设定可访问之前属性值
        System.out.println(p.toString());
        name1.setAccessible(true);
        name1.set(p,"小千");
        //查看name字段设置可访问之后的属性值
        System.out.println(p.toString());
    }
}

```

运行结果为：

```java

**********输出结果**********

----------getField(String name )的使用----------
java.lang.NoSuchFieldException: age
	at java.lang.Class.getField(Class.java:1703)
	at ReflectField.main(ReflectField.java:23)
public int Person.height

----------getFields()的使用----------
public int Person.height
public int Person.weight

----------getDeclaredField(String name )的使用----------
protected java.lang.String Person.sex
private int Person.age

----------getDeclaredFields()的使用----------
private int Person.age
private java.lang.String Person.name
public int Person.height
public int Person.weight
protected java.lang.String Person.sex
java.lang.String Person.hobby

----------获取变量的值----------
heght：180
sex：male

java.lang.IllegalAccessException: Class ReflectField can not access a member of class Person with modifiers "private"
	at sun.reflect.Reflection.ensureMemberAccess(Reflection.java:102)
	at java.lang.reflect.AccessibleObject.slowCheckMemberAccess(AccessibleObject.java:296)
	at java.lang.reflect.AccessibleObject.checkAccess(AccessibleObject.java:288)
	at java.lang.reflect.Field.get(Field.java:390)
	at ReflectField.main(ReflectField.java:67)
    
age：0
    
----------设置变量的值----------
修改所有属性值：Person{age=0, name='null', height=180, sex='male', hobby='篮球'}
java.lang.IllegalAccessException: Class ReflectField can not access a member of class Person with modifiers "private"
	at sun.reflect.Reflection.ensureMemberAccess(Reflection.java:102)
	at java.lang.reflect.AccessibleObject.slowCheckMemberAccess(AccessibleObject.java:296)
	at java.lang.reflect.AccessibleObject.checkAccess(AccessibleObject.java:288)
	at java.lang.reflect.Field.set(Field.java:761)
	at ReflectField.main(ReflectField.java:84)
Person{age=0, name='小千', height=170, sex='male', hobby='篮球'}

```



#### 2.2.2 使用Constructor类

Class类中有关Constructor类的基本方法如下：

|     方法返回值      |                       方法名                        |                           方法功能                           |
| :-----------------: | :-------------------------------------------------: | :----------------------------------------------------------: |
|  `Constructor<T>`   |    `getConstructor(Class<?>....parameterTypes)`     |      返回指定参数类型、具有public访问权限的构造函数对象      |
| `Constructor<?> []` |                 `getConstructors()`                 |  返回所有具有public访问权限的构造函数的Constructor对象数组   |
|  `Constructor<T>`   | `getDeclaredConstructor(Class<?>...parameterTypes)` | 返回指定参数类型、且不考虑权限修饰符（包括private）声明的构造函数对象 |
| `Constructor<?>[]`  |             `getDeclaredConstructors()`             | 返回所有声明的构造函数对象、且不考虑权限修饰符（包括private） |
|         `T`         |                   `newInstance()`                   | 调用类的空参构造创建此 Class 对象所表示类的一个实例，返回创建的实例， |

在Person类中添加如下代码：

```java
	/**
	 * 私有构造方法
     * 用作测试Constructor类使用
     * @param name
     */
    private Person(String name) {
        this.name = name;
    }
```



新建一个ReflectConstructor，添加如下代码：

```java
import java.lang.reflect.Constructor;

public class ReflectConstructor {
    /**
     *
     * Class对象中关于Constructor的操作
     *
     */


    public static void main(String[] args) throws Exception {
        //获取Person类的class对象
        Class pes = Class.forName("Person");

        System.out.println("**********输出结果**********");
     
        //getConstructor(Class<?>....parameterTypes)的使用
        System.out.println("------------getConstructor(Class<?>....parameterTypes)的使用-----------");
        //获取带int,String 参数的构造方法对象
        //获取的构造方法一定要是 public 修饰且存在的
        //否则抛出NoSuchMethodException
        Constructor constructor = pes.getConstructor(int.class,String.class);
        //Constructor 对象通过调用 newInstance()方法来运行构造方法实例化对象
        //newInstance() 方法中的参数填写根据获得Constructor对象时传入的参数
        //我这里上面传入了int.class , String.class 所以这里带的参数为int，String型
        Object p1 = constructor.newInstance(10, "小千");
        System.out.println(p1);
        System.out.println();

        //getConstructors()的使用
        System.out.println("------------getConstructors()的使用-----------");
        Constructor[] constructors = pes.getConstructors();
        for (Constructor c: constructors){
            System.out.println(c);
        }
        System.out.println();

        //getDeclaredConstructor(Class<?>...parameterTypes)的使用的使用
        System.out.println("------------getDeclaredConstructor(Class<?>...parameterTypes)的使用-----------");
        //获取修饰符为私有的构造方法对象
        Constructor constructor1 = pes.getDeclaredConstructor(String.class);
        //注意这里使用私有构造之前必须将其设为可访问,否则会抛异常
        constructor1.setAccessible(true);
        Object p2 = constructor1.newInstance("大千");
        System.out.println(p1);
        System.out.println();

        //getDeclaredConstructors()的使用
        System.out.println("------------getDeclaredConstructors()的使用-----------");
        Constructor[] constructors1 = pes.getDeclaredConstructors();
        for (Constructor c: constructors1){
            System.out.println(c);
        }
        System.out.println();


        //newInstance()的使用
        System.out.println("------------newInstance()的使用-----------");
        //通过Class对象调用其newInstance()方法，实例化该类的对象
        //newInstance()方法实际上是调用了类的空参构造函数来实例对象的
        //注意这里newInstance()方法参数必须为空
        Object p3 = pes.newInstance();
        System.out.println(p3);
    }
}

```

运行结果如下：

```
**********输出结果**********

------------getConstructor(Class<?>....parameterTypes)的使用-----------
Person{age=10, name='小千', height=180, sex='male', hobby='篮球'}

------------getConstructors()的使用-----------
public Person(int,java.lang.String)
public Person()

------------getDeclaredConstructor(Class<?>...parameterTypes)的使用-----------
Person{age=10, name='小千', height=180, sex='male', hobby='篮球'}

------------getDeclaredConstructors()的使用-----------
public Person(int,java.lang.String)
public Person()
private Person(java.lang.String)

------------newInstance()的使用-----------
Person{age=0, name='null', height=180, sex='male', hobby='篮球'}
```



#### 2.2.3 使用Method类

Class 类中有关Method类的基本操作如下：

| 方法返回值  |                           方法名                            |                           方法功能                           |
| :---------: | :---------------------------------------------------------: | :----------------------------------------------------------: |
|  `Method`   |    `getMethod(String name, Class<?>....parameterTypes)`     | 返回一个指定参数类型的已声明的 Method 对象，包括继承的父类 public 方法。 |
| `Method []` |                       `getMethods()`                        | 返回该Class对象中所有由 public 权限修饰符且已声明的 Method 方法对象，包括继承的父类 public 方法。 |
|  `Method`   | `getDeclaredMethod(String name, Class<?>...parameterTypes)` | 返回指定参数类型、且不考虑权限修饰符（包括private）的已声明的声明的方法 Method 对象，但不包括继承的方法。 |
| `Method[]`  |                   `getDeclaredMethods()`                    | 返回所有已声明的方法 Method 对象、且不考虑权限修饰符（包括private），但不包括继承的方法 |



在Person类中添加如下代码：

```java
/**
     * 用作测试Method类的使用
     */
    public void show(String s){
        System.out.println(s);
        System.out.println("运行 public show");
    }

    private void display(){
        System.out.println("运行 private display");
    }
```



新建一个 ReflectMethod 类，添加代码如下：

```java
import java.lang.reflect.Method;

public class ReflectMethod {
    /**
     * Class对象中关于Method的操作
     *
     */


    public static void main(String[] args) throws Exception {
        //获取Person类的class对象
        Class pes = Class.forName("Person");

        //创建一个Person实例
        Person p = new Person();

        System.out.println("**********输出结果**********");
        System.out.println("----------getMethod(String name, Class<?>....parameterTypes)的使用----------");
        //获得Class对象中public修饰的已声明的指定参数的方法的对象，传入参数
        //由于一个方法都是由方法名和参数列表指定的，这里方法参数需要指定方法名，后面接参数列表的class
        Method show = pes.getMethod("show", String.class);
        //invoke 负责执行方法对象指定的方法，传入指定执行的实例对象，和参数列表
        show.invoke(p,"千");

        System.out.println("----------getMethods()的使用----------");
        //获的Class对象中所有public权限修饰符修饰的方伐，包括父类的方法
        //这里打印出来了Object类的方法，验证结论
        Method[] methods = pes.getMethods();
        for (Method m : methods) {

            System.out.println(m);
        }


        System.out.println("----------getDeclaredMethod(String name, Class<?>...parameterTypes)的使用----------");
        //获取指定参数类型、且不考虑权限修饰符（包括private）的已声明的方法 Method 对象，但不包括继承的方法。
        Method display = pes.getDeclaredMethod("display");
        System.out.println(display);
        //忽略权限修饰符，暴力反射
        display.setAccessible(true);
        display.invoke(p);

        System.out.println("----------getDeclaredMethods()的使用----------");
        //获取所有已声明的方法 Method 对象、且不考虑权限修饰符（包括private），但不包括继承的方法
        //这里没有打印出来Object类的方法，无法获得继承（父类）的方法对象
        Method[] declaredMethods = pes.getDeclaredMethods();
        for (Method m : declaredMethods) {
            System.out.println(m);
        }

        
    }
}

```



输出结果：

```java
**********输出结果**********
----------getMethod(String name, Class<?>....parameterTypes)的使用----------
千
运行 public show
----------getMethods()的使用---------- 
能够获得Object类的方法对象    
public java.lang.String Person.toString()
public java.lang.String Person.getName()
public void Person.setName(java.lang.String)
public void Person.show(java.lang.String)
public void Person.setAge(int)
public int Person.getAge()
public final void java.lang.Object.wait() throws java.lang.InterruptedException
public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException
public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException
public boolean java.lang.Object.equals(java.lang.Object)
public native int java.lang.Object.hashCode()
public final native java.lang.Class java.lang.Object.getClass()
public final native void java.lang.Object.notify()
public final native void java.lang.Object.notifyAll()
----------getDeclaredMethod(String name, Class<?>...parameterTypes)的使用----------
private void Person.display()
运行 private display
----------getDeclaredMethods()的使用----------
不能获得Object类的方法对象
public java.lang.String Person.toString()
public java.lang.String Person.getName()
public void Person.setName(java.lang.String)
private void Person.display()
public void Person.show(java.lang.String)
public void Person.setAge(int)
public int Person.getAge()

```



#### 2.2.4 获取类名

获取类名的方法相对简单，通过以下代码：

```java
		//获取Person类的class对象
        Class pes = Class.forName("Person");
        //返回由类对象表示的实体（类，接口，数组类，原始类型或空白）的名称，作为 String
        String className = pes.getName();
        System.out.println(className);
```

运行结果：

```java
类名：Person
```



OK，反射使用基本结束。





## 3. 参考链接：

[深入理解Java类型信息(Class对象)与反射机制](https://blog.csdn.net/javazejian/article/details/70768369)