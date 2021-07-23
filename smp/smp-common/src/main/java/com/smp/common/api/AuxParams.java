package com.smp.common.api;

import java.util.Map;

/**
 * 用于控制数据处理的扩展参数，本质上是一个key为字符串，value为任意对象的HashMap。
 */
@SuppressWarnings("serial")
public class AuxParams extends java.util.HashMap<String, Object> {

	/**
	 * 创建AuxParams对象。
	 *
	 * Constructs an empty <tt>HashMap</tt> with the default initial capacity
	 * (16) and the default load factor (0.75).
	 */
	public AuxParams() {}

	/**
	 * 创建初始容量为给定值的AuxParams对象。
	 *
	 * Constructs an empty <tt>HashMap</tt> with the specified initial
	 * capacity and the default load factor (0.75).
	 *
	 * @param initialCapacity the initial capacity.
	 * @throws IllegalArgumentException if the initial capacity is negative.
	 */
	public AuxParams(int initialCapacity) {
		super(initialCapacity);
	}

	/**
	 * 创建初始容量、负载因子为给定值的AuxParams对象。
	 *
	 * Constructs an empty <tt>HashMap</tt> with the specified initial
	 * capacity and load factor.
	 *
	 * @param initialCapacity the initial capacity
	 * @param loadFactor      the load factor
	 * @throws IllegalArgumentException if the initial capacity is negative
	 *                                  or the load factor is nonpositive
	 */
	public AuxParams(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	/**
	 * 创建AuxParams对象，并将给定的内容复制到当前对象中。
	 *
	 * Constructs a new <tt>HashMap</tt> with the same mappings as the
	 * specified <tt>Map</tt>.  The <tt>HashMap</tt> is created with
	 * default load factor (0.75) and an initial capacity sufficient to
	 * hold the mappings in the specified <tt>Map</tt>.
	 *
	 * @param m the map whose mappings are to be placed in this map
	 * @throws NullPointerException if the specified map is null
	 */
	public AuxParams(Map<? extends String, ?> m) {
		super(m);
	}
}
