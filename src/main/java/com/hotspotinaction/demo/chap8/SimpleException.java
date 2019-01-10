package com.hotspotinaction.demo.chap8;

/**
 * 程序示例：演示异常
 * 
 * @author Chen Tao
 * 
 */
public class SimpleException extends Exception {
	private static final long serialVersionUID = -8983381128076107849L;

	public SimpleException(String message) {
		super(message);
	}

	@Override
	public String toString() {
		return "SimpleException [" + super.getMessage() + "]";
	}
}
