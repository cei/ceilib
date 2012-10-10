package cei.fileloader;

import org.springframework.stereotype.Repository;

import cei.domains.SystemFile;
import cei.support.spring.repository.ibatis.DaoSupport;


@Repository
public class FileloaderDao extends DaoSupport {
	public SystemFile bySeq(SystemFile fileloader) {
		return select("system.file-bySeq", fileloader);
	}

	public int save(SystemFile fileloader) {
		return insert("system.file-save", fileloader);
	}
	
	public int garbage(SystemFile fileloader) {
		return update("system.file-garbage", fileloader);
	}

	public int recycle(SystemFile fileloader) {
		return update("system.file-recycle", fileloader);
	}
	
	public int remove(SystemFile fileloader) {
		return delete("system.file-remove", fileloader);
	}

	public void accessCount(SystemFile fileloader) {
		update("system.file-access", fileloader);
	}
}
