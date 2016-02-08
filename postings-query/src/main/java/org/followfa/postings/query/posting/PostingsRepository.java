package org.followfa.postings.query.posting;

import java.util.List;

interface PostingsRepository {
	List<Posting> listPostingsForUser(long userId, int maxResults);

	Long getLastPostingEventIdFor(long userId);

	void savePosting(Posting posting);

	void setLastPostingEventIdFor(long userId, long lastPostingEventId);
}
