package com.hotspotinaction.demo.chap1;

import java.util.HashMap;

/**
 * 程序示例：简化泛型定义
 * 
 * @author Chen Tao
 * 
 */
public class SimplifiedGeneric {

	public static void main(String[] args) {
		// before Java 7
		HashMap<String, HashMap<Long, String>> map1 = new HashMap<String, HashMap<Long, String>>();
		// Java 7
		HashMap<String, HashMap<Long, String>> map2 = new HashMap<>();
	}

}
