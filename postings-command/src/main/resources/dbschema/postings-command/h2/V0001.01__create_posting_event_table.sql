CREATE TABLE PostingEvent (
	postingEventId BIGINT PRIMARY KEY AUTO_INCREMENT,
	postingId BIGINT,

	userId BIGINT,
	createdAt TIMESTAMP,
	clientEventId VARCHAR(32),
	postingEventType VARCHAR(32),

	postingText CLOB
);
