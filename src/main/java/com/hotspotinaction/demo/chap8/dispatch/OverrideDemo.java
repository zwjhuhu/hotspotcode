package com.hotspotinaction.demo.chap8.dispatch;

/**
 * 程序示例：演示覆盖
 * 
 * @author Chen Tao
 * 
 */
public class OverrideDemo {

	public static void main(String[] args) {
		OverrideDemo demo = new OverrideDemo();
		A a = demo.new A();
		A b = demo.new B();
		A c = demo.new C();

		a.printit();
		b.printit();
		c.printit();
	}

	class A {
		public void printit() {
			System.out.println("AAAA.");
		}
	}

	class B extends A {
		public void printit() {
			System.out.println("BBBB.");
		}
	}

	class C extends A {
		public void printit() {
			System.out.println("CCCC.");
		}
	}
}