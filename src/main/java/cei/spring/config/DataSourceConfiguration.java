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

	@Value( "${database.driver}" )		String driver;
	@Value( "${database.url}" )			String url;
	@Value( "${database.username}" )	String username;
	@Value( "${database.password}" )	String password;

	@Bean( destroyMethod = "close" )
	public DataSource dataSource() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName( driver );
		ds.setUrl( url );
		ds.setUsername( username );
		ds.setPassword( password );
		return ds;
	}

	@Bean
	public PlatformTransactionManager txManager() {
		return new DataSourceTransactionManager(dataSource());
	}
}
