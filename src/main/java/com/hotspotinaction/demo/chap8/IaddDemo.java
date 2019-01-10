package com.hotspotinaction.demo.chap8;

/**
 * 程序示例：iadd指令
 * 
 * @author Chen Tao
 * 
 */
public class IaddDemo {
	public static int i = 111;
	public static int j = 222;

	public static void main(String args[]) {
		System.out.println(iaddIns(37));
		System.out.println("i=" + i + ",j=" + j);
		swap();
		System.out.println("i=" + i + ",j=" + j);
	}

	public static int iaddIns(int i) {
		return i + 1;
	}

	public static void swap() {
		int temp;
		temp = i;
		i = j;
		j = temp;
	}
}
