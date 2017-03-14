package com.leader.core.util;

public class ByteKit {

	/* static fields */
	/** 库信息 */
	public static final String toString = ByteKit.class.getName();

	/* static methods */
	/** 在字节数组中指定位置读出一个布尔值 */
	public static boolean readBoolean(byte[] bytes, int pos) {
		return bytes[pos] != 0;
	}

	/** 在字节数组中指定位置读出一个字节 */
	public static byte readByte(byte[] bytes, int pos) {
		return bytes[pos];
	}

	/** 在字节数组中指定位置读出一个无符号字节 */
	public static int readUnsignedByte(byte[] bytes, int pos) {
		return bytes[pos] & 0xff;
	}

	/** 在字节数组中指定位置读出一个字符 */
	public static char readChar(byte[] bytes, int pos) {
		return (char) readUnsignedShort(bytes, pos);
	}

	/** 在字节数组中指定位置读出一个字符，低位在前，高位在后 */
	public static char readChar_(byte[] bytes, int pos) {
		return (char) readUnsignedShort_(bytes, pos);
	}

	/** 在字节数组中指定位置读出一个短整型数值 */
	public static short readShort(byte[] bytes, int pos) {
		return (short) readUnsignedShort(bytes, pos);
	}

	/** 在字节数组中指定位置读出一个短整型数值，低位在前，高位在后 */
	public static short readShort_(byte[] bytes, int pos) {
		return (short) readUnsignedShort_(bytes, pos);
	}

	/** 在字节数组中指定位置读出一个无符号短整型数值 */
	public static int readUnsignedShort(byte[] bytes, int pos) {
		return (bytes[pos + 1] & 0xff) + ((bytes[pos] & 0xff) << 8);
	}

	/** 在字节数组中指定位置读出一个无符号短整型数值，低位在前，高位在后 */
	public static int readUnsignedShort_(byte[] bytes, int pos) {
		return ((bytes[pos + 1] & 0xff) << 8) + (bytes[pos] & 0xff);
	}

	/** 在字节数组中指定位置读出一个整型数值 */
	public static int readInt(byte[] bytes, int pos) {
		return ((bytes[pos + 3] & 0xff)) + ((bytes[pos + 2] & 0xff) << 8) + ((bytes[pos + 1] & 0xff) << 16)
				+ ((bytes[pos] & 0xff) << 24);
	}

	/** 在字节数组中指定位置读出一个整型数值，低位在前，高位在后 */
	public static int readInt_(byte[] bytes, int pos) {
		return ((bytes[pos + 3] & 0xff) << 24) + ((bytes[pos + 2] & 0xff) << 16) + ((bytes[pos + 1] & 0xff) << 8)
				+ ((bytes[pos] & 0xff));
	}

	/** 在字节数组中指定位置读出一个浮点数值 */
	public static float readFloat(byte[] bytes, int pos) {
		return Float.intBitsToFloat(readInt(bytes, pos));
	}

	/** 在字节数组中指定位置读出一个浮点数值，低位在前，高位在后 */
	public static float readFloat_(byte[] bytes, int pos) {
		return Float.intBitsToFloat(readInt_(bytes, pos));
	}

	/** 在字节数组中指定位置读出一个长整型数值 */
	public static long readLong(byte[] bytes, int pos) {
		return (bytes[pos + 7] & 0xffL) + ((bytes[pos + 6] & 0xffL) << 8) + ((bytes[pos + 5] & 0xffL) << 16)
				+ ((bytes[pos + 4] & 0xffL) << 24) + ((bytes[pos + 3] & 0xffL) << 32) + ((bytes[pos + 2] & 0xffL) << 40)
				+ ((bytes[pos + 1] & 0xffL) << 48) + ((bytes[pos] & 0xffL) << 56);
	}

	/** 在字节数组中指定位置读出一个长整型数值，低位在前，高位在后 */
	public static long readLong_(byte[] bytes, int pos) {
		return ((bytes[pos + 7] & 0xffL) << 56) + ((bytes[pos + 6] & 0xffL) << 48) + ((bytes[pos + 5] & 0xffL) << 40)
				+ ((bytes[pos + 4] & 0xffL) << 32) + ((bytes[pos + 3] & 0xffL) << 24) + ((bytes[pos + 2] & 0xffL) << 16)
				+ ((bytes[pos + 1] & 0xffL) << 8) + (bytes[pos] & 0xffL);
	}

	/** 在字节数组中指定位置读出一个双浮点数值 */
	public static double readDouble(byte[] bytes, int pos) {
		return Double.longBitsToDouble(readLong(bytes, pos));
	}

	/** 在字节数组中指定位置读出一个双浮点数值，低位在前，高位在后 */
	public static double readDouble_(byte[] bytes, int pos) {
		return Double.longBitsToDouble(readLong_(bytes, pos));
	}

	/** 读出动态长度的字节长度 */
	public static int getReadLength(byte b) {
		int n = b & 0xff;
		if (n >= 0x80)
			return 1;
		if (n >= 0x40)
			return 2;
		if (n >= 0x20)
			return 4;
		throw new IllegalArgumentException(toString + " getReadLength, invalid number:" + n);
	}

	/**
	 * 读出动态长度， 数据大小采用动态长度，整数类型下，最大为512M，
	 * <li>1xxx xxxx表示（0~0x80） 0~128B，</li>
	 * <li>01xx xxxx xxxx xxxx表示（0~0x4000） 0~16K，</li>
	 * <li>001x xxxx xxxx xxxx xxxx xxxx xxxx xxxx表示（0~0x20000000） 0~512M，</li>
	 */
	public static int readLength(byte[] data, int pos) {
		int n = data[pos] & 0xff;
		if (n >= 0x80)
			return n - 0x80;
		else if (n >= 0x40)
			return (n << 8) + (data[pos + 1] & 0xff) - 0x4000;
		else if (n >= 0x20)
			return (n << 24) + ((data[pos + 1] & 0xff) << 16) + ((data[pos + 2] & 0xff) << 8) + (data[pos + 3] & 0xff)
					- 0x20000000;
		else
			throw new IllegalArgumentException(toString + " readLength, invalid number:" + n);
	}

	/** 写入一个布尔值在字节数组中指定位置 */
	public static void writeBoolean(boolean b, byte[] bytes, int pos) {
		bytes[pos] = (byte) (b ? 1 : 0);
	}

	/** 写入一个字节在字节数组中指定位置 */
	public static void writeByte(byte b, byte[] bytes, int pos) {
		bytes[pos] = b;
	}

	/** 在字节数组中指定位置写入一个字符 */
	public static void writeChar(char c, byte[] bytes, int pos) {
		writeShort((short) c, bytes, pos);
	}

	/** 写入一个字符在字节数组中指定位置，低位在前，高位在后 */
	public static void writeChar_(char c, byte[] bytes, int pos) {
		writeShort_((short) c, bytes, pos);
	}

	/** 写入一个短整型数值在字节数组中指定位置 */
	public static void writeShort(short s, byte[] bytes, int pos) {
		bytes[pos] = (byte) (s >>> 8);
		bytes[pos + 1] = (byte) s;
	}

	/** 写入一个短整型数值在字节数组中指定位置，低位在前，高位在后 */
	public static void writeShort_(short s, byte[] bytes, int pos) {
		bytes[pos] = (byte) s;
		bytes[pos + 1] = (byte) (s >>> 8);
	}

	/** 写入一个整型数值在字节数组中指定位置 */
	public static void writeInt(int i, byte[] bytes, int pos) {
		bytes[pos] = (byte) (i >>> 24);
		bytes[pos + 1] = (byte) (i >>> 16);
		bytes[pos + 2] = (byte) (i >>> 8);
		bytes[pos + 3] = (byte) i;
	}

	/** 在字节数组中指定位置写入一个整型数值，低位在前，高位在后 */
	public static void writeInt_(int i, byte[] bytes, int pos) {
		bytes[pos] = (byte) i;
		bytes[pos + 1] = (byte) (i >>> 8);
		bytes[pos + 2] = (byte) (i >>> 16);
		bytes[pos + 3] = (byte) (i >>> 24);
	}

	/** 写入一个浮点数值在字节数组中指定位置 */
	public static void writeFloat(float f, byte[] bytes, int pos) {
		writeInt(Float.floatToIntBits(f), bytes, pos);
	}

	/** 写入一个浮点数值在字节数组中指定位置，低位在前，高位在后 */
	public static void writeFloat_(float f, byte[] bytes, int pos) {
		writeInt_(Float.floatToIntBits(f), bytes, pos);
	}

	/** 写入一个长整型数值在字节数组中指定位置 */
	public static void writeLong(long l, byte[] bytes, int pos) {
		bytes[pos] = (byte) (l >>> 56);
		bytes[pos + 1] = (byte) (l >>> 48);
		bytes[pos + 2] = (byte) (l >>> 40);
		bytes[pos + 3] = (byte) (l >>> 32);
		bytes[pos + 4] = (byte) (l >>> 24);
		bytes[pos + 5] = (byte) (l >>> 16);
		bytes[pos + 6] = (byte) (l >>> 8);
		bytes[pos + 7] = (byte) l;
	}

	/** 写入一个长整型数值在字节数组中指定位置，低位在前，高位在后 */
	public static void writeLong_(long l, byte[] bytes, int pos) {
		bytes[pos] = (byte) l;
		bytes[pos + 1] = (byte) (l >>> 8);
		bytes[pos + 2] = (byte) (l >>> 16);
		bytes[pos + 3] = (byte) (l >>> 24);
		bytes[pos + 4] = (byte) (l >>> 32);
		bytes[pos + 5] = (byte) (l >>> 40);
		bytes[pos + 6] = (byte) (l >>> 48);
		bytes[pos + 7] = (byte) (l >>> 56);
	}

	/** 写入一个双浮点数值在字节数组中指定位置 */
	public static void writeDouble(double d, byte[] bytes, int pos) {
		writeLong(Double.doubleToLongBits(d), bytes, pos);
	}

	/** 写入一个双浮点数值在字节数组中指定位置，低位在前，高位在后 */
	public static void writeDouble_(double d, byte[] bytes, int pos) {
		writeLong_(Double.doubleToLongBits(d), bytes, pos);
	}

	/**
	 * 写入动态长度， 数据大小采用动态长度，整数类型下，最大为512M，
	 * <li>1xxx xxxx表示（0~0x80） 0~128B，</li>
	 * <li>01xx xxxx xxxx xxxx表示（0~0x4000） 0~16K，</li>
	 * <li>001x xxxx xxxx xxxx xxxx xxxx xxxx xxxx表示（0~0x20000000） 0~512M，</li>
	 */
	public static int writeLength(int len, byte[] bytes, int pos) {
		if (len >= 0x20000000 || len < 0)
			throw new IllegalArgumentException(toString + " writeLength, invalid len:" + len);
		if (len >= 0x4000) {
			writeInt(len + 0x20000000, bytes, pos);
			return 4;
		} else if (len >= 0x80) {
			writeShort((short) (len + 0x4000), bytes, pos);
			return 2;
		} else {
			writeByte((byte) (len + 0x80), bytes, pos);
			return 1;
		}
	}

	/** 将指定的字节数据转换为ISO-8859-1格式的字符串 */
	public static String readISO8859_1(byte[] data) {
		return readISO8859_1(data, 0, data.length);
	}

	/** 将指定的字节数据转换为ISO-8859-1格式的字符串 */
	public static String readISO8859_1(byte[] data, int pos, int len) {
		char[] array = new char[len];
		for (int i = pos + len - 1, j = array.length - 1; i >= pos; i--, j--)
			array[j] = (char) data[i];
		return new String(array);
	}

	/** 将指定的字符串转换为ISO-8859-1格式的字节数据 */
	public static byte[] writeISO8859_1(String str) {
		return writeISO8859_1(str, 0, str.length());
	}

	/** 将指定的字符串转换为ISO-8859-1格式的字节数据 */
	public static byte[] writeISO8859_1(String str, int index, int len) {
		byte[] data = new byte[len];
		writeISO8859_1(str, index, len, data, 0);
		return data;
	}

	/** 将指定的字符串转换为ISO-8859-1格式的字节数据 */
	public static void writeISO8859_1(String str, int index, int len, byte[] data, int pos) {
		int c;
		for (int i = index + len - 1, j = pos + len - 1; i >= index; i--, j--) {
			c = str.charAt(i);
			data[j] = (c > 256) ? 63 : (byte) c;
		}
	}

	/** 将指定的字符数组转换为ISO-8859-1格式的字节数据 */
	public static void writeISO8859_1(char[] chars, int index, int len, byte[] data, int pos) {
		int c;
		for (int i = index + len - 1, j = pos + len - 1; i >= index; i--, j--) {
			c = chars[i];
			data[j] = (c > 256) ? 63 : (byte) c;
		}
	}

	/** 将指定的UTF8格式的字节数据转换为字符串，返回null表示失败 */
	public static String readUTF(byte[] data) {
		char[] array = new char[data.length];
		int n = readUTF(data, 0, data.length, array);
		return (n >= 0) ? new String(array, 0, n) : null;
	}

	/** 将指定的UTF8格式的字节数据转换为字符串，返回null表示失败 */
	public static String readUTF(byte[] data, int pos, int length) {
		char[] array = new char[length];
		int n = readUTF(data, pos, length, array);
		return (n >= 0) ? new String(array, 0, n) : null;
	}

	/**
	 * 将指定的UTF8格式的字节数据转换为字符串， 返回读到的字符数，-1表示失败
	 */
	public static int readUTF(byte[] data, int pos, int length, char[] array) {
		int i, c, cc, ccc;
		int n = 0, end = pos + length;
		while (pos < end) {
			c = data[pos] & 0xff;
			i = c >> 4;
			if (i < 8) {
				// 0xxx xxxx
				pos++;
				array[n++] = (char) c;
			} else if (i == 12 || i == 13) {
				// 110x xxxx 10xx xxxx
				pos += 2;
				if (pos > end)
					return -1;
				cc = data[pos - 1];
				if ((cc & 0xC0) != 0x80)
					return -1;
				array[n++] = (char) (((c & 0x1f) << 6) | (cc & 0x3f));
			} else if (i == 14) {
				// 1110 xxxx 10xx xxxx 10xx
				// xxxx
				pos += 3;
				if (pos > end)
					return -1;
				cc = data[pos - 2];
				ccc = data[pos - 1];
				if (((cc & 0xC0) != 0x80) || ((ccc & 0xC0) != 0x80))
					return -1;
				array[n++] = (char) (((c & 0x0f) << 12) | ((cc & 0x3f) << 6) | (ccc & 0x3f));
			} else
				// 10xx xxxx 1111 xxxx
				return -1;
		}
		return n;
	}

	/** 获得指定的字符串转换为UTF8格式的字节数据的长度 */
	public static int getUTFLength(String str, int index, int len) {
		int utfLen = 0;
		int c;
		for (int i = index; i < len; i++) {
			c = str.charAt(i);
			if ((c >= 0x0001) && (c <= 0x007f))
				utfLen++;
			else if (c > 0x07ff)
				utfLen += 3;
			else
				utfLen += 2;
		}
		return utfLen;
	}

	/** 获得指定的字符数组转换为UTF8格式的字节数据的长度 */
	public static int getUTFLength(char[] chars, int index, int len) {
		int utfLen = 0;
		int c;
		for (int i = index; i < len; i++) {
			c = chars[i];
			if ((c >= 0x0001) && (c <= 0x007f))
				utfLen++;
			else if (c > 0x07ff)
				utfLen += 3;
			else
				utfLen += 2;
		}
		return utfLen;
	}

	/** 将指定的字符串转换为UTF8格式的字节数据 */
	public static byte[] writeUTF(String str) {
		return writeUTF(str, 0, str.length());
	}

	/** 将指定的字符串转换为UTF8格式的字节数据 */
	public static byte[] writeUTF(String str, int index, int len) {
		byte[] data = new byte[getUTFLength(str, index, len)];
		writeUTF(str, index, len, data, 0);
		return data;
	}

	/** 将指定的字符串转换为UTF8格式的字节数据 */
	public static void writeUTF(String str, int index, int len, byte[] data, int pos) {
		int c;
		for (int i = index; i < len; i++) {
			c = str.charAt(i);
			if ((c >= 0x0001) && (c <= 0x007f)) {
				data[pos++] = (byte) c;
			} else if (c > 0x07ff) {
				data[pos++] = (byte) (0xe0 | ((c >> 12) & 0x0f));
				data[pos++] = (byte) (0x80 | ((c >> 6) & 0x3f));
				data[pos++] = (byte) (0x80 | (c & 0x3f));
			} else {
				data[pos++] = (byte) (0xc0 | ((c >> 6) & 0x1f));
				data[pos++] = (byte) (0x80 | (c & 0x3f));
			}
		}
	}

	/** 将指定的字符数组转换为UTF8格式的字节数据 */
	public static void writeUTF(char[] chars, int index, int len, byte[] data, int pos) {
		int c;
		for (int i = index; i < len; i++) {
			c = chars[i];
			if ((c >= 0x0001) && (c <= 0x007f)) {
				data[pos++] = (byte) c;
			} else if (c > 0x07ff) {
				data[pos++] = (byte) (0xe0 | ((c >> 12) & 0x0f));
				data[pos++] = (byte) (0x80 | ((c >> 6) & 0x3f));
				data[pos++] = (byte) (0x80 | (c & 0x3f));
			} else {
				data[pos++] = (byte) (0xc0 | ((c >> 6) & 0x1f));
				data[pos++] = (byte) (0x80 | (c & 0x3f));
			}
		}
	}

	/* constructors */
	private ByteKit() {
	}

}
