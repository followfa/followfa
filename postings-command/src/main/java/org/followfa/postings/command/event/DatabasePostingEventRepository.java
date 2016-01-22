package org.followfa.postings.command.event;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.followfa.defensive.ReturnValue.notNull;

@Repository
class DatabasePostingEventRepository implements PostingEventRepository {
	private EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	@Transactional
	public void addPostingEvent(final PostingEvent postingEvent) {
		entityManager.persist(postingEvent);
	}

	@Override
	public List<PostingEvent> getPostingEventsFor(final long userId, final Long lastPostingEventId) {
		List events = null;

		if(lastPostingEventId == null) {
			events = entityManager.createQuery("from PostingEvent pe where pe.userId = :userId order by pe.postingEventId asc")
					.setParameter("userId", userId)
					.getResultList();
		} else {
			events = entityManager.createQuery("from PostingEvent pe where pe.userId = :userId and pe.postingEventId > :lastPostingEventId " +
					"order by pe.postingEventId asc")
					.setParameter("userId", userId)
					.setParameter("lastPostingEventId", lastPostingEventId)
					.getResultList();
		}

		return notNull(events);
	}
}
