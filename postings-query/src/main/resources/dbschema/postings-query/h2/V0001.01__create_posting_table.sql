CREATE TABLE Posting (
	postingId BIGINT PRIMARY KEY AUTO_INCREMENT,

	userId BIGINT,
	createdAt TIMESTAMP,
	updatedAt TIMESTAMP,
	lastClientEventId VARCHAR(32),
	lastPostingEventId BIGINT,

	postingText CLOB
);

CREATE TABLE LastPostingEventIdByUser (
	userId BIGINT PRIMARY KEY,
	lastPostingEventId BIGINT
)