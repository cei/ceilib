package cei.code;

import java.util.List;

import cei.code.domains.Code;


public interface ITree extends ICode {
	List<Code> getTree( String group, String code );
	List<Code> getTree( String group, String code, int level );
	List<Code> getTree( String group, String code, boolean use );
	List<Code> getTree( String group, String code, int level, boolean use );
}
