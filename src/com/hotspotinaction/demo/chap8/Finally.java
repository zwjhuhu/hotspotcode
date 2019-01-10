package com.hotspotinaction.demo.chap8;

/**
 * 程序示例：进入finally语句块的路径
 * 
 * @author Chen Tao
 * 
 */
public class Finally {
	public void finally1() {
		try {
			System.out.println(1);
		} finally {
			System.out.println(2);
		}
	}

	public void finally2_break() {
		int i = 33;
		switch (i) {
			case 0 :
				try {
					System.out.println(1);
					break;
				} finally {
					System.out.println(2);
				}
		}
	}

	public void finally2_continue() {
		for (int i = 0; i < 100; i++)
			try {
				System.out.println(1);
				continue;
			} finally {
				System.out.println(2);
			}
	}

	public void finally3_return() {
		try {
			System.out.println(1);
			return;
		} finally {
			System.out.println(2);
		}

	}

	public void finally4_ex() throws Exception {
		try {
			System.out.println(1);
			throw new Exception("exception in try block");
		} finally {
			System.out.println(2);
		}

	}

}
