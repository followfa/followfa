package org.followfa.postings.query.posting;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
class LastPostingEventIdByUser {
	@Id
	private long userId;
	private long lastPostingEventId;

	private LastPostingEventIdByUser() {
		//Required for hibernate
	}

	public LastPostingEventIdByUser(final long userId, final long lastPostingEventId) {
		this();

		this.userId = userId;
		this.lastPostingEventId = lastPostingEventId;
	}

	public long getUserId() {
		return userId;
	}

	public long getLastPostingEventId() {
		return lastPostingEventId;
	}
}
