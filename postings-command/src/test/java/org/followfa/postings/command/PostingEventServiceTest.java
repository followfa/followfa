package org.followfa.postings.command;

import org.followfa.postings.command.event.PostingEvent;
import org.followfa.postings.command.event.PostingEventRepository;
import org.followfa.postings.command.event.PostingEventType;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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

	private Matcher<PostingEvent> isPostingEvent(final long userId, final String postingText) {
		return allOf(
				hasProperty("userId", is(userId)),
				hasProperty("postingText", is(postingText)),
				hasProperty("createdAt", is(notNullValue())),
				hasProperty("postingEventType", is(PostingEventType.CREATED))
		);
	}
}