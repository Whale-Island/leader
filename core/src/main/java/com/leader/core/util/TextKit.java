package com.leader.core.util;

import java.io.UnsupportedEncodingException;

/**
 * 类说明：文字操作功能库
 */
public final class TextKit {

	/** 库信息 */
	public static final String toString = TextKit.class.getName();
	/** 一行文字的长度限制 */
	public static final int LEN_LIMIT = 80;

	/** 零长度的字符串 */
	public static final String EMPTY_STRING = "";
	/** 零长度的字符串数组 */
	public static final String[] EMPTY_STRING_ARRAY = {};
	/** 空字符串数组 */
	public static final String[] NULL_STRING_ARRAY = { "" };

	/** 第一个ASCII字符 */
	public static final char FIRST_ASCII = ' ';
	/** 最后一个ASCII字符 */
	public static final char LAST_ASCII = '~';
	/** 第一个数字字符 */
	public static final char FIRST_NUMBER = '0';
	/** 最后一个数字字符 */
	public static final char LAST_NUMBER = '9';
	/** 第一个大写英文字符 */
	public static final char FIRST_UPPER_ENGLISH = 'A';
	/** 最后一个大写英文字符 */
	public static final char LAST_UPPER_ENGLISH = 'Z';
	/** 第一个小写英文字符 */
	public static final char FIRST_LOWER_ENGLISH = 'a';
	/** 最后一大大写英文字符 */
	public static final char LAST_LOWER_ENGLISH = 'z';
	/** 第一个汉字字符 */
	public static final char FIRST_CHINESE = '\u4E00';
	/** 最后一个汉字字符 */
	public static final char LAST_CHINESE = '\u9FFF';
	/** 数字字符集 */
	public static final char[] NUMBER = { FIRST_NUMBER, LAST_NUMBER };
	/** 大写英文字符集 */
	public static final char[] UPPER_ENGLISH = { FIRST_UPPER_ENGLISH, LAST_UPPER_ENGLISH };
	/** 小写英文字符集 */
	public static final char[] LOWER_ENGLISH = { FIRST_LOWER_ENGLISH, LAST_LOWER_ENGLISH };
	/** 英文字符集 */
	public static final char[] ENGLISH = { FIRST_UPPER_ENGLISH, LAST_UPPER_ENGLISH, FIRST_LOWER_ENGLISH,
			LAST_LOWER_ENGLISH };
	/** ASCII字符集 */
	public static final char[] ASCII = { FIRST_ASCII, LAST_ASCII };
	/** 汉字字符集 */
	public static final char[] CHINESE = { FIRST_CHINESE, LAST_CHINESE };
	/** unicodes字符集 */
	public static final char[] UNICODES = { FIRST_ASCII, '\uFFFF' };
	/** 回车及unicodes字符集 */
	public static final char[] LF_UNICODES = { 10, 10, FIRST_ASCII, '\uFFFF' };

	/**
	 * 汉字的数词数组，可以重设该数组中的汉字， {'零','一','二','三','四','五','六','七','八','九'};
	 */
	public static final char[] DIGITS = { '\u96F6', '\u4E00', '\u4E8C', '\u4E09', '\u56DB', '\u4E94', '\u516D',
			'\u4E03', '\u516B', '\u4E5D' };
	/**
	 * 汉字的量词数组，可以重设该数组中的汉字， {'','十','百','千','万','亿'};
	 */
	public static final char[] DIGIT_BITS = { ' ', '\u5341', '\u767E', '\u5343', '\u4E07', '\u4EBF' };
	/**
	 * 汉字的大写数词数组，可以重设该数组中的汉字， {'零','壹','贰','叁','肆','伍','陆','柒','捌','玖'};
	 */
	public static final char[] UPPER_DIGITS = { '\u96F6', '\u58F9', '\u8D30', '\u53C1', '\u8086', '\u4F0D', '\u9646',
			'\u67D2', '\u634C', '\u7396' };
	/**
	 * 汉字的大写量词数组，可以重设该数组中的汉字， {'','拾','佰','扦','萬','亿'};
	 */
	public static final char[] UPPER_DIGIT_BITS = { ' ', '\u62FE', '\u4F70', '\u6266', '\u842C', '\u4EBF' };

	/** 转换字符串为整数，可以分析16进制数字（以#或0x开头） */
	public static int parseInt(String str) {
		return (int) parseLong(str);
	}

	/** 转换字符串为长整数，可以分析16进制数字（以#或0x开头） */
	public static long parseLong(String str) {
		if (str == null || str.length() == 0)
			return 0;
		if (str.charAt(0) == '#')
			return Long.parseLong(str.substring(1), 16);
		if (str.length() > 1 && str.charAt(0) == '0' && str.charAt(1) == 'x')
			return Long.parseLong(str.substring(2), 16);
		return Long.parseLong(str);
	}

	/** 转换字符串为浮点数，可以分析16进制数字（以#或0x开头） */
	public static float parseFloat(String str) {
		if (str == null || str.length() == 0)
			return 0;
		if (str.charAt(0) == '#')
			return Long.parseLong(str.substring(1), 16);
		if (str.length() > 1 && str.charAt(0) == '0' && str.charAt(1) == 'x')
			return Long.parseLong(str.substring(2), 16);
		return Float.parseFloat(str);
	}

	/** 转换字符串为双浮点数，可以分析16进制数字（以#或0x开头） */
	public static double parseDouble(String str) {
		if (str == null || str.length() == 0)
			return 0;
		if (str.charAt(0) == '#')
			return Long.parseLong(str.substring(1), 16);
		if (str.length() > 1 && str.charAt(0) == '0' && str.charAt(1) == 'x')
			return Long.parseLong(str.substring(2), 16);
		return Double.parseDouble(str);
	}

	/** 转换字符串数组为整数数组，可以分析16进制数字（以#或0x开头） */
	public static int[] parseIntArray(String[] strs) {
		if (strs == null)
			return null;
		int[] array = new int[strs.length];
		for (int i = 0; i < strs.length; i++)
			array[i] = parseInt(strs[i]);
		return array;
	}

	/** 转换字符串数组为浮点数数组，可以分析16进制数字（以#或0x开头） */
	public static float[] parseFloatArray(String[] strs) {
		if (strs == null)
			return null;
		float[] array = new float[strs.length];
		for (int i = 0; i < strs.length; i++)
			array[i] = parseFloat(strs[i]);
		return array;
	}

	/** 转换字符串为布尔值，0或false为假，1或true为真 */
	public static boolean parseBoolean(String str) {
		if (str == null || str.length() == 0)
			return false;
		if (str.length() == 1)
			return str.charAt(0) == '1';
		return str.equalsIgnoreCase("true");
	}

	/** 字符串分解方法，以separator为分隔字符把str字符串分解成字符串数组 */
	public static String[] split(String str, char separator) {
		if (str == null)
			return EMPTY_STRING_ARRAY;
		if (str.length() == 0)
			return NULL_STRING_ARRAY;
		int i = 0, j = 0, n = 1;
		while ((j = str.indexOf(separator, i)) >= 0) {
			i = j + 1;
			n++;
		}
		String[] strs = new String[n];
		if (n == 1) {
			strs[0] = str;
			return strs;
		}
		i = j = n = 0;
		while ((j = str.indexOf(separator, i)) >= 0) {
			strs[n++] = (i == j) ? EMPTY_STRING : str.substring(i, j);
			i = j + 1;
		}
		strs[n] = (i >= str.length()) ? EMPTY_STRING : str.substring(i);
		return strs;
	}

	/** 字符串分解方法，以separator为分隔字符串把str字符串分解成字符串数组 */
	public static String[] split(String str, String separator) {
		if (str == null)
			return EMPTY_STRING_ARRAY;
		if (str.length() == 0)
			return NULL_STRING_ARRAY;
		int len = (separator != null) ? separator.length() : 0;
		if (len == 0) {
			String[] strs = { str };
			return strs;
		}
		int i = 0, j = 0, n = 1;
		while ((j = str.indexOf(separator, i)) >= 0) {
			i = j + len;
			n++;
		}
		String[] strs = new String[n];
		if (n == 1) {
			strs[0] = str;
			return strs;
		}
		i = j = n = 0;
		while ((j = str.indexOf(separator, i)) >= 0) {
			strs[n++] = (i == j) ? EMPTY_STRING : str.substring(i, j);
			i = j + len;
		}
		strs[n] = (i >= str.length()) ? EMPTY_STRING : str.substring(i);
		return strs;
	}

	/** 将字符串分解成多行字符串，“\n”为换行符，自动去掉“\r” */
	public static String[] splitLine(String str) {
		String[] strs = split(str, '\n');
		int start, end;
		for (int i = 0; i < strs.length; i++) {
			start = 0;
			end = strs[i].length();
			if (end == 0)
				continue;
			if (strs[i].charAt(0) == '\r')
				start++;
			if (strs[i].charAt(end - 1) == '\r')
				end--;
			if (start >= end)
				strs[i] = EMPTY_STRING;
			else if ((end - start) < strs[i].length())
				strs[i] = strs[i].substring(start, end);
		}
		return strs;
	}

	/**
	 * 字符串替换方法， 把str字符串中的从start到start+count的子字符串替换为swap
	 */
	public static String replace(String str, String swap, int start, int count) {
		if (str == null)
			return null;
		int len = str.length() + swap.length() - count;
		CharBuffer cb = new CharBuffer(len);
		cb.append(str.substring(0, start)).append(swap);
		cb.append(str.substring(start + count));
		return cb.getString();
	}

	/**
	 * 字符串替换方法， 把str字符串中的从start到start+count的子字符串替换为swap， cb为临时使用的字符串缓存
	 */
	public static String replace(String str, String swap, int start, int count, CharBuffer cb) {
		if (str == null)
			return null;
		int len = str.length() + swap.length() - count;
		cb.clear();
		cb.setCapacity(len);
		cb.append(str.substring(0, start)).append(swap);
		cb.append(str.substring(start + count));
		return cb.getString();
	}

	/** 字符串替换方法 */
	public static String replace(String str, String target, String swap) {
		if (str == null)
			return null;
		return replace(str, target, swap, false, null);
	}

	/**
	 * 字符串替换方法，使用java默认的字符串查找方法， str为字符串，target为目标字符串，
	 * swap为替换字符串，caseless为忽略大小写， cb为临时使用的字符串缓存
	 */
	public static String replace(String str, String target, String swap, boolean caseless, CharBuffer cb) {
		if (str == null)
			return null;
		String str1 = str;
		String target1 = target;
		if (caseless) {
			str1 = str.toLowerCase();
			target1 = target.toLowerCase();
		}
		int index = str1.indexOf(target1);
		if (index < 0)
			return str;
		if (cb != null) {
			cb.clear();
			cb.setCapacity(str.length() + swap.length() - target.length());
		} else
			cb = new CharBuffer(str.length() + swap.length() - target.length());
		return replace(str, swap, index, target.length(), cb);
	}

	/**
	 * 替换全部的字符串替换方法，使用java默认的字符串查找方法， str为字符串，target为目标字符串，swap为替换字符串
	 */
	public static String replaceAll(String str, String target, String swap) {
		if (str == null)
			return null;
		return replaceAll(str, target, swap, false, null);
	}

	/**
	 * 替换全部的字符串替换方法，使用java默认的字符串查找方法， str为字符串，target为目标字符串，
	 * swap为替换字符串，caseless为忽略大小写
	 */
	public static String replaceAll(String str, String target, String swap, boolean caseless) {
		if (str == null)
			return null;
		return replaceAll(str, target, swap, caseless, null);
	}

	/**
	 * 替换全部的字符串替换方法，使用java默认的字符串查找方法， str为字符串，target为目标字符串，
	 * swap为替换字符串，caseless为忽略大小写， cb为临时使用的字符串缓存
	 */
	public static String replaceAll(String str, String target, String swap, boolean caseless, CharBuffer cb) {
		if (str == null)
			return null;
		int len = target.length();
		if (len == 0)
			return str;
		String str1 = str;
		String target1 = target;
		if (caseless) {
			str1 = str.toLowerCase();
			target1 = target.toLowerCase();
		}
		int start = 0;
		int end = str1.indexOf(target1, start);
		if (end < 0)
			return str;
		if (cb != null) {
			cb.clear();
			cb.setCapacity(str.length());
		} else
			cb = new CharBuffer(str.length());
		while (end >= 0) {
			cb.append(str.substring(start, end)).append(swap);
			start = end + len;
			end = str1.indexOf(target1, start);
		}
		cb.append(str.substring(start));
		return cb.getString();
	}

	/** 得到对象的基本表示 */
	public static String getBaseString(Object obj) {
		CharBuffer cb = new CharBuffer();
		getBaseString(obj, cb);
		return cb.getString();
	}

	/** 得到对象的基本表示 */
	public static void getBaseString(Object obj, CharBuffer cb) {
		cb.append(obj.getClass().getName()).append('@').append(obj.hashCode());
	}

	/** 将指定的布尔数数组转换成字符串 */
	public static String toString(boolean[] array) {
		if (array == null)
			return "boolean[]null";
		CharBuffer cb = new CharBuffer(array.length * 2 + 2);
		cb.append('{');
		toString(array, 0, array.length, ",", cb);
		cb.append('}');
		return cb.getString();
	}

	/** 将指定的布尔数数组转换成字符串 */
	public static String toString(boolean[] array, int index, int length) {
		CharBuffer cb = new CharBuffer(length * 2 + 2);
		cb.append('{');
		toString(array, index, length, ",", cb);
		cb.append('}');
		return cb.getString();
	}

	/** 将指定的布尔数数组转换成字符串 */
	public static String toString(boolean[] array, int index, int length, String separator) {
		CharBuffer cb = new CharBuffer(length * (separator.length() + 1) + 2);
		cb.append('{');
		toString(array, index, length, separator, cb);
		cb.append('}');
		return cb.getString();
	}

	/** 将指定的布尔数数组转换成字符串 */
	public static void toString(boolean[] array, int index, int length, String separator, CharBuffer cb) {
		int n = index + length - 1;
		for (int i = index; i < n; i++)
			cb.append((array[i]) ? '1' : '0').append(separator);
		if (n >= 0)
			cb.append(array[n]);
	}

	/** 将指定的字节数组转换成字符串 */
	public static String toString(byte[] array) {
		if (array == null)
			return "byte[]null";
		CharBuffer cb = new CharBuffer(array.length * 5 + 2);
		cb.append('{');
		toString(array, 0, array.length, ",", cb);
		cb.append('}');
		return cb.getString();
	}

	/** 将指定的字节数组转换成字符串 */
	public static String toString(byte[] array, int index, int length) {
		CharBuffer cb = new CharBuffer(length * 5 + 2);
		cb.append('{');
		toString(array, index, length, ",", cb);
		cb.append('}');
		return cb.getString();
	}

	/** 将指定的字节数数组转换成字符串 */
	public static String toString(byte[] array, int index, int length, String separator) {
		CharBuffer cb = new CharBuffer(length * (separator.length() + 4) + 2);
		cb.append('{');
		toString(array, index, length, separator, cb);
		cb.append('}');
		return cb.getString();
	}

	/** 将指定的字节数数组转换成字符串 */
	public static void toString(byte[] array, int index, int length, String separator, CharBuffer cb) {
		int n = index + length - 1;
		for (int i = index; i < n; i++)
			cb.append(array[i]).append(separator);
		if (n >= 0)
			cb.append(array[n]);
	}

	/** 将指定的短整数数组转换成字符串 */
	public static String toString(short[] array) {
		if (array == null)
			return "short[]null";
		CharBuffer cb = new CharBuffer(array.length * 6 + 2);
		cb.append('{');
		toString(array, 0, array.length, ",", cb);
		cb.append('}');
		return cb.getString();
	}

	/** 将指定的短整数数组转换成字符串 */
	public static String toString(short[] array, int index, int length) {
		CharBuffer cb = new CharBuffer(length * 6 + 2);
		cb.append('{');
		toString(array, index, length, ",", cb);
		cb.append('}');
		return cb.getString();
	}

	/** 将指定的短整数数组转换成字符串 */
	public static String toString(short[] array, int index, int length, String separator) {
		CharBuffer cb = new CharBuffer(length * (separator.length() + 5) + 2);
		cb.append('{');
		toString(array, index, length, separator, cb);
		cb.append('}');
		return cb.getString();
	}

	/** 将指定的短整数数组转换成字符串 */
	public static void toString(short[] array, int index, int length, String separator, CharBuffer cb) {
		int n = index + length - 1;
		for (int i = index; i < n; i++)
			cb.append(array[i]).append(separator);
		if (n >= 0)
			cb.append(array[n]);
	}

	/** 将指定的字符数组转换成字符串 */
	public static String toString(char[] array) {
		if (array == null)
			return "char[]null";
		CharBuffer cb = new CharBuffer(array.length * 2 + 2);
		cb.append('{');
		toString(array, 0, array.length, ",", cb);
		cb.append('}');
		return cb.getString();
	}

	/** 将指定的字符数组转换成字符串 */
	public static String toString(char[] array, int index, int length) {
		CharBuffer cb = new CharBuffer(length * 2 + 2);
		cb.append('{');
		toString(array, index, length, ",", cb);
		cb.append('}');
		return cb.getString();
	}

	/** 将指定的字符数组转换成字符串 */
	public static String toString(char[] array, int index, int length, String separator, String prefix, String suffix) {
		CharBuffer cb = new CharBuffer(length * (separator.length() + 1) + prefix.length() + suffix.length());
		cb.append(prefix);
		toString(array, index, length, separator, cb);
		cb.append(suffix);
		return cb.getString();
	}

	/** 将指定的字符数组转换成字符串 */
	public static void toString(char[] array, int index, int length, String separator, CharBuffer cb) {
		int n = index + length - 1;
		for (int i = index; i < n; i++)
			cb.append(array[i]).append(separator);
		if (n >= 0)
			cb.append(array[n]);
	}

	/** 将指定的整数数组转换成字符串 */
	public static String toString(int[] array) {
		if (array == null)
			return "int[]null";
		CharBuffer cb = new CharBuffer(array.length * 9 + 2);
		cb.append('{');
		toString(array, 0, array.length, ",", cb);
		cb.append('}');
		return cb.getString();
	}

	/** 将指定的整数数组转换成字符串 */
	public static String toString(int[] array, int index, int length) {
		CharBuffer cb = new CharBuffer(length * 9 + 2);
		cb.append('{');
		toString(array, index, length, ",", cb);
		cb.append('}');
		return cb.getString();
	}

	/** 将指定的整数数组转换成字符串 */
	public static String toString(int[] array, int index, int length, String separator) {
		CharBuffer cb = new CharBuffer(length * (separator.length() + 8) + 2);
		cb.append('{');
		toString(array, index, length, separator, cb);
		cb.append('}');
		return cb.getString();
	}

	/** 将指定的整数数组转换成字符串 */
	public static void toString(int[] array, int index, int length, String separator, CharBuffer cb) {
		int n = index + length - 1;
		for (int i = index; i < n; i++)
			cb.append(array[i]).append(separator);
		if (n >= 0)
			cb.append(array[n]);
	}

	/** 将指定的长整数数组转换成字符串 */
	public static String toString(long[] array) {
		if (array == null)
			return "long[]null";
		CharBuffer cb = new CharBuffer(array.length * 16 + 2);
		cb.append('{');
		toString(array, 0, array.length, ",", cb);
		cb.append('}');
		return cb.getString();
	}

	/** 将指定的长整数数组转换成字符串 */
	public static String toString(long[] array, int index, int length) {
		CharBuffer cb = new CharBuffer(length * 16 + 2);
		cb.append('{');
		toString(array, index, length, ",", cb);
		cb.append('}');
		return cb.getString();
	}

	/** 将指定的长整数数组转换成字符串 */
	public static String toString(long[] array, int index, int length, String separator) {
		CharBuffer cb = new CharBuffer(length * (separator.length() + 15) + 2);
		cb.append('{');
		toString(array, index, length, separator, cb);
		cb.append('}');
		return cb.getString();
	}

	/** 将指定的长整数数组转换成字符串 */
	public static void toString(long[] array, int index, int length, String separator, CharBuffer cb) {
		int n = index + length - 1;
		for (int i = index; i < n; i++)
			cb.append(array[i]).append(separator);
		if (n >= 0)
			cb.append(array[n]);
	}

	/** 将指定的浮点数数组转换成字符串 */
	public static String toString(float[] array) {
		if (array == null)
			return "float[]null";
		CharBuffer cb = new CharBuffer(array.length * 10 + 2);
		cb.append('{');
		toString(array, 0, array.length, ",", cb);
		cb.append('}');
		return cb.getString();
	}

	/** 将指定的浮点数数组转换成字符串 */
	public static String toString(float[] array, int index, int length) {
		CharBuffer cb = new CharBuffer(length * 10 + 2);
		cb.append('{');
		toString(array, index, length, ",", cb);
		cb.append('}');
		return cb.getString();
	}

	/** 将指定的浮点数数组转换成字符串 */
	public static String toString(float[] array, int index, int length, String separator) {
		CharBuffer cb = new CharBuffer(length * (separator.length() + 9) + 2);
		cb.append('{');
		toString(array, index, length, separator, cb);
		cb.append('}');
		return cb.getString();
	}

	/** 将指定的浮点数数组转换成字符串 */
	public static void toString(float[] array, int index, int length, String separator, CharBuffer cb) {
		int n = index + length - 1;
		for (int i = index; i < n; i++)
			cb.append(array[i]).append(separator);
		if (n >= 0)
			cb.append(array[n]);
	}

	/** 将指定的双浮点数数组转换成字符串 */
	public static String toString(double[] array) {
		if (array == null)
			return "double[]null";
		CharBuffer cb = new CharBuffer(array.length * 16 + 2);
		cb.append('{');
		toString(array, 0, array.length, ",", cb);
		cb.append('}');
		return cb.getString();
	}

	/** 将指定的双浮点数数组转换成字符串 */
	public static String toString(double[] array, int index, int length) {
		CharBuffer cb = new CharBuffer(length * 16 + 2);
		cb.append('{');
		toString(array, index, length, ",", cb);
		cb.append('}');
		return cb.getString();
	}

	/** 将指定的双浮点数数组转换成字符串 */
	public static String toString(double[] array, int index, int length, String separator) {
		CharBuffer cb = new CharBuffer(length * (separator.length() + 15) + 2);
		cb.append('{');
		toString(array, index, length, separator, cb);
		cb.append('}');
		return cb.getString();
	}

	/** 将指定的双浮点数数组转换成字符串 */
	public static void toString(double[] array, int index, int length, String separator, CharBuffer cb) {
		int n = index + length - 1;
		for (int i = index; i < n; i++)
			cb.append(array[i]).append(separator);
		if (n >= 0)
			cb.append(array[n]);
	}

	/** 将指定的对象数组转换成字符串 */
	public static String toString(Object[] array) {
		if (array == null)
			return "Object[]null";
		CharBuffer cb = new CharBuffer(array.length * 25 + 2);
		cb.append('{');
		toString(array, 0, array.length, ",", cb);
		cb.append('}');
		return cb.getString();
	}

	/** 将指定的对象数组转换成字符串 */
	public static String toString(Object[] array, int index, int length) {
		CharBuffer cb = new CharBuffer(array.length * 25 + 2);
		cb.append('{');
		toString(array, index, length, ",", cb);
		cb.append('}');
		return cb.getString();
	}

	/** 将指定的对象数组转换成字符串 */
	public static String toString(Object[] array, int index, int length, String separator, String prefix,
			String suffix) {
		CharBuffer cb = new CharBuffer(length * (separator.length() + 24) + prefix.length() + suffix.length());
		cb.append(prefix);
		toString(array, index, length, separator, cb);
		cb.append(suffix);
		return cb.getString();
	}

	/** 将指定的对象数组转换成字符串 */
	public static void toString(Object[] array, int index, int length, String separator, CharBuffer cb) {
		int n = index + length - 1;
		for (int i = index; i < n; i++)
			cb.append(array[i]).append(separator);
		if (n >= 0)
			cb.append(array[n]);
	}

	/** 获得指定分隔符分隔的子字符串在字符串中的位置，-1表示不包含 */
	public static int subWith(String str, String subString, String separator) {
		if (subString == null || subString.length() == 0)
			return -1;
		if (subString.length() > str.length())
			return -1;
		if (subString.length() == str.length())
			return str.equals(subString) ? 0 : -1;
		int i = 0, j = 0, k = 0;
		while (true) {
			j = str.indexOf(subString, i);
			if (j < 0)
				return -1;
			i = j + subString.length();
			k = j;
			if (k > 0) {
				k = j - separator.length();
				if (k < 0)
					continue;
				if (str.indexOf(separator, k) != k)
					continue;
			}
			k = i;
			if (k == str.length())
				return j;
			if (k + separator.length() > str.length())
				return -1;
			if (str.indexOf(separator, k) != k)
				continue;
			return j;
		}
	}

	/** 二次劈分，获得指定键的值 */
	public static String getSubValue(String str, String subKey, String separator, String subSeparator) {
		if (subKey == null || subKey.length() == 0)
			return null;
		if (subKey.length() + subSeparator.length() > str.length())
			return null;
		int i = 0, j = 0, k = 0;
		while (true) {
			j = str.indexOf(subKey, i);
			if (j < 0)
				return null;
			i = j + subKey.length();
			k = j;
			if (k > 0) {
				k = j - separator.length();
				if (k < 0)
					continue;
				if (str.indexOf(separator, k) != k)
					continue;
			}
			k = i;
			if (k + subSeparator.length() > str.length())
				return null;
			if (k + subSeparator.length() == str.length())
				return EMPTY_STRING;
			if (str.indexOf(subSeparator, k) != k)
				continue;
			i = k + subSeparator.length();
			j = str.indexOf(separator, i);
			if (j < 0)
				return str.substring(i);
			return str.substring(i, j);
		}
	}

	/**
	 * 判断一个字符串是否有效，
	 * 
	 * @param charRangeSet为允许通过的字符范围集
	 *            ，两个字符为一组范围，
	 * @return 返回0表示成功，否则返回失败的字符
	 */
	public static char valid(String str, char[] charRangeSet) {
		char c;
		int len = str.length();
		for (int i = 0; i < len; i++) {
			c = str.charAt(i);
			if (!valid(c, charRangeSet))
				return c;
		}
		return 0;
	}

	/**
	 * 判断一个字符是否有效，
	 * 
	 * @param charRangeSet为允许通过的字符范围集
	 *            ，两个字符为一组范围，
	 */
	public static boolean valid(char c, char[] charRangeSet) {
		if (charRangeSet == null)
			return true;
		for (int i = 0, n = charRangeSet.length - 1; i < n; i += 2) {
			if (c >= charRangeSet[i] && c <= charRangeSet[i + 1])
				return true;
		}
		return false;
	}

	/**
	 * 正则表达式的$1功能*， 得到第一个begin和end间的字符串（不含）
	 */
	public static String betweenString(String str, String begin, String end) {
		return betweenString(str, begin, end, 0);
	}

	/**
	 * 正则表达式的$1功能*， 得到第一个begin和end间的字符串（不含），从指定的偏移量开始查找
	 */
	public static String betweenString(String str, String begin, String end, int offset) {
		if (str == null)
			return null;
		if (offset < 0)
			offset = 0;
		if (begin.length() + end.length() + offset > str.length())
			return null;
		int i = 0, j = str.length();
		if (begin != null && begin.length() > 0) {
			i = str.indexOf(begin, offset);
			if (i < 0)
				return null;
			i += begin.length();
		}
		if (end != null && end.length() > 0) {
			j = str.indexOf(end, i);
			if (j < 0)
				return null;
		}
		if (i == j)
			return EMPTY_STRING;
		return str.substring(i, j);
	}

	/** 将数字转换成汉字数字 */
	public static String toChineseNumber(int number) {
		return toChineseNumber(number, false);
	}

	/** 将数字转换成汉字数字，参数upper表示是否大写 */
	public static String toChineseNumber(int number, boolean upper) {
		char[] digits = (upper) ? UPPER_DIGITS : DIGITS;
		char[] bits = (upper) ? UPPER_DIGIT_BITS : DIGIT_BITS;
		CharBuffer cb = new CharBuffer();
		toChineseNumber(number, cb, digits, bits, false);
		if (!upper && cb.length() > 1 && cb.read(0) == digits[1] && cb.read(1) == bits[1])
			cb.setOffset(1);
		return cb.getString();
	}

	/**
	 * 将数字转换成汉字数字并写入到CharBuffer中
	 * 
	 * @param number
	 *            表示要转换的数字，必须为正数
	 * @param cb
	 *            表示转换后的汉字数字要写入的CharBuffer
	 * @param digits
	 *            表示使用的汉字数字数组
	 * @param bits
	 *            表示使用的汉字单位数组
	 * @param fillZero
	 *            表示前面是否补零
	 */
	public static void toChineseNumber(int number, CharBuffer cb, char[] digits, char[] bits, boolean fillZero) {
		if (number < 0)
			throw new IllegalArgumentException(toString + " toChineseNumber, invalid number, number=" + number);
		if (number >= 100000000) {
			int n = number / 100000000;
			toChineseNumber(n, cb, digits, bits, fillZero);
			cb.append(bits[5]);
			number %= 100000000;
			if (number == 0)
				return;
			fillZero = true;
			if (n % 10 == 0) {
				cb.append(digits[0]);
				fillZero = false;
			}
		}
		if (number >= 10000) {
			int n = number / 10000;
			toChineseNumber(n, cb, digits, bits, fillZero);
			cb.append(bits[4]);
			number %= 10000;
			if (number == 0)
				return;
			fillZero = true;
			if (n % 10 == 0) {
				cb.append(digits[0]);
				fillZero = false;
			}
		}
		boolean zero = false;
		for (int i = 3, n = 1000; i >= 0; i--, n /= 10) {
			int t = number / n;
			if (t != 0) {
				fillZero = true;
				if (zero) {
					cb.append(digits[0]);
					zero = false;
				}
				cb.append(digits[t]);
				if (i > 0)
					cb.append(bits[i]);
				fillZero = true;
			} else {
				if (fillZero)
					zero = true;
			}
			number %= n;
			if (number == 0)
				return;
		}
	}

	/**
	 * 将字符串截取指定长度(从头开始, 截取多少个字符)
	 * 
	 * @param text
	 *            要截取的字符串
	 * @param length
	 *            需要的长度
	 * @param encode
	 *            编码
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String substring(String text, int length, String encode) throws UnsupportedEncodingException {
		if (text == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		int currentLength = 0;
		for (char c : text.toCharArray()) {
			currentLength += String.valueOf(c).getBytes(encode).length;
			if (currentLength <= length) {
				sb.append(c);
			} else {
				break;
			}
		}
		return sb.toString();
	}

	/* constructors */
	private TextKit() {
	}

}