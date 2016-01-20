package org.followfa.postings.query;

import org.followfa.cancellable.WaitForOperationService;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class UpdatePostingsServiceTest {
	private UpdatePostingsService updatePostingsService;
	private PostingEventsListService postingEventsListService;
	private WaitForOperationService waitForOperationService;

	@Before
	public void setup() {
		postingEventsListService = mock(PostingEventsListService.class);
		waitForOperationService = mock(WaitForOperationService.class);

		doAnswer(invocation -> {
			((Runnable) invocation.getArguments()[0]).run();
			return null;
		}).when(waitForOperationService).executeAndWait(any(), anyLong());
		
		updatePostingsService = new UpdatePostingsService(postingEventsListService, waitForOperationService);
	}

	@Test
	public void fetchesPostingEventsFromListServiceWhenUpdatingPostings() {
		updatePostingsService.updatePostingsOfUser(1L);

		verify(postingEventsListService).listNewestEventsForUser(eq(1L));
	}
}