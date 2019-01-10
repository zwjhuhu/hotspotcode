package com.hotspotinaction.demo.chap8;

/**
 * 程序示例：演示异常
 * 
 * @author Chen Tao
 * 
 */
public class SimpleThrowException {

	public static void main(String[] args) {
		SimpleThrowException ste = new SimpleThrowException();
		try {
			ste.throwIt();
		} catch (SimpleException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void throwIt() throws SimpleException {
		throw new SimpleException("This is a simple exception.");
	}

}
/**
 * output: SimpleException [This is a simple exception.]
 */
