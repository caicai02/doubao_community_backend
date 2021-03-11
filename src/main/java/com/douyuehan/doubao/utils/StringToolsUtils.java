package com.douyuehan.doubao.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.commons.lang3.math.NumberUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ZXW on 2014/11/12.
 */
public class StringToolsUtils {

	/**
	 *
	 * @param elements 传进来的数组
	 * @param size 表示拼接的元素个数
	 * @param regex 表示分隔符
	 * @return
	 */
	public static String splicingElement(String[] elements, int size, String regex) {
		String ret = "";
		for (int i = 0; i < size; i++) {
			if (i < size - 1)
				ret += elements[i] + regex;
			else {
				ret += elements[i];
			}
		}
		return ret;
	}


	public static String addStr(String addStr,String oldStr,int index){
		String[] strs=oldStr.split(",");
		if(index>strs.length-1){
			return null;
		}
		String newStr="";
		for(int i=0;i<strs.length;i++){
			if(i==index){
				newStr=newStr+addStr+",";
			}
			if(i==strs.length-1){
				newStr=newStr+strs[i];
			}else{
				newStr=newStr+strs[i]+",";
			}
		}
		return newStr;
	}
	

	/****
	 *
	 * 通过两点经纬度获取距离
	 */

	public static double Distance(double long1, double lat1, double long2, double lat2) {
		double a, b, R;
		R = 6378137; // 地球半径
		lat1 = lat1 * Math.PI / 180.0;
		lat2 = lat2 * Math.PI / 180.0;
		a = lat1 - lat2;
		b = (long1 - long2) * Math.PI / 180.0;
		double d;
		double sa2, sb2;
		sa2 = Math.sin(a / 2.0);
		sb2 = Math.sin(b / 2.0);
		d = 2
				* R
				* Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)
				* Math.cos(lat2) * sb2 * sb2));
		return d;
	}


	/****
	 * 将下划线分隔的字符串转换成驼峰格式(this_is_a_string --> thisIsAString)
	 * */
	public static String toCamelCase(String input) {
		StringBuilder sb = new StringBuilder();
		final char delim = '_';
		char value;
		boolean capitalize = false;
		for (int i = 0; i < input.length(); ++i) {
			value = input.charAt(i);
			if (value == delim) {
				capitalize = true;
			} else if (capitalize) {
				sb.append(Character.toUpperCase(value));
				capitalize = false;
			} else {
				sb.append(value);
			}
		}

		return sb.toString();
	}

	/**
	 *
	 *  将java对象转换成json格式输出
	 */

	public static String toJson(Object obj) {
		ObjectMapper om = new ObjectMapper();
		StringWriter sw = new StringWriter();
		try {
			om.writeValue(sw, obj);
			String str = sw.toString();
			sw.close();
			return str;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	public static String alibabaToJson(Object obj) {
		String jsonString = com.alibaba.fastjson.JSON.toJSONString(obj);
		return jsonString;
	}

	

	/****
	 * 将json字符串转换成Java对象
	 * */
	public static <T> T toObject(String data, Class<T> clazz) {
		ObjectMapper om = new ObjectMapper();
		om.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, false);//设置可用单引号
		om.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, false);//设置字段可以不用双引号包括
		try {
			return om.readValue(data, clazz);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T toObject(String data, TypeReference<T> tr) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, false);//设置可用单引号
		objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, false);//设置字段可以不用双引号包括
		try {
			return objectMapper.readValue(data, tr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/***
	 * 截取字符串前num位
	 * */
	public static String cutStr(String str, int limit) {
		if (str.length() > limit) {
			return str.substring(0, limit) + "……";
		} else {
			return str;
		}
	}

	public static List<Integer> StringToList(String str, String split) {
		if (str == null || "".equals(str)) {
			return null;
		} else {
			List<Integer> ids = new LinkedList<Integer>();
			if (str.contains(split)) {
				String[] strs = str.split(split);
				for (String st : strs) {
					if (NumberUtils.isNumber(st)) {
						ids.add(Integer.parseInt(st));
					}
				}
			} else {
				if (NumberUtils.isNumber(str)) {
					ids.add(Integer.parseInt(str));
				}
			}
			if (ids.size() > 0) {
				return ids;
			} else {
				return null;
			}

		}
	}

	/**
	 * @description 字符串判空 （空串或null返回false）
	 * @param input
	 *            可变参数
	 * @return boolean 非空返回false
	 * @author don
	 * @time 2015年3月16日 上午10:49:15
	 */
	public static boolean isBlank(String... input) {
		if (input == null || input.length == 0) {
			return true;
		}
		for (String str : input) {
			if (str == null ? true : ("".equals(str.trim()) ? true : false)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @description 多对象全属性判空
	 * @param obj
	 *            可变参数
	 * @return boolean 非空返回false
	 * @author don
	 * @time 2015年3月18日 下午4:01:33
	 */
	public static boolean isBlankManyObj(Object... obj) {
		// 遍历传入的 对象数组
		for (Object object : obj) {
			if (object == null) {
				return true;
			}

			Field[] fields = object.getClass().getDeclaredFields();
			for (Field field : fields) {
				// 获取当前对象的属性名，封装成对应 get方法
				String field_name = field.getName();
				field_name = field_name.substring(0, 1).toUpperCase() + field_name.substring(1);

				// 调用对应方法获取 对象的属性值
				Method method;
				try {
					method = object.getClass().getMethod("get" + field_name);

					if (String.class.equals(method.getReturnType())) {
						// 字符串判空
						String value = (String) method.invoke(object);
						if (isBlank(value)) {
							return true;
						}
					} else {
						// 其他类型 判null
						Object value = (Object) method.invoke(object);
						if (value == null) {
							return true;
						}
					}
				} catch (Exception e) {
					continue;
				}
			}

		}
		return false;

	}

	/**
	 * @description 单个对象判空
	 * @param object
	 * @param isinclude
	 *            属性包含与否
	 * @param fields
	 *            对象的属性名称
	 * @return boolean
	 * @author don
	 * @time 2015年3月18日 下午4:33:48
	 */
	public static boolean isBlankOneObj(Object object, boolean isinclude, String... fields) {
		if (object == null) {
			return true;
		}
		if (isBlank(fields)) {
			isinclude = false;
		}
		if (isinclude) {
			for (String field_name : fields) {
				// 调用对应方法获取 对象的属性值
				Method method;
				try {
					field_name = field_name.substring(0, 1).toUpperCase() + field_name.substring(1);
					method = object.getClass().getMethod("get" + field_name);

					if (String.class.equals(method.getReturnType())) {
						// 字符串判空
						String value = (String) method.invoke(object);
						if (isBlank(value)) {
							return true;
						}
					} else {
						// 其他类型 判null
						Object value = (Object) method.invoke(object);
						if (value == null) {
							return true;
						}
					}
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}

		} else {
			Field[] fields_ref = object.getClass().getDeclaredFields();

			for (Field field : fields_ref) {
				for (String notInclude : fields) {
					if (field.getName().equals(notInclude)) {
						field = null;
						break;
					}
				}
				if (field == null) {
					continue;
				}
				// 获取当前对象的属性名，封装成对应 get方法
				String field_name = field.getName();
				field_name = field_name.substring(0, 1).toUpperCase() + field_name.substring(1);

				// 调用对应方法获取 对象的属性值
				Method method;
				try {
					method = object.getClass().getMethod("get" + field_name);

					if (String.class.equals(method.getReturnType())) {
						// 字符串判空
						String value = (String) method.invoke(object);
						if (isBlank(value)) {
							return true;
						}
					} else {
						// 其他类型 判null
						Object value = (Object) method.invoke(object);
						if (value == null) {
							return true;
						}
					}
				} catch (Exception e) {
					continue;
				}
			}
		}

		return false;

	}

	/**
	 * @Descripiton 判断是否存在非空属性
	 * @param obj
	 * @return 存在为 true
	 *
	 * @author don
	 * @date 2015年7月28日 上午10:08:49
	 */
	public static boolean isNotAllBlankObj(Object... obj) {
		// 遍历传入的 对象数组
		for (Object object : obj) {
			if (object == null) {
				return false;
			}

			Field[] fields = object.getClass().getDeclaredFields();
			for (Field field : fields) {
				// 获取当前对象的属性名，封装成对应 get方法
				String field_name = field.getName();
				field_name = field_name.substring(0, 1).toUpperCase() + field_name.substring(1);

				// 调用对应方法获取 对象的属性值
				Method method;
				try {
					method = object.getClass().getMethod("get" + field_name);

					if (String.class.equals(method.getReturnType())) {
						// 字符串判空
						String value = (String) method.invoke(object);
						if (!isBlank(value)) {
							return true;
						}
					} else {
						// 其他类型 判null
						Object value = (Object) method.invoke(object);
						if (value != null) {
							return true;
						}
					}
				} catch (Exception e) {
					continue;
				}
			}

		}
		return false;
	}

	/**
	 * @description SQL防注入验证
	 * @param title
	 * @return boolean 非注入SQL返回true
	 * @author don
	 * @time 2015年3月18日 下午1:44:40
	 */
	public static boolean isRightSQL(String title) {
		if (title != null) {
			title = title.toLowerCase();// 统一转为小写
			String badStr = "'|and|exec|execute|insert|select|delete|update|count|drop|*|%|chr|mid|master|truncate|"
					+ "char|declare|sitename|net user|xp_cmdshell|;|or|-|+|,|like'|and|exec|execute|insert|create|drop|"
					+ "table|from|grant|use|group_concat|column_name|"
					+ "information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|*|"
					+ "chr|mid|master|truncate|char|declare|or|;|-|--|+|,|like|//|/|%|#";// 过滤掉的sql关键字，可以手动添加
			String[] badStrs = badStr.split("\\|");
			for (int i = 0; i < badStrs.length; i++) {
				if (title.indexOf(badStrs[i]) >= 0) {
					return false;
				}
			}

		}
		return true;
	}

	/**
	 * 将一个 JavaBean 对象转化为一个 Map
	 *
	 * @param bean
	 *            要转化的JavaBean 对象
	 * @return 转化出来的 Map 对象
	 * @throws IntrospectionException
	 *             如果分析类属性失败
	 * @throws IllegalAccessException
	 *             如果实例化 JavaBean 失败
	 * @throws InvocationTargetException
	 *             如果调用属性的 setter 方法失败
	 */
	public static Map<String, Object> BeanToMap(Object bean) throws IntrospectionException,
			IllegalAccessException,
			InvocationTargetException {
		Class<? extends Object> type = bean.getClass();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);

		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					returnMap.put(propertyName, result);
				}
			}
		}
		return returnMap;
	}

	/**
	 *删除对象文件 @注释语句
	 *
	 */
	public static void delSqlAnno() throws IOException {
		File file = new File("D:/Develop/GIT_rep/jinyi/console/src/main/java/com/jinyi/show/model");
		File[] files = file.listFiles();
		for (File nowFile : files) {
			FileReader read = new FileReader(nowFile);
			BufferedReader readb = new BufferedReader(read);

			File newFile = new File("D:/Develop/GIT_rep/jinyi/console/src/main/java/com/jinyi/show/" + nowFile.getName());
			if (!newFile.exists()) {
				newFile.createNewFile();
			}
			FileOutputStream out = new FileOutputStream(newFile);

			String line = null;
			while ((line = readb.readLine()) != null) {
				if (line.contains("@")) {
					System.out.println(line);
					continue;
				}
				if (line.contains("import")) {
					continue;
				}
				out.write((line + "\n").getBytes());
			}
			newFile.renameTo(new File(newFile.getName().substring(0, newFile.getName().length() - 1)));
			out.flush();
			out.close();
			readb.close();
		}
	}

	// 将对象转化成WIKI对象
	public static void getWikiObj(Class<?> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			System.out.println("|" + field.getName() + "|" + field.getType().getSimpleName() + "|false|" + "XXXXXX|");
		}
	}


	/**
	 * 判断多个int数组区间是否有重叠
	 * @param list
	 * @return
	 */
	public static boolean isExists(List<int []> list){
		for (int i = 0; i < list.size(); i++) {
			for (int j = i + 1; j < list.size(); j++) {
				if(list.get(i)[0]<list.get(j)[1]
						&&list.get(i)[0]>list.get(j)[0]
						|| (list.get(i)[1]<list.get(j)[1]
						&&list.get(i)[1]>list.get(j)[0])) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 判断一个字符串是否符合手机号格式
	 * @author mgj
	 * @date 2016年2月25日下午2:51:06
	 * @param str
	 * @return
	 */
	public static boolean isPhoneNum(String str) {
		if(isBlank(str)){
			return false;
		}
		Pattern pattern = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	/**
	 * 用指定的字符来替换某字符串中的字符区间(18013765032 --> 180****5032)
	 * @author mgj
	 * @date 2016年2月26日下午1:12:40
	 * @param originalStr 原始字符串
	 * @param startIndex 开始下标(包含该字符)
	 * @param endIndex 结束下标(不包含该字符)
	 * @param character 用来替换的字符(如 "*")
	 * @return
	 */
	public static String replaceWith(String originalStr, int startIndex, int endIndex, char character){
		//如果原字符串为空或负数(null, "", " ", -1), 或长度小于开始下标, 或结束下标<=开始下标, 则直接返回
		if(isBlank(originalStr) || originalStr.length() < startIndex || endIndex <= startIndex
				|| startIndex < 0 || endIndex < 0){
			return originalStr;
		}
		StringBuilder strBuilder= new StringBuilder(originalStr);
		for(int i = startIndex;
			i < (endIndex <= (originalStr.length() - 1) ? endIndex : originalStr.length());
			i ++ ){
			strBuilder.setCharAt(i, character);
		}
		return strBuilder.toString();
	}


	/**
	 *
	 * @Description: 生成随机字母
	 * @param @param c
	 * @param @return
	 * @return String
	 * @throws
	 * @author wx
	 * @date 2016年5月3日
	 */
	public static String getrandom(int c) {
		String str = "";
		for (int i = 0; i < c; i++) {// 你想生成几个字符的，就把7改成几，如果改成1,那就生成一个随机字母．
			str = str + (char) (Math.random() * 26 + 'A');
		}
		return str;
	}



	//json转List
	public static <T> List<T> getListFromJson(String json, Class<T[]> clazz) {
		try {
			T[] arr = new Gson().fromJson(json, clazz);
			return Arrays.asList(arr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}
