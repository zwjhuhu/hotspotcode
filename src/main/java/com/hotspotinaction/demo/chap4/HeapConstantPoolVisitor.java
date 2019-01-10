package com.hotspotinaction.demo.chap4;

import java.io.File;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import sun.jvm.hotspot.oops.ConstantPool;
import sun.jvm.hotspot.oops.DefaultHeapVisitor;
import sun.jvm.hotspot.oops.DefaultMetadataVisitor;
import sun.jvm.hotspot.oops.InstanceKlass;
import sun.jvm.hotspot.oops.Klass;
import sun.jvm.hotspot.oops.MetadataField;
import sun.jvm.hotspot.oops.Oop;
import sun.jvm.hotspot.oops.Symbol;
import sun.jvm.hotspot.utilities.U1Array;

public class HeapConstantPoolVisitor extends DefaultHeapVisitor {

	private PrintStream tty = null;
	Map<String, ConstantPool> pools = new HashMap<>();

	/** 开始 */
	public void prologue(long usedSize) {
		try {
			System.out.println("the begin ==================================");
			String dir = "D:\\log\\";
			File file = new File(dir + "ConstantPoolTestCase-" + System.currentTimeMillis() + ".log");
			tty = new PrintStream(file);
		} catch (Exception exp) {
			throw new RuntimeException(exp);
		}
	}

	/** 结束 */
	public void epilogue() {
		try {
			System.out.println("the end ==================================");
		} catch (Exception exp) {
			throw new RuntimeException(exp);
		}
	}

	public boolean doObj(Oop obj)          {
		Klass kl = obj.getKlass();
		if(kl instanceof InstanceKlass) {
			String name = kl.getName().asString();
			if(name.startsWith("java/lang") && !pools.containsKey(name)) {
				tty.println(name);
				ConstantPool pool = ((InstanceKlass) kl).getConstants();
				pools.put(name, pool);
				dumpPool(pool);
			}
		}
		
		return false;
		
	}

	class PoolVistor extends DefaultMetadataVisitor {

		@Override
		public void doMetadata(MetadataField field, boolean isVMField) {
			tty.println("---" + field.getSignature());
		}

	}

	private void dumpPool(ConstantPool constantPool) {
		try {

			constantPool.printValueOn(tty);
			tty.println();
			tty.println();

			Map<String,Short> utf8ToIndex = new HashMap<>();
			U1Array tags = constantPool.getTags();
			int len = (int) constantPool.getLength();
			int ci = 0; // constant pool index

			// collect all modified UTF-8 Strings from Constant Pool

			for (ci = 1; ci < len; ci++) {
				byte cpConstType = tags.at(ci);
				if (cpConstType == ConstantPool.JVM_CONSTANT_Utf8) {
					Symbol sym = constantPool.getSymbolAt(ci);
					utf8ToIndex.put(sym.asString(), new Short((short) ci));
				} else if (cpConstType == ConstantPool.JVM_CONSTANT_Long
						|| cpConstType == ConstantPool.JVM_CONSTANT_Double) {
					ci++;
				}
			}

			for (ci = 1; ci < len; ci++) {
				int cpConstType = (int) tags.at(ci);
				// write cp_info
				// write constant type
				switch (cpConstType) {
				case ConstantPool.JVM_CONSTANT_Utf8: {
					tty.print("类型：" + cpConstType);
					Symbol sym = constantPool.getSymbolAt(ci);
					tty.print(" ;长度：" + (short) sym.getLength());
					tty.println(" ;CP[" + ci + "] = modified UTF-8 " + sym.asString());
					break;
				}

				case ConstantPool.JVM_CONSTANT_Unicode:
					throw new IllegalArgumentException("Unicode constant!");

				case ConstantPool.JVM_CONSTANT_Integer:
					tty.print("类型：" + cpConstType);
					tty.println(" ;CP[" + ci + "] = int " + constantPool.getIntAt(ci));
					break;

				case ConstantPool.JVM_CONSTANT_Float:
					tty.print("类型：" + cpConstType);
					tty.println(" ;CP[" + ci + "] = float " + constantPool.getFloatAt(ci));
					break;

				case ConstantPool.JVM_CONSTANT_Long: {
					tty.print("类型：" + cpConstType);
					long l = constantPool.getLongAt(ci);
					// long entries occupy two pool entries
					ci++;
					tty.println(", " + l);
					break;
				}

				case ConstantPool.JVM_CONSTANT_Double:
					tty.print("类型：" + cpConstType);
					tty.println(", " + constantPool.getDoubleAt(ci));
					// double entries occupy two pool entries
					ci++;
					break;

				case ConstantPool.JVM_CONSTANT_Class: {
					tty.print("类型：" + cpConstType);
					// Klass already resolved. ConstantPool constains
					// klassOop.
					Klass refKls = constantPool.getKlassAt(ci);
					String klassName = refKls.getName().asString();
					Short s = (Short) utf8ToIndex.get(klassName);
					tty.print(", klassName=" + klassName);
					tty.print(", " + s.shortValue());
					tty.println(", CP[" + ci + "] = class " + s);
					break;
				}

				case ConstantPool.JVM_CONSTANT_ClassIndex: {
					tty.print("类型：" + ConstantPool.JVM_CONSTANT_Class);
					String klassName = constantPool.getSymbolAt(ci).asString();
					Short s = (Short) utf8ToIndex.get(klassName);
					tty.print(", " + s.shortValue());
					tty.println(", CP[" + ci + "] = class " + s);
					break;
				}

				case ConstantPool.JVM_CONSTANT_String: {
					tty.print("类型：" + cpConstType);
					String str = constantPool.getSymbolAt(ci).asString();
					tty.print(", " + str);
					Short s = (Short) utf8ToIndex.get(str);
					tty.print(", " + s.shortValue());
					tty.println(", " + "CP[" + ci + "] = string " + s);
					break;
				}

				case ConstantPool.JVM_CONSTANT_StringIndex: {
					tty.print("类型：" + cpConstType);
					tty.print(", " + ConstantPool.JVM_CONSTANT_StringIndex);
					String val = constantPool.getSymbolAt(ci).asString();

					Short s = (Short) utf8ToIndex.get(val);
					tty.print(", " + s.shortValue());
					tty.println(", " + "CP[" + ci + "] = string " + s);
					break;
				}

				// all external, internal method/field references
				case ConstantPool.JVM_CONSTANT_Fieldref:
				case ConstantPool.JVM_CONSTANT_Methodref:
				case ConstantPool.JVM_CONSTANT_InterfaceMethodref: {
					tty.print("类型：" + cpConstType);
					int value = constantPool.getIntAt(ci);
					short klassIndex = (short) (value & 0xFFFF);
					short nameAndTypeIndex = (short) (value & 0xFFFF);
					tty.print(", " + klassIndex);
					tty.print(", " + nameAndTypeIndex);
					tty.println(", " + "CP[" + ci + "] = ref klass = " + klassIndex + ", N&T = " + nameAndTypeIndex);
					break;
				}

				case ConstantPool.JVM_CONSTANT_NameAndType: {
					tty.print("类型：" + cpConstType);
					int value = constantPool.getIntAt(ci);
					short nameIndex = (short) (value & 0xFFFF);
					short signatureIndex = (short) (value & 0xFFFF);
					tty.print(", " + nameIndex);
					tty.print(", " + signatureIndex);
					tty.println(", " + "CP[" + ci + "] = N&T name = " + nameIndex + ", type = " + signatureIndex);
					break;
				}

				case ConstantPool.JVM_CONSTANT_MethodHandle: {
					tty.print("类型：" + cpConstType);
					int value = constantPool.getIntAt(ci);
					short nameIndex = (short) (value & 0xFFFF);
					short signatureIndex = (short) (value & 0xFFFF);
					tty.print(", " + nameIndex);
					tty.print(", " + signatureIndex);
					tty.println(", " + "CP[" + ci + "] = N&T name = " + nameIndex + ", type = " + signatureIndex);
					break;
				}

				case ConstantPool.JVM_CONSTANT_InvokeDynamic: {
					tty.print("类型：" + cpConstType);
					int value = constantPool.getIntAt(ci);
					short bsmIndex = (short) (value & 0xFFFF);
					short nameAndTypeIndex = (short) (value & 0xFFFF);
					tty.print(", " + bsmIndex);
					tty.print(", " + nameAndTypeIndex);
					tty.println(", " + "CP[" + ci + "] = indy BSM = " + bsmIndex + ", N&T = " + nameAndTypeIndex);
					break;
				}

				default:
					// TODO
				} // switch
			}

		} catch (Exception exp) {
			throw new RuntimeException(exp);
		}
	}

}
