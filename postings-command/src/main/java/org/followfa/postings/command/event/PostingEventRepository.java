package org.followfa.postings.command.event;

import java.util.List;

interface PostingEventRepository {
	void addPostingEvent(PostingEvent postingEvent);

	List<PostingEvent> getPostingEventsFor(long userId, Long lastPostingEventId);
}
