package org.followfa.postings.command.event;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
}
