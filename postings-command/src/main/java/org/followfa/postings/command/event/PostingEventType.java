package org.followfa.postings.command.event;

import org.followfa.postings.query.event.EventType;

public enum PostingEventType implements EventType {
	CREATED {
		@Override
		public void onEventType(final Runnable onCreated) {
			onCreated.run();
		}
	}
}
