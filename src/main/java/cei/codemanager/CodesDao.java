package cei.codemanager;

import java.util.List;

import org.springframework.stereotype.Repository;

import cei.domains.SystemCode;
import cei.support.spring.repository.ibatis.DaoSupport;


@Repository("cei-system-code-dao")
public class CodesDao extends DaoSupport {

	//Codes
	public void save(SystemCode param) {
		insert("system.code-save", param);
	}
	public int update(SystemCode param) {
		return update("system.code-update", param);
	}
	public int remove(SystemCode param) {
		return delete("system.code-remove", param);
	}

	public SystemCode getCode(SystemCode param) {
		return select("system.code-get", param);
	}
	public List<SystemCode> getCodes(SystemCode param) {
		return list("system.code-get", param);
	}


	//Trees
	public List<SystemCode> getTree(SystemCode param) {
		return list("system.tree", param);
	}
}
