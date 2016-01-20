package org.followfa.postings.query;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
class DatabasePostingsRepository implements PostingsRepository {
	@Override
	public List<Posting> listPostingsForUser(final long userId, final long maxResults) {
		return null;
	}
}
