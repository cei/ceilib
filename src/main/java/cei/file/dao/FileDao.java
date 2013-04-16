package cei.file.dao;

import org.springframework.stereotype.Repository;

import cei.file.File;
import cei.support.spring.repository.ibatis.DaoSupport;

@Repository( "cei-file-dao" )
public class FileDao extends DaoSupport {
	public File bySeq( File file ) {
		return select( "cei.file.bySeq", file);
	}

	public int save(File file) {
		return insert("cei.file.save", file);
	}
	
	public int garbage(File file) {
		return update("cei.file.garbage", file);
	}

	public int recycle(File file) {
		return update("cei.file.recycle", file);
	}
	
	public int remove(File file) {
		return delete("cei.file.remove", file);
	}

	public void accessCount(File file) {
		update("cei.file.access", file);
	}
}
