package org.followfa.postings.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
class PostingsService implements QueryPostingsService {
	private final UpdatePostingsService updatePostingsService;
	private final PostingsRepository postingsRepository;

	@Autowired
	public PostingsService(final UpdatePostingsService updatePostingsService, final PostingsRepository postingsRepository) {
		Assert.notNull(updatePostingsService, "updatePostingsService must not be null.");
		Assert.notNull(postingsRepository, "postingsRepository must not be null.");

		this.updatePostingsService = updatePostingsService;
		this.postingsRepository = postingsRepository;
	}

	@Override
	public List<Posting> listPostingsForCurrentUser(final long userId, final long maxResults) {
		updatePostingsService.updatePostingsOfUser(userId);
		return postingsRepository.listPostingsForUser(userId, maxResults);
	}
}
