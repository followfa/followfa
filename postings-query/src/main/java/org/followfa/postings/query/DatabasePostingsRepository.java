package org.followfa.postings.query;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
class DatabasePostingsRepository implements PostingsRepository {
	@Override
	public List<Posting> listPostingsForUser(final long userId, final long maxResults) {
		return null;
	}

	@Override
	public long getLastPostingEventIdFor(final long userId) {
		return 0;
	}

	@Override
	public void createPosting(final Posting posting) {

	}

	@Override
	public void setLastPostingEventIdFor(final long userId, final long lastPostingEventId) {

	}
}
