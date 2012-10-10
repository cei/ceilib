package cei.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
		"cei.codemanager",
		"cei.fileloader",
		"cei.web.plugin"
})
public class PluginConfiguration {
}