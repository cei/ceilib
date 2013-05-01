package cei.code.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cei.code.Code;
import cei.support.spring.repository.ibatis.DaoSupport;

@Repository("cei-code-dao")
public class CodeDao extends DaoSupport {

	public void save( Code param ) {
		insert( "cei.code.save", param );
	}

	public int update( Code param ) {
		return update( "cei.code.update", param );
	}

	public int remove( Code param ) {
		return delete( "cei.code.remove", param );
	}

	public Code getCode( Code param ) {
		return select( "cei.code.get", param );
	}

	public List<Code> getCodes( Code param ) {
		return list( "cei.code.get", param );
	}

	public List<Code> getTree( Code param ) {
		return list( "cei.code.tree", param );
	}
}
