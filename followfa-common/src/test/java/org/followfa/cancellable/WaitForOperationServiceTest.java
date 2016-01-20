package org.followfa.cancellable;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.StopWatch;

import java.util.concurrent.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class WaitForOperationServiceTest {
	private ExecutorService executor = Executors.newCachedThreadPool();
	private WaitForOperationService waitForOperationService;

	@Before
	public void setup() {
		waitForOperationService = new WaitForOperationService(executor);
	}

	@Test
	public void schedulesOperationWithExecutorService() {
		final Runnable runnable = mock(Runnable.class);

		waitForOperationService.executeAndWait(runnable, 100L);

		verify(runnable).run();
	}

	@Test
	public void waitsForResult() throws InterruptedException, TimeoutException, ExecutionException {
		final Runnable runnable = () -> {
			try {
				Thread.sleep(100L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};

		StopWatch sw = new StopWatch();
		sw.start();
		waitForOperationService.executeAndWait(runnable, 1000L);
		sw.stop();

		assertThat(sw.getTotalTimeMillis(), is(greaterThan(90L)));
		assertThat(sw.getTotalTimeMillis(), is(lessThan(200L)));
	}

	@Test(timeout = 1000L)
	public void returnsEarlyWhenTheRunnableBlocks() {
		final Runnable runnable = () -> {
			synchronized (this) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		StopWatch sw = new StopWatch();
		sw.start();
		waitForOperationService.executeAndWait(runnable, 100L);
		sw.stop();

		assertThat(sw.getTotalTimeMillis(), is(greaterThan(90L)));
		assertThat(sw.getTotalTimeMillis(), is(lessThan(200L)));
	}
}