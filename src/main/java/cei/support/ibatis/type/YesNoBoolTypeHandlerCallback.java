package cei.support.ibatis.type;

import java.sql.SQLException;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

public class YesNoBoolTypeHandlerCallback implements TypeHandlerCallback {
	public static final String YES = "Y";
	public static final String NO = "N";
	
	public Object getResult(ResultGetter getter) throws SQLException {
		return (getter == null || !YES.equalsIgnoreCase(getter.getString())) ? false : true;
	}
	
	public void setParameter(ParameterSetter setter, Object object) throws SQLException {
		setter.setString((object != null && (Boolean)object) ? YES : NO);
	}
	
	public Object valueOf(String str) {
		return str.equalsIgnoreCase(YES);
	}
	
	public static String valueOf(boolean bool) {
		return bool ? YES : NO;
	}
}
