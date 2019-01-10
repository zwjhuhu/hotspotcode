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
		ConstantPoolTestCase cptc = new ConstantPoolTestCase();
		System.out.println(cptc.a1a1a1);
		System.out.println(cptc.b2b1b1);
		System.out.println(cptc.c1c1c1);
	}

}
