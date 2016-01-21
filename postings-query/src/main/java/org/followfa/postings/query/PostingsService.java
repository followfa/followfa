package org.followfa.postings.query;

import org.followfa.cancellable.WaitForOperationService;
import org.followfa.defensive.Args;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.followfa.defensive.ReturnValue.notNull;

@Service
class PostingsService implements QueryPostingsService {
	private final UpdatePostingsService updatePostingsService;
	private final PostingsRepository postingsRepository;
	private final WaitForOperationService waitForOperationService;

	@Autowired
	public PostingsService(final UpdatePostingsService updatePostingsService, final PostingsRepository postingsRepository,
			final WaitForOperationService waitForOperationService) {
		Args.notNull(updatePostingsService, "updatePostingsService");
		Args.notNull(postingsRepository, "postingsRepository");
		Args.notNull(waitForOperationService, "waitForOperationService");

		this.updatePostingsService = updatePostingsService;
		this.postingsRepository = postingsRepository;
		this.waitForOperationService = waitForOperationService;
	}

	@Override
	public List<Posting> listPostingsForCurrentUser(final long userId, final long maxResults) {
		waitForOperationService.executeAndWait(() -> updatePostingsService.fetchAndUpdatePostingsFor(userId), 500L);

		List<Posting> postings = postingsRepository.listPostingsForUser(userId, maxResults);

		return notNull(postings);
	}
}
