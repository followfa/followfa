package org.followfa.cancellable;

import net.davidtanzer.jdefensive.Args;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
public class WaitForOperationService {
	private final ExecutorService executor;

	@Autowired
	public WaitForOperationService(final ExecutorService executor) {
		Args.notNull(executor, "executor");

		this.executor = executor;
	}

	public void executeAndWait(Runnable runnable, long timeout) {
		Args.notNull(runnable, "runnable");

		final Future<?> future = executor.submit(runnable);

		try {
			future.get(timeout, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new IllegalStateException("Interrupted when waiting for a result", e);
		} catch (ExecutionException e) {
			throw new IllegalStateException("Runnable for executeAndWait threw an error", e.getCause());
		} catch (TimeoutException e) {
			//This is OK, we simply return here. The timeout was (more or less) expected.
		}
	}
}
