package properties;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cei.spring.config.PropertyConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PropertyConfiguration.class})
public class PropertyLoadingTest {

	@Value("${test}")
	String test;

	@Value("${database.driver}")
	String driver;

	@Test
	public void test() throws Exception {
		System.out.println("--------- output --------------");
		System.out.println(test);
		System.out.println(driver);
	}
	
//	@Test
	public void test2() {
		List<String> list = new ArrayList<String>();
		list.add("test");
		list.add("check");
		
		System.out.println(list.toArray(new String[list.size()]));
	}
}
