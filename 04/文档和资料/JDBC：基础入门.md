---
typora-root-url: ./
---

## 1. JDBC概念

JDBC 是英文词语 **Java DataBase Connectivity** 的缩写，我们也将其称为 Java 数据库连接。

这样解释肯定太过笼统，那 JDBC 的本质是什么呢？

众所周知，Java 的发展闪光点之一就是其跨平台的特性。其实现了一份代码，多个平台都能运行，而这个特性的核心技术在于 **JVM** 这个中间人的存在，帮助java 字节码在不同平台上都能成功运行。

而 JDBC 也是做一个中间人的作用。我们希望，我们写同样的数据库操作，能在不同的数据库中都能使用。

但是不同的数据库厂商又有自己的规范，Sun 公司也发现了这个问题，于是其定义了一套 Java 中关于数据库操作的规范（接口），每个数据库厂商再根据这套规范编写相应的驱动（jar 包），我们使用这套接口进行代码的编写，真正执行的则是相应数据库提供的jar包中的实现类。

具体看下图：

![](/JDBCSimple/01.png)

## 2. JDBC 入门使用

JDBC 操作数据库的步骤大致可以分为五步：

1. 注册驱动，告诉程序要连接哪个数据库。

2. 获取连接对象，和数据库牵个手，建立联系。

3. 组装SQL语句，确定要对数据库的操作。

4. 获取SQL语句执行对象，向数据库要执行sql语句的能力。

5. 执行SQL语句，执行组装好的SQL语句。 
6. 对执行SQL的结果进行处理，确定SQL语句执行正确。

7. 释放连接对象，和数据库分手。

接下来我们从零开始体验一次

### 2.1 建立测试所用的数据库和表

为了下面的示例使用，我们先建立一个数据库和一张表用作测试。

首先进入 mysql 的控制台，进入成功的结果应该是这样：

![](/JDBCSimple/02.png)

然后新建一个数据库 hk

```mysql
create database hk;
```

然后进入 hk 数据库，并新建一张user表，这个表有两个字段：

```mysql
user hk；
create table user(user char(10),password char(16));
```

插入两条数据：

```mysql
insert into user value('小明','123456');
insert into user value('小华','123456');
```

### 2.1 编写连接数据库代码

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class simpleDemo {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //1.注册驱动,告诉程序要连接哪个数据库.
        Class.forName("com.mysql.jdbc.Driver");
        //2.获取连接对象，和数据库牵个手，建立联系。
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hk","你的用户名，一般为 root ","你的用户名密码");
        //3.组装SQL语句，确定要对数据库的操作。
        String sql = "update user set user = '小李' where user = '小明'";
        //4.获取SQL语句执行对象，向数据库要执行sql语句的能力。
        Statement stm = connection.createStatement();
        //5.执行SQL语句，执行组装好的SQL语句。
        int result = stm.executeUpdate(sql);
        //6.对执行SQL的结果进行处理，确定SQL语句执行正确。
        System.out.println(result);
        //7.释放连接对象，和数据库分手。
        stm.close();
        connection.close();

    }
}
```

在这里我执行了数据库更新操作，更新`user`表中将第一行记录的名字从小明改为小李。

然后我们检查一下SQL语句是否真的执行了，在控制台查询表中使用 `select * from user` 查询表所有记录，结果如下:

```mysql
+--------+----------+
| user   | password |
+--------+----------+
| 小李   | 123456   |
| 小华   | 123456   |
+--------+----------+
2 rows in set (0.00 sec)
```

修改成功。

## 3. JDBC 使用的各个类功能解析

### 3.1 DriverManager

#### f1. 注册驱动

我们使用 `Class.forName("com.mysql.jdbc.Driver");` 这行代码来实现数据库连接驱动的注册。

如果有过反射的基础的话，我们知道，`Class.forName` 会将指定的 class文件 装载进内存而形成 Class 对象。

这里意思就是将类 `com.mysql.jdbc.Driver` 加载进内存；但是，加载类时如何实现注册驱动的功能呢？查看 `com.mysql.jdbc.Driver` 类的源码，内容如下：

```java
public class Driver extends NonRegisteringDriver implements java.sql.Driver {
    public Driver() throws SQLException {
    }

    static {
        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException var1) {
            throw new RuntimeException("Can't register driver!");
        }
    }
}
```

可以看到类中有一段静态代码块，静态代码块会在类加载时就会执行。

再查看静态代码块中的内容，这里 **DriverManager** 类调用了其的一个静态方法registerDriver并传入 Driver 类的一个实例。

registerDriver 方法，从名字上看是注册驱动，为了确定结果，我们翻阅 api 文档找到 DriverManager类中的registerDriver 方法，文档中如此解释：



> ```
> public static void registerDriver(Driver driver)
>                            throws SQLException
> ```
>
> 注册与给定的驱动程序`DriverManager` 。  新加载的驱动程序类应该调用方法`registerDriver`使其自己已知的`DriverManager`  。 如果驱动目前已注册，则不采取任何行动。



到这里大致就清楚了，注册驱动其实真正的实现是：加载`com.mysql.jdbc.Driver` 类-> 自动执行静态代码块 -> 调用**DriverManager**类的静态方法registerDriver实现注册 。



#### f2. 获取连接数据库的对象

我们使用 `getConnection(String url,String user,String password)`方法来获取数据库连接对象，这个方法接收三个参数，三个参数的具体含义如下：

- url : 指定数据库连接的路径 

  - url 在不同的数据库中的格式不一样。

  - 在mysql数据库中，格式为：**jdbc:mysql://ip地址:mysql数据库使用的端口/数据库名**。

    比如我要访问本机（本机ip地址为 127.0.0.1 或 localhost）上的mysql数据库（端口号默认为3306）中的hk数据库，url 应该这样写：

    ```java
    jdbc:mysql://localhost:3306/hk
    ```

  - 如果访问的本机上的mysql而且端口号是3306，则 url 中ip地址和端口号可省略，如：

    ```
    jdbc:mysql:///hk
    ```

- user：数据库的用户名，我们使用的mysql用户名默认是root。

- password：数据库用户的密码。

---

### 3.3 Connection 

#### f1. 获取执行SQL语句的对象

- 获取执行SQL语句的 Statement对象

  ```java
  Statement stm = connection.createStatement();
  ```

- 获取执行SQL语句的 PrepareStatement对象（**推荐**）

  ```java
  PrepareStatement = connection.prepareStatement(String sql);
  ```

  通常来说 PrepareStatement 比Statement 更安全且快速，在大多数时候我们都应使用PrepareStatement来操作SQL语句。

#### f2. 事务管理

事务就是一个业务操作，其一般包含多个数据库操作，Connection类中也提供了事务管理的方法，使用的步骤为：

1. 开启事务：

   ```
   connection.setAutoCommit(false);
   ```

   默认所有的SQL操作都是自动提交的，将自动提交关闭代表我想手动提交，也就是开启事务。

2. 提交事务：

   ```java
   connection.commit();
   ```

3. 回滚事务：

   ```
   connection.rollback();
   ```

---

### 3.4 Statement

Statement 是执行sql语句的对象，封装了许多方法用于对数据库的操作。

一般我们不使用这个类来操作SQL语句，只需大概了解就行。

#### f1. 执行DML、DDL语句

DML（insert、update、delete）,DDL语句（create、alter、drop）相关的数据库操作在Statement 类中都使用**excuteUpdate(String sql)** 方法来实现。

如：

```java
  		String sql = "update user set user = '小李' where user = '小明'";
        Statement stm = connection.createStatement();
        int result = stm.executeUpdate(sql);
```

**excuteUpdate(String sql)** 方法返回一个整数代表SQL语句执行后对数据库中内容影响的行数，可以通过这个结果来判断SQL语句执行是否成功。

#### f2. 执行DQL语句

DQL（select）语句是指数据库中查询记录的语句，在Statement类中使用**excuteQuery（String sql）**方法实现。

如：

```java
		String sql = "select * from user";
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeUpdate(sql);
```

**excuteUpdate(String sql)** 返回一个 ResultSet（结果集对象），里面存储了从数据库中查询出来的记录集合。

---

### 3.5 ResultSet

结果集(ResultSet)是从数据库中查询结果返回的一种对象，可以说结果集是一个存储查询结果的对象，但是结果集并不仅仅具有存储的功能，他同时还具有操纵数据的功能，可能完成对数据的更新等。

#### f1. 游标cursors

在ResultSet结果集中 ，对数据的操作都是以游标（cursors）来操控的，游标类似指针，游标指向ResultSet 的某一行。

![](/JDBCSimple/03.png)

当我们通过执行的SQL语句获得了ResultSet 对象，此时游标位置默认如上图一样，指向查询结果中第一条记录的上一个位置，通过使用ResultSet类中控制游标的方法，可以将其位置自由移动，如 `next()`游标向下移动一行。

#### f2. getXXX(parameter.....)方法获取数据

- XXX代表获取的数据类型，如Int为`getInt`、String 为`getString`

- parameter 代表参数类型：

  - 参数为 Int：代表列的编号，如：

    `getString(1)` 表示获取当前游标行第一列的字符串类型数据

    **注意**：这里列号是从**1**开始的，而不是和数组一样从0开始。

  - 参数为String：代表列的名称，如：

    `getString("user")`表示获取当前游标行列名为**user**的字符串类型数据

#### f3. 结合游标和getXXX()遍历结果集

几个关键点：

- 使用 boolean next() 方法

  当使用ResultSet 的对象调用`next()`方法时，游标会向下移动一行，返回值有两种结果:

  - 返回`true`：表示该行存在，游标指向该行。
  - 返回`false`：表示该行在结果集中不存在，游标此时指向最后一行之后。

- 使用while循环

  每次获取一行数据之前都通过执行 `next()`判断下一行是否存在，存在则使用相应的`getXXX()`方法获取该行的数据。

示例：

```
/**
 * 循环实现遍历结果集
 */
//每次获取数据之前都判断下一行数据是否存在
while(rs.next()){
	....
	String user = rs.getString("user");//获取当前行列名为user的数据
	String password = rs.getString("password");//获取当前行列名为password的数据
	...
}
```

---

### 3.6 PrepareStament

PrepareStament 和 Statemt 一样都是执行 SQL 语句的对象，但是PrepareStatement 比 Statement 更安全和高效，我们在日常使用中都是使用这个对象来执行SQL语句。

在使用了PrepareStatement之后，我们操作数据库的步骤有所改变：



1. 首先注册驱动，告诉程序要连接哪个数据库。

   ```java
   Class.forName("com.mysql.jdbc.Driver");
   ```

   

2. 获取连接对象，和数据库牵手，建立联系。

   ```java
   Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/数据库名","你的用户名，一般为 root ","你的用户名密码");
   ```

   

3. 编写带有占位符 ”?“ 的SQL语句。

   原始的SQL语句定义：

   ```java
   String sql = "select * from user  where password = '123456'";
   ```

   带占位符的SQL语句定义：

   ```java
   String sql = "select * from user where password = ?";
   ```

   这里对于哪里填充占位符没有特定的规定，填充了占位符的地方后面就需要进行占位符赋值。

   一般来说我们都是在控制语句 where 后面的赋值语句中设置占位符，这样我们可以根据后面对占位符赋值的不同来获取不同的SQL操作结果。

   

4. 获取SQL语句执行对象并传入带有占位符的sql语句，此时SQL语句会被预编译。

   ```java
   PrepareStatement pst = connection.prepareStament(sql);
   ```

   

5. 对SQL语句中的占位符进行赋值。

   ```java
   pst.setString(1,"123456");
   ```

   在PrepareStament 类中有许多 setXXX(参数1,参数2)  方法负责对SQL语句中的占位符进行赋值。

   每个setXXX方法中接收两个参数：

   - 参数1：指定要赋值的占位符位置。如`setInt(1,1)`将第一个占位符设置为整型 1

   - 参数2：指定要赋值的占位符数据值，该数据的类型应和方法名中XXX指定的数据类型相同。如：

     ```java
     //第二个参数传入的值数据类型为long,方法名中执行的数据也为long
     void setLong(int parameterIndex,long x) throws SQLException
     //方法功能：将指定的参数设置为给定的Java long值。
     ```

   **注意**：占位符是从**1**开始的，第一个占位符的索引为1而不是0。

   

6. 执行SQL语句。

   ```java
      ResultSet rs = pst.executeQuery();
   ```

   **注意**：

   PrepareStatement 是 Statement 的子类，会继承Statement的所有方法，在调用执行SQL语句的方法时，注意不要调用到 Statement 的方法。区别的办法就是 PrepareStatement 特有的方法都是 **无参** 的，如：

   - 查询方法 `executeQuery()`  。对应数据库中 DQL（select）操作，返回结果集ResultSet对象。
   - 更新方法 `executeUpdate()` 。对应数据库中DML（insert、update、delete）,DDL语句（create、alter、drop）操作,返回执行该SQL语句对数据库影响的行数。

   

7. 对执行SQL语句的结果进行处理。

   ```java
   while(rs.next()) {
               String user = rs.getString("user");
               String pas = rs.getString("password");
               System.out.println("用户名：" + user + "    密码：" + pas);
           }
   ```

   输出结果为：

   ```java
   用户名：小明    密码：123456
   用户名：小华    密码：123456
   ```

   

8. 释放连接对象，和数据库分手。

   ```java
   		rs.close();
           pst.close();
           connection.close();
   ```

   关闭连接的时候要遵循先小后大的顺序。在这里 rs 对象由 pst 对象获取到，而 pst 对象又由 connection 对象获取到。所以顺序为 rs > pst > connection。

根据上面的步骤，合并起来的示例：

```java
import java.sql.*;

public class PreDemo {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //1.注册驱动,告诉程序要连接哪个数据库.
        Class.forName("com.mysql.jdbc.Driver");
        //2.获取连接对象，和数据库牵个手，建立联系。
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/数据库名","你的用户名，一般为 root ","你的用户名密码");
        //3.编写带有占位符 ”?“ 的SQL语句。
        String sql = "select * from user where password = ?";
        //4.获取SQL语句执行对象并传入带有占位符的sql语句。
        PreparedStatement pst = connection.prepareStatement(sql);
        //5. 对SQL语句中的占位符进行赋值。
        pst.setString(1,"123456");
        //6.执行SQL语句，执行组装好的SQL语句。
        ResultSet rs = pst.executeQuery();
        //7.对执行SQL语句的结果进行处理
        while(rs.next()) {
            String user = rs.getString("user");
            String pas = rs.getString("password");
            System.out.println("用户名：" + user + "    密码：" + pas);
        }
        //8.释放连接对象，和数据库分手。
        rs.close();
        pst.close();
        connection.close();

    }
}
```



## 4. JDBC 使用进阶

### 4.1 更严谨的使用JDBC，使用try catch捕获异常

在上面的连接数据库的示例中，我们在`main`方法上抛出了多个异常，也就是说，我们操作数据库的步骤中可能会遇到多种异常，但是直接抛出异常很有可能会直接终止程序，这样的设计对于程序体验来说是非常不好的。

对于数据库连接操作来说，在程序中直接抛出异常而直接终止程序很有可能会造成资源没有成功释放等问题。

于是，在使用数据库操作时，我们应该将异常捕获而不是直接抛出，把可能发生异常的代码用 try 包括，然后根据捕获到的不同异常使用 catch 进行不同的异常处理；最后，无论程序有没有异常我们都要关闭资源，将关闭资源的代码写在 finally 代码块中。

根据以上总结，将上面的示例修改为使用 try catch  之后的代码为：

```java
import java.sql.*;

/**
 * @author qianfanguojin
 * 使用了 try catch 之后的数据库连接操作写法
 */
public class TryPreDemo {


    public static void main(String[] args){

        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            //1.注册驱动,告诉程序要连接哪个数据库.
            Class.forName("com.mysql.jdbc.Driver");
            //2.获取连接对象，和数据库牵个手，建立联系。
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hk1","你的用户名，一般为 root ","你的用户名密码");
            //3.编写带有占位符 ”?“ 的SQL语句。
            String sql = "select * from user where password = ?";
            //4.获取SQL语句执行对象并传入带有占位符的sql语句。
            pst = connection.prepareStatement(sql);
            //5. 对占位符进行赋值。
            pst.setString(1,"123456");
            //6.执行SQL语句，执行组装好的SQL语句。
            rs = pst.executeQuery();
            //7.对执行SQL的结果进行处理
            while(rs.next()) {
                String user = rs.getString("user");
                String pas = rs.getString("password");
                System.out.println("用户名：" + user + "    密码：" + pas);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            //8.释放连接对象，和数据库分手。
            //关闭结果集对象
            //释放前应该判断对象是否为空，不为空再执行关闭操作，避免空指针异常
            if(rs != null){
                try{
                    rs.close();

                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            //关闭数据库操作对象
            if(pst != null){
                try{
                    pst.close();

                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            //关闭连接对象
            if(connection != null){
                try{
                    connection.close();

                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

        }
    }
}
```



---

### 4.2. 优化JDBC代码冗余，设计简单的 JDBC 工具类

在使用数据库操作对象 PrepareStatement 对象后，我们操作数据库的步骤一共有八步，分别是：

1. 注册驱动。
2. 获取连接对象。
3. 编写带有占位符 ”?“ 的SQL语句。
4. 获取SQL语句执行对象并传入带有占位符的SQL语句。
5. 对SQL语句中的占位符进行赋值。
6. 执行SQL语句。
7. 对执行SQL的结果进行处理。
8. 释放资源。

而我们每个步骤都需要编写代码完成，再加上异常处理的代码，代码量就比较感人了，我们的程序可读性就比较差了。这样显然是不够简洁的。

但是我们仔细观察这些步骤的相关的代码后，可以发现其实其中有许多步骤使用的代码逻辑都是重复的，也就是可复用的。如 *注册驱动*，*获取连接对象*，还有最后的*资源释放*。只有SQL语句的包装与执行在各个程序中都是不一样的。

对于这种可以复用的代码，我们一般都使用工具类来对其进行包装。那么，我们就来实现一下这个工具类 `JDBCUtils`；

不要要注意的一点是对于工具类来说，我们应该让其使用起来方便调用，所以我们定义的方法都应该是静态的，这样我们就可以直接通过类名调用。

下面我们依次分析我们功能的具体实现：

#### f1. 注册驱动

一般来说，在一个程序的运行过程之中我们只需注册一次驱动即可，如何让在其注册一次或者说注册的代码只运行一次呢？

优秀的人肯定已经想起来了，我们可以使用静态代码块，在静态代码块中的内容只会在类被加载时执行且仅执行一次。

于是我们可以在 JDBCUtils 工具类中添加代码：

```java
	/**
     *静态代码块，在 JDBCUtils 仅在类加载时调用一次
     */
    static {
        try {
            //注册驱动
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
```

 OK ,注册驱动步骤结束。

#### f2. 获取连接对象

##### 1. 使用成员变量

在获取连接对象时，我们使用：

```java
DriverManager.getConnection(String url, String user, String password);
```

该方法需要我们传入三个参数，而我们既然使用了工具类，显然每次都要输入参数是极度不方便的。

我们可以先将这些参数在工具类 `JDBCUtils` 中定义为成员变量并赋好值，然后设计一个无参方法只要返回使用 `DriverManager` 获取到的连接对象就可以了。

在 `JDBCUtils` 中添加代码：

```java
	private static String URL = "jdbc:mysql://localhost:3306/hk1";
    private static String USER = "你的用户名，一般为 root";
    private static String PASSWORD = "你的用户名密码";
    
    /**
     * 返回获取的连接对象
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL,USER,PASSWORD);
    }
```

经过这样处理，我们只要使用 `JDBCUtils.geConnection()`就可直接得到连接对象，看起来是不是方便了许多？

但是这样还是有一个问题，当我们需要连接另一个数据库，或者说我的数据密码修改过时，此时的参数url,user,password 可能不一样，我们就要去修改 `JDBCUtils`中成员变量的值，而每次使用都要去修改程序中代码明显是不方便的，这和我们工具类的设计理念不合。

##### 2. 使用配置文件Properties

我们在平时使用软件时会使用一种叫做 **配置文件** 的东西，很多软件都通过修改配置文件来实现不同的设置效果。

相对于修改代码，修改配置文件更简单也更安全。

我们可以将参数都放到配置文件中，然后读取配置文件就可获得我们需要的值。

在 Java 中，配置文件都是 .properties 格式的文件，里面的数据是以键值对作为参数配置的。同时 Java 也提供了一个 **Properties类** 负责对  .properties 格式的文件进行处理。

为了我们的工具类具有更强的适应性，应该使用配置文件来控制和修改参数，下面我们修改为配置文件模式。

首先我们在 src 目录下新建一个 jdbc.properties 文件用作存储我们的数值，并在里面添加以下内容：

```java
url=jdbc:mysql://localhost:3306/hk1
user=你的用户名，一般为 root
password=你的用户名密码
driver=com.mysql.jdbc.Driver
```

然后在 `JDBCUtils`中将url，user，password的默认值都删除，并修改staic代码块中代码：

```java
static {
        try {
            //获取配置文件信息
            Properties pro = new Properties();
            //获取文件输入流
            InputStream in = JDBCUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
            //加载文件
            pro.load(in);
            URL = pro.getProperty("url");
            USER = pro.getProperty("user");
            PASSWORD = pro.getProperty("password");
            //注册驱动
            Class.forName("com.mysql.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
```

OK ，获取连接对象步骤结束。



#### f3. 关闭资源处理

解决完上面两个功能之后，我们来处理最后一个功能，如何关闭资源。

对于每次数据库连接操作来说，首先一定需要连接对象`Connection`以及SQL语句执行对象`PrepareStatement`；

而在执行查询DQL（select)操作时还需要结果集对象 ResultSet 对象；这样一来，有的时候就只需要关闭两个对象，有的时候则需要关闭三个对象。

而我们方法的设计肯定是一个功能一个方法，为了解决这个问题，我们可以使用方法重载来解决这个问题，明白了这点之后，我们在 `JDBCUtils`添加代码：

```java
/**
     * 关闭资源，关闭三个对象
     * @param rs
     * @param pst
     * @param conn
     */
    public static void close(ResultSet rs, PreparedStatement pst,Connection conn){
        //关闭结果集对象
        //释放前应该判断对象是否为空，不为空再执行关闭操作，避免空指针异常
        if(rs != null){
            try{
                rs.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        //关闭数据库操作对象
        if(pst != null){
            try{
                pst.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        
        //关闭连接对象
        if(conn != null){
            try{
                conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭资源，关闭两个对象
     * @param pst
     * @param conn
     */
    public static void close(PreparedStatement pst,Connection conn){
        //调用三参的方法，将第一个参数设为null，间接关闭两个对象
        close(null,pst,conn);
    }
```



#### 工具类功能合并及测试

通过上面的步骤，我们的一个 JDBC工具类就设计完成了，整合后的代码为：

```java
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author qianfanguojin
 * 简单的JDBC工具类
 */
public class JDBCUtils {
    private static String URL;
    private static String USER;
    private static String PASSWORD;
    /**
     *静态代码块，在 JDBCUtils 仅在类加载时调用一次
     */
    static {
        try {
            //处理properties配置文件的对象
            Properties pro = new Properties();
            //获取文件输入流
            InputStream in = JDBCUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
            //加载文件
            pro.load(in);
            //将获取到数值赋给相应的参数
            URL = pro.getProperty("url");
            USER = pro.getProperty("user");
            PASSWORD = pro.getProperty("password");
            //注册驱动
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回获取的连接对象
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL,USER,PASSWORD);
    }

    /**
     * 关闭资源，关闭三个对象
     * @param rs
     * @param pst
     * @param conn
     */
    public static void close(ResultSet rs, PreparedStatement pst,Connection conn){
        //关闭结果集对象
        //释放前应该判断对象是否为空，不为空再执行关闭操作，避免空指针异常
        if(rs != null){
            try{
                rs.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        //关闭数据库操作对象
        if(pst != null){
            try{
                pst.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        //关闭连接对象
        if(conn != null){
            try{
                conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

    }

    /**
     * 关闭资源，关闭两个对象
     * @param pst
     * @param conn
     */
    public static void close(PreparedStatement pst,Connection conn){
        //调用三参的方法，将第一个参数设为null，间接关闭两个对象
        close(null,pst,conn);
    }
}
```



然后我们修改上面的示例，在使用了 `JDBCUtils`工具类后代码为：

```java
    public static void main(String[] args){
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            //1.注册驱动,告诉程序要连接哪个数据库.
            Class.forName("com.mysql.jdbc.Driver");
            //2.获取连接对象，和数据库牵个手，建立联系。
            connection = JDBCUtils.getConnection();
            //3.编写带有占位符 ”?“ 的SQL语句。
            String sql = "select * from user where password = ?";
            //4.获取SQL语句执行对象并传入带有占位符的sql语句。
            pst = connection.prepareStatement(sql);
            //5. 对占位符进行赋值。
            pst.setString(1,"123456");
            //6.执行SQL语句，执行组装好的SQL语句。
            rs = pst.executeQuery();
            //7.对执行SQL的结果进行处理
            while(rs.next()) {
                String user = rs.getString("user");
                String pas = rs.getString("password");
                System.out.println("用户名：" + user + "     密码：" + pas);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            //8.释放连接对象，和数据库分手。
            JDBCUtils.close(rs,pst,connection);
        }
    }
```

通过对比可以看出，刚才的代码为70多行，使用了工具类之后我们就只有30多行了，成功简化代码。

---

### 4.3 使用JDBC控制事务

#### 4.3.1 事务概述

事务：一个包含多个步骤的业务操作。如果这个业务操作被事务管理，那么这个操作要么同时成功，要么同时失败。可以将一个事务看作一次数据库操作中一个或多个SQL语句的执行。

转账操作就是一个明显的事务管理的例子。小明要转账 500 块给小李，这个业务操作需要修改两个数据，首先将小明的账户数据库减去 500 ,然后再将小李的账户数据库增加 500。

假如小明的账户已经减去了500 ，但是当执行小李的账户数据库增加500时出现了问题，程序终止。此时小明500块已经没了，但是小李账户并没有增加500块，这明显时不能接受的；产生这种情况的原因是在默认情况下，JDBC所有的SQL操作都是自动提交的，也就是执行一次SQL语句提交一次，上面的情况就是小明的操作已经提交了，但小李的操作并没有提交成功，于是产生了这种异常。

为了避免这种情况的出现，我们可以引入事务操作，将小明账户减500和小李账户增500的两个操作作为一个事务，这个事务中的数据库操作必须同时执行成功，操作才会被提交，只要有任何一个步骤出错，这两个SQL操作都应被取消。



#### 4.3.2 简单的事务操作

在 JDBC 操作中，我们使用Connection 对象来对事务进行管理。

在上文中介绍 [Connection 对象的功能](#3.3 Connection ) 中也大致介绍了了Connection对象可控制事务操作，包括 **开启事务**、**提交事务**、**回滚事务**。

接下来使用一个转账的示例来演示如何使用事务：

首先我们先查询表中此时两人所有的钱款数：

```sql
mysql> select user,money from user;
+--------+-------+
| user   | money |
+--------+-------+
| 小明   |  1000 |
| 小李   |  1000 |
+--------+-------+
2 rows in set (0.00 sec)
```

编写带事务操作的转账代码：

```java
/**
 * @author qianfanguojin
 * JDBC 事务的简单示例
 */

public class TransactionDemo {



    public static void main(String[] args){
        Connection conn = null;
        PreparedStatement pst1 = null;
        PreparedStatement pst2 = null;
        try {

            //1.注册驱动以及获取数据库连接对象.
            conn = JDBCUtils.getConnection();
            //开启事务
            //默认情况下，所有数据库操作都会被自动提交没执行一条SQL语句就提交一次
            //如果要开启事务就应将其设为false，手动选择提交时间
            conn.setAutoCommit(false);
            //3.编写带有占位符 ”?“ 的SQL语句，并对相应的SQL语句占位符赋值
            //3.1 小明账户减500
            String sql1 = "update user set money = money - ? where user = ?";
            pst1 = conn.prepareStatement(sql1);
            pst1.setInt(1,500);
            pst1.setString(2,"小明");
            //3.2 小李账户加500
            String sql2 = "update user set money = money + ? where user = ?";
            pst2 = conn.prepareStatement(sql2);
            pst2.setInt(1,500);
            pst2.setString(2,"小李");

            //4.执行SQL语句
            int count1 = pst1.executeUpdate();
            int count2 = pst2.executeUpdate();
            System.out.println(count1);
            System.out.println(count2);

            //提交事务
            //代码运行到这里该次业务操作的逻辑不出任何差错，此时提交事务
            conn.commit();

            // 注意这里catch的异常应该要用范围更大的异常类
            // 这样才能在无论代码中出现什么异常的情况下我们都能抓到然后进行事务回滚
        } catch (Exception e) {
            //事务回滚
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            //8.释放连接对象，和数据库分手。
            JDBCUtils.close(pst1,conn);
            JDBCUtils.close(pst2,conn);
        }
    }
```

运行代码，再次查询此时两人的钱款数：

```sql
mysql> select user,money from user;
+--------+-------+
| user   | money |
+--------+-------+
| 小明   |   500 |
| 小李   |  1500 |
+--------+-------+
2 rows in set (0.00 sec)
```

修改成功，但是因为这是没有产生异常情况下的，此时我主动产生一个异常让其在执行第一次SQL操作时产生异常，修改部分代码：

```java
			//4.执行SQL语句
            int count1 = pst1.executeUpdate();
            int a = 3/0;
            int count2 = pst2.executeUpdate();
```

再执行一次程序，程序会抛出异常，此时查询两人的钱款数：

```java
mysql> select user,money from user;
+--------+-------+
| user   | money |
+--------+-------+
| 小明   |   500 |
| 小李   |  1500 |
+--------+-------+
2 rows in set (0.00 sec)
```

可以看到此时两人的钱款数并没有改变，没有出现问题。

但是当我们关闭事务操作时，也就是将这三句代码删除：

```java
conn.setAutoCommit(false);
conn.commit();
conn.rollback();
```

再运行一次程序，查询两人的钱款数：

```java
mysql> select user,money from user;
+--------+-------+
| user   | money |
+--------+-------+
| 小明   |     0 |
| 小李   |  1500 |
+--------+-------+
2 rows in set (0.00 sec)
```

此时出现了异常，小明的钱款已经减去了500，而小李却没有得到500，在程序中执行了第一个SQL操作减500，在执行第二个SQL操作加500前因为出现异常而程序终止。

由上得知，在使用事务后，我们能有效的避免一些程序设计上产生的问题。特别是在执行多个SQL语句时，事务操作尤为重要，所有的SQL语句必须都执行正常这项事务才应该被提交。



#### 4.3.3 事务操作的注意点

- 在该项事务的第一个SQL操作之前开启事务： `conn.setAutoCommit(false);`
- 在该项事务的最后一个SQL操作之后提交事务： `conn.commit();`
- 在异常处理 catch 中进行事务回滚： `conn.rollback();`









