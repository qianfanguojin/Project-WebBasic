## 一. 数据库连接池

### 1. 什么是数据库连接池

百科定义：数据库连接池负责分配、管理和释放数据库连接，它允许应用程序重复使用一个现有的数据库连接，而不是再重新建立一个；释放空闲时间超过最大空闲时间的数据库连接来避免因为没有释放数据库连接而引起的数据库连接遗漏。这项技术能明显提高对数据库操作的性能。

个人理解：每次数据库连接都要申请一个连接对象，这项操作要经过系统底层，相对来说是非常耗时的。我们可以在程序开始时初始化多个数据库连接对象，并将其保留在一个容器（池子）中，该池子中的连接对象可以重复被使用，使用时直接向该容器获取连接对象，使用完后再将连接对象归还给该容器，这个容器（池子）称为数据库连接池。



### 2. 为什么使用数据库连接池

#### 2.1 传统的数据库连接方式

每一个获取数据库连接对象的操作都需要从系统底层获取资源，申请资源是一件极度耗时的工作。

而每一次使用完这个数据库连接之后，我们又直接将资源释放给系统底层，下次使用还是要重新向系统申请资源。

这样来回反复，会浪费大量的时间以及系统资源。并且，当同时连接数据库的次数数量很大时，此时系统资源和时间都被消耗在数据库连接上，我们想要得到的数据库中的数据的时间就被延迟，这是非常影响用户的体验的。又因为每一次我们使用完数据库连接就要完全释放该连接，在大量的数据库连接中，程序如果出现异常，此时资源并没有完全释放，大量的连接资源没有及时释放，容易发生内存泄漏。

在上述模式下，数据库连接如下图：

![](/JDBCAdvanced/01.png)

#### 2.2 引入数据库连接池后的数据库连接模式

在上面的分析中我们得知，程序在获取数据库连接时这段操作其实是非常耗时的，而一般的程序运行的速度都很快，如果让程序运行的时间大部分花在等待数据库的连接操作中，这样的程序效率是很低的。

那么有没有办法解决呢？我们可以思考一下，其实解决这个问题的关键点就在于两点：

1. 如何解决请求连接对象速度的问题，
2. 如何有效管理大量的连接。

我们在学操作系统的时候，许多时候都提到了**缓存**这个技术，缓存的出现就是为了解决速度不匹配的问题。那么在我们这里我们可以这样想，在程序需要请求数据库连接之前我们就把连接对象创建好，此时的连接对象在内存中已经初始化好了，程序只要直接拿就是了，获取连接对象的速度就会快很多。第一个问题解决。

但是当产生大量的连接请求时，此时我们需要初始化多个连接对象，这些连接对象的创建服务于一次请求，用完又被释放掉了，这样会产生大量的资源浪费，而且如果程序出现异常，多个连接对象没有被完全释放，容易造成内存泄漏，这是不可取的。

而在程序设计中，我们有一个知识叫做 **复用性** ,将一样的东西放在一起，然后不同的地方都可以使用。利用这个知识，我们可以引入 **容器（池子）** ，将一定数量建立好的连接对象放入容器中，每次连接都向该容器获取，用完又放回该容器，这样一个对象就可以在不同的时候服务不同的连接；同时，在处理多个连接时，我们可以通过控制容器中最大连接对象的数量，避免无穷无尽的连接请求；通过对容器资源的释放，又可将其中的连接对象资源同时释放。

更关键的是我们可以在容器中加入相应的管理模块和监视模块，可以对其中的连接对象进行管理，方便开发人员进行测试以及优化。

为了实现上述这些想法和操作，人们提出了一个技术：**==数据库连接池==**，其目的就是用来解决和实现我们刚才讨论的这些问题。

在引入数据库连接池后，此时的数据库连接如图：

![](/JDBCAdvanced/02.png)

**注意一点**：数据库连接池的实现是在客户端实现的，而不是在服务端的数据库的中实现，在客户端对连接对象进行请求和管理。在数据库端对应也有一个叫做线程池的东西，和连接池的效果都差不多，当然，这都是后话了。

#### 2.3 实现一个简单的数据库连接池

说了这么多，我们还是来实践一下把，我们接下来就来手动实现一个简单的连接池。

首先我们要确定我们实现的目标：

1. 一次性创建多个连接对象
2. 新增连接对象
3. 回收连接对象

简单实现的代码：

```java
/**
 * @author qianfanguojin
 * 自己简单设计的一个数据库连接池
 * 须实现DataSource接口
 */
public class MyDataSource implements DataSource {

        //使用链表来存放多个Connection对象
        private LinkedList<Connection> connectionLinkedList = new LinkedList<Connection>();
    
        /**
         * 初始化多个连接对象
         */
        public MyDataSource(){
            //初始化十个连接对象，并将其放到链表中
            for (int i = 0 ; i < 10 ; i++ ) {
                try {
                    //注册驱动
                    Class.forName("com.mysql.jdbc.Driver");
                    //获取连接对象
                    Connection connection = DriverManager.
                            getConnection("jdbc:mysql://localhost:3306/hk1", "root", "987075");
                    //将连接对象放到链表（池子）中
                    connectionLinkedList.add(connection);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


        }


        /**
         * 获得连接池中的一个连接对象
         * @return
         * @throws SQLException
         */
        @Override
        public Connection getConnection() throws SQLException {
            //返回第一个连接对象，并将该对象从链表中去除
            return connectionLinkedList.removeFirst();
        }

        /**
         *将使用完的连接对象回收进连接池
         * @param conn
         */
        public void backConnection(Connection conn) {
                connectionLinkedList.add(conn);
        }
```

测试代码如下：

```java
public class MyDataSourceTest {
		//Junit 单元测试
    @Test
    public void test_getConnection(){

        //1.初始化一个连接池对象
        MyDataSource dataSource = new MyDataSource();

        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            //2.获取连接池中的Connection对象
            conn = dataSource.getConnection();
            //3.编写带有占位符 ”?“ 的SQL语句。
            String sql = "select * from user where password = ?";
            //4.获取SQL语句执行对象并传入带有占位符的sql语句。
            pst = conn.prepareStatement(sql);
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
        }finally {
            dataSource.backConnection(conn);
        }
    }

```



### 3. 开发中常用的数据连接池

在上面的示例中，我们自己实现了一个数据库连接池，实现了简单的一些功能。但是在实际开发中，对数据库连接池的功能要求更全面，一般来说，数据库厂商会在驱动包中集成实现了 `DataSource`接口的数据库连接池，而在开源平台中，有许多优秀的数据库连接池实现，如：

- [c3p0](https://github.com/swaldman/c3p0)： 优秀的开源数据库连接池包，但是年代比较老了。
- Druid(德鲁伊)： 阿里开源的数据库连接池包，支付宝和淘宝也在使用，后起之秀。

接下来我们就学习一下他们的使用方式。

#### 3.1 使用C3P0

官方文档地址：https://www.mchange.com/projects/c3p0/#quickstart 我们可以从中得到使用信息。

##### step1. 导入相关 jar 包

不管是使用什么开源库，导包都是第一步。查看c3p0的官方文档，有这么一句：

> c3p0 was designed to be butt-simple to use. Just put the files `lib/c3p0-0.9.5.2.jar` and `lib/mchange-commons-java-0.2.15.jar` in your application's effective `CLASSPATH`

大致意思就是 c3p0 使用很方便，将`c3p0-0.9.5.2.jar` 和`mchange-commons-java-0.2.15.jar`放到应用的环境变量中即可。

这里就告诉我们需要导入两个包到我们的项目中，这两个包的下载地址分别为：

[c3p0-0.9.5.2.jar](https://mvnrepository.com/artifact/com.mchange/c3p0/0.9.5.2)

[mchange-commons-java-0.2.15.jar](https://mvnrepository.com/artifact/com.mchange/mchange-commons-java/0.2.15)

然后我们在我们的 src 同级目录新建一个 libs 目录用于存放所有的 jar包 并将上面的两个 jar 包复制进来，此时目录结构如下：

![](/JDBCAdvanced/03.png)

然后右键点击`c3p0-0.9.5.2.jar`文件，在弹出的菜单栏中靠下部分点击 `Add as Library` ,然后在弹出的弹窗中点击 OK ,就可将其导入项目的 `ClassPath`中。

相应的，对 `mchange-commons-java-0.2.15.jar`也执行相同的步骤。操作完成后，我们可以展开该包查看其中的信息，说明导入成功：

![](/JDBCAdvanced/04.png)

当然数据库连接驱动就不用多说了，一定也要导入的，这里我就不再细述了。

##### step2. 定义配置文件

使用c3p0 有两种方式，一种是硬编码，也就是配置都写在代码里，如官方文档中示例：

```java
ComboPooledDataSource cpds = new ComboPooledDataSource();
cpds.setDriverClass( "org.postgresql.Driver" ); //loads the jdbc driver            
cpds.setJdbcUrl( "jdbc:postgresql://localhost/testdb" );
cpds.setUser("dbuser");                                  
cpds.setPassword("dbpassword");  
```

但是很明显，这样的写法有许多缺点。一是代码冗余性多，二是将来代码维护的时候非常麻烦，所以我们更推荐使用配置文件的模式。

查看官方文档，再配置文件栏可以找到这句：

> Configuration files are normally looked up under standard names (`c3p0.properties` or `c3p0-config.xml`) at the top level of an application's classpath, but the XML configuration can be placed anywhere in an application's file system or classpath, if the system property [`com.mchange.v2.c3p0.cfg.xml`](https://www.mchange.com/projects/c3p0/#locating_configuration_information) is set.

大致意思就是：通常来说配置文件的名字都应设为`c3p0.properties`或者`c3p0-config.xml`，而且该文件的位置应该在 classpath （类路径）目录，也就是 src 目录下。如果`com.mchange.v2.c3p0.cfg.xml` 指定了XML位置的话，XML文件也可以放在任意目录下。

目前我们从简开始，我们直接在 src 目录下新建`c3p0-config.xml`文件，添加如下内容：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--c3p0配置信息-->
<c3p0-config>
<!--    默认配置-->
    <default-config>
        <!--连接参数-->
        <property name="driverClass">com.mysql.jdbc.Driver</property>
        <property name="jdbcUrl">jdbc:mysql://localhost:3306/hk1</property>
        <property name="user">root</property>
        <property name="password">987075</property>

        <!--初始化连接池中申请的连接对象数量-->
        <property name="initialPoolSize">10</property>
        <property name="maxIdleTime">30</property>
        <!-- 连接池中最大的连接数量-->
        <property name="maxPoolSize">100</property>
        <!-- 连接池中最小的连接数量-->
        <property name="minPoolSize">10</property>
        <!-- 超时时间(ms)-->
        <property name="checkoutTimeout">3000</property>
    </default-config>


    <!--附加配置，配置不同的数据库厂商 -->
    <named-config name="mySource">
        <property name="driverClass">com.mysql.jdbc.Driver</property>
        <property name="jdbcUrl">jdbc:mysql://localhost:3306/bookstore</property>
        <property name="user">root</property>
        <property name="password">xxxx</property>

        <property name="initialPoolSize">10</property>
        <property name="maxIdleTime">30</property>
        <property name="maxPoolSize">100</property>
        <property name="minPoolSize">10</property>
    </named-config>
</c3p0-config>
```



##### step3. 使用示例 

添加代码：

```java
**
 * C3P0 的简单示例
 */
public class C3P0Demo {
    public static void main(String[] args) {
        //1.创建数据库连接池对象
        DataSource ds = new ComboPooledDataSource();
        //2.获取连接对象
        try {
            Connection conn = ds.getConnection();
            System.out.println(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

输出结果：

```java
com.mchange.v2.c3p0.impl.NewProxyConnection@5fe5c6f [wrapping: com.mysql.jdbc.JDBC4Connection@6979e8cb]
```

**注意：**

> 在不传参情况下，`new ComboPooledDataSource()` 创建对象使用默认构造函数，此时使用的配置为配置文件中的 `default-config` ，但我们也可以在创建对象时传入参数指定配置：`new ComboPooledDataSource("mySource")` 则指定配置文件中名为`mySource` 的配置。



#### 3.2 使用Druid

官方文档：[https://github.com/alibaba/druid/wiki/%E9%A6%96%E9%A1%B5](https://github.com/alibaba/druid/wiki/首页)

以后我们都推荐使用Druid来开发，接下来介绍Druid的使用。


##### step1. 导入 jar 包

导包的操作和上面C3P0的方式一样，这里不再细述，这里给出Druid jar 包下载地址：

[druid-1.1.10.jar](https://mvnrepository.com/artifact/com.alibaba/druid/1.1.10)

##### step2. 使用配置文件

在Druid官方文档的说明中， DataSource可配置的属性列表和说明如下：

| 配置                                      | 缺省值               | 说明                                                         |
| ----------------------------------------- | -------------------- | ------------------------------------------------------------ |
| name                                      |                      | 配置这个属性的意义在于，如果存在多个数据源，监控的时候可以通过名字来区分开来。如果没有配置，将会生成一个名字，格式是："DataSource-" + System.identityHashCode(this). 另外配置此属性至少在1.0.5版本中是不起作用的，强行设置name会出错。[详情-点此处](http://blog.csdn.net/lanmo555/article/details/41248763)。 |
| url                                       |                      | 连接数据库的url，不同数据库不一样。例如：<br />mysql : jdbc:mysql://10.20.153.104:3306/druid2<br /> oracle : jdbc:oracle:thin:@10.20.149.85:1521:ocnauto |
| username                                  |                      | 连接数据库的用户名                                           |
| password                                  |                      | 连接数据库的密码。如果你不希望密码直接写在配置文件中，可以使用ConfigFilter。[详细看这里](https://github.com/alibaba/druid/wiki/%E4%BD%BF%E7%94%A8ConfigFilter) |
| driverClassName                           | 根据url自动识别      | 这一项可配可不配，如果不配置druid会根据url自动识别dbType，然后选择相应的driverClassName |
| initialSize                               | 0                    | 初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时 |
| maxActive                                 | 8                    | 最大连接池数量                                               |
| maxIdle                                   | 8                    | 已经不再使用，配置了也没效果                                 |
| minIdle                                   |                      | 最小连接池数量                                               |
| maxWait                                   |                      | 获取连接时最大等待时间，单位毫秒。配置了maxWait之后，缺省启用公平锁，并发效率会有所下降，如果需要可以通过配置useUnfairLock属性为true使用非公平锁。 |
| poolPreparedStatements                    | false                | 是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭。 |
| maxPoolPreparedStatementPerConnectionSize | -1                   | 要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true。在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置大一些，比如说100 |
| validationQuery                           |                      | 用来检测连接是否有效的sql，要求是一个查询语句，常用select 'x'。如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会起作用。 |
| validationQueryTimeout                    |                      | 单位：秒，检测连接是否有效的超时时间。底层调用jdbc Statement对象的void setQueryTimeout(int seconds)方法 |
| testOnBorrow                              | true                 | 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。 |
| testOnReturn                              | false                | 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。 |
| testWhileIdle                             | false                | 建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。 |
| keepAlive                                 | false<br/>（1.0.28） | 连接池中的minIdle数量以内的连接，空闲时间超过minEvictableIdleTimeMillis，则会执行keepAlive操作。 |
| timeBetweenEvictionRunsMillis             | 1分钟（1.0.14）      | 有两个含义：<br />1) Destroy线程会检测连接的间隔时间，如果连接空闲时间大于等于minEvictableIdleTimeMillis则关闭物理连接。<br />2) testWhileIdle的判断依据，详细看testWhileIdle属性的说明 |
| numTestsPerEvictionRun                    | 30分钟（1.0.14）     | 不再使用，一个DruidDataSource只支持一个EvictionRun           |
| minEvictableIdleTimeMillis                |                      | 连接保持空闲而不被驱逐的最小时间                             |
| connectionInitSqls                        |                      | 物理连接初始化的时候执行的sql                                |
| exceptionSorter                           | 根据dbType自动识别   | 当数据库抛出一些不可恢复的异常时，抛弃连接                   |
| filters                                   |                      | 属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有：<br />监控统计用的filter:stat<br />日志用的filter:log4j<br />防御sql注入的filter:wall |
| proxyFilters                              |                      | 类型是List<com.alibaba.druid.filter.Filter>，如果同时配置了filters和proxyFilters，是组合关系，并非替换关系 |

与上面的 C3P0 一样，这些属性也可以硬编码式的直接在代码里通过不同的 setXX() 方法设置，但不推荐，我们一般使用配置文件，在这里，我们需要使用 Java配置文件格式 properties 来配置相应的属性。

首先依旧是在 src 目录（可以任意目录下）下新建文件名为 druid.properties 的File文件，在里面填入一些必要的配置属性即可： 

```properties
# 驱动位置
driverClassName=com.mysql.jdbc.Driver
# 连接路径
url=jdbc:mysql://localhost:3306/hk1
# 数据库用户名
username=root
# 数据库密码
password=987075
# 初始化时连接池连接对象的数量
initialSize=5
# 连接池最大连接数量
maxActive=10
```



##### step3. 使用示例

一个简单使用示例如下：

```java
/**
 * Druid 数据库连接池的简单示例
 */
public class DruidDemo {

    public static void main(String[] args) throws Exception {

        //1.获取配置文件
        Properties pro = new Properties();
        InputStream in = DruidDemo.class.getClassLoader().getResourceAsStream("druid.properties");
        pro.load(in);
        System.out.println(pro);
        //2.创建数据库连接池对象
        //需要传入properties对象获取其中的配置信息
        DataSource ds = DruidDataSourceFactory.createDataSource(pro);

        //3.获取连接对象
        Connection conn = ds.getConnection();
        System.out.println(conn);
      	//4.将连接对象放回连接池
        //使用了连接池时,close方法功能为连接对象退回连接池
        conn.close();
    }
}

```

输出结果为：

```
{maxActive=10, password=987075, url=jdbc:mysql://localhost:3306/hk1, driverClassName=com.mysql.jdbc.Driver, initialSize=5, username=root}
信息: {dataSource-1} inited
com.mysql.jdbc.JDBC4Connection@42f93a98
```

成功获取到了连接对象。



## 二、设计结合了数据库连接池的工具类JDBCUtils

前面我们设计了基本的 JDBCUtils 工具类，其可以帮我们简化代码，增加加代码的复用性...。在引入数据库连接池后，我们再来梳理以下我们工具类的功能：

- 初始化数据库连接池
- 获取连接对象
- 关闭连接，并将连接对象退回连接池

接下来就是各个功能的实现：

### f1. 初始化数据库连接池

一般来说，初始化的操作都只要执行一次而且是最早执行越好。于是我们初始化的代码可以放在 `static` 代码块中，具体的实现如下：


```java

		//数据库连接池引用
    private static DataSource dataSource;
		/**
     * 静态代码块，负责连接池的初始化操作
     */
    static {
        Properties pro = null;
        InputStream in = null;
        try {
            //1.加载配置文件，从中获取连接池的属性配置
            pro = new Properties();
            in = JDBCUtils.class.getClassLoader()
                    .getResourceAsStream("druid.properties");
            pro.load(in);

            //2.建立Druid数据库连接池并指定配置
            dataSource = DruidDataSourceFactory.createDataSource(pro);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
```



### f2. 获取连接对象

```java
/**
     * 获取连接池
     * @return 连接池的引用
     */
    public static DataSource getDataSource() {
        return dataSource;
    }
```

附加一个获取连接池的方法：

```java
		/**
     * 获取连接池
     * @return 连接池的引用
     */
    public static DataSource getDataSource() {
        return dataSource;
    }
```



### f3. 关闭连接，并将连接对象退回连接池

```java
		/**
     * 关闭资源，连接对象会被归还进连接池
     * @param rs
     * @param pst
     * @param conn
     */
    public static void close(ResultSet rs, PreparedStatement pst, Connection conn){
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

        //关闭本次连接，连接对象退回连接池
        if(conn != null){
            try{
                conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }


    /**
     * 关闭资源，连接对象会被归还进连接池
     * @param pst
     * @param conn
     */
    public static void close(PreparedStatement pst,Connection conn){
        //调用三参的方法，将第一个参数设为null，间接关闭两个对象
        close(null,pst,conn);
    }
```



### 工具类功能合并及测试

通过上面的步骤，我们的一个 JDBC工具类就设计完成了，整合后的代码为：

```java
/**
 * @author qianfanguojin
 * 引入Druid数据库连接池后的JDBC工具类
 */
public class JDBCUtils {
    //数据库连接池引用
    private static DataSource dataSource;

    /**
     * 静态代码块，负责连接池的初始化操作
     */
    static {
        Properties pro = null;
        InputStream in = null;
        try {
            //1.加载配置文件，从中获取连接池的属性配置
            pro = new Properties();
            in = JDBCUtils.class.getClassLoader()
                    .getResourceAsStream("druid.properties");
            pro.load(in);

            //2.建立Druid数据库连接池并指定配置
            dataSource = DruidDataSourceFactory.createDataSource(pro);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取连接池
     * @return 连接池的引用
     */
    public static DataSource getDataSource() {
        return dataSource;
    }


    /**
     * 从连接池中获取一个连接
     * @return 连接池中的一个连接
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }


    /**
     * 关闭资源，连接对象会被归还进连接池
     * @param rs
     * @param pst
     * @param conn
     */
    public static void close(ResultSet rs, PreparedStatement pst, Connection conn){
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

        //关闭本次连接，连接对象退回连接池
        if(conn != null){
            try{
                conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }


    /**
     * 关闭资源，连接对象会被归还进连接池
     * @param pst
     * @param conn
     */
    public static void close(PreparedStatement pst,Connection conn){
        //调用三参的方法，将第一个参数设为null，间接关闭两个对象
        close(null,pst,conn);
    }

}
```

然后我们使用一个小示例来测试：

```java
/**
 * @author qianfanguojin
 * 测试工具类的使用
 */
public class JDBCUtilsDemo {

    public static void main(String[] args) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pst = null;

        try {
            //1. 从连接池获得连接对象
            conn = JDBCUtils.getConnection();
            //2.构建带占位符的SQL语句
            String sql = "select * from user where password = ?";
            //4.获取SQL语句执行对象并传入带有占位符的sql语句。
            pst = conn.prepareStatement(sql);
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
        }finally {
            //8.关闭资源
            JDBCUtils.close(rs,pst,conn);
        }
    }


}
```

输出结果：

```java
用户名：小明    密码：123456
用户名：小李    密码：123456
```



OK ，本节到这里结束。

## 参考

> [**谈谈数据库连接池的原理**](https://blog.csdn.net/shuaihj/article/details/14223015)