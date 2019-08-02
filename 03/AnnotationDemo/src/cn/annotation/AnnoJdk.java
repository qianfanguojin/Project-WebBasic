package cn.annotation;


/**
 * 1. `@Override` ：检测该被注解标注的方法是否是继承自父类（接口）的。
 * 2. `@Deprecated`：被该注解标注的内容，表示已经过时。
 * 3. `@SuppressWarning`：压制（忽略）警告。
 */
@SuppressWarnings("all")
public class AnnoJdk {

    /**
     * /@Override注解是对代码的一种严谨性检查
     * 在方法上声明该注解能够确保方法是重写自父类的
     */

    //声明了Override注解后，该方法必须在父类中存在
    //所有的类都继承了Object类，toString方法在Object类中，这里不报错
    @Override
    public String toString() {
        return super.toString();
    }

    //这里会报错
    //声明了@Oerride注解后，该方法必须在父类能够找到，toString1方法在父类中不存在
    //@Override
    public String toString1(){
        return "1";
    }


    /**
     * /@Deprecated注解对方法进行过时标记
     * 被该注解标识的方法在使用时会提示过时，在ide中该方法被画了一条线
     */
    @Deprecated
    public void add(){
        //旧的方法
    }

    public void addnew(){
        //新的方法
        add();
    }


}
