package db.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.lob.OracleLobHandler;
import org.springframework.jdbc.support.nativejdbc.SimpleNativeJdbcExtractor;
import org.springframework.orm.ibatis.SqlMapClientFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class TestDBConfiguration {

	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		ds.setUrl("jdbc:oracle:thin:@localhost:1521:XE");
		ds.setUsername("TEST");
		ds.setPassword("TEST");
		return ds;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}
	
	@Bean
	@Lazy
	@Autowired
	public SqlMapClientFactoryBean sqlMapClient(DataSource dataSource) throws IOException {
		ClassPathXmlApplicationContext sqlMapXml = new ClassPathXmlApplicationContext();
		Resource[] mappers = sqlMapXml.getResources("query/**/*.xml");

		SqlMapClientFactoryBean client = new SqlMapClientFactoryBean();
		client.setDataSource(dataSource);
		client.setLobHandler(lobHandler());
		client.setConfigLocation(new ClassPathResource("sqlMap-config.cei.xml"));
		client.setMappingLocations(mappers);

		return client;
	}

	@Bean
	public OracleLobHandler lobHandler() {
		OracleLobHandler handler = new OracleLobHandler();
		handler.setNativeJdbcExtractor(new SimpleNativeJdbcExtractor());
		return handler;
	}
}