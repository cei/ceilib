package cei.code.impl;

import java.util.List;

import javax.annotation.Resource;

import cei.code.ICode;
import cei.code.dao.CodeDao;
import cei.code.domains.Code;

public class CodeManagerImpl {

	@Resource( name = "cei.code.dao" )
	protected CodeDao dao;

	public void save( Code code ) {
		dao.save( code );
	}
	
	public int garbage( Code code ) {
		return update( code, false );
	}

	public int garbage( List<Code> codes ) {
		return update( codes, false );
	}

	public int recycle( Code code ) {
		return update( code, true );
	}

	public int recycle( List<Code> codes ) {
		return update( codes, true );
	}

	public int remove( Code code ) {
		return delete( code );
	}

	public int remove( List<Code> codes ) {
		return delete( codes );
	}

	public Code getCode( String code ) {
		return getCode( ICode.COMMON_CODE, code );
	}

	public Code getCode( String code, boolean use ) {
		return getCode( ICode.COMMON_CODE, code, use );
	}

	public Code getCode( String group, String code ) {
		return getCode( group, code, true );
	}

	public Code getCode( String group, String code, boolean use ) {
		Code _code = new Code();
		_code.setGroup( group );
		_code.setCode( code );
		_code.setUse( use );
		return dao.getCode( _code );
	}

	public List<Code> getCodes( String code ) {
		return getCodes( ICode.COMMON_CODE, code, true );
	}

	public List<Code> getCodes( String code, boolean use ) {
		return getCodes( ICode.COMMON_CODE, code, use );
	}

	public List<Code> getCodes( String group, String code ) {
		return getCodes( group, code, true );
	}

	public List<Code> getCodes( String group, String code, boolean use ) {
		Code _code = new Code();
		_code.setGroup( group );
		_code.setParent( code );
		_code.setUse( use );
		return dao.getCodes( _code );
	}

	private int update( Object object, boolean garbage ) {
		if ( object instanceof List ) {
			@SuppressWarnings("unchecked")
			List<Code> codes = ( List<Code> )object;
			int result = 0;

			for ( Code code : codes ) {
				code.setUse(garbage);
				result += dao.update( code );
			}
			
			return result;
		}
		else {
			Code code = ( Code )object;
			code.setUse( garbage );
			return dao.update( code );
		}
	}

	private int delete( Object object ) {
		if ( object instanceof List ) {
			@SuppressWarnings( "unchecked" )
			List<Code> codes = ( List<Code> )object;
			int result = 0;

			for ( Code code : codes ) {
				result += dao.remove(code);
			}
			
			return result;
		}
		else {
			Code code = ( Code )object;
			return dao.remove( code );
		}
	}
}
