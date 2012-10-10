package cei.codemanager;

import java.util.List;

import cei.domains.SystemCode;

public interface Codes {
	String COMMON_CODE = "COMMON_CODE";

	void save(SystemCode param);
	int garbage(SystemCode param);
	int garbage(List<SystemCode> params);
	int recycle(SystemCode param);
	int recycle(List<SystemCode> params);
	int remove(SystemCode param);
	int remove(List<SystemCode> params);
	
	SystemCode getCode(String code);
	SystemCode getCode(String code, boolean use);
	SystemCode getCode(String group, String code);
	SystemCode getCode(String group, String code, boolean use);

	List<SystemCode> getCodes(String code);
	List<SystemCode> getCodes(String code, boolean use);
	List<SystemCode> getCodes(String group, String code);
	List<SystemCode> getCodes(String group, String code, boolean use);
}
