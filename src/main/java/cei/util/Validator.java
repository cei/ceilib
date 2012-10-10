package cei.util;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public abstract class Validator {
	public static boolean isExpired(Date date, int targetcount) {
		if(date == null) return false;

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, -targetcount);

		Calendar target = Calendar.getInstance();

		target.setTime(date);
		return calendar.after(target);
	}

	public static boolean isJumin(String ssn) {
		if(ssn == null) return false;

		ssn = ssn.replaceAll("-", "").replaceAll(",", "").replaceAll("\\.", "");
		if(!Pattern.matches("[0-9]{13}", ssn)) return false;
		
		
		int sum = 0;
		int[] template = new int[]{2,3,4,5,6,7,8,9,2,3,4,5};
		for(int i = 0; i < template.length; i++)
			sum += Integer.parseInt(ssn.substring(i, i + 1)) * template[i];

		if(Integer.parseInt(ssn.substring(12)) == (11 - sum % 11) % 10)
			return true;
		
		return false;
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
	 */
	public static void charRemover(Object obj, String target, String ... fieldNames) {
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
}
