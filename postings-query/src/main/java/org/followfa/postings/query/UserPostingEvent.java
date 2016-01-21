package org.followfa.postings.query;

import java.sql.Timestamp;

public interface UserPostingEvent {
	Long getPostingEventId();
	Long getPostingId();
	long getUserId();
	Timestamp getCreatedAt();
	String getClientEventId();
	EventType getPostingEventType();
	String getPostingText();
}
