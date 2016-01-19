package org.followfa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FollowfaWebappApplication.class)
@WebAppConfiguration
public class FollowfaWebappApplicationTest {

	@Test
	public void contextLoads() {
		//Are you serious, Spring? A test without an assert? OK, let's leave it here for now...
	}

	@Test(expected = AssertionError.class)
	public void checkThatAssertionsAreEnabled() {
		assert false : "Assertions must be enabled when running the test!";
	}
}
