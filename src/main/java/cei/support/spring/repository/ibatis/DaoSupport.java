package cei.support.spring.repository.ibatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapClient;

public class DaoSupport extends SqlMapClientDaoSupport {
	
	@Autowired(required = false)
	public void setSuperSqlMapClient(SqlMapClient sqlMapClient) {
		super.setSqlMapClient(sqlMapClient);
	}

	@SuppressWarnings("unchecked")
	protected <T> T insert(String queryId, Object parameterObject) {
		return (T)getSqlMapClientTemplate().insert(queryId, parameterObject);
	}

	protected Object insert(String queryId) {
		return getSqlMapClientTemplate().insert(queryId);
	}

	protected int update(String queryId) {
		return update(queryId, null);
	}
	
	protected int update(String queryId, Object parameterObject) {
		return getSqlMapClientTemplate().update(queryId, parameterObject);
	}

	protected int delete(String queryId, Object parameterObject) {
		return getSqlMapClientTemplate().delete(queryId, parameterObject);
	}

	@SuppressWarnings("unchecked")
	protected <T> T select(String queryId, Object parameterObject) {
		return (T)getSqlMapClientTemplate().queryForObject(queryId, parameterObject);
	}
	
	protected <T> T select(String queryId) {
		return select(queryId, null);
	}

	@SuppressWarnings("unchecked")
	protected <T> List<T> list(String queryId, Object parameterObject) {
		return (List<T>)getSqlMapClientTemplate().queryForList(queryId, parameterObject);
	}

	@SuppressWarnings("unchecked")
	protected <T> List<T> list(String queryId, Object parameterObject, int page, int rowPerPage) {

		if(page == 0 || rowPerPage == 0)
			return (List<T>)getSqlMapClientTemplate().queryForList(queryId, parameterObject);
		else
			return (List<T>)getSqlMapClientTemplate().queryForList(queryId, parameterObject, (page - 1) * rowPerPage, rowPerPage);
	}

	@SuppressWarnings("unchecked")
	protected <T> List<T> list(String queryId, Object parameterObject, Object page, Object rowPerPage) {
		if(page == null || rowPerPage == null) return (List<T>)getSqlMapClientTemplate().queryForList(queryId, parameterObject);
		else {
			int _page = 0;
			int _rowPerPage = 0;
			if(page != null) _page = Integer.parseInt((String)page);
			if(rowPerPage != null) _rowPerPage = Integer.parseInt((String)rowPerPage);
	
			return list(queryId, parameterObject, _page, _rowPerPage);
		}
	}

	protected <T> List<T> list(String queryId) {
		return list(queryId, null);
	}

	protected <T> List<T> list(String queryId, int page, int rowPerPage) {
		return list(queryId, null, page, rowPerPage);
	}
}