package cn.annotation;


/**
 * @author qianfanguojin
 * 计算器类
 */
public class Calculator {

    //减法
    @AnnoCheck
    public void add () {
        System.out.println("1 + 0 =" + (1 + 0));
    }

    //减法
    @AnnoCheck
    public void sub() {
        System.out.println("1 - 0 =" + (1 - 0));
    }

    //乘法
    @AnnoCheck
    public void mul() {
        System.out.println("1 * 0 =" + (1 * 0));
    }
    //除法
    @AnnoCheck
    public void div() {
        System.out.println("1 / 0 =" + (1 / 0));
    }

    public void showSuccess() {
        System.out.println("啊哈哈，没有BUG");
    }

}
