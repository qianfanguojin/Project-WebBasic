import java.lang.reflect.Field;
import java.net.URL;

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
        System.out.println("==========获得相应的对象==========");
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

        System.out.println("==========进阶使用==========");
        System.out.println("----------获取变量的值----------");
        //返回指定对象上Field表示的值
        //会对你访问的字段进行权限修饰符检查。也就是private的不可访问
        //如想访问私有，必须使用setAccessible(true)《暴力反射》来忽略权限修饰符的检查
        //否则抛出IllegalAccessException异常
        System.out.println("height：" + height.get(p));
        System.out.println("sex：" + sex.get(p));
        try{
            //age1为获取到的私有对象
            System.out.println("age：" + age1.get(p));
        }catch (IllegalAccessException iae){
            iae.printStackTrace();
        }
        //暴力反射
        age1.setAccessible(true);
        System.out.println("age：" + age1.get(p));//输出为0，这是因为int类型默认值为0


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
