package org.followfa.postings.query;

import java.util.List;

public interface PostingEventsListService {
	List<? extends UserPostingEvent> listNewestEventsForUser(long userId);
}
