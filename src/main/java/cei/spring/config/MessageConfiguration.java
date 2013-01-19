package cei.spring.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

	private static final String messagesPath = "/messages/**/*";

	@Bean
	public MessageSupport messageSource() {
		MessageSupport messageSource = new MessageSupport();

		List<String> messages = new ArrayList<String>();
		messages.add("classpath:message.cei");

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext();
		Resource[] resources = null;
		
		try {
			resources = ctx.getResources("classpath:" + messagesPath);

			for(Resource res : resources)
				messages.add(res.getURI().toString().replace(".xml", ""));
			
			messageSource.setFallbackToSystemLocale(true);
			messageSource.setBasenames(messages.toArray(new String[messages.size()]));
			
		}
		catch(IOException ioe) {
			log.info("No have messages");
		}
		
		return messageSource;
	}
}
