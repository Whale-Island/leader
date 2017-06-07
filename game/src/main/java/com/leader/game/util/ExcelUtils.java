package com.leader.game.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.leader.game.data.Element;
import com.leader.game.data.Scope;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import lombok.extern.log4j.Log4j;

@Log4j
public class ExcelUtils {
	public static <T> List<T> load(Class<T> t, String path)
			throws BiffException, IOException, InstantiationException, IllegalAccessException, NoSuchFieldException,
			SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
		List<T> list = new ArrayList<T>();
		Workbook rwb = null;
		Cell cell = null;
		T data = null;
		// 创建输入流
		InputStream stream = new FileInputStream(path);

		// 获取Excel文件对象
		rwb = Workbook.getWorkbook(stream);

		// 获取文件的指定工作表 默认的第一个
		Sheet sheet = rwb.getSheet(0);
		// 获取表头
		Cell[] handers = sheet.getRow(1);
		Map<String, Integer> map = new HashMap<>();
		for (Cell hander : handers) {
			map.put(hander.getContents(), hander.getColumn());
		}

		// 行数(表头的目录不需要，从2开始)
		for (int i = 2; i < sheet.getRows(); i++) {
			data = t.newInstance();

			Field[] fs = t.getDeclaredFields();
			for (Field field : fs) {
				if (!map.containsKey(field.getName())) {
					log.error("表格未找到对象属性:" + t.getName() + "-->" + field.getName());
					throw new NullPointerException();
				}

				int column = map.get(field.getName());
				cell = sheet.getCell(column, i);
				// 该单元格是否禁用
				if (cell.getContents().toLowerCase().equals("null"))
					continue;

				field.setAccessible(true);
				Object value = null;

				// 必须是实现类
				if (field.getType().isAssignableFrom(HashMap.class)) {
					String[] strs = cell.getContents().split("\\|");
					Element e = field.getAnnotation(Element.class);
					Class<?>[] generics = e.generic();
					if (generics.length != 2)
						log.error(field.getName() + "缺少泛型注释!");

					value = field.getType().newInstance();
					Method m = field.getType().getMethod("put", Object.class, Object.class);
					for (int j = 0; j < strs.length; j++) {
						String[] kv = strs[j].split(":");
						Object k = transform(kv[0], generics[0]);
						Object v = transform(kv[1], generics[1]);
						m.invoke(value, k, v);
					}
				} else
					value = transform(cell.getContents(), field.getType());

				// 赋值
				field.set(data, value);
			}
			// 把刚获取的列存入list
			list.add(data);
		}
		return list;
	}

	/** 其他通用的赋值转换 */
	private static Object transform(String str, Class<?> c) {
		Object o = null;
		if (c.getName().equals("java.lang.String")) {
			o = str;
		} else if (c.getName().equals("int") || c.isAssignableFrom(Integer.class)) {
			o = Integer.valueOf(str);
		} else if (c.getName().equals("[I")) {// 数组
			String[] strs = str.split(",");
			int[] values = new int[strs.length];
			for (int j = 0; j < strs.length; j++) {
				values[j] = Integer.valueOf(strs[j]);
			}
			o = values;
		} else if (c.getName().equals("com.leader.game.data.Scope")) {// 范围值
			String[] strs = str.split("-");
			Scope scope = new Scope();
			scope.setMin(Integer.valueOf(strs[0]));
			scope.setMax(Integer.valueOf(strs[1]));

			o = scope;
		}
		return o;
	}
}
