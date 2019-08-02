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
    String name;
}

@A1
@A2
public class AnnoGet extends AnnoGetH{

    int age;




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
@Retention(RetentionPolicy.RUNTIME)
@interface AH3{

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

@Retention(RetentionPolicy.RUNTIME)
@interface A3{

}