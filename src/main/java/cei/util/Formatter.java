package cei.util;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.sql.Clob;;

/**
 * 기본적인 데이터 형태를 정의
 * @author (C)2011 Cheoeumin Co,.Ltd.
 * @since 1.0
 */
public abstract class Formatter {
	private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	private static final DecimalFormat df = new DecimalFormat();
	private static final String ONLY_BYTES = " Byte";
	private static final String KILO_BYTES = " KB";
	private static final String MEGA_BYTES = " MB";

	public static String decimal(Number number) {
		return df.format(number);
	}
	
	public static String decimal(Number number, String format) {
		df.applyPattern(format);
		return decimal(number);
	}
	
	public static String today() {
		return new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(new Date());
	}

	public static String today(int i) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, i);
		return new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(c.getTime());
	}

	public static String todate(String format) {
		return new SimpleDateFormat(format).format(new Date());
	}

	public static Map<String, Object> paginate(int page, int rowCount, int rowPerPage, int pagePerList) {
		int totalPage = new Double(Math.ceil((double)rowCount / (double)rowPerPage)).intValue();
		int startPageNum = (((page - 1) / pagePerList) * 10) + 1;

		List<Integer> pages = new ArrayList<Integer>(pagePerList);

		for(int i = startPageNum; i < startPageNum + pagePerList; i++) {
			if(i > totalPage) break;
			pages.add(i);
		}
		
		Map<String, Object> items = new HashMap<String, Object>(4);
		items.put("totalPage", totalPage);
		items.put("rowCount", rowCount);
		items.put("page", page);
		items.put("pages", pages);
		items.put("prev", startPageNum - 1 < 1 ? startPageNum = 1 : startPageNum - 1);
		items.put("next", startPageNum + pagePerList > totalPage ? totalPage : startPageNum + pagePerList);
		return items;
	}
	
	public static String CLOB2String(Clob data) {
		if(data == null) return "";

		StringBuffer sb = new StringBuffer();
		Reader reader = null;
		char[] ch = new char[1024];
		Clob c = (Clob)data;

		try {
			reader = c.getCharacterStream();
			
			while(reader.read(ch) > 0) {
				sb.append(ch);
			}

			return sb.toString();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(reader != null) {
				try {
					reader.close();
				}
				catch(IOException ioe) {
					ioe.printStackTrace();
				}

				reader = null;
			}
		}
		
		return "";
	}
	
	public static String nl2Br(String data) {
		if(data == null) return "";
		return data.replaceAll("\n", "<br>");
	}

	/**
	 * <pre>
	 * target으로 지정한 문자를 제거
	 * 
	 * &lt;조건&gt;
	 * - 필드가 String
	 * - filedNames에 필드의 명칭이 정의된 것
	 * 
	 * &lt;사용예&gt;
	 * <code>Formatter.removeAt(VO객체(Domain객체), 제거대상문자, 필드1, 필드2, ...)</code>
	 * </pre>
	 * 
	 * @param obj
	 * @param target
	 * @param fieldNames
	 * @author (C)2011 Cheoeumin Co,.Ltd
	 * @since 0.1
	 */
	public static void remove(Object obj, String target, String ... fieldNames) {
		Field[] fields = obj.getClass().getDeclaredFields();

		try {
			for(Field field : fields)
			{
				String name = field.getName();
				for(String fieldName : fieldNames)
				{
					if(name.equals(fieldName))
					{
						field.setAccessible(true);
						Object fieldValue = field.get(obj);
						
						if(fieldValue instanceof String) {
							field.set(obj, ((String)fieldValue).replaceAll(target, ""));
						}
					}
				}
			}
		}
		catch(IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * target으로 지정한 문자를 제거
	 * 
	 * &lt;조건&gt;
	 * - 필드가 String
	 * - filedNames에 필드의 명칭이 정의된 것
	 * 
	 * &lt;사용예&gt;
	 * <code>Validator.removeAt(VO객체(Domain객체), 제거대상문자, 필드1, 필드2, ...)</code>
	 * </pre>
	 * 
	 * @param obj
	 * @param target
	 * @param fieldNames
	 * @author (C)2011 Cheoeumin Co,.Ltd
	 */
	public static void removeAt(Object obj, String target, String ... fieldNames) {
		Field[] fields = obj.getClass().getDeclaredFields();

		try {
			for(Field field : fields)
			{
				String name = field.getName();
				for(String fieldName : fieldNames)
				{
					if(name.equals(fieldName))
					{
						field.setAccessible(true);
						Object fieldValue = field.get(obj);
						
						if(fieldValue instanceof String) {
							field.set(obj, ((String)fieldValue).replaceAll(target, ""));
						}
					}
				}
			}
		}
		catch(IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public static String filesize(Number filesize) {
		return filesize(filesize.toString());
	}

	public static String filesize(String filesize) {
		if(filesize != null) {
			Pattern pt = Pattern.compile("([0-9]+)");
			Matcher m = pt.matcher(filesize);
			
			StringBuffer result = new StringBuffer();
			if(m.find()) {
				do {
					result.append(m.group());
				}
				while(m.find());
	
				try {
					long parseValue = Long.valueOf(result.toString());
					double size = (parseValue > 0) ? parseValue / 1024 : 0;
	
					if(size < 10) return size + ONLY_BYTES;
					else if(size < 1000) return size + KILO_BYTES; 
					else {
						df.applyPattern("0.00");
						return df.format(size / 1000) + MEGA_BYTES; 
					}
				}
				catch(Exception e) {
					return "0" + ONLY_BYTES;
				}
			}
		}

		return "unknown size";
	}
}
