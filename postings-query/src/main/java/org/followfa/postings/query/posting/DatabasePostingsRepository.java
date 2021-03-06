package org.followfa.postings.query.posting;

import net.davidtanzer.jdefensive.Returns;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
class DatabasePostingsRepository implements PostingsRepository {
	private EntityManager entityManager;

	@Override
	public List<Posting> listPostingsForUser(final long userId, final int maxResults) {
		List postings = entityManager.createQuery("from Posting p where p.userId = :userId order by p.postingId desc")
				.setParameter("userId", userId)
				.setMaxResults(maxResults)
				.getResultList();

		return Returns.notNull(postings);
	}

	@Override
	@Transactional
	public Long getLastPostingEventIdFor(final long userId) {
		LastPostingEventIdByUser lastPostingEvent = entityManager.find(LastPostingEventIdByUser.class, userId, LockModeType.PESSIMISTIC_WRITE);
		if(lastPostingEvent != null) {
			return lastPostingEvent.getLastPostingEventId();
		}
		return null;
	}

	@Override
	@Transactional
	public void savePosting(final Posting posting) {
		entityManager.merge(posting);
	}

	@Override
	@Transactional
	public void setLastPostingEventIdFor(final long userId, final long lastPostingEventId) {
		entityManager.merge(new LastPostingEventIdByUser(userId, lastPostingEventId));
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
