package org.followfa.postings.query;

import org.followfa.cancellable.WaitForOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
class UpdatePostingsService {
	private final PostingEventsListService postingEventsListService;
	private final WaitForOperationService waitForOperationService;

	@Autowired
	public UpdatePostingsService(final PostingEventsListService postingEventsListService, final WaitForOperationService waitForOperationService) {
		Assert.notNull(postingEventsListService, "postingEventsListService must not be null.");
		Assert.notNull(waitForOperationService, "waitForOperationService must not be null.");

		this.postingEventsListService = postingEventsListService;
		this.waitForOperationService = waitForOperationService;
	}

	public void updatePostingsOfUser(final long userId) {
		waitForOperationService.executeAndWait(() -> postingEventsListService.listNewestEventsForUser(userId), 500L);
	}
}
