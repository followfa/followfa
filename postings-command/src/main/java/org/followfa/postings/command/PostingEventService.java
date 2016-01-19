package org.followfa.postings.command;

import org.followfa.postings.command.event.PostingEvent;
import org.followfa.postings.command.event.PostingEventRepository;
import org.followfa.postings.command.event.PostingEventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.sql.Timestamp;

@Service
class PostingEventService implements CreatePostingService {
	private final PostingEventRepository postingEventRepository;

	@Autowired
	PostingEventService(final PostingEventRepository postingEventRepository) {
		Assert.notNull(postingEventRepository, "Parameter postingEventRepository must not be null.");

		this.postingEventRepository = postingEventRepository;
	}

	@Override
	public void createNewPosting(final long userId, final String postingText) {
		Assert.hasLength(postingText);

		postingEventRepository.addPostingEvent(new PostingEvent(null, userId, new Timestamp(System.currentTimeMillis()), null, PostingEventType.CREATED, postingText));
	}
}