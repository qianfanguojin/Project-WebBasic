package cn.annotation;

import java.lang.reflect.Method;


/**
 * @author qianfanguojin
 * 测试类，对方法测试并生成测试信息
 */
public class CalculatorCheck {

    public static void main(String[] args) throws Exception{
        //获取Calculator类的Class对象
        Class cls = Class.forName("cn.annotation.Calculator");
        //创建一个类实例用于运行方法
        Calculator ca =  new Calculator();
        //计算运行错误的次数
        int num = 0 ;
        //打印结果的字符串
        StringBuilder strb = new StringBuilder();
        //获取该类中的所有方法
        Method[] methods = cls.getMethods();
        for(Method m: methods) {
            //判断该方法上是否有注解，有则是要测试的方法
            if (m.isAnnotationPresent(AnnoCheck.class)) {
                //测试的方法可能会有错误，我们应该捕获异常
                try {
                    m.invoke(ca);
                    strb.append("方法: " + m.getName() + " 运行成功\n");
                }catch (Exception e){
                    num++;
                    strb.append("方法: " + m.getName() + " 运行出现异常\n");
                    strb.append("异常名为：" + e.getCause().getClass().getSimpleName() + "\n");
                    strb.append("异常的原因：" + e.getCause().getMessage() + "\n");

                }

            }
        }
        strb.append("--------------------\n");
        strb.append("发现" + num + "个错误！");
        System.out.println(strb.toString());
    }
}
