package org.followfa.postings.query;

import java.util.List;

public interface QueryPostingsService {
	List<Posting> listPostingsFor(long userId, long maxResults);
}
