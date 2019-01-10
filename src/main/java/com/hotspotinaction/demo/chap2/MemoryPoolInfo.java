package com.hotspotinaction.demo.chap2;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.util.LinkedList;
import java.util.List;

/**
 * 程序示例：JMX示例程序
 * 
 * @author Chen Tao
 * 
 */
public class MemoryPoolInfo {

	private String poolName = "CMS";
	private String collectorName = "ConcurrentMarkSweep";

	public static void main(String[] args) {

		MemoryPoolInfo t = null;
		if (args.length == 2) {
			t = new MemoryPoolInfo(args[0], args[1]);
		} else {
			System.out.println("Defaulting to monitor CMS pool and collector.");
			t = new MemoryPoolInfo();
		}
		t.run();
	}

	public MemoryPoolInfo(String pool, String collector) {
		poolName = pool;
		collectorName = collector;
	}

	public MemoryPoolInfo() {
	}

	/**
	 * 输出内存池及垃圾收集器等信息
	 */
	public void run() {
		// Use some memory, enough that we expect collections should
		// have happened.
		// Must run with options to ensure no stop the world full GC,
		// but e.g. at least one CMS cycle.
		allocationWork(300 * 1024 * 1024);
		System.out.println("Done allocationWork");

		// Verify some non-zero results are stored.
		List<MemoryPoolMXBean> pools = ManagementFactory.getMemoryPoolMXBeans();
		int poolsFound = 0;
		int poolsWithStats = 0;
		for (int i = 0; i < pools.size(); i++) {
			MemoryPoolMXBean pool = pools.get(i);
			String name = pool.getName();
			System.out.println("found pool: " + name);

			if (name.contains(poolName)) {
				long usage = pool.getCollectionUsage().getUsed();
				System.out.println(name + ": usage after GC = " + usage);
				poolsFound++;
				if (usage > 0) {
					poolsWithStats++;
				}
			}
		}
		if (poolsFound == 0) {
			throw new RuntimeException("无匹配的内存池：请打开-XX:+UseConcMarkSweepGC");
		}

		List<GarbageCollectorMXBean> collectors = ManagementFactory.getGarbageCollectorMXBeans();
		int collectorsFound = 0;
		int collectorsWithTime = 0;
		for (int i = 0; i < collectors.size(); i++) {
			GarbageCollectorMXBean collector = collectors.get(i);
			String name = collector.getName();
			System.out.println("found collector: " + name);
			if (name.contains(collectorName)) {
				collectorsFound++;
				System.out.println(name + ": collection count = " + collector.getCollectionCount());
				System.out.println(name + ": collection time  = " + collector.getCollectionTime());
				if (collector.getCollectionCount() <= 0) {
					throw new RuntimeException("collection count <= 0");
				}
				if (collector.getCollectionTime() > 0) {
					collectorsWithTime++;
				}
			}
		}
		// verify:
		if (poolsWithStats < poolsFound) {
			throw new RuntimeException("pools found with zero stats");
		}

		if (collectorsWithTime < collectorsFound) {
			throw new RuntimeException("collectors found with zero time");
		}
		System.out.println("Test passed.");
	}

	public void allocationWork(long target) {

		long sizeAllocated = 0;
		List<byte[]> list = new LinkedList<>();
		long delay = 50;
		long count = 0;

		while (sizeAllocated < target) {
			int size = 1024 * 1024;
			byte[] alloc = new byte[size];
			if (count % 2 == 0) {
				list.add(alloc);
				sizeAllocated += size;
				System.out.print(".");
			}
			try {
				Thread.sleep(delay);
			} catch (InterruptedException ie) {
			}
			count++;
		}
	}

}
