package org.followfa.postings.query.posting;

import org.followfa.cancellable.WaitForOperationService;
import org.followfa.postings.query.posting.Posting;
import org.followfa.postings.query.posting.PostingsRepository;
import org.followfa.postings.query.posting.PostingsService;
import org.followfa.postings.query.posting.UpdatePostingsService;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class PostingsServiceTest {
	private PostingsService postingsService;
	private PostingsRepository postingsRepository;
	private UpdatePostingsService updatePostingsService;
	private WaitForOperationService waitForOperationService;

	@Before
	public void setup() {
		postingsRepository = mock(PostingsRepository.class);
		updatePostingsService = mock(UpdatePostingsService.class);

		waitForOperationService = mock(WaitForOperationService.class);
		doAnswer(invocation -> {
			((Runnable) invocation.getArguments()[0]).run();
			return null;
		}).when(waitForOperationService).executeAndWait(any(), anyLong());

		postingsService = new PostingsService(updatePostingsService, postingsRepository, waitForOperationService);
	}

	@Test
	public void triesToUpdatePostings_WhenListingPostingsForCurrentUser() {
		postingsService.listPostingsForCurrentUser(1L, 50L);

		verify(updatePostingsService).fetchAndUpdatePostingsFor(eq(1L));
	}

	@Test
	public void getsNewestPostingsOfUserFromPostingsRepository() {
		Posting posting = new Posting.Builder()
				.withPostingText("Hello World")
				.withUserId(1L)
				.build();
		when(postingsRepository.listPostingsForUser(eq(1L), anyLong())).thenReturn(Arrays.asList(posting));

		final List<Posting> postings = postingsService.listPostingsForCurrentUser(1L, 50L);

		assertNotNull(postings);
		assertThat(postings.size(), is(1));
		assertThat(postings, hasItem(hasProperty("postingText", is("Hello World"))));
	}
}