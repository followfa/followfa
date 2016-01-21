package org.followfa.postings.query.posting;

import org.followfa.cancellable.WaitForOperationService;
import org.followfa.postings.query.event.EventType;
import org.followfa.postings.query.event.PostingEventsListService;
import org.followfa.postings.query.event.UserPostingEvent;
import org.followfa.postings.query.posting.PostingsRepository;
import org.followfa.postings.query.posting.UpdatePostingsService;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class UpdatePostingsServiceTest {
	private UpdatePostingsService updatePostingsService;
	private PostingEventsListService postingEventsListService;
	private WaitForOperationService waitForOperationService;
	private PostingsRepository postingsRepository;

	@Before
	public void setup() {
		postingEventsListService = mock(PostingEventsListService.class);
		postingsRepository = mock(PostingsRepository.class);

		updatePostingsService = new UpdatePostingsService(postingEventsListService, postingsRepository);
	}

	@Test
	public void fetchesPostingEventsFromListServiceWhenUpdatingPostings() {
		updatePostingsService.fetchAndUpdatePostingsFor(1L);

		verify(postingEventsListService).listNewestEventsForUser(eq(1L), anyLong());
	}

	@Test
	public void usesLastPostingIdFromOwnDatabaseToQueryForNewPostings() {
		when(postingsRepository.getLastPostingEventIdFor(anyLong())).thenReturn(100L);

		updatePostingsService.fetchAndUpdatePostingsFor(1L);

		verify(postingEventsListService).listNewestEventsForUser(anyLong(), eq(100L));
	}

	@Test
	public void createsNewPostingForEveryPostingEventWithEventTypeCreated() {
		final UserPostingEvent event1 = new TestUserPostingEvent("Posting 1", 11L);
		final UserPostingEvent event2 = new TestUserPostingEvent("Posting 2", 12L);
		when(postingEventsListService.listNewestEventsForUser(anyLong(), anyLong())).thenReturn((List) Arrays.asList(event1, event2));

		updatePostingsService.fetchAndUpdatePostingsFor(1L);

		verify(postingsRepository).savePosting(argThat(allOf(
				hasProperty("lastPostingEventId", is(11L)),
				hasProperty("postingText", is("Posting 1"))
		)));
		verify(postingsRepository).savePosting(argThat(allOf(
				hasProperty("lastPostingEventId", is(12L)),
				hasProperty("postingText", is("Posting 2"))
		)));
	}

	@Test
	public void savesLastProcessedEventIdToTheDatabase() {
		final UserPostingEvent event1 = new TestUserPostingEvent("Posting 1", 11L);
		final UserPostingEvent event2 = new TestUserPostingEvent("Posting 2", 12L);
		when(postingEventsListService.listNewestEventsForUser(anyLong(), anyLong())).thenReturn((List) Arrays.asList(event1, event2));

		updatePostingsService.fetchAndUpdatePostingsFor(1L);

		verify(postingsRepository).setLastPostingEventIdFor(1L, 12L);
	}

	private class TestUserPostingEvent implements UserPostingEvent {
		private String postingText;
		private Long postingEventId;

		public TestUserPostingEvent(final String postingText, final Long postingEventId) {
			this.postingText = postingText;
			this.postingEventId = postingEventId;
		}

		@Override
		public Long getPostingEventId() {
			return postingEventId;
		}

		@Override
		public Long getPostingId() {
			return null;
		}

		@Override
		public long getUserId() {
			return 1L;
		}

		@Override
		public Timestamp getCreatedAt() {
			return new Timestamp(System.currentTimeMillis());
		}

		@Override
		public String getClientEventId() {
			return "cid";
		}

		@Override
		public EventType getPostingEventType() {
			return (onCreated) -> onCreated.run();
		}

		@Override
		public String getPostingText() {
			return postingText;
		}
	}
}