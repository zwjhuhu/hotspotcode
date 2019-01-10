package com.hotspotinaction.demo.chap6;

import java.io.PrintStream;

import sun.jvm.hotspot.code.CodeBlob;
import sun.jvm.hotspot.debugger.AddressException;
import sun.jvm.hotspot.runtime.Frame;
import sun.jvm.hotspot.runtime.JavaThread;
import sun.jvm.hotspot.runtime.RegisterMap;
import sun.jvm.hotspot.runtime.Threads;
import sun.jvm.hotspot.runtime.VM;
import sun.jvm.hotspot.tools.Tool;
import sun.jvm.hotspot.utilities.Assert;

/**
 * 程序示例：查看帧
 * 
 * @author Chen Tao
 * 
 */
public class FrameDump extends Tool {

	public void dumpFrame(JavaThread cur) {
		RegisterMap regMap = cur.newRegisterMap(true);
		Frame f = cur.getCurrentFrameGuess();
		PrintStream tty = System.out;
		int i = 0;
		while (f != null) {
			tty.print("查找帧，已发现帧(相对栈深=" + i-- + ")： ");
			if (f.isInterpretedFrame()) {
				tty.print("解释（interpreted）");
			} else if (f.isCompiledFrame()) {
				tty.print("编译（compiled）");
			} else if (f.isEntryFrame()) {
				tty.print("进入点（entry）");
			} else if (f.isNativeFrame()) {
				tty.print("本地（native）");
			} else if (Assert.ASSERTS_ENABLED) {
				Assert.that(!VM.getVM().isCore(), "noncore builds only");
				CodeBlob cb = VM.getVM().getCodeCache().findBlob(f.getPC());
				if (cb != null && cb.isRuntimeStub()) {
					tty.print("胶水（glue）");
				}
			} else {
				tty.print("外部（external）");
			}

			/**
			 * step 1: get pc、sp、fp、method
			 */
			tty.print(" 帧：程序计数器（PC） = " + f.getPC() + ", 栈指针（SP） = " + f.getSP() + ", 帧指针（FP） = " + f.getFP());
			sun.jvm.hotspot.oops.Method curMethod = f.getInterpreterFrameMethod();
			if (null != curMethod) {
				sun.jvm.hotspot.oops.Symbol methodSymbol = curMethod.getName();
				tty.println();
				tty.print("方法名：" + methodSymbol.asString());
			}

			tty.println();

			/**
			 * step 2: get CodeBlob
			 */
			if (VM.getVM().getCodeCache().contains(f.getPC())) {
				CodeBlob cb = VM.getVM().getCodeCache().findBlob(f.getPC());
				if (Assert.ASSERTS_ENABLED) {
					Assert.that(cb != null, "sanity check");
				}
				tty.print("CodeBlob: " + cb.toString());
				tty.print(", codeBegin=" + cb.codeBegin());
				tty.print(", codeEnd=" + cb.codeEnd());
				tty.println();
			}

			if (!f.isFirstFrame()) {
				f = f.sender(regMap);
			} else {
				f = null;
			}
		}
	}
	public void run() {
		// Ready to go with the database...
		try {
			Threads threads = VM.getVM().getThreads();

			PrintStream tty = System.out;

			// 参考：StackTrace.java
			for (JavaThread cur = threads.first(); cur != null; cur = cur.next()) {
				if (cur.isJavaThread()) {
					tty.print("Thread ");
					cur.printThreadIDOn(tty);
					tty.println();
					String threadName = cur.getThreadName();
					tty.print("名称：" + threadName);
					tty.print(": (线程状态 = " + cur.getThreadState());
					tty.println(')');

					// 我们只关注main线程
					if (!"main".equalsIgnoreCase(threadName)) {
						continue;
					}

					// dump线程帧
					dumpFrame(cur);
					tty.println();
					tty.println();
				}
			}

		} catch (AddressException e) {
			System.err.println(getName() + "Error accessing address 0x" + Long.toHexString(e.getAddress()));
			e.printStackTrace();
		}
	}
	public String getName() {
		return "FrameDump";
	}

	/**
	 * 
	 * @param args
	 *            pid
	 */
	public static void main(String[] args) {
		FrameDump tool = new FrameDump();
		tool.start(args);
		tool.stop();
	}

}
