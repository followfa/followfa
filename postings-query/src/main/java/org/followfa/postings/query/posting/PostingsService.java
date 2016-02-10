package org.followfa.postings.query.posting;

import net.davidtanzer.jdefensive.Args;
import net.davidtanzer.jdefensive.Returns;
import org.followfa.cancellable.WaitForOperationService;
import org.followfa.postings.query.QueryPostingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
	public List<Posting> listPostingsForCurrentUser(final long userId, final int maxResults) {
		waitForOperationService.executeAndWait(() -> updatePostingsService.fetchAndUpdatePostingsFor(userId), 500L);

		List<Posting> postings = postingsRepository.listPostingsForUser(userId, maxResults);

		return Returns.notNull(postings);
	}
}
