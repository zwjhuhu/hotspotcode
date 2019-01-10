package com.hotspotinaction.demo.chap8.dispatch;

/**
 * 程序示例：演示重载
 * 
 * @author Chen Tao
 * 
 */
public class OverloadDemo {
	public static void main(String[] args) {
		OverloadDemo demo = new OverloadDemo();
		demo.printit(100);
		demo.printit(100, 200);
		demo.printit("This is a string.");
	}

	public void printit(int i) {
		System.out.println("int value is: " + i);
	}

	public int printit(int i, int j) {
		System.out.println("int value is: " + i + " and " + j);
		return 0;
	}

	public void printit(String s) {
		System.out.println("string is: " + s);
	}
}