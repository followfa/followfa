package org.followfa.web.posting;

import org.followfa.postings.command.CreatePostingService;
import org.followfa.postings.query.QueryPostingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.stream.Stream;

@Service
class TranslationService implements PostingTranslationService {
	private final CreatePostingService createPostingService;
	private final QueryPostingsService queryPostingsService;
	private final PostingTranslator postingTranslator;

	@Autowired
	TranslationService(final CreatePostingService createPostingService, final QueryPostingsService queryPostingsService, final PostingTranslator postingTranslator) {
		Assert.notNull(createPostingService, "createPostingService must not be null.");
		Assert.notNull(queryPostingsService, "queryPostingsService must not be null.");
		Assert.notNull(postingTranslator, "postingTranslator must not be null.");

		this.createPostingService = createPostingService;
		this.queryPostingsService = queryPostingsService;
		this.postingTranslator = postingTranslator;
	}

	@Override
	public void createNewPosting(final long userId, final String postingText) {
		createPostingService.createNewPosting(userId, postingText);
	}

	@Override
	public Stream<PostingViewModel> getNewestPostingsFor(final long userId, final long maxResults) {
		return queryPostingsService.listPostingsFor(userId, maxResults).stream().map(postingTranslator::translate);
	}
}
