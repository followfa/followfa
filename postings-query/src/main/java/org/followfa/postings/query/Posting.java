package org.followfa.postings.query;

import net.davidtanzer.auto_tostring.values_info.ByClassValueInfo;
import net.davidtanzer.jobjectformatter.ObjectFormatter;
import net.davidtanzer.jobjectformatter.annotations.Formatted;
import net.davidtanzer.jobjectformatter.annotations.FormattedInclude;
import net.davidtanzer.jobjectformatter.annotations.TransitiveInclude;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class Posting implements QueriedPosting {
	@Id
	@GeneratedValue
	private Long postingId;

	private long userId;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	private String lastClientEventId;
	private long lastPostingEventId;

	private String postingText;

	private Posting() {
		//Required for hibernate
	}

	private Posting(final Long postingId, final long userId, final Timestamp createdAt, final Timestamp updatedAt, final String lastClientEventId, final long lastPostingEventId, final String postingText) {
		this();

		this.postingId = postingId;
		this.userId = userId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.lastClientEventId = lastClientEventId;
		this.lastPostingEventId = lastPostingEventId;
		this.postingText = postingText;
	}

	public Long getPostingId() {
		return postingId;
	}

	public long getUserId() {
		return userId;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public String getLastClientEventId() {
		return lastClientEventId;
	}

	public long getLastPostingEventId() {
		return lastPostingEventId;
	}

	public String getPostingText() {
		return postingText;
	}

	@Override
	@Formatted(value = FormattedInclude.ALL_FIELDS, transitive = TransitiveInclude.ANNOTADED_FIELDS)
	public String toString() {
		return ObjectFormatter.format(this);
	}

	public static class Builder {
		private Long postingId = null;

		private long userId;
		private Timestamp createdAt = new Timestamp(System.currentTimeMillis());
		private Timestamp updatedAt;
		private String lastClientEventId;
		private long lastPostingEventId;
		private String postingText;

		public Builder() {
		}

		public Builder withUserId(final long userId ) {
			this.userId = userId;
			return this;
		}

		public Builder withUpdatedAt(final Timestamp updatedAt) {
			this.updatedAt = updatedAt;
			return this;
		}

		public Builder withLastClientEventId(final String lastClientEventId) {
			this.lastClientEventId = lastClientEventId;
			return this;
		}

		public Builder withLastPostingEventId(final long lastPostingEventId) {
			this.lastPostingEventId = lastPostingEventId;
			return this;
		}

		public Builder withPostingText(final String postingText) {
			this.postingText = postingText;
			return this;
		}

		public Posting build() {
			return new Posting(postingId, userId, createdAt, updatedAt, lastClientEventId, lastPostingEventId, postingText);
		}
	}
}
