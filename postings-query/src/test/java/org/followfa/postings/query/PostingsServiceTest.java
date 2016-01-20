package org.followfa.postings.query;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PostingsServiceTest {
	private PostingsService postingsService;
	private PostingsRepository postingsRepository;
	private UpdatePostingsService updatePostingsService;

	@Before
	public void setup() {
		postingsRepository = mock(PostingsRepository.class);
		updatePostingsService = mock(UpdatePostingsService.class);

		postingsService = new PostingsService(updatePostingsService, postingsRepository);
	}

	@Test
	public void triesToUpdatePostings_WhenListingPostingsForCurrentUser() {
		postingsService.listPostingsForCurrentUser(1L, 50L);

		verify(updatePostingsService).updatePostingsOfUser(eq(1L));
	}

	@Test
	public void getsNewestPostingsOfUserFromPostingsRepository() {
		when(postingsRepository.listPostingsForUser(eq(1L), anyLong())).thenReturn(Arrays.asList(new Posting("Hello World", 1L)));

		final List<Posting> postings = postingsService.listPostingsForCurrentUser(1L, 50L);

		assertNotNull(postings);
		assertThat(postings.size(), is(1));
		assertThat(postings, hasItem(hasProperty("postingText", is("Hello World"))));
	}
}