package org.followfa.postings.query;

import org.followfa.cancellable.WaitForOperationService;
import org.followfa.defensive.Args;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class UpdatePostingsService {
	private final PostingEventsListService postingEventsListService;
	private final WaitForOperationService waitForOperationService;

	@Autowired
	public UpdatePostingsService(final PostingEventsListService postingEventsListService, final WaitForOperationService waitForOperationService) {
		Args.notNull(postingEventsListService, "postingEventsListService");
		Args.notNull(waitForOperationService, "waitForOperationService");

		this.postingEventsListService = postingEventsListService;
		this.waitForOperationService = waitForOperationService;
	}

	public void updatePostingsOfUser(final long userId) {
		waitForOperationService.executeAndWait(() -> postingEventsListService.listNewestEventsForUser(userId), 500L);
	}
}
