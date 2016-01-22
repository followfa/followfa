package org.followfa.postings.command.event;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class PostingEventServiceTest {
	private PostingEventService postingEventService;
	private PostingEventRepository postingEventRepository;

	@Before
	public void setup() {
		postingEventRepository = mock(PostingEventRepository.class);
		postingEventService = new PostingEventService(postingEventRepository);
	}

	@Test
	public void savesPostingEventWhenAskedToCreateNewPosting() {
		postingEventService.createNewPosting(1L, "Posting Text");
		
		verify(postingEventRepository).addPostingEvent(argThat(isPostingEvent(1L, "Posting Text")));
	}

	@Test
	public void returnsPostingEventsForUser() {
		final List<PostingEvent> configuredPostingEvents = mock(List.class);
		when(postingEventRepository.getPostingEventsFor(eq(1L), eq(5L))).thenReturn(configuredPostingEvents);

		final List<PostingEvent> postingEvents = postingEventService.listNewestEventsForUser(1L, 5L);

		assertSame(configuredPostingEvents, postingEvents);
	}

	private Matcher<PostingEvent> isPostingEvent(final long userId, final String postingText) {
		return allOf(
				hasProperty("userId", is(userId)),
				hasProperty("postingText", is(postingText)),
				hasProperty("createdAt", is(notNullValue())),
				hasProperty("postingEventType", is(PostingEventType.CREATED))
		);
	}
}