package org.followfa.postings.command;

import org.followfa.defensive.Args;
import org.followfa.postings.command.event.PostingEvent;
import org.followfa.postings.command.event.PostingEventRepository;
import org.followfa.postings.command.event.PostingEventType;
import org.followfa.postings.query.PostingEventsListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

import static org.followfa.defensive.ReturnValue.notNull;

@Service
class PostingEventService implements CreatePostingService, PostingEventsListService {
	private final PostingEventRepository postingEventRepository;

	@Autowired
	PostingEventService(final PostingEventRepository postingEventRepository) {
		Args.notNull(postingEventRepository, "postingEventRepository");

		this.postingEventRepository = postingEventRepository;
	}

	@Override
	public void createNewPosting(final long userId, final String postingText) {
		Args.notEmpty(postingText, "postingText");

		postingEventRepository.addPostingEvent(new PostingEvent(null, userId, new Timestamp(System.currentTimeMillis()), null, PostingEventType.CREATED, postingText));
	}

	@Override
	public List<PostingEvent> listNewestEventsForUser(final long userId) {
		final List<PostingEvent> postingEvents = null;

		return notNull(postingEvents);
	}
}
