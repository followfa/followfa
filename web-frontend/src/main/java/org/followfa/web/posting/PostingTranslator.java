package org.followfa.web.posting;

import org.followfa.postings.query.Posting;
import org.springframework.stereotype.Service;

@Service
class PostingTranslator {
	public PostingViewModel translate(final Posting posting) {
		return new PostingViewModel(posting.getPostingText());
	}
}
