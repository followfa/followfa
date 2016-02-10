package org.followfa.postings.command.event;

import net.davidtanzer.jdefensive.Args;
import net.davidtanzer.jdefensive.Returns;
import org.followfa.postings.command.CreatePostingService;
import org.followfa.postings.query.event.PostingEventsListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;


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
	public List<PostingEvent> listNewestEventsForUser(final long userId, Long lastEventId) {
		final List<PostingEvent> postingEvents = postingEventRepository.getPostingEventsFor(userId, lastEventId);

		return Returns.notNull(postingEvents);
	}
}
