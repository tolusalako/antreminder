package net.csthings;

import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.Test;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AntreminderApplication.class)
@WebAppConfiguration
@Test(enabled = false)
public class AntreminderApplicationTests {

	@Test
	public void WwebSocServiceTest() {
	}

}
