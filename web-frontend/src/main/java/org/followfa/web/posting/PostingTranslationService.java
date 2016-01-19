package org.followfa.web.posting;

import java.util.stream.Stream;

public interface PostingTranslationService {
	void createNewPosting(long userId, String postingText);

	Stream<PostingViewModel> getNewestPostingsForCurrentUser(long userId, long maxResults);
}
