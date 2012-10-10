package messages;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cei.spring.config.MessageConfiguration;
import cei.support.spring.message.MessageSupport;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MessageConfiguration.class})
public class MessageLoadingTest {

	@Autowired
	MessageSupport messages;
	
	@Test
	public void test() {
		System.out.println(messages.get("test"));
		System.out.println("ok complete!");
	}
}
