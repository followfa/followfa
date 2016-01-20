package org.followfa.web.posting;

import org.followfa.postings.query.QueriedPosting;
import org.springframework.stereotype.Service;

@Service
class PostingTranslator {
	public PostingViewModel translate(final QueriedPosting queriedPosting) {
		return new PostingViewModel(queriedPosting.getPostingText());
	}
}
