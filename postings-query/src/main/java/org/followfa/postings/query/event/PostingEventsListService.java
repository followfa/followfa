package org.followfa.postings.query.event;

import java.util.List;

public interface PostingEventsListService {
	List<? extends UserPostingEvent> listNewestEventsForUser(long userId, long lastEventId);
}
