package cei.fileloader;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import cei.domains.SystemFile;

public interface FileloaderService {
	SystemFile up(MultipartFile file) throws IOException;
	SystemFile bySeq(SystemFile param);

	int garbage(SystemFile param);
	int recycle(SystemFile param);
	int remove(SystemFile param) throws IOException;

}
