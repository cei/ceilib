package db;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cei.support.spring.repository.ibatis.DaoSupport;

import db.config.TestDBConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDBConfiguration.class})
public class ConnectionTest extends DaoSupport {
	@Test
	public void test() {
		System.out.println(select("test.check.time"));
	}
}
