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
        System.out.println("能够获得Object类的方法对象");
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
        System.out.println("不能获得Object类的方法对象");
        //获取所有已声明的方法 Method 对象、且不考虑权限修饰符（包括private），但不包括继承的方法
        //这里没有打印出来Object类的方法，无法获得继承（父类）的方法对象
        Method[] declaredMethods = pes.getDeclaredMethods();
        for (Method m : declaredMethods) {
            System.out.println(m);
        }


    }
}
