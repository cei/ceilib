package cei.web.plugin.fileloader;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cei.domains.SystemFile;
import cei.fileloader.FileloaderService;
import cei.web.spring.view.Views;
import cei.web.spring.view.type.Download;
import cei.web.spring.view.type.Json;


@Controller
@RequestMapping("/fileloader")
public class FileloaderController {

	@Value("${base.charset}")
	private String BASE_CHARSET;

	@Autowired
	FileloaderService service;

	@RequestMapping("/up")
	public Views up(@RequestPart MultipartFile file) {
		Json j = new Json();

		try {
			j.add("file", service.up(file));
			j.add("success", true);
			j.add("message", null);
		}
		catch(Exception e) {
			e.printStackTrace();
			j.add("success", false);
			j.add("message", e.getMessage());
		}

		return j;
	}

	@RequestMapping("/down/{seq}")
	public Views down(
			HttpServletRequest request,
			@ModelAttribute SystemFile fileloader,
			@RequestParam(required = false) boolean afterCloseWindow) {

		SystemFile file = service.bySeq(fileloader);

		String userAgent = request.getHeader("User-Agent");
		String orgName = file.getOrgName();

		if(userAgent != null) {
			try {
				orgName = userAgent.indexOf("MSIE") > -1 ? URLEncoder.encode(orgName, BASE_CHARSET) : new String(orgName.getBytes(BASE_CHARSET), "8859_1");
			}
			catch(UnsupportedEncodingException ue) {
				ue.printStackTrace();
			}
		}

		return new Download(file.getPath() + File.separator + file.getSaveName(), orgName);
	}

	@ResponseBody
	@RequestMapping("/garbage/{seq}")
	public String garbage(@ModelAttribute SystemFile fileloader) {
		return Integer.toString(service.garbage(fileloader));
	}

	@ResponseBody
	@RequestMapping("/recycle/{seq}")
	public String recycle(@ModelAttribute SystemFile fileloader) {
		return Integer.toString(service.recycle(fileloader));
	}

	@ResponseBody
	@RequestMapping("/remove/{seq}")
	public String remove(@ModelAttribute SystemFile fileloader) {
		try {
			return Integer.toString(service.remove(fileloader));
		}
		catch(IOException ioe) {
			return ioe.getMessage();
		}
	}
}
