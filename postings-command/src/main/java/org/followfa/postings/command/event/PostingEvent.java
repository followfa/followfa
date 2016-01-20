package org.followfa.postings.command.event;

import net.davidtanzer.jobjectformatter.ObjectFormatter;
import net.davidtanzer.jobjectformatter.annotations.*;
import org.followfa.postings.query.UserPostingEvent;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class PostingEvent implements UserPostingEvent {
	@Id
	@GeneratedValue
	@FormattedField(transitive = FormattedFieldType.DEFAULT)
	private Long postingEventId;
	@FormattedField(transitive = FormattedFieldType.DEFAULT)
	private Long postingId;

	private long userId;
	private Timestamp createdAt;
	private String clientEventId;
	@Formatted(transitive = TransitiveInclude.ALL_FIELDS)
	private PostingEventType postingEventType;

	private String postingText;

	private PostingEvent() {
		//Required for hibernate :(
	}

	public PostingEvent(final Long postingId, final long userId, final Timestamp createdAt, final String clientEventId, final PostingEventType postingEventType, final String postingText) {
		this();

		this.postingId = postingId;
		this.userId = userId;
		this.createdAt = createdAt;
		this.clientEventId = clientEventId;
		this.postingEventType = postingEventType;
		this.postingText = postingText;
	}

	public Long getPostingEventId() {
		return postingEventId;
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

	public String getClientEventId() {
		return clientEventId;
	}

	public PostingEventType getPostingEventType() {
		return postingEventType;
	}

	public String getPostingText() {
		return postingText;
	}

	@Override
	@Formatted(value = FormattedInclude.ALL_FIELDS, transitive = TransitiveInclude.ANNOTADED_FIELDS)
	public String toString() {
		return ObjectFormatter.format(this);
	}
}
