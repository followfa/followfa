package org.followfa.postings.query;

import java.util.List;

public interface PostingsRepository {
	List<Posting> listPostingsForUser(long userId, long maxResults);
}
