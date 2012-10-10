package cei.spring.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;

@Configuration
public class PropertyConfiguration {
	private static final Logger log = LoggerFactory.getLogger("--- Property Configuration ---");

	@Bean
	public static PropertySourcesPlaceholderConfigurer placeholder() throws IOException {
		PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext();
		List<Resource> resourceList = new ArrayList<Resource>();
		resourceList.add(ctx.getResource("classpath:system-property.cei.xml"));

		try {
			resourceList.addAll(Arrays.asList(ctx.getResources("classpath:properties/**/*")));
		}
		catch(IOException ioe) {
			log.info("No have property");
		}
		finally {
			pspc.setLocations(resourceList.toArray(new Resource[resourceList.size()]));
		}

		return pspc;
	}
}
