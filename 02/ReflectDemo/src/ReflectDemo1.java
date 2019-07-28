import java.sql.SQLOutput;

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

        //判断三个对象是否相等
        System.out.println(cla1 == cla2);
        System.out.println(cla1 == cla3);
        System.out.println(cla2 == cla3);


    }
}
