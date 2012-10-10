package cei.fileloader;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
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

import cei.domains.SystemFile;
import cei.util.Formatter;

@Service
public class FileloaderServiceImpl implements FileloaderService {
	private static final Logger log = LoggerFactory.getLogger("--- CEI Fileloader ---");
	private static final int RANDOM_LIMIT_NUM = 99;
	private static final Random random = new Random();
	
	@Value("${file.storage.path}")
	String fileStoragePath;

	@Autowired
	FileloaderDao dao;

	public SystemFile up(MultipartFile file) throws IOException {
		StringBuffer path = new StringBuffer(File.separator);
		int randomPathName = 0;

		randomPathName = random.nextInt(RANDOM_LIMIT_NUM);
		path.append(Formatter.decimal(randomPathName, "00"));
		path.append(File.separator);
		
		randomPathName = random.nextInt(RANDOM_LIMIT_NUM);
		path.append(Formatter.decimal(randomPathName, "00"));
		path.append(File.separator);
		
		log.debug("file save path: {}", fileStoragePath + path.toString());

		File storage = new File(fileStoragePath + path.toString());

		if(!storage.exists())
			if(!storage.mkdirs())
				if(!storage.mkdir())
					throw new IOException(fileStoragePath + path.toString() + "\n directory creates error!!!");

		String uuid = UUID.randomUUID().toString();

		SystemFile param = new SystemFile();
		param.setOrgName(file.getOriginalFilename());
		param.setSaveName(uuid);
		param.setPath(path.toString());
		param.setType(file.getContentType());
		param.setSize(file.getSize());
		param.setSeq(dao.save(param));

		FileCopyUtils.copy(
				new BufferedInputStream(file.getInputStream()),
				new BufferedOutputStream(new FileOutputStream(fileStoragePath + path.toString() + uuid)));

		return param;
	}
	
	public int garbage(SystemFile fileloader) {
		return dao.garbage(fileloader);
	}

	public int recycle(SystemFile fileloader) {
		return dao.recycle(fileloader);
	}

	public int remove(SystemFile fileloader) throws IOException {
		int result = 0;
		fileloader = bySeq(fileloader);
		if(fileloader != null)
			if(new File(fileloader.getPath() + fileloader.getSaveName()).delete())
				result = dao.remove(fileloader);

		return result;
	}

	@Transactional
	public SystemFile bySeq(SystemFile fileloader) {
		dao.accessCount(fileloader);

		fileloader = dao.bySeq(fileloader);
		fileloader.setPath(fileStoragePath + fileloader.getPath());
				
		return fileloader;
	}
}
