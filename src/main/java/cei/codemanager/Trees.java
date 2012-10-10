package cei.codemanager;

import java.util.List;

import cei.domains.SystemCode;

public interface Trees {
	List<SystemCode> getTree(String group, String code);
	List<SystemCode> getTree(String group, String code, int level);
	List<SystemCode> getTree(String group, String code, boolean use);
	List<SystemCode> getTree(String group, String code, int level, boolean use);

	void save(SystemCode systemCode);
	int garbage(SystemCode systemCodes);
	int garbage(List<SystemCode> systemCodes);
	int recycle(SystemCode systemCodes);
	int recycle(List<SystemCode> systemCodes);
	int remove(SystemCode systemCodes);
	int remove(List<SystemCode> systemCodes);
}
