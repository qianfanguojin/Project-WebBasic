package cn.annotation;

import java.lang.annotation.*;


//@Role("USER")
//@Role("ADMIN")
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