package cei.codemanager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cei.domains.SystemCode;


@Service("cei-system-code-service")
public class CodesImpl implements Codes {

	@Resource(name = "cei-system-code-dao")
	CodesDao dao;

	public void save(SystemCode param) {
		dao.save(param);
	}
	public int garbage(SystemCode param) {
		return update(param, false);
	}

	public int garbage(List<SystemCode> params) {
		return update(params, false);
	}
	public int recycle(SystemCode param) {
		return update(param, true);
	}
	public int recycle(List<SystemCode> params) {
		return update(params, true);
	}
	public int remove(SystemCode param) {
		return delete(param);
	}
	public int remove(List<SystemCode> params) {
		return delete(params);
	}

	public SystemCode getCode(String code) {
		return getCode(Codes.COMMON_CODE, code);
	}
	public SystemCode getCode(String code, boolean use) {
		return getCode(Codes.COMMON_CODE, code, use);
	}
	public SystemCode getCode(String group, String code) {
		return getCode(group, code, true);
	}
	public SystemCode getCode(String group, String code, boolean use) {
		SystemCode param = new SystemCode();
		param.setGroup(group);
		param.setCode(code);
		param.setUse(use);
		return dao.getCode(param);
	}

	public List<SystemCode> getCodes(String code) {
		return getCodes(Codes.COMMON_CODE, code, true);
	}
	public List<SystemCode> getCodes(String code, boolean use) {
		return getCodes(Codes.COMMON_CODE, code, use);
	}
	public List<SystemCode> getCodes(String group, String code) {
		return getCodes(group, code, true);
	}
	public List<SystemCode> getCodes(String group, String code, boolean use) {
		SystemCode param = new SystemCode();
		param.setGroup(group);
		param.setParent(code);
		param.setUse(use);
		return dao.getCodes(param);
	}

	private int update(Object object, boolean garbage) {
		int result = 0;
		
		if(object instanceof List) {
			@SuppressWarnings("unchecked")
			List<SystemCode> params = (List<SystemCode>)object;

			for(SystemCode param : params) {
				param.setUse(garbage);
				result += dao.update(param);
			}
		}
		else {
			SystemCode param = (SystemCode)object;
			param.setUse(garbage);
			result = dao.update(param);
		}

		return result;
	}

	private int delete(Object object) {
		int result = 0;
		
		if(object instanceof List) {
			@SuppressWarnings("unchecked")
			List<SystemCode> params = (List<SystemCode>)object;
			for(SystemCode param : params) result += dao.remove(param);
		}
		else {
			SystemCode param = (SystemCode)object;
			result = dao.remove(param);
		}

		return result;
	}
}
