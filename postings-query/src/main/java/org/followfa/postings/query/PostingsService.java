package org.followfa.postings.query;

import org.followfa.defensive.Args;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.followfa.defensive.ReturnValue.notNull;

@Service
class PostingsService implements QueryPostingsService {
	private final UpdatePostingsService updatePostingsService;
	private final PostingsRepository postingsRepository;

	@Autowired
	public PostingsService(final UpdatePostingsService updatePostingsService, final PostingsRepository postingsRepository) {
		Args.notNull(updatePostingsService, "updatePostingsService");
		Args.notNull(postingsRepository, "postingsRepository");

		this.updatePostingsService = updatePostingsService;
		this.postingsRepository = postingsRepository;
	}

	@Override
	public List<Posting> listPostingsForCurrentUser(final long userId, final long maxResults) {
		updatePostingsService.updatePostingsOfUser(userId);
		List<Posting> postings = postingsRepository.listPostingsForUser(userId, maxResults);

		return notNull(postings);
	}
}
