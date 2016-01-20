package org.followfa.postings.query;

import java.util.List;

public interface QueryPostingsService {
	List<? extends QueriedPosting> listPostingsForCurrentUser(long userId, long maxResults);
}
