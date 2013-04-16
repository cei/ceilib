package cei.spring.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

import cei.support.spring.message.MessageSupport;

@Configuration
public class MessageConfiguration {
	private static final Logger log = LoggerFactory.getLogger("--- Message Configuration ---");

	@Bean
	public MessageSupport messageSource() {
		MessageSupport messageSource = new MessageSupport();
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext();

		Resource[] resources = null;

		try {
			resources = ctx.getResources( "classpath:messages/**/*" );

			String[] messages = new String[resources.length];
			for ( int i = 0; i < resources.length; i++ ) {
				messages[i] = resources[i].getURI().toString().replace(".xml", "");
				log.info( "loading: {}", messages[i] );
			}

			messageSource.setFallbackToSystemLocale(true);
			messageSource.setBasenames(messages);
		}
		catch(IOException ioe) {
			log.info("No have messages");
		}
		
		return messageSource;
	}
}
