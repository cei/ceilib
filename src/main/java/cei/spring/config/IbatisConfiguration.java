package cei.spring.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.support.lob.OracleLobHandler;
import org.springframework.jdbc.support.nativejdbc.SimpleNativeJdbcExtractor;
import org.springframework.orm.ibatis.SqlMapClientFactoryBean;

@Configuration
public class IbatisConfiguration {
	private static final Logger log = LoggerFactory.getLogger("--- Ibatis Configurations ---");

	@Bean
	@Autowired
	public SqlMapClientFactoryBean sqlMapClient(DataSource dataSource) throws IOException {
		PathMatchingResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver();
		Resource[] resources = resourceLoader.getResources("classpath*:queries/**/*.xml");

		Resource sqlMapConfig = new ClassPathResource("classpath*:sqlMap-config.cei.xml");

		SqlMapClientFactoryBean client = new SqlMapClientFactoryBean();
		client.setDataSource(dataSource);
		client.setLobHandler(lobHandler());
		client.setConfigLocation(sqlMapConfig);
		client.setMappingLocations(resources);

		for(Resource res : resources) log.info("Loading sql mappers: {}", res.getFilename());

		return client;
	}

	@Bean
	public OracleLobHandler lobHandler() {
		OracleLobHandler handler = new OracleLobHandler();
		handler.setNativeJdbcExtractor(new SimpleNativeJdbcExtractor());
		return handler;
	}
}
