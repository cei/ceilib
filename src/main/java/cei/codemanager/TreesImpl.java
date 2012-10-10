package cei.codemanager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cei.domains.SystemCode;


@Service("cei-system-tree-service")
public class TreesImpl implements Trees {

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

	public List<SystemCode> getTree(String group, String code) {
		return getTree(group, code, 0, true);
	}
	public List<SystemCode> getTree(String group, String code, int level) {
		return getTree(group, code, level, true);
	}
	public List<SystemCode> getTree(String group, String code, boolean use) {
		return getTree(group, code, 0, use);
	}
	public List<SystemCode> getTree(String group, String code, int level, boolean use) {
		SystemCode param = new SystemCode();
		param.setGroup(group);
		param.setCode(code);
		param.setLevel(level);
		param.setUse(use);

		return dao.getTree(param);
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
