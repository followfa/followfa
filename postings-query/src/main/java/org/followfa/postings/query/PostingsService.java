package org.followfa.postings.query;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
class PostingsService implements QueryPostingsService {
	@Override
	public List<Posting> listPostingsFor(final long userId, final long maxResults) {
		return null;
	}
}
