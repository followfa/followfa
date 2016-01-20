package org.followfa.cancellable;

import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;

@Service
public class WaitForOperationService {
	private ExecutorService executor;

	public void executeAndWait(Runnable runnable, long timeout) {
	}
}
