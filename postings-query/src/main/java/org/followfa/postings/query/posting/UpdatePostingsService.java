package org.followfa.postings.query.posting;

import org.followfa.defensive.Args;
import org.followfa.postings.query.event.PostingEventsListService;
import org.followfa.postings.query.event.UserPostingEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
class UpdatePostingsService {
	private final PostingEventsListService postingEventsListService;
	private final PostingsRepository postingsRepository;

	@Autowired
	public UpdatePostingsService(final PostingEventsListService postingEventsListService, final PostingsRepository postingsRepository) {
		Args.notNull(postingEventsListService, "postingEventsListService");
		Args.notNull(postingsRepository, "postingsRepository");

		this.postingEventsListService = postingEventsListService;
		this.postingsRepository = postingsRepository;
	}

	@Transactional
	public void fetchAndUpdatePostingsFor(final long userId) {
		final Long lastPostingEventId = postingsRepository.getLastPostingEventIdFor(userId);
		final List<? extends UserPostingEvent> newPostingEvents = postingEventsListService.listNewestEventsForUser(userId, lastPostingEventId);

		newPostingEvents.stream().forEach(event -> event.getPostingEventType().onEventType(
				() -> createNewPostingOf(event)
		));

		if(newPostingEvents.size() > 0) {
			long lastNewlyFetchedPostingId = newPostingEvents.get(newPostingEvents.size() - 1).getPostingEventId();
			postingsRepository.setLastPostingEventIdFor(userId, lastNewlyFetchedPostingId);
		}
	}

	private void createNewPostingOf(final UserPostingEvent event) {
		final Posting posting = new Posting.Builder()
				.withUserId(event.getUserId())
				.withLastClientEventId(event.getClientEventId())
				.withLastPostingEventId(event.getPostingEventId())
				.withUpdatedAt(event.getCreatedAt())
				.withPostingText(event.getPostingText())
				.build();

		postingsRepository.savePosting(posting);
	}
}
