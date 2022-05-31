package com.platform.modules.util;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.annotation.Annotation;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.UUID;
import java.util.Vector;

import org.apache.commons.io.IOUtils;

public class ToolsUtil {

	private final static String[] simpleClass = { "java.lang.String", "java.lang.Double", "java.lang.Integer", "java.lang.Float", "java.lang.Lang", "java.lang.Number",
			"java.lang.Short" };

	/**
	 * 检查obj是否为空(包括""和null)
	 *
	 * @param obj
	 * @return 空返回true
	 */
	public static boolean isEmpty(Object obj) {
		return obj == null || "".equals(obj.toString());
	}

	/**
	 * 在特定的map&lt;id:p_id&gt;中,用来判定是否存在从属关系,即判断id是否从属于p_id(可能包含多层),
	 * id和p_id相同将返回false
	 *
	 * @param map
	 *            特定格式的map,格式为&lt;id,p_id&gt;
	 * @param id
	 * @param p_id
	 * @return
	 */
	public static boolean checkParent(Map<String, String> map, String id, String p_id) {
		while (true) {
			if (map.containsKey(id)) {
				return p_id.equals(map.get(id)) || checkParent(map, map.get(id), p_id);
			} else {
				break;
			}
		}
		return false;
	}
	/**
	 * 数组转换成字符串,用split隔开
	 *
	 * @param array
	 *            数组,可以是简单类型的数组String[],Integer[],Double[]等
	 * @param split
	 *            分隔字符
	 * @return
	 */
	public static String Array2String(Object[] array, String split) {
		if (!(array == null || array.length == 0)) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; i++)
				sb.append(array[i]).append(split);
			sb.delete(sb.length() - split.length(), sb.length());
			return sb.toString();
		} else {
			return null;
		}
	}
	/**
	 * 把一个bean对象转换成Map
	 *
	 * @param bean
	 * @return
	 */
	public static Map<String, Object> getMapByBean(Object bean) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			java.beans.BeanInfo info = java.beans.Introspector.getBeanInfo(bean.getClass());
			if (info != null) {
				java.beans.PropertyDescriptor pds[] = info.getPropertyDescriptors();
				for (java.beans.PropertyDescriptor pd : pds) {
					String fieldName = pd.getName();
					if (fieldName != null && !fieldName.equals("class")) {
						java.lang.reflect.Method method = pd.getReadMethod();
						try {
							Object value = method.invoke(bean);
							// System.out.println("[" +
							// bean.getClass().getName() + "]." +
							// pd.getName() + "("
							// + pd.getPropertyType().getName() + ") = " +
							// value);
							if (!ToolsUtil.isEmpty(value)) {
								map.put(pd.getName(), value);
								// System.out.println("PUT:" + pd.getName() +
								// "=" + value);
							}
						} catch (Exception e) {
							System.out.println("读取[" + pd.getName() + "]参数值异常!");
							e.printStackTrace();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
