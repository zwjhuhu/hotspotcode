package com.hotspotinaction.demo.chap1;

/**
 * 程序示例：数值字面量增强
 * 
 * @author Chen Tao
 * 
 */
public class Literals {

	public static void main(String[] args) {
		Literals tool = new Literals();
		System.out.println(tool.getBinaryInt("0100"));
		System.out.println(tool.getBinaryIntLiterals());
	}

	/**
	 * 用下划线表示数值字面量
	 */
	public void underscoresNumericLiterals() {
		long a = 10_000_000L;
		int b = 0b1100_1000_0011_0000;
		System.out.println(a);
		System.out.println(b);
	}

	/**
	 * befor Java 7
	 * 
	 * @param number
	 * @return
	 */
	public int getBinaryInt(String number) {
		int a = -1;
		try {
			a = Integer.parseInt(number, 2);
		} catch (NumberFormatException e) {
			// TODO 异常处理
		}
		return a;
	}

	/**
	 * Java 7: 二进制字面量
	 * 
	 * @return
	 */
	public int getBinaryIntLiterals() {
		int a = 0b0100;
		return a;
	}
}
