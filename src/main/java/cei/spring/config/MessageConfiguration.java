package cei.spring.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import cei.support.spring.message.MessageSupport;

@Configuration
public class MessageConfiguration {
	private static final Logger log = LoggerFactory.getLogger("--- Message Configuration ---");

	@Bean
	public MessageSupport messageSource() {
		MessageSupport messageSource = new MessageSupport();
		ResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver();

		log.debug("check #1");
		
		try {
			Resource[] resources = resourceLoader.getResources("classpath*:messages/**/*");

			log.debug("resource.length : " + resources.length);

			if(resources.length > 0) {
				String[] messages = new String[resources.length];
				int i = 0;

				for (Resource resource : resources) {
					String url = resource.getURL().toString();
					
					if(url.toLowerCase().endsWith(".xml")) {
						messages[i] = url.replace("\\.xml$", "");
						log.info("loading: {}", messages[i]);
					}
				}

				messageSource.setFallbackToSystemLocale(true);
				messageSource.setBasenames(messages);
			}
		}
		catch(Exception e) {
			log.info("No have messages");
		}
		
		return messageSource;
	}
}
