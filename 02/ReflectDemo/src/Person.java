public class Person {
    private int age;
    private String name;

    /**
     * 用作测试Filed类的使用
     */
    public int height = 180;
    public int weight = 120;
    protected String sex = "male";
    String hobby = "篮球";

    /**
     * 私有构造方法
     * 用作测试Constructor类使用
     * @param name
     */
    private Person(String name) {
        this.name = name;
    }



    public Person () {

    }

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", height=" + height +
                ", sex='" + sex + '\'' +
                ", hobby='" + hobby + '\'' +
                '}';
    }

    /**
     * 用作测试Method类的使用
     */
    public void show(String s){
        System.out.println(s);
        System.out.println("运行 public show");
    }

    private void display(){
        System.out.println("运行 private display");
    }

}
