import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.URL;

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
