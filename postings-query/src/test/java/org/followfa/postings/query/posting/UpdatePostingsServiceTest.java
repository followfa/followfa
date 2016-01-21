package org.followfa.postings.query.posting;

import org.followfa.cancellable.WaitForOperationService;
import org.followfa.postings.query.event.EventType;
import org.followfa.postings.query.event.PostingEventsListService;
import org.followfa.postings.query.event.UserPostingEvent;
import org.followfa.postings.query.posting.PostingsRepository;
import org.followfa.postings.query.posting.UpdatePostingsService;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
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

		verify(postingsRepository).savePosting(argThat(isPostingWith(11L, "Posting 1")));
		verify(postingsRepository).savePosting(argThat(isPostingWith(12L, "Posting 2")));
	}

	private Matcher<Posting> isPostingWith(final long lastPostingEventId, final String postingText) {
		return new BaseMatcher<Posting>() {
			@Override
			public boolean matches(final Object item) {
				if(item instanceof Posting) {
					return ((Posting) item).getLastPostingEventId() == lastPostingEventId &&
							((Posting) item).getPostingText().equals(postingText);
				}
				return false;
			}

			@Override
			public void describeTo(final Description description) {
				description.appendText("Posting with lastPostingEventId="+lastPostingEventId+" and postingText="+postingText);
			}
		};
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