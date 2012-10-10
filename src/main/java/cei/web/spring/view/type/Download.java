package cei.web.spring.view.type;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import cei.support.spring.message.MessageSupport;
import cei.web.spring.view.Views;

public class Download implements Views {
	private String sourceFilePath = null;
	private String outFileName = null;
	private InputStream is = null;
	private String contentType = "application/octet-stream";
	
	@Autowired
	MessageSupport messageSupport;

	public Download(InputStream is, String outFileName) {
		this.is = is;
		this.outFileName = outFileName;
	}
	
	public Download(InputStream is, String outFileName, String contentType) {
		this(is, outFileName);
		this.contentType = contentType;
	}
	
	public Download(String sourceFilePath, String outFileName) {
		this.sourceFilePath = sourceFilePath;
		this.outFileName = outFileName;
	}

	public Download(String sourceFile, String outFileName, String contentType)
	{
		this(sourceFile, outFileName);
		this.contentType = contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentType() {
		return contentType;
	}

	public Download getData() {
		return this;
	}

	public void render(Map<String, ?> param,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if(is == null) {
			try {
				File file = new File(sourceFilePath);
				is = new BufferedInputStream(new FileInputStream(file));
			}
			catch(FileNotFoundException fnfe) {
				Views alert = new Alert(messageSupport.get("fileloader.notFound", outFileName));
				alert.render(param, request, response);
				return;
			}
			catch(Exception e) {
				Views alert = new Alert(messageSupport.get("fileloader.failure", outFileName));
				alert.render(param, request, response);
				return;
			}
		}

		response.setContentType(getContentType());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + outFileName + "\"");

		OutputStream os = response.getOutputStream();

		byte[] bytes = new byte[8192];
		while(is.read(bytes) != -1) os.write(bytes);

		os.flush();
		os.close();
		is.close();
	}
}
