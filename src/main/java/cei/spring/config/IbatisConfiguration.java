package cei.spring.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.support.lob.OracleLobHandler;
import org.springframework.jdbc.support.nativejdbc.SimpleNativeJdbcExtractor;
import org.springframework.orm.ibatis.SqlMapClientFactoryBean;

@Configuration
public class IbatisConfiguration {
	private static final Logger log = LoggerFactory.getLogger("--- Ibatis Configurations ---");

	@Bean
	@Lazy
	@Autowired
	public SqlMapClientFactoryBean sqlMapClient(DataSource dataSource) throws IOException {
		ClassPathXmlApplicationContext sqlMapXml = new ClassPathXmlApplicationContext();
		Resource sqlMapConfig = new ClassPathResource("sqlMap-config.cei.xml");
		Resource[] mappers = sqlMapXml.getResources("classpath:queries/**/*.xml");

		SqlMapClientFactoryBean client = new SqlMapClientFactoryBean();
		client.setDataSource(dataSource);
		client.setLobHandler(lobHandler());
		client.setConfigLocation(sqlMapConfig);
		client.setMappingLocations(mappers);

		for(Resource res : mappers) log.info("Loading sql mappers: {}", res.getFilename());

		return client;
	}

	@Lazy(true)
	@Bean
	public OracleLobHandler lobHandler() {
		OracleLobHandler handler = new OracleLobHandler();
		handler.setNativeJdbcExtractor(new SimpleNativeJdbcExtractor());
		return handler;
	}
}
