package org.followfa.postings.query;

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
		createdAt = new Timestamp(System.currentTimeMillis());
	}

	public Posting(final String postingText, final long userId) {
		this();

		this.postingText = postingText;
		this.userId = userId;
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

	public void setUpdatedAt(final Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getLastClientEventId() {
		return lastClientEventId;
	}

	public void setLastClientEventId(final String lastClientEventId) {
		this.lastClientEventId = lastClientEventId;
	}

	public long getLastPostingEventId() {
		return lastPostingEventId;
	}

	public void setLastPostingEventId(final long lastPostingEventId) {
		this.lastPostingEventId = lastPostingEventId;
	}

	public String getPostingText() {
		return postingText;
	}

	public void setPostingText(final String postingText) {
		this.postingText = postingText;
	}

	@Override
	@Formatted(value = FormattedInclude.ALL_FIELDS, transitive = TransitiveInclude.ANNOTADED_FIELDS)
	public String toString() {
		return ObjectFormatter.format(this);
	}
}
