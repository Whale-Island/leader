package com.leader.core.util;

/**
 * 类说明：字符缓存类
 */
public class CharBuffer {

	/* static fields */
	/** 默认的初始容量大小 */
	public static final int CAPACITY = 32;
	/** 空字符串的显示文字 */
	public static final String NULL = "null";

	/* fields */
	/** 字符数组 */
	char[] array;
	/** 字符缓存的长度 */
	int top;
	/** 字符缓存的偏移量 */
	int offset;

	/* constructors */
	/** 构造一个默认的大小的字符缓存对象 */
	public CharBuffer() {
		this(CAPACITY);
	}

	/** 构造一个指定的大小的字符缓存对象 */
	public CharBuffer(int capacity) {
		if (capacity < 1)
			throw new IllegalArgumentException(getClass().getName() + " <init>, invalid capatity:" + capacity);
		array = new char[capacity];
		top = 0;
		offset = 0;
	}

	/** 构造一个指定的字符数组的字符缓存对象 */
	public CharBuffer(char[] data) {
		if (data == null)
			throw new IllegalArgumentException(getClass().getName() + " <init>, null data");
		array = data;
		top = data.length;
		offset = 0;
	}

	/** 构造一个指定的字符数组的字符缓存对象 */
	public CharBuffer(char[] data, int index, int length) {
		if (data == null)
			throw new IllegalArgumentException(getClass().getName() + " <init>, null data");
		if (index < 0 || index > data.length)
			throw new IllegalArgumentException(getClass().getName() + " <init>, invalid index:" + index);
		if (length < 0 || data.length < index + length)
			throw new IllegalArgumentException(getClass().getName() + " <init>, invalid length:" + length);
		array = data;
		top = index + length;
		offset = index;
	}

	/** 构造一个指定的字符串的字符缓存对象 */
	public CharBuffer(String str) {
		if (str == null)
			throw new IllegalArgumentException(getClass().getName() + " <init>, null str");
		int len = str.length();
		array = new char[len + CAPACITY];
		str.getChars(0, len, array, 0);
		top = len;
		offset = 0;
	}

	/* properties */
	/** 得到字符缓存的容积 */
	public int capacity() {
		return array.length;
	}

	/** 设置字符缓存的容积，只能扩大容积 */
	public void setCapacity(int len) {
		int c = array.length;
		if (len <= c)
			return;
		for (; c < len; c = (c << 1) + 1)
			;
		char[] temp = new char[c];
		System.arraycopy(array, 0, temp, 0, top);
		array = temp;
	}

	/** 得到字符缓存的长度 */
	public int top() {
		return top;
	}

	/** 设置字符缓存的长度 */
	public void setTop(int top) {
		if (top < offset)
			throw new IllegalArgumentException(this + " setTop, invalid top:" + top);
		if (top > array.length)
			setCapacity(top);
		this.top = top;
	}

	/** 得到字符缓存的偏移量 */
	public int offset() {
		return offset;
	}

	/** 设置字符缓存的偏移量 */
	public void setOffset(int offset) {
		if (offset < 0 || offset > top)
			throw new IllegalArgumentException(this + " setOffset, invalid offset:" + offset);
		this.offset = offset;
	}

	/** 得到字符缓存的使用长度 */
	public int length() {
		return top - offset;
	}

	/** 得到字符缓存的字符数组，一般使用toArray()方法 */
	public char[] getArray() {
		return array;
	}

	/* methods */
	/** 得到当前位置的字符 */
	public char read() {
		return array[offset++];
	}

	/** 得到指定位置的字符 */
	public char read(int pos) {
		return array[pos];
	}

	/** 设置当前位置的字符 */
	public void write(char c) {
		array[top++] = c;
	}

	/** 设置指定偏移位置的字符 */
	public void write(char c, int pos) {
		array[pos] = c;
	}

	/**
	 * 根据当前偏移位置读入指定的字符数组
	 * 
	 * @param data
	 *            指定的字符数组
	 * @param pos
	 *            指定的字符数组的起始位置
	 * @param len
	 *            读入的长度
	 */
	public void read(char[] data, int pos, int len) {
		System.arraycopy(array, offset, data, pos, len);
		offset += len;
	}

	/**
	 * 写入指定字符数组
	 * 
	 * @param data
	 *            指定的字符数组
	 * @param pos
	 *            指定的字符数组的起始位置
	 * @param len
	 *            写入的长度
	 */
	public void write(char[] data, int pos, int len) {
		if (array.length < top + len)
			setCapacity(top + len);
		System.arraycopy(data, pos, array, top, len);
		top += len;
	}

	/** 追加指定对象 */
	public CharBuffer append(Object obj) {
		return append(obj != null ? obj.toString() : NULL);
	}

	/** 追加指定字符串 */
	public CharBuffer append(String str) {
		if (str == null)
			str = NULL;
		int len = str.length();
		if (len <= 0)
			return this;
		if (array.length < top + len)
			setCapacity(top + len);
		str.getChars(0, len, array, top);
		top += len;
		return this;
	}

	/** 追加指定字符数组 */
	public CharBuffer append(char[] data) {
		if (data == null)
			return append(NULL);
		return append(data, 0, data.length);
	}

	/** 追加指定字符数组 */
	public CharBuffer append(char[] data, int pos, int len) {
		if (data == null)
			return append(NULL);
		write(data, pos, len);
		return this;
	}

	/** 追加指定布尔值 */
	public CharBuffer append(boolean b) {
		int pos = top;
		if (b) {
			if (array.length < pos + 4)
				setCapacity(pos + CAPACITY);
			array[pos] = 't';
			array[pos + 1] = 'r';
			array[pos + 2] = 'u';
			array[pos + 3] = 'e';
			top += 4;
		} else {
			if (array.length < pos + 5)
				setCapacity(pos + CAPACITY);
			array[pos] = 'f';
			array[pos + 1] = 'a';
			array[pos + 2] = 'l';
			array[pos + 3] = 's';
			array[pos + 4] = 'e';
			top += 5;
		}
		return this;
	}

	/** 追加指定字符 */
	public CharBuffer append(char c) {
		if (array.length < top + 1)
			setCapacity(top + CAPACITY);
		array[top++] = c;
		return this;
	}

	/** 追加指定整数 */
	public CharBuffer append(int i) {
		if (i == Integer.MIN_VALUE) {
			append("-2147483648");
			return this;
		}
		int pos = top;
		int len = 0, n = 0, j;
		if (i < 0) {
			i = -i;
			for (n = 0, j = i; (j /= 10) > 0; n++)
				;
			len = n + 2;
			if (array.length < pos + len)
				setCapacity(pos + len);
			array[pos++] = '-';
		} else {
			for (n = 0, j = i; (j /= 10) > 0; n++)
				;
			len = n + 1;
			if (array.length < pos + len)
				setCapacity(pos + len);
		}
		while (n >= 0) {
			array[pos + n] = (char) ('0' + (i % 10));
			i /= 10;
			n--;
		}
		top += len;
		return this;
	}

	/** 追加指定长整数 */
	public CharBuffer append(long i) {
		if (i == Long.MIN_VALUE) {
			append("-9223372036854775808");
			return this;
		}
		int pos = top;
		int len = 0, n = 0;
		long j;
		if (i < 0) {
			i = -i;
			for (n = 0, j = i; (j /= 10) > 0; n++)
				;
			len = n + 2;
			if (array.length < pos + len)
				setCapacity(pos + len);
			array[pos++] = '-';
		} else {
			for (n = 0, j = i; (j /= 10) > 0; n++)
				;
			len = n + 1;
			if (array.length < pos + len)
				setCapacity(pos + len);
		}
		while (n >= 0) {
			array[pos + n] = (char) ('0' + (i % 10));
			i /= 10;
			n--;
		}
		top += len;
		return this;
	}

	/** 追加指定浮点数 */
	public CharBuffer append(float f) {
		return append(Float.toString(f));
	}

	/** 追加指定浮点数 */
	public CharBuffer append(double d) {
		return append(Double.toString(d));
	}

	/** 得到字符缓存当前长度的字符数组 */
	public char[] toArray() {
		char[] data = new char[top - offset];
		System.arraycopy(array, offset, data, 0, data.length);
		return data;
	}

	/** 清除字符缓存对象 */
	public void clear() {
		top = 0;
		offset = 0;
	}

	/** 获得字符串 */
	public String getString() {
		return new String(array, offset, top - offset);
	}

	/* common methods */
	@Override
	public int hashCode() {
		int hash = 0;
		char temp[] = array;
		int len = top, i = offset;
		for (; i < len; i++)
			hash = 31 * hash + temp[i];
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof CharBuffer))
			return false;
		CharBuffer cb = (CharBuffer) obj;
		if (cb.top != top)
			return false;
		if (cb.offset != offset)
			return false;
		for (int i = top - 1; i >= 0; i--) {
			if (cb.array[i] != array[i])
				return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return super.toString() + "[" + top + "," + offset + "," + array.length + "]";
	}

}