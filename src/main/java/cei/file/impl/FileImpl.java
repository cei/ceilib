package cei.file.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import cei.file.File;
import cei.file.IFile;
import cei.file.dao.FileDao;
import cei.util.Formatter;

@Service( "cei-file" )
public class FileImpl implements IFile {
	private static final Logger log = LoggerFactory.getLogger("--- CEI File ---");
	private static final Random random = new Random();
	private static final int RANDOM_LIMIT_NUM = 99;
	
	@Value("${file.storage.path}")
	String fileStoragePath;

	@Autowired
	FileDao dao;

	public File up(MultipartFile file) throws IOException {
		StringBuffer path = new StringBuffer(java.io.File.separator);
		int randomPathName = 0;

		randomPathName = random.nextInt(RANDOM_LIMIT_NUM);
		path.append(Formatter.decimal(randomPathName, "00"));
		path.append(java.io.File.separator);
		
		randomPathName = random.nextInt(RANDOM_LIMIT_NUM);
		path.append(Formatter.decimal(randomPathName, "00"));
		path.append(java.io.File.separator);
		
		log.info("file save path: {}", fileStoragePath + path.toString());

		java.io.File storage = new java.io.File(fileStoragePath + path.toString());

		if(!storage.exists())
			if(!storage.mkdirs())
				if(!storage.mkdir())
					throw new IOException(fileStoragePath + path.toString() + "\n directory creates error!!!");

		String uuid = UUID.randomUUID().toString();

		File param = new File();
		param.setOriginalName(file.getOriginalFilename());
		param.setSaveName(uuid);
		param.setPath(path.toString());
		param.setMime(file.getContentType());
		param.setSize(file.getSize());
		param.setSeq(dao.save(param));

		FileCopyUtils.copy(
				new BufferedInputStream( file.getInputStream() ),
				new BufferedOutputStream( new FileOutputStream( fileStoragePath + path.toString() + uuid ) ) );

		return param;
	}
	
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
	public File bySeq( File file, boolean access ) {
		if ( access ) {
			dao.accessCount(file);
		}

		file = dao.bySeq( file );
		file.setPath( fileStoragePath + file.getPath() );

		return file;
	}
}
