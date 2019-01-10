package com.hotspotinaction.demo.chap8.dispatch;

/**
 * 程序示例：invoke家族指令
 * 
 * @author Chen Tao
 * 
 */
public class PrepareInvokeDemo {

	public static void main(String[] args) {
		PrepareInvokeDemo demo = new PrepareInvokeDemo();
		demo.callInvokevirtual();
	}

	/**
	 * 演示：invokevirtual
	 */
	public void callInvokevirtual() {
		this.add(3, 5);
		add(3, 5);
	}

	public int add(int i, int j) {
		return i + j;
	}

	/**
	 * 演示：invokestatic
	 */
	public static void callInvokestatic() {
		staticAdd(3, 5);
	}

	public static int staticAdd(int i, int j) {
		return i + j;
	}

}
