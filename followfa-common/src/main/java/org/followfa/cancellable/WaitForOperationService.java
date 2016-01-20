package org.followfa.cancellable;

import org.followfa.defensive.Args;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;

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
	}
}
