package jnnit;

import junit.Calculator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class CalculatorTest {
    /**
     * 在所有测试方法之前执行
     * 一般用于数据和资源的初始化
     */
    @Before
    public void beforeMethod(){
        System.out.println("Before");
    }

    /**
     * 在所有测试方法之后执行
     * 一般用于数据和资源的释放
     */
    @After
    public void afterMethod() {
        System.out.println("After");
    }


    /**
     * 测试add方法
     */
    @Test
    public void testAdd(){
        //1.创建计算器对象
        Calculator ca = new Calculator();
        //2.调用add方法
        int result = ca.add(1,2);
        System.out.println(result);
        //3.使用断言
        Assert.assertEquals(3,result);
    }

}
