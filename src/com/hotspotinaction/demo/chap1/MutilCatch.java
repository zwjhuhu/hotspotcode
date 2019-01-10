package com.hotspotinaction.demo.chap1;

import java.util.HashMap;
import java.util.Map;

/**
 * 程序示例：异常处理增强
 * 
 * @author Chen Tao
 * 
 */
public class MutilCatch {

	public static void main(String[] args) {
		MutilCatch tool = new MutilCatch();
		System.out.println(tool.mutilCatch("0100"));
	}

	/**
	 * before Java 7
	 * 
	 * @param number
	 * @return
	 */
	public int mutilCatch(String number) {
		int a = -1;
		try {
			a = a / 0;
			a = Integer.parseInt(number, 2);
		} catch (NumberFormatException e) {
			// TODO 异常处理
		} catch (ArithmeticException e) {
			// TODO 异常处理
		}
		return a;
	}

	/**
	 * Java 7
	 * 
	 * @param number
	 * @return
	 */
	public int mutilCatchV7(String number) {
		int a = -1;
		try {
			a = a / 0;
			a = Integer.parseInt(number, 2);
		} catch (NumberFormatException | ArithmeticException e) {
			// TODO 异常处理
		}
		return a;
	}

	/**
	 * 简化范型定义
	 */
	public void simple() {
		Map<String, Map<Integer, String>> map1 = new HashMap<String, Map<Integer, String>>();
		Map<String, Map<Integer, String>> map2 = new HashMap<>();
	}
}
