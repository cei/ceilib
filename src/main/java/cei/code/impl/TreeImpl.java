package cei.code.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import cei.code.ITree;
import cei.code.domains.Code;

@Service( "cei.tree" )
public class TreeImpl extends CodeManagerImpl implements ITree {

	public List<Code> getTree( String group, String code ) {
		return getTree( group, code, 0, true );
	}

	public List<Code> getTree( String group, String code, int level ) {
		return getTree( group, code, level, true );
	}

	public List<Code> getTree( String group, String code, boolean use ) {
		return getTree( group, code, 0, use );
	}

	public List<Code> getTree( String group, String code, int level, boolean use ) {
		Code param = new Code();
		param.setGroup( group );
		param.setCode( code );
		param.setLevel( level );
		param.setUse( use );
		return dao.getTree( param );
	}
}
