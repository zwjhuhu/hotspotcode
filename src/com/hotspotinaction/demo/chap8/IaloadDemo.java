package com.hotspotinaction.demo.chap8;

/**
 * 程序示例：数组越界检查
 * 
 * @author Chen Tao
 * 
 */
public class IaloadDemo {

	public static void main(String[] args) {
		int[] src = {10, 20, 30, 40, 50};
		int dst = src[10]; // 数组访问，越界检查
	}
}
/**
 * Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: 10 at
 * com.hotspotinaction.demo.chap8.IaloadDemo.main(IaloadDemo.java:7)
 */
