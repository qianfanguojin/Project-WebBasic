package cn.annotation;


import java.lang.annotation.*;

@AnnoElement(a=1,b=2)
//注解作用于类、方法、成员变量上。
@Target({ElementType.TYPE,ElementType.METHOD,ElementType.FIELD})
public @interface AnnoBasic {
    public abstract String name();
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
//    AnnoBasic h() default @AnnoBasic;

    //数组
    int[] i() default 0;


}

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
        //获得子类的Class对象
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

/**
 * 定义注解可以在java文档中出现
 */
@Documented
@interface DO{

}