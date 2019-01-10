package com.hotspotinaction.demo.chap4;

import java.util.logging.Logger;

import sun.misc.BASE64Encoder;

/**
 * 程序示例：BASE64加密/解密工具类
 * 
 * @author Chen Tao
 * 
 */
public class BASE64Util {
	private static Logger log = Logger.getLogger(BASE64Util.class.getName());

	public String encodeBase64(String message) {
		BASE64Encoder encoder = new BASE64Encoder();
		String result = encoder.encodeBuffer(message.getBytes());
		log.info(message);
		log.info(result);
		return result;
	}

	public static void main(String[] args) {
		BASE64Util util = new BASE64Util();
		util.encodeBase64("Hello World!");
	}
}
