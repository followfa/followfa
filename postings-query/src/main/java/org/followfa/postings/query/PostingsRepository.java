package org.followfa.postings.query;

import java.util.List;

public interface PostingsRepository {
	List<Posting> listPostingsForUser(long userId, long maxResults);

	long getLastPostingEventIdFor(long userId);

	void createPosting(Posting posting);

	void setLastPostingEventIdFor(long userId, long lastPostingEventId);
}
