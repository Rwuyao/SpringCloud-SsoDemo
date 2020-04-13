package org.youkong.sso;

public class SingletonClass {
	int i = 0;
    public SingletonClass() {
        System.out.println("SingletonClass被初始化 " + ++i + " 次");
    }
}
