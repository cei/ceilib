package cei.spring.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DataSourceConfiguration {

	@Value("${cei.development:false}")
	String developement;

	
	@Value("${database.driver}")		String driver;
	@Value("${database.opr.url}")		String url;
	@Value("${database.opr.username}")	String username;
	@Value("${database.opr.password}")	String password;
	@Value("${database.test.url}")		String testUrl;
	@Value("${database.test.username}")	String testUsername;
	@Value("${database.test.password}")	String testPassword;

	@Bean(destroyMethod = "close")
	public DataSource dataSource() {

		boolean isDevelopement = Boolean.valueOf(developement);
		
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(driver);
		ds.setUrl(isDevelopement ? testUrl : url);
		ds.setUsername(isDevelopement ? testUsername : username);
		ds.setPassword(isDevelopement ? testPassword : password);
		
		return ds;
	}

	@Bean
	public PlatformTransactionManager txManager() {
		return new DataSourceTransactionManager(dataSource());
	}
}