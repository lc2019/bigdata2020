package day04_mxdx;

public class dongtaibangding {
    public static void main(String[] args) {
        A a = new B();
        /**
         * 动态绑定机制
         * 成员方法在执行的过程中，jvm会将方法与当前调用的对象实际内存进行绑定
         * 属性没有动态绑定机制，在哪里声明就在哪里使用
         */
//        System.out.println(a.getRes()); //20=10+10 调用的是父类的方法
        //还是查看b内存的，绑定getI 然后在进行返回
        System.out.println(a.getRes()); //30
    }
}

class A {
    public int i = 10;

    public int getRes() {
        return getI() + 10;
    }

    public int getI() {
        return i;
    }
}

class B extends A {
    public int i = 20;

    //    public int getRes() {
//        return i + 20; //实际省略了关键字 this.i
//    }
    public int getI() {
        return i;
    }
}