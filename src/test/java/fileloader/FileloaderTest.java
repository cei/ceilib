package fileloader;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import net.sf.cglib.core.Converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import cei.domains.SystemFile;
import cei.fileloader.FileloaderService;
import cei.spring.config.DataSourceConfiguration;
import cei.spring.config.IbatisConfiguration;
import cei.spring.config.PluginConfiguration;
import cei.spring.config.PropertyConfiguration;
import cei.util.convert.ConvertToJson;
import cei.web.spring.view.type.Json;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PropertyConfiguration.class, PluginConfiguration.class, DataSourceConfiguration.class, IbatisConfiguration.class})
public class FileloaderTest {
	
	@Autowired
	FileloaderService service;

	@Test
	public void test() throws Exception {
//		MultipartFile mf = new MockMultipartFile("test", "한글사랑.xml", "text/plain", "아름다운우리한글".getBytes());
//		System.out.println(ConvertToJson.getInstance().getJson(service.up(mf)));
		System.out.println(ConvertToJson.getInstance().getJson(service.remove(new SystemFile(4))));
	}
}
