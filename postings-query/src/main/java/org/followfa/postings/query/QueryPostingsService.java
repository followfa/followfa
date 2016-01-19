package org.followfa.postings.query;

import java.util.List;

public interface QueryPostingsService {
	List<Posting> listPostingsForCurrentUser(long userId, long maxResults);
}
