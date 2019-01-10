package com.hotspotinaction.demo.chap4;

/**
 * 程序示例：探测运行时常量池
 * 
 * @author Chen Tao
 * 
 */
public class ConstantPoolTestCase {
	static final int a1a1a1 = 2;
	static final boolean b2b1b1 = true;
	static final String c1c1c1 = "This is a ConstantPool Test Case.";

	public static void main(String[] args) {
		System.out.println(ConstantPoolTestCase.a1a1a1);
		System.out.println(ConstantPoolTestCase.b2b1b1);
		System.out.println(ConstantPoolTestCase.c1c1c1);
	}

}
