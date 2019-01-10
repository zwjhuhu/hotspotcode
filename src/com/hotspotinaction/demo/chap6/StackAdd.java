package com.hotspotinaction.demo.chap6;

/**
 * 程序示例：演示栈操作
 * 
 * @author Chen Tao
 * 
 */
public class StackAdd {

	public int baseValue = 4095; // 0x0000000000000fff

	public static void main(String[] args) {
		StackAdd adder = new StackAdd();
		int i = 123; // 0x7b
		int j = 456; // 0x1c8
		int r = adder.add(i, j);
		System.out.println(r);
	}

	public int add(int i, int j) {
		int temp = i + j;
		temp += baseValue;
		return temp;
	}

}
