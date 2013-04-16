package cei.spring.config;

import java.io.IOException;

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

		Resource[] resources = null;

		try {
			resources = ctx.getResources("classpath:properties/**/*.xml");

			for ( Resource r : resources ) {
				log.info( "loading: {}", r.getFilename() );
			}

			pspc.setLocations(resources);
		}
		catch(IOException ioe) {
			log.info("No have property");
		}

		return pspc;
	}
}
