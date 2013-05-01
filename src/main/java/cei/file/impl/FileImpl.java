package cei.file.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cei.file.File;
import cei.file.IFile;
import cei.file.dao.FileDao;

@Service("cei-file")
public class FileImpl implements IFile {
	protected static final Logger log = LoggerFactory.getLogger("--- CEI File ---");

	@Value("${file.storage.path}")
	protected String fileStoragePath;

	@Autowired
	protected FileDao dao;
	
	public int garbage( File file ) {
		return dao.garbage( file );
	}

	public int recycle( File file ) {
		return dao.recycle( file );
	}

	public boolean remove( File file ) {
		file = bySeq( file, false );
		if ( file != null ) {
			if ( new java.io.File( file.getPath() + file.getSaveName() ).delete() ) {
				return dao.remove( file ) > 0;
			}
		}

		return false;
	}

	public File bySeq( File file ) {
		return bySeq( file, true );
	}

	@Transactional
	public File bySeq( File file, boolean updateAccessCount ) {
		if(updateAccessCount) {
			dao.accessCount(file);
		}

		file = dao.bySeq(file);
		file.setPath(fileStoragePath + file.getPath());

		return file;
	}
}
